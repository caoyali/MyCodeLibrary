package com.example.forev.mycodelibrary.simpleView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
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

    final static float PER_HEIGHT_ALPHA = 0.25f;
    final static float PER_HEIGHT_ATTRACTED = 0.17f;
    final static int FLING = 8;
    private GestureDetector mGestureDetector;
    private SpringAnimation mTopYAnimation;
    private ConstraintLayout mTopCons;
    private ConstraintLayout mBottomCons;
    private ConstraintLayout mCenterCons;
    private ConstraintLayout mCenterConsBg;
    private Scroller mScroller;
    private int mScreenHeight;
    private LayoutInflater mInflater;
    //表示bottom需要处理自己的滑动事件，父布局无需滑动。
    private static boolean isBottomRequestScroll = false;
    private static OnScrollListener sOnScrollListener;

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
        mCenterCons = findViewById(R.id.mCenterCons);
        mCenterConsBg = findViewById(R.id.mCenterBg);
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
                int startY = mScroller.getFinalY();
                if (isBottomRequestScroll && startY == -topViewHeight) {
                    isBottomRequestScroll = false;
                    return false;
                }
                mScroller.computeScrollOffset();
                mScroller.startScroll(mScroller.getFinalX(), mScroller.getFinalY(), 0, -(int) distanceY);

                int finalTop = mScroller.getFinalY();

                if (finalTop < (-topViewHeight)) {
                    mScroller.setFinalY(-topViewHeight);
                    if (mScroller.getFinalY() != startY){
                        callListener(distanceY > 0);
                    }
                    animateTopCons();
                    return false;
                }
                if (distanceY < 0) {
                    if (finalTop >= 0) {
                        mScroller.setFinalY(0);
                        if (mScroller.getFinalY() != startY){
                            callListener(distanceY > 0);
                        }
                        animateTopCons();
                        return false;
                    }
                }
                if (mScroller.getFinalY() != startY){
                    callListener(distanceY > 0);
                }
                animateTopCons();
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                resetBottomIfNeed();
                int startY = mScroller.getFinalY();
                if (isBottomRequestScroll && startY == -mTopCons.getHeight()) {
                    isBottomRequestScroll = false;
                    return false;
                }
                if (Math.abs(velocityY) > Math.abs(velocityX)) {
                    mScroller.fling(mScroller.getFinalX(), mScroller.getFinalY(), 0,
                            (int) velocityY / FLING, 0, 0, -(mTopCons.getHeight()), 0);
                    if (mScroller.getFinalY() != startY){
                        callListener(velocityY > 0);
                    }
                    animateTopCons();
                }
                return false;
            }
        });

        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AttractedScrollView, defStyleAttr, 0);
        int topId = typedArray.getResourceId(R.styleable.AttractedScrollView_layout_id_top, -1);
        int bottomId = typedArray.getResourceId(R.styleable.AttractedScrollView_layout_id_bottom, -1);
        int centerId = typedArray.getResourceId(R.styleable.AttractedScrollView_layout_id_center, -1);
        typedArray.recycle();
        addTopChildToTopCons(topId);
        addBottomChildToBottomCons(bottomId);
        addCenterChildToCenterCons(centerId);
        initAnimators();
    }

    private void callListener(boolean isUp){
        if (null != sOnScrollListener){
            sOnScrollListener.onScroll(isUp);
        }
    }

    private void addTopChildToTopCons(int layoutId) {
        if (layoutId <= 0) {
            return;
        }
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

    private void addBottomChildToBottomCons(int layoutId) {
        if (layoutId <= 0) {
            return;
        }
        View bottomV = inflate(getContext(), layoutId, null);
        bottomV.setId(R.id.attracted_scroll_view_bottom);
        int bottomId = R.id.attracted_scroll_view_bottom;
        int parentId = R.id.mBottomCons;
        ConstraintSet bottomSet = new ConstraintSet();
        bottomSet.connect(bottomId, ConstraintSet.START, parentId, ConstraintSet.START);
        bottomSet.connect(bottomId, ConstraintSet.END, parentId, ConstraintSet.END);
        bottomSet.connect(bottomId, ConstraintSet.TOP, parentId, ConstraintSet.TOP);
        bottomSet.constrainWidth(bottomId, ConstraintSet.MATCH_CONSTRAINT);
        bottomSet.constrainHeight(bottomId, ConstraintSet.WRAP_CONTENT);
        mBottomCons.addView(bottomV);
        bottomSet.applyTo(mBottomCons);
    }

    private void addCenterChildToCenterCons(int layoutId) {
        if (layoutId <= 0) {
            return;
        }
        View centerV = inflate(getContext(), layoutId, null);
        centerV.setId(R.id.attracted_scroll_view_center);
        int centerId = R.id.attracted_scroll_view_center;
        int parentId = R.id.mCenterBg;
        ConstraintSet centerSet = new ConstraintSet();
        centerSet.connect(centerId, ConstraintSet.START, parentId, ConstraintSet.START);
        centerSet.connect(centerId, ConstraintSet.END, parentId, ConstraintSet.END);
        centerSet.connect(centerId, ConstraintSet.TOP, parentId, ConstraintSet.TOP);
        centerSet.constrainWidth(centerId, ConstraintSet.MATCH_CONSTRAINT);
        centerSet.constrainHeight(centerId, ConstraintSet.WRAP_CONTENT);
        mCenterCons.addView(centerV);
        mCenterConsBg.setAlpha(0.0f);
        centerSet.applyTo(mCenterCons);
    }

    private void initAnimators() {
        mTopYAnimation = new SpringAnimation(mTopCons, DynamicAnimation.TRANSLATION_Y, 0);
        mTopYAnimation.setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_PIXELS);
        mTopYAnimation.addUpdateListener(new DynamicAnimation.OnAnimationUpdateListener() {
            @Override
            public void onAnimationUpdate(DynamicAnimation animation, float value, float velocity) {
                float height = mTopCons.getHeight();
                float scrollLength = Math.abs(value);
                mBottomCons.setTranslationY(value);
                mCenterCons.setTranslationY(value);
                //透明度变色临界区域
                if (scrollLength <= height && scrollLength > height * (1.0f - PER_HEIGHT_ALPHA)) {
                    float alphaHeight = height * PER_HEIGHT_ALPHA;
                    float h = height - scrollLength;
                    float alpha = h / alphaHeight;
                    mTopCons.setAlpha(alpha);
                    mCenterConsBg.setAlpha(1 - alpha);
                } else {
                    if (mCenterConsBg.getAlpha() != 1.0f) {
                        mTopCons.setAlpha(1.0f);
                    }
                    if (scrollLength <= height * (1.0f - PER_HEIGHT_ALPHA)
                            && mCenterConsBg.getAlpha() > 0) {
                        mCenterConsBg.setAlpha(0.0f);
                    }
                }
            }
        });

        SpringForce topSpringForce = mTopYAnimation.getSpring();
        topSpringForce.setStiffness(SpringForce.STIFFNESS_MEDIUM);
        topSpringForce.setDampingRatio(SpringForce.DAMPING_RATIO_NO_BOUNCY);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    //临时设置当前控件是否因某一子控件的特殊情况，而打断scroll动作，不进行滑动。
    public static void isChildRequestScroll(boolean isScroll) {
        isBottomRequestScroll = isScroll;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int startY = mScroller.getFinalY();
        int topViewHeight = mTopCons.getHeight();
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        viewGroup.dispatchTouchEvent(event);
        boolean isDeal = mGestureDetector.onTouchEvent(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int y = mScroller.getFinalY();
                if (Math.abs(y) > topViewHeight * (1.0 - PER_HEIGHT_ATTRACTED)) {
                    mScroller.setFinalY((int) -topViewHeight);
                    animateTopCons();
                    return false;
                }
                break;
        }
        return isDeal;
    }

    private boolean isInViewArea(View view, float x, float y) {
        Rect r = new Rect();
        view.getLocalVisibleRect(r);
        if (x > r.left && x < r.right && y > r.top && y < r.bottom) {
            return true;
        }
        return false;
    }

    private void animateTopCons() {
        mTopYAnimation.animateToFinalPosition(mScroller.getFinalY());
    }

    private boolean isNeedSetBottomHeight() {
        return mTopCons.getHeight() + mBottomCons.getHeight() <= mScreenHeight;
    }

    private void resetBottomHeight() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                mScreenHeight);
        mBottomCons.setLayoutParams(params);
    }

    private void resetBottomIfNeed() {
        if (isNeedSetBottomHeight()) {
            resetBottomHeight();
        }
    }

    public void cancelAllAnimators() {
        mTopYAnimation.cancel();
    }

    public static void setOnScrollListener(OnScrollListener listener){
        sOnScrollListener = listener;
    }

    public interface OnScrollListener{
        void onScroll(boolean isUp);
    }
}
