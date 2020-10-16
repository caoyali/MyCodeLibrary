package com.example.forev.mycodelibrary.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * a custom view with bob animation when user click it!
 */
public class BobAnimationView extends View {

    boolean isInited = false;

    private int maxD = 15;
    private int minD = 3;

    private ValueAnimator mValueAnimator;
    private Paint mPaint;


    int[] coclors = new int[] {
            Color.parseColor("#FFE83F"),
            Color.parseColor("#FCC01F"),
            Color.parseColor("#FB619B")
    };

    private enum Shape{
        CIRCLE(0),
        SQUARE(1),
        TRIANDLE(2);

        int value;

        Shape(int value) {
            this.value = value;
        }
    }

    private List<Ball> mBalls = new ArrayList<>();

    private class Ball {
        public int shape;
        public int color;
        public float x;
        public float y;
        public float r;
        public float vX;
        public float vY;
        public float aX;
        public float aY;
    }

    public BobAnimationView(Context context) {
        super(context);
    }

    public BobAnimationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BobAnimationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    private void initBall() {
        mPaint = new Paint();
        float d = 0;
        int dRound = maxD - minD;
        Random r = new Random();
        float centerX;
        float centerY;
        int width = getWidth();
        int height = getHeight();
        float bigCenterX = width / 2;
        float bigCenterY = height / 2;

        for (int i = 0; i < 100; i++) {
            Ball ball = new Ball();
            ball.color = coclors[r.nextInt(3)];
            ball.shape = r.nextInt(3);
            ball.r = r.nextInt(dRound) + minD;
            ball.x = r.nextInt(width);
            ball.y = r.nextInt(height);

            centerX = ball.x + d / 2.0f;
            centerY = ball.y + d / 2.0f;

            if (centerX - bigCenterX >= 0) {
                ball.aX = 2;
                ball.vX = 1;
            } else {
                ball.aX = -2;
                ball.vX = -1;
            }
            //速度（）

            if (centerY - bigCenterY >= 0) {
                ball.aY = 2 * ((centerX - bigCenterX) / (centerY - bigCenterY));
                ball.vY = 1;
            } else {
                ball.aY = -2 * ((centerX - bigCenterX) / (centerY - bigCenterY));
                ball.vY = -1;
            }

            mBalls.add(ball);
        }

        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setRepeatCount(-1);
        mValueAnimator.setDuration(40000);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                updateBall();
                invalidate();
            }
        });
    }

    private float rangInt(int i, int j) {
        int max = Math.max(i, j);
        int min = Math.min(i, j);
        return (int) (min + Math.ceil(Math.random() * (max - min)));
    }


    private void updateBall() {
        //更新粒子的位置,自由落地运动
        for (Ball ball : mBalls) {
            ball.x += ball.vX;
            ball.y += ball.vY;

            ball.vX += ball.aX;
            ball.vY += ball.aY;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (!isInited) {
            initBall();
            isInited = true;
        }
//        canvas.translate(500, 500);
        for (Ball ball : mBalls) {
            mPaint.setColor(ball.color);
            if (ball.shape == Shape.CIRCLE.value) {
                canvas.drawCircle(ball.x, ball.y, ball.r, mPaint);
            } else if (ball.shape == Shape.SQUARE.value) {
                Rect rect = new Rect((int)ball.x, (int) ball.y, (int)(ball.x + 2 * ball.r), (int)(ball.y + 2 * ball.r));
                canvas.drawRect(rect, mPaint);
            } else if (ball.shape == Shape.TRIANDLE.value) {
                canvas.drawCircle(ball.x, ball.y, ball.r, mPaint);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            mValueAnimator.start();
        }
        return super.onTouchEvent(event);
    }
}
