package com.example.forev.mycodelibrary.fragment;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.forev.mycodelibrary.R;
import com.example.forev.mycodelibrary.simpleView.EasyTestButton;

public class AnswerCardFra extends BaseFragment {
    public static final int TYPE_HTML = 0x01;
    public static final int YYPE_NATIVE = 0x02;
    int mSwitchStatus = 0;
    EasyTestButton mDemo;
    ViewGroup mRootView;
    GridLayout mGridLayout;
    Button mCommitBtn;

    @Override
    protected int getLayoutId() {
        return R.layout.fra_answer_card;
    }

    @Override
    protected void initView(View rootView) {
        mDemo = rootView.findViewById(R.id.mDemo);
        mRootView = rootView.findViewById(R.id.mRootView);
        mGridLayout = rootView.findViewById(R.id.mGridLayout);
        mCommitBtn = rootView.findViewById(R.id.mCommitBtn);
        showFraWithAnimation();

        for (int i = 0; i < 5; i++) {
            EasyTestButton button = new EasyTestButton(getContext());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMarginStart(26 * 3);
            params.setMarginEnd(26 * 3);
            params.setMargins(26*3, 10*3, 26*3, 10*3);
            button.setLayoutParams(params);
            button.setLeftText("懂");
            button.setRightText("170人");
            mGridLayout.addView(button);
        }

        mDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.mDemo:
                        if ((mSwitchStatus ^ 0) == 0) {
                            mDemo.setNormal();
                            mDemo.setLeftText("哈哈哈哈哈哈哈哈");
                        } else if ((mSwitchStatus ^ 1) == 0) {
                            mDemo.setCorrect();
                            mDemo.setRightText("oh my gold!!!");
                        } else {
                            mDemo.setError();
                            mSwitchStatus = 0;
                            break;
                        }
                        mSwitchStatus++;
                        break;
                }
            }
        });
    }

    private void showFraWithAnimation() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRootView, "alpha", 0.0f, 0.5f, 1.0f);
        objectAnimator.setDuration(300);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mRootView, "translationY", 1080, 500, 0);
        objectAnimator1.setDuration(300);
        set.play(objectAnimator).with(objectAnimator1);
        set.start();
    }

    public void hideFraWithAnimation(AnimatorListenerAdapter listener) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRootView, "alpha", 1.0f, 0.5f, 0.0f);
        objectAnimator.setDuration(300);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mRootView, "translationY", 0, 500, 1080);
        objectAnimator1.setDuration(300);
        set.play(objectAnimator).with(objectAnimator1);
        set.addListener(listener);
        set.start();
    }
}
