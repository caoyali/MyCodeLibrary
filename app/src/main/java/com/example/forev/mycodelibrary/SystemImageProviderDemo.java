package com.example.forev.mycodelibrary;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import butterknife.BindView;
import butterknife.OnClick;

public class SystemImageProviderDemo extends BaseActivity {
    private static final String TAG = "SystemImageProviderDemo";
    private static final int READ_REQUEST_CODE = 42;

    @BindView(R.id.imageView)
    ImageView mImageView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_system_image_plrovider;
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.mGoSysImage,  R.id.mCreateDocument})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mGoSysImage:
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                intent.setType("image/*");
                startActivityForResult(intent, READ_REQUEST_CODE);
                break;
            case R.id.mCreateDocument:
                Intent intent1 = new Intent(Intent.ACTION_CREATE_DOCUMENT);
                intent1.setType("*/*");
                intent1.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent1, READ_REQUEST_CODE);
                break;
        }
    }


    Uri uri = null;
    /**
     *
     * @param requestCode 刚才你打开内容提供程序输入的请求码，以确认你收回来的数据，的确是当前这行代码请求过去对应的结果。
     * @param resultCode 一种选没选择成功的标志
     * @param data 数据里面会含有一条uri
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (READ_REQUEST_CODE == requestCode) {
            if (Activity.RESULT_OK == resultCode) {
                if (null != data) {
                    uri = data.getData();
                    Log.i(TAG, "uri = " + uri);
                    //打印结果为 uri = content://com.android.providers.media.documents/document/image%3A8291
                    dumpImageMeteData(uri);
//                  AsyncTask就是一个给你处理好在主线程在子线程进行方法分类的工具类。不明白看源码去！
//                    我的笔记中有介绍 Interview2 这个本子里有
                    final MyAsyncTask myAsyncTask = new MyAsyncTask();
                    myAsyncTask.execute(uri);
                }
            } else if (Activity.RESULT_CANCELED == resultCode) {
                Log.i(TAG, "用户取消了选择!");
            }
        }
    }

    private void dumpImageMeteData(Uri uri){
        Cursor cursor = getContentResolver().query(uri, null, null,
                null, null, null);

        if (null != cursor && cursor.moveToFirst()){
            int displayIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            String displayName = cursor.getString(displayIndex);
            Log.i(TAG, "dumpImageMeteData()...displayName = " + displayName);
            int sizeColumnIndex = cursor.getColumnIndex(OpenableColumns.SIZE);
            if (!cursor.isNull(sizeColumnIndex)){
                String size = cursor.getString(sizeColumnIndex);
                Log.i(TAG, "dumpImageMeteData()...size = " + size);
            } else {
                Log.i(TAG, "dumpImageMeteData()...size = " + "得到的是空");
            }

            cursor.close();
        }
    }

    private Bitmap getBitMapFromUri(Uri uri) {
        try {
            ParcelFileDescriptor parcelFileDescriptor = getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Bitmap bitmap = BitmapFactory.decodeFileDescriptor(fileDescriptor);
            return bitmap;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /***
     * 获取图片的线程。这类数据量比较大，最好就是在子线程中进行处理
     */
    private class MyAsyncTask extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] objects) {

            try {
                Thread.sleep(5000);
                Uri uri = (Uri) objects[0];
                Bitmap bitmap = getBitMapFromUri(uri);
                return bitmap;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            Bitmap bitmap = (Bitmap) o;
            mImageView.setImageBitmap(bitmap);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Object[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onCancelled(Object o) {
            super.onCancelled(o);
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }
}
