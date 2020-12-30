package com.example.forev.mycodelibrary;

import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.forev.mycodelibrary.utils.NV21ToBitmap;
import com.example.forev.mycodelibrary.view.CameraPreview;

/**
 * 本类的目的是尝试着用surface view 去展示一个指定的YUV数据。鉴于网上根本没有直接的答案，只能通过别的
 * 方式去了解这块。
 * 以解决一个截屏，转换成的bitmap 有色块的问题。这个色块具体不知道是出在转换为bitmap的环节，
 * 还是本身底层给的YUV数据就有色块。
 */
public class SurfaceShowYUVActivity extends BaseActivity{
    private static final String TAG = SurfaceShowYUVActivity.class.getSimpleName();
    private Camera mCamera;
    private CameraPreview mPreview;
    private ImageView mPreFrame;
    private boolean isPreFrameHaveSrc = false;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_surface_show_yuv;
    }

    @Override
    protected void initView() {
        new InitCameraThread().start();
        mPreFrame = findViewById(R.id.mPreFrame);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != mCamera) {
            if (safeCameraOpen()) {
                mPreview.setCamera(mCamera);
            } else {
                Log.e(TAG, "无法操作camera");
            }
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseCamera();
    }

    private void releaseCamera() {
        if (null != mCamera) {
            mCamera.setPreviewCallback(null);
            mCamera.release();
            mCamera = null;
        }
    }
    private boolean safeCameraOpen() {
        boolean qOpened = false;
        try {
            releaseCamera();
            mCamera = Camera.open();
            qOpened = (null != mCamera);
        } catch (Exception e) {
            Log.e(TAG, "打开相机失败");
            e.printStackTrace();
        }
        return qOpened;
    }

    private class InitCameraThread extends Thread {
        @Override
        public void run() {
            super.run();
            if (safeCameraOpen()) {
                Log.d(TAG, "SurfaceShowYUVActivity 开启摄像头");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mPreview = new CameraPreview(SurfaceShowYUVActivity.this, mCamera);
                        mPreview.setmPreviewCallback(new Camera.PreviewCallback() {
                            @Override
                            public void onPreviewFrame(byte[] data, Camera camera) {
                                if (!isPreFrameHaveSrc) {
                                    // YUV -> Bitmap
                                    final int width = camera.getParameters().getPreviewSize().width;
                                    final int height = camera.getParameters().getPreviewSize().height;

                                    final Bitmap bitmap = new NV21ToBitmap(getApplication()).nv21ToBitmap(data, width, height);

                                    mPreFrame.postDelayed(new Runnable() {
                                        @Override
                                        public void run() {
                                                mPreFrame.setImageBitmap(bitmap);
                                        }
                                    }, 0);
                                    isPreFrameHaveSrc = true;
                                }
                            }
                        });
                        FrameLayout preview = findViewById(R.id.mCameraPreviewPannel);
                        preview.addView(mPreview);
                    }
                });
            }
        }
    }
}
