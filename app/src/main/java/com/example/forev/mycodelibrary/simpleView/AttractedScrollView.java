package com.example.forev.mycodelibrary.simpleView;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.support.animation.SpringForce;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.example.forev.mycodelibrary.R;


public class AttractedScrollView extends LinearLayout {

    private GestureDetector mGestureDetector;
    private SpringAnimation mTopYAnimation;
    private ConstraintLayout mTopCons;
    private ConstraintLayout mBottomCons;
    private Scroller mScroller;
    private boolean mIsRootViewInterceptMode = false;
    private int mScreenHeight;
    private LayoutInflater mInflater;

    public AttractedScrollView(Context context) {
        super(context, null, 0);
    }

    public AttractedScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AttractedScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
    }

    private void initView(Context context, AttributeSet attrs, int defStyleAttr) {
        mInflater = LayoutInflater.from(context);
        mInflater.inflate(R.layout.attracted_scroll_view, this);

        mTopCons = findViewById(R.id.mTopCons);
        mBottomCons = findViewById(R.id.mBottomCons);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        mScreenHeight = windowManager.getDefaultDisplay().getHeight();

        mScroller = new Scroller(this.getContext());
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
                resetBottomIfNeed();
                int topViewHeight = mTopCons.getHeight();
                mScroller.computeScrollOffset();
                if (mIsRootViewInterceptMode) {
                    mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, 0);
                    mIsRootViewInterceptMode = false;
                    return false;
                }
                mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, -(int) distanceY);

                int finalTop = mScroller.getFinalY();
                if (finalTop < (-topViewHeight)) {
                    mScroller.setFinalY(-topViewHeight);
                    animateTop();
                    return false;
                }
                if (distanceY < 0) {
                    if (finalTop >= 0) {
                        mScroller.setFinalY(0);
                        animateTop();
                        return false;
                    }
                }
                animateTop();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                resetBottomIfNeed();
                if (Math.abs(velocityY) > Math.abs(velocityX)) {
                    int oldY = mScroller.getFinalY();
                    mScroller.fling(mScroller.getFinalX(), mScroller.getFinalY(), 0, (int) velocityY / 5, 0, 0, -(mTopCons.getHeight()), 0);
                    animateTop();
                }
                return false;
            }
        });

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AttractedScrollView, defStyleAttr, 0);
        int topId = typedArray.getResourceId(R.styleable.AttractedScrollView_layout_id_top, -1);
        int bottomId = typedArray.getResourceId(R.styleable.AttractedScrollView_layout_id_bottom, -1);
        typedArray.recycle();
        addTopChildToTopCons(topId);
        addBottomChildToBottomCons(bottomId);
        initAnimators();
    }

    private void addTopChildToTopCons(int layoutId){
        View topV = inflate(getContext(), layoutId, null);
        topV.setId(R.id.attracted_scroll_view_top);
        int topVId = R.id.attracted_scroll_view_top;
        int parentId = R.id.mTopCons;
        ConstraintSet topSet = new ConstraintSet();
        topSet.connect(topVId, ConstraintSet.START, parentId, ConstraintSet.START);
        topSet.connect(topVId, ConstraintSet.END, parentId, ConstraintSet.END);
        topSet.connect(topVId, ConstraintSet.TOP, parentId, ConstraintSet.TOP);
        topSet.constrainWidth(topVId, ConstraintSet.MATCH_CONSTRAINT);
        topSet.constrainHeight(topVId, ConstraintSet.WRAP_CONTENT);
        mTopCons.addView(topV);
        topSet.applyTo(mTopCons);
    }

    private void addBottomChildToBottomCons(int layoutId){
        View bottomV = inflate(getContext(), layoutId, null);
        bottomV.setId(R.id.attracted_scroll_view_bottom);
        int bottomId = R.id.attracted_scroll_view_bottom;
        int parentId = R.id.mBottomCons;
        ConstraintSet bottomSet = new ConstraintSet();
        bottomSet.connect(bottomId, ConstraintSet.START, parentId, ConstraintSet.START);
        bottomSet.connect(bottomId, ConstraintSet.END, parentId, ConstraintSet.END);
        bottomSet.connect(bottomId, ConstraintSet.TOP, R.id.mTopCons, ConstraintSet.BOTTOM);
        bottomSet.constrainWidth(bottomId, ConstraintSet.MATCH_CONSTRAINT);
        bottomSet.constrainHeight(bottomId, ConstraintSet.WRAP_CONTENT);
        mBottomCons.addView(bottomV);
        bottomSet.applyTo(mBottomCons);
    }

    private void initAnimators() {
        mTopYAnimation = new SpringAnimation(mTopCons, DynamicAnimation.TRANSLATION_Y, 0);
        mTopYAnimation.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS);
        mTopYAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                mBottomCons.setTranslationY(value);
            }
        });

        SpringForce topSpringForce = mTopYAnimation.getSpring();
        topSpringForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        topSpringForce.setDampingRatio(SpringForce.DAMPING_RATIO_MEDIUM_BOUNCY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean isDeal = mGestureDetector.onTouchEvent(event);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof ViewGroup) {
                child.dispatchTouchEvent(event);
            } else {
                child.onTouchEvent(event);
            }
        }
        return isDeal;
    }

    private void animateTop() {
        mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
    }

    private boolean isNeedSetBottomHeight(){
        return mTopCons.getHeight() + mBottomCons.getHeight() <= mScreenHeight;
    }

    private void resetBottomHeight(){
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, mScreenHeight);
        mBottomCons.setLayoutParams(params);
    }

    private void resetBottomIfNeed(){
        if (isNeedSetBottomHeight()){
            resetBottomHeight();
        }
    }

    public void cancelAllAnimators() {
        mTopYAnimation.cancel();
    }
}
