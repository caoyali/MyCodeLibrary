package com.example.forev.mycodelibrary.utils;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * 总结，如果你想写一个可以滚动的视图，利用scrollTo这种方法的话，那么很不幸，
 * 这个方法只会导致当前viewGroup里面的子视图滚动，指的并不是自己本身滚动！
 * 就像一个固定的框，下面压着一个画了图片的布，你用scrollTo  或者 scrollBy
 * 只不过执行了一个类似于扯布的操作！这种操作引发这个框里的图片发生了位置上的变化。
 * 但是事实上框（ViewGroup）还是框，没什么根本的变化！一定不要和属性动画混淆，这两者完全两码事
 * 之前我就混淆了！
 * 另外注意的一点是，既然这个原理好比来回移动画布的话，那么很明显了，当进行scroll的时候，
 * 里面所有的子视图的位置，根本没有发生任何改变！要做的只不过是设置一下画布与屏幕的位置，然后重绘界面，即可！
 * 所以总结下来，scroll操作，没有改变任何关键视图的任何属性！
 */
public class MyScrollView extends FrameLayout {

    private static final String TAG = "MyScrollView";
    Paint mBackgroundPaint;
    GestureDetector mGestureDetector;
    Scroller mScroller;
    VelocityTracker mVelocityTracker;
    ValueAnimator scrollAnimator = ValueAnimator.ofFloat(0, 1);
    int currentY;

    public MyScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mBackgroundPaint = new Paint();
        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                scrollAnimator.cancel();
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
                mScroller.computeScrollOffset();
                logdivBig("onScroll");
                logd();
                //这个mScroller.getFinalX()所处的时机计算之前的，当startScroll之后，会重新算成新的值
                mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, (int) distanceY);
                logdivSmail("onScroll");
                logd();
                //经本人血与泪的趟坑经历，，背后的画布大小是无穷大的，，你要是scroll的太过分，可能整个布局都会呵呵呵！！全部溢出了天际。
//                scrollTo(mScroller.getFinalX(), mScroller.getFinalY());
                scrollTo(mScroller.getFinalY());
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                if (scrollAnimator.isRunning()){
                    return false;
                }
                    mScroller.computeScrollOffset();
                    logdivBig("onFling");
                    logd();
                    mScroller.fling(0, 0, 0, (int) -velocityY, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                    logdivSmail("onFling");
                    logd();
                    scrollAnimator.start();
                    return false;
            }
        });
        mScroller = new Scroller(getContext());
        //阻力值，这个相当重要！！如果你不想你的控件一下子滚到天边
        mScroller.setFriction(0.6f);
        mVelocityTracker = VelocityTracker.obtain();

        scrollAnimator = ValueAnimator.ofFloat(0.0f,1.0f);
        scrollAnimator.setDuration(300);
//        scrollAnimator.setInterpolator(new DecelerateInterpolator());
        scrollAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mScroller.computeScrollOffset();
                float per = animation.getAnimatedFraction();
                int finalY = mScroller.getFinalY();
                int lastY = mScroller.getCurrY();
                int dec = finalY - lastY;
                int realPorition = (int) (lastY + dec * per);
                logdivSmail("onAnimationUpdate");
                Log.d(TAG, "finalY=" + finalY);
                Log.d(TAG, "realPorition=" + realPorition);
                scrollTo(realPorition);
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isConsume = mGestureDetector.onTouchEvent(event);
        return isConsume;
    }

    @Override
    public void computeScroll() {
    }

    private void logdivBig(String s){
        Log.d(TAG, "==============================================================="+s);
    }

    private void logdivSmail(String s){
        Log.d(TAG, "------------------------------"+s);
    }
    private void logd(){
        Log.d(TAG, "mScroller.getStartY()=" + mScroller.getStartY());
        Log.d(TAG, "mScroller.getFinalY()=" + mScroller.getFinalY());
        Log.d(TAG, "mScroller.getCurrY()=" + mScroller.getCurrY());
    }

    View child;
    int height;
    int rootHeight;
    int dec;
    private void scrollTo(int Y){
        if (null == child){
            child = getChildAt(0);
            height = child.getHeight();
            rootHeight = getHeight();
            dec = height - rootHeight;
        }
        if(Y == 0){
            return;
        }
        //二者的高差！！！
        if (Y + dec <= 0){
            return;
        }
        if (Y > 0){
            return;
        }
        scrollTo(mScroller.getFinalX(), Y);
    }
}
