package com.example.forev.mycodelibrary.view;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;

/**
 * 目前网上找不到YUV数据的获取，与预览方式，只能用Android原生的获取方式来模拟这种数据
 * 问题的关键在于，是如何对这些YUV数据进行展示
 *
 * study:
 * 相机与Surface之间的数据联系，及，为什么相机的数据展示在了Surfaceview之上了，其SurfaceView持有的
 * SurfaceHolder应该是一个很重要的桥梁。有时间可以研究一下源码，这种关系到底是什么关系，是什么环节被建立的。
 */
public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = CameraPreview.class.getSimpleName();
    private SurfaceHolder mHolder;
    private Camera mCamera;
    private int mFrameCount = 0;
    public CameraPreview(Context context) {
        super(context);
    }

    public CameraPreview(Context context, Camera camera) {
        super(context);
        this.mCamera = camera;
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    public void setCamera(Camera camera) {
        this.mCamera = camera;
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        try {
            mCamera.setPreviewDisplay(holder);
            mCamera.startPreview();
        } catch (IOException e) {
            Log.e(TAG, "设置相机预览失败： " + e.getMessage());
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        // 若需要旋转，更改大小或者重新设置，请确保已经停止预览
        if (mHolder.getSurface() == null) {
            return;
        }

        try {
            mCamera.stopPreview();
        } catch (Exception e) {
            Log.e(TAG, "surfaceChanged() e=" + e);
        }

        Camera.Parameters parameters = mCamera.getParameters();
        if (this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE) {
            mCamera.setDisplayOrientation(90);
        } else {
            mCamera.setDisplayOrientation(0);
        }

        try {
            mCamera.setPreviewDisplay(mHolder);
            mCamera.setPreviewCallback(mCameraPreviewCallback);
            mCamera.startPreview();
        } catch (Exception e) {
            Log.d(TAG, "开启相机预览失败： " + e);
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    private Camera.PreviewCallback mCameraPreviewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

            mFrameCount ++;
            Log.d(TAG, "onPreviewFrame: data.length=" + data.length + ", frameCount=" + mFrameCount);

            if (null != mPreviewCallback) {
                mPreviewCallback.onPreviewFrame(data, camera);
            }

//
//                if (null == frame) {
//                    ECHandlerHelper.postRunnOnUI(new Runnable() {
//                        @Override
//                        public void run() {
//                            callback.onScreenShot(null);
//                        }
//                    });
//                }
//                final int width = frame.getWidth();
//                final int height = frame.getHeight();
//
//                SurfaceView surfaceView = new SurfaceView(getContext());
//
//                final Bitmap bitmap = new NV21ToBitmap(getContext()).nv21ToBitmap(frame.getYuvBuffer(), width, height);
//
//
//                ECHandlerHelper.postRunnOnUI(new Runnable() {
//                    @Override
//                    public void run() {
//                        mTestYuvBitmap.setImageBitmap(bitmap);
////                                            callback.onScreenShot(bitmap);
//                    }
//                });
        }
    };

    private Camera.PreviewCallback mPreviewCallback;

    public void setmPreviewCallback(Camera.PreviewCallback mPreviewCallback) {
        this.mPreviewCallback = mPreviewCallback;
    }
}
