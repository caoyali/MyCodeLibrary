package com.example.forev.mycodelibrary;

import android.animation.ObjectAnimator;
import android.os.Build;
import android.support.animation.DynamicAnimation;
import android.support.animation.SpringAnimation;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.ScrollView;

import butterknife.BindView;

/**
 * 这是一个简单的具有阻力的吸顶界面控件！
 */
public class ViewGroupAnimatorActivity extends BaseActivity {
    @BindView(R.id.mRootView)
    ViewGroup mRootView;
    @BindView(R.id.mPartOne)
    ViewGroup mPartOne;
    @BindView(R.id.mPartTwo)
    ViewGroup mPartTwo;
    @BindView(R.id.mScrollView)
    ScrollView mScrollView;
    int mHeight;
    int mOldScrollY;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_group_animator;
    }

    @Override
    protected void initView() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
                @Override
                public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                    mHeight = mPartOne.getHeight();
                    int bound = mHeight / 2;
                    boolean isUp = oldScrollY - scrollY < 0;
                    {
                        //scrolly:scrollview滚动的高度
                        //tv_content.getHeight()内容1的高度
                        if(isUp){
                            if(scrollY > mHeight){
                                mPartOne.setVisibility(View.GONE);
                            }
                        }else {
                            if (View.GONE == mPartOne.getVisibility()){
                                if (0 == mPartTwo.getTop()){
                                    mPartOne.setVisibility(View.VISIBLE);
                                }

                            }
                        }
                    }
                }
            });
        }

    }
}
