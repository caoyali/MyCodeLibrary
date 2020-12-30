package com.example.forev.mycodelibrary.view;

import android.content.Context;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.view.TextureView;

public class CameraPreviewTestureView extends TextureView {
    private static final String TAG = CameraPreviewTestureView.class.getSimpleName();
    private Camera mCamera;
    public CameraPreviewTestureView(Context context) {
        super(context);
    }

    public CameraPreviewTestureView(Context context, Camera camera) {
        super(context);
//        this.
    }

    public CameraPreviewTestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
