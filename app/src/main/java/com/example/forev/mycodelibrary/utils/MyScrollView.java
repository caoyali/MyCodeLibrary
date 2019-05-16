package com.example.forev.mycodelibrary.utils;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;
import android.widget.Toast;

import com.example.forev.mycodelibrary.R;

public class MyScrollView extends View {

    Paint mBackgroundPaint;
    GestureDetector mGestureDetector;
    Scroller mScroller;
    ValueAnimator mScrollAnimator;

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        mBackgroundPaint = new Paint();
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Toast.makeText(getContext(), "filing...", 500).show();
               int cx = mScroller.getCurrX();
               int cy = mScroller.getCurrY();
               //scroll是一个帮助计算的辅助类
                mScroller.fling(cx, cy,(int)velocityX / 3, (int)velocityY/3, 4, 8, 4, 8);
                postInvalidate();
                return true;
            }
        });
        mScroller = new Scroller(getContext(), null, true);
        mScrollAnimator = ValueAnimator.ofFloat(0, 1);
        mScrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                if (!mScroller.isFinished()){
                    mScroller.computeScrollOffset();
                } else {
                    mScrollAnimator.cancel();
//                    onScrollFinished();
                }
            }
        });

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int w = 1000;
        int h = 5000;
        setMeasuredDimension(w, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null == mBackgroundPaint.getShader()){
            int color1 = getContext().getResources().getColor(R.color.gray_5_FFD8D8D8);
            int color2 = getContext().getResources().getColor(R.color.red_3_FFFF3B30);
            int color3 = getContext().getResources().getColor(R.color.purple_0_FF9598FF);
            int color4 = getContext().getResources().getColor(R.color.aqms_level_5);
            int color5  = getContext().getResources().getColor(R.color.blue_0_FF007AFF);
            int[] colors = new int[]{color1, color2, color3, color4, color5};
            LinearGradient linearGradient = new LinearGradient(0, 0, getWidth(), getHeight(), colors, null, Shader.TileMode.MIRROR);
            mBackgroundPaint.setShader(linearGradient);
            mBackgroundPaint.setStyle(Paint.Style.FILL);
            mBackgroundPaint.setColor(color5);
        }

        Rect rect = new Rect(0, 0, getWidth(), getHeight());
        canvas.drawRect(rect, mBackgroundPaint);
        mScrollAnimator.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isConsume = mGestureDetector.onTouchEvent(event);
        return isConsume;
    }
}
