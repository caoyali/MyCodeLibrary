package com.example.forev.mycodelibrary.utils;

import android.content.Context;
import android.support.animation.DynamicAnimation;
import android.support.animation.FlingAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.example.forev.mycodelibrary.R;

public class ForceTopView1 extends ConstraintLayout {
    private static final String TAG = "ForceTopView";
    private static final int TOP_ID = R.id.force_view_top;
    private static final int BOTTOM_ID = R.id.force_view_bottom;
    private GestureDetector mGestureDetector;
    private FlingAnimation mRootViewAnim;
    private SpringAnimation mTopYAnimation;
    private SpringAnimation mBottomYAnimation;
    private View mTopChild;
    private View mBottomChild;
    private Scroller mScroller;
    private int mLastXIntercepted;
    private int mLastYIntercepted;
    private boolean mIsRootViewInterceptMode = false;

    private boolean bottomScrollToTop;

    public ForceTopView1(Context context) {
        super(context);
        initView();
    }

    public ForceTopView1(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public ForceTopView1(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
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
                mScroller.computeScrollOffset();
                mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, -(int)distanceY);
                int finalTop = mScroller.getFinalY();
                int topViewHeight = mTopChild.getHeight();
                Log.d(TAG, "finalTop = " + finalTop);
                Log.d(TAG, "topViewHeight = " + topViewHeight);
                Log.d(TAG, "distanceY = "+ distanceY);
                if (finalTop < (-topViewHeight)){
                    mScroller.setFinalY(-topViewHeight);
                    mTopYAnimation.start();
//                    mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
                    return false;
                }
                //向下拉
                if (distanceY < 0){
                    if (finalTop >= 0){
                        mScroller.setFinalY(0);
//                        mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
                        return false;
                    }
                }
                mTopYAnimation.start();
                mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
//                if (null == mRootViewAnim){
//                    initTopAnim();
//                }
////                mTopYAnimation.setStartVelocity(6000).start();
//                mRootViewAnim.start();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "onFling" +   "  velocityY=" + velocityY);
                if(Math.abs(velocityY) > Math.abs(velocityX)){
                    if (null == mRootViewAnim){
                        initTopAnim();
                    }
                    mRootViewAnim.setStartVelocity(velocityY);
                    mRootViewAnim.start();
                }
                return false;
            }
        });
        mScroller = new Scroller(this.getContext());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        return mGestureDetector.onTouchEvent(event);
    }

    //紫色
    public void addTopView(int topId){
        mTopChild = inflate(getContext(),topId, null );
        mTopChild.setId(TOP_ID);
        addView(mTopChild, 0);
    }

    //黄色
    public void addBottomView(int bottomId){
        mBottomChild = inflate(getContext(), bottomId, null);
        mBottomChild.setId(BOTTOM_ID);
        addView(mBottomChild, 1);
        setOriginConstraintSet();
    }

    private void setOriginConstraintSet(){
        ConstraintSet set = new ConstraintSet();
        int parentId = getId();
        set.connect(TOP_ID, ConstraintSet.START, parentId, ConstraintSet.START);
        set.connect(TOP_ID, ConstraintSet.END, parentId, ConstraintSet.END);
        set.connect(TOP_ID, ConstraintSet.TOP, parentId, ConstraintSet.TOP);
        set.constrainHeight(TOP_ID, ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(TOP_ID, ConstraintSet.MATCH_CONSTRAINT);

        set.connect(BOTTOM_ID, ConstraintSet.START, parentId, ConstraintSet.START);
        set.connect(BOTTOM_ID, ConstraintSet.END, parentId, ConstraintSet.END);
        set.connect(BOTTOM_ID, ConstraintSet.TOP, TOP_ID, ConstraintSet.BOTTOM);
        set.constrainHeight(BOTTOM_ID, ConstraintSet.WRAP_CONTENT);
        set.constrainWidth(BOTTOM_ID, ConstraintSet.MATCH_CONSTRAINT);
        set.applyTo(this);
        initAnimators();
    }

    private void initAnimators(){
        mTopYAnimation = new SpringAnimation(getChildAt(0), DynamicAnimation.TRANSLATION_Y, 0);
        mTopYAnimation.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS);
        mBottomYAnimation = new SpringAnimation(getChildAt(1), DynamicAnimation.TRANSLATION_Y, 0);
        mTopYAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                mBottomYAnimation.animateToFinalPosition(value);
//                mBottomChild.setTranslationY(value);
            }
        });
        SpringForce topSpringForce = mTopYAnimation.getSpring();
        topSpringForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        topSpringForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
        SpringForce bottomSpringForce = mBottomYAnimation.getSpring();
        bottomSpringForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        bottomSpringForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
    }

    private void initTopAnim(){
        mRootViewAnim = new FlingAnimation(this, DynamicAnimation.TRANSLATION_Y);
        mRootViewAnim.setMinValue(-mTopChild.getHeight());
        mRootViewAnim.setMaxValue(0);
        mRootViewAnim.setFriction(1.1f);
    }
    public void moveTopViewOut(){
        mScroller.setFinalY(-mTopChild.getHeight());
        mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
    }

    public void moveTopViewIn(){
        mScroller.setFinalY(0);
        mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
    }

    public void setBottomScrollToTop(boolean b){
        this.bottomScrollToTop = b;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent");
        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int deltaX = x - mLastXIntercepted;
        int deltaY = y - mLastYIntercepted;

        mLastXIntercepted = x;
        mLastYIntercepted = y;
        int topViewHeight = mTopChild.getHeight();
        mScroller.computeScrollOffset();
        int finalY = mScroller.getFinalY();
        Log.d(TAG, "finalY = " + finalY + " topViewHeight = " + topViewHeight);
        if (finalY > -topViewHeight && isTouchInView(mBottomChild, ev)){
//            //此时要禁止bottom的scroll反应，但是允许它相应点击。 同时top的其余部位依然要有事件响应
            switch (ev.getAction()){
                case MotionEvent.ACTION_MOVE:
                    mIsRootViewInterceptMode = Math.abs(deltaY) > Math.abs(deltaX);
                    return mIsRootViewInterceptMode;
            }
        }
        return super.onInterceptTouchEvent(ev);

    }

    //判断点击事件是否在当前view中
    private boolean isTouchInView(View view, MotionEvent event) {
        int x = (int) event.getRawX();
        int y = (int) event.getRawY();
        int[] local = new int[2];
        view.getLocationOnScreen(local);
        int subVX = local[0];
        int subVY = local[1];
        int subWidth = view.getWidth();
        int subHeight = view.getHeight();
        if(x > subVX && x < subVX + subWidth && y > subVY && y < subVY + subHeight) {
            return true;
        }
        return false;
    }

    //由于子view的多样性，直接拦截并不是一个很可取的方案
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
