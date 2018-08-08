package com.example.forev.mycodelibrary.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by forev on 2018/8/8.
 * 实现一个从右向左缩，又从左到右还原的动画
 */

public class MyRotateAnimation extends Animation{
    Camera mCamera;
    float x;
    float y;
    public MyRotateAnimation() {}
    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        y = height;
        setDuration(3000l);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);
        //interpolatedTime 代表的是进度
        mCamera.save();
        if (interpolatedTime > 0.6) {
//            代表当前的进度来讲理应回到哪个角度
            mCamera.rotateY(225 * (1-interpolatedTime));
        }else {
            mCamera.rotateY(150 * interpolatedTime);
        }
        Matrix matrix = t.getMatrix();
        mCamera.getMatrix(matrix);
        matrix.preTranslate(x, - y/2);
        matrix.postTranslate(x, y / 2);
        mCamera.restore();
    }
}
