package com.example.forev.mycodelibrary.utils;

import android.content.Context;
import android.content.res.TypedArray;
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
import android.view.WindowManager;
import android.widget.Scroller;

import com.example.forev.mycodelibrary.R;

public class ForceTopView extends ConstraintLayout {
    private static final String TAG = "ForceTopView";
    private static final int TOP_ID = R.id.force_view_top;
    private static final int BOTTOM_ID = R.id.force_view_bottom;
    private GestureDetector mGestureDetector;
    private SpringAnimation mTopYAnimation;
    private FlingAnimation mTopFlingAnimation;
//    private SpringAnimation mBottomYAnimation;
    private FlingAnimation mRootViewFling;
    private View mTopChild;
    private View mBottomChild;
    private Scroller mScroller;
    private int mLastXIntercepted;
    private int mLastYIntercepted;
    private boolean mIsRootViewInterceptMode = false;
    private boolean mIsChildRequestIntercept = false;
    private boolean mIsResetBottomHeight = false;
    private int mScreenHeight;


    public ForceTopView(Context context) {
        super(context, null, 0);
    }

    public ForceTopView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ForceTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
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
//                LogUtil.get().logBigDiv("before computeScrollOffset");
//                Log.d(TAG, "getFinalY = " + mScroller.getFinalY());
//                Log.d(TAG, "getCurrY = " + mScroller.getCurrY());
//                Log.d(TAG, "getStartY = " + mScroller.getStartY());
                int topViewHeight = mTopChild.getHeight();
                int bottomHeight = mBottomChild.getHeight();

                if (!isHeightMoreThanWindow()){
                    return false;
                }
                if (bottomHeight < mScreenHeight && !mIsResetBottomHeight){
                    resetBottomHeight();
                    mIsResetBottomHeight = true;
                }
                mScroller.computeScrollOffset();

//                LogUtil.get().logSmallDiv("after computeScrollOffset");
//                Log.d(TAG, "getFinalY = " + mScroller.getFinalY());
//                Log.d(TAG, "getCurrY = " + mScroller.getCurrY());
//                Log.d(TAG, "getStartY = " + mScroller.getStartY());
//                Log.d(TAG, "distanceY = " + distanceY);//distanceY这个数值在最开始的时候有非常严重的问题

                if(mIsRootViewInterceptMode){
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, 0);
                    mIsRootViewInterceptMode = false;
                    return false;
                }
                mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, - (int) distanceY);
                int finalTop = mScroller.getFinalY();

//                LogUtil.get().logSmallDiv("after scroll");
//                Log.d(TAG, "getFinalY = " + mScroller.getFinalY());
//                Log.d(TAG, "getCurrY = " + mScroller.getCurrY());
//                Log.d(TAG, "getStartY = " + mScroller.getStartY());
//                Log.d(TAG, "topViewHeight = " + topViewHeight);
//                Log.d(TAG, "distanceY = " + distanceY);
                //拉取的距离远
                if (finalTop < (-topViewHeight)) {
                    mScroller.setFinalY(-topViewHeight);
                    mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
//                        mScroller.setFinalY(-topViewHeight);
//                        setTranslationY(mScroller.getFinalY());
                    return false;
                }
                //向下拉
                if (distanceY < 0) {
                    if (finalTop >= 0) {
                        mScroller.setFinalY(0);
                        mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
//                            mScroller.setFinalY(0);
//                            setTranslationY(mScroller.getFinalY());
                        return false;
                    }
                }
                mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
//                setTranslationY(mScroller.getFinalY());
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                Log.d(TAG, "onFling" + "  velocityY=" + velocityY);
                if(!isHeightMoreThanWindow()){
                    return false;
                }
                if (Math.abs(velocityY) > Math.abs(velocityX)) {
//                    getRootViewFlingAnim().setStartVelocity(velocityY).start();
                    //minY与maxY指的是，scroll再怎么计算，finalY的位置必须位于这个区域内！！
                    mScroller.fling(mScroller.getFinalX(), mScroller.getFinalY(), 0, (int) velocityY / 5, 0, 0, -(mTopChild.getHeight()), 0);
                    int finalY = mScroller.getFinalY();
                    mTopYAnimation.animateToFinalPosition(finalY);
                }
                return false;
            }
        });
        mScroller = new Scroller(this.getContext());
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.ForceTopView, defStyleAttr, 0);
        int topId = typedArray.getResourceId(R.styleable.ForceTopView_layout_id_top, -1);
        int bottomId = typedArray.getResourceId(R.styleable.ForceTopView_layout_id_bottom, -1);
        addTopView(topId);
        addBottomView(bottomId);
        typedArray.recycle();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent");
        return mGestureDetector.onTouchEvent(event);
    }

    private void addTopView(int topId) {
        if (topId <= 0){
            return;
        }
        mTopChild = inflate(getContext(), topId, null);
        mTopChild.setId(TOP_ID);
        addView(mTopChild, 0);
    }

    private void addBottomView(int bottomId) {
        if (bottomId <= 0){
            return;
        }
        mBottomChild = inflate(getContext(), bottomId, null);
        mBottomChild.setId(BOTTOM_ID);
        addView(mBottomChild, 1);
        setOriginConstraintSet();
    }

    private void setOriginConstraintSet() {
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

    private void resetBottomHeight(){
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
        set.constrainHeight(BOTTOM_ID, mScreenHeight);
        set.constrainWidth(BOTTOM_ID, ConstraintSet.MATCH_CONSTRAINT);
        set.applyTo(this);
    }

    private void initAnimators() {
        mTopYAnimation = new SpringAnimation(getChildAt(0), DynamicAnimation.TRANSLATION_Y, 0);
        mTopYAnimation.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS);
//        mBottomYAnimation = new SpringAnimation(getChildAt(1), DynamicAnimation.TRANSLATION_Y, 0);
        mTopYAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
//                mBottomYAnimation.animateToFinalPosition(value);
                mBottomChild.setTranslationY(value);
            }
        });

        SpringForce topSpringForce = mTopYAnimation.getSpring();
        topSpringForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        topSpringForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
//        SpringForce bottomSpringForce = mBottomYAnimation.getSpring();
//        bottomSpringForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
//        bottomSpringForce.setDampingRatio(SpringForce.STIFFNESS_MEDIUM);

    }

    private FlingAnimation getTopAnimaton() {
        if (null == mTopFlingAnimation) {
            mTopFlingAnimation = new FlingAnimation(mTopChild, DynamicAnimation.TRANSLATION_Y);
            mTopFlingAnimation.setFriction(1.1f)
                    .setMinValue(-mTopChild.getHeight())
                    .setMaxValue(0);
        }
        return mTopFlingAnimation;
    }

    private FlingAnimation getRootViewFlingAnim() {
        if (null == mRootViewFling) {
            mRootViewFling = new FlingAnimation(ForceTopView.this, DynamicAnimation.TRANSLATION_Y);
            mRootViewFling.setFriction(1.1f)
                    .setMinValue(-mTopChild.getHeight())
                    .setMaxValue(0);
        }
        return mRootViewFling;
    }

    public void setChildRequestIntercept(boolean isIntercept){
        mIsChildRequestIntercept = isIntercept;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d(TAG, "onInterceptTouchEvent");
        if (!isHeightMoreThanWindow()){
            return false;
        }
        if (mIsChildRequestIntercept){
            mIsChildRequestIntercept = false;
            return true;
        }

        int x = (int) ev.getX();
        int y = (int) ev.getY();
        int deltaX = x - mLastXIntercepted;
        int deltaY = y - mLastYIntercepted;

        mLastXIntercepted = x;
        mLastYIntercepted = y;
        mScroller.computeScrollOffset();
        int currentY = mScroller.getFinalY();
        int topViewHeight = mTopChild.getHeight();
        Log.d(TAG, "finalY = " + currentY + " topViewHeight = " + topViewHeight);
        if (currentY > -topViewHeight && isTouchInView(mBottomChild, ev)) {
//            //此时要禁止bottom的scroll反应，但是允许它相应点击。 同时top的其余部位依然要有事件响应
            switch (ev.getAction()) {
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
        if (x > subVX && x < subVX + subWidth && y > subVY && y < subVY + subHeight) {
            return true;
        }
        return false;
    }

    private boolean isHeightMoreThanWindow(){
        int topChildHeight = mTopChild.getHeight();
        int bottomChildHeight = mBottomChild.getHeight();
        int totalHeight = topChildHeight + bottomChildHeight;
        return totalHeight > mScreenHeight;
    }

    //由于子view的多样性，直接拦截并不是一个很可取的方案
    public int dpToPx(int dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }
}
