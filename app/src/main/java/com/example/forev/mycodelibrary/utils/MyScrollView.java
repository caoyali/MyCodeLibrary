package com.example.forev.mycodelibrary.utils;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
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

    Paint mBackgroundPaint;
    GestureDetector mGestureDetector;
    Scroller mScroller;

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
                //动画完成了
                mScroller.startScroll(0, getScrollY(), 0, (int)distanceY);
                invalidate();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
        mScroller = new Scroller(getContext());

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
        if (mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
        }
    }
}
