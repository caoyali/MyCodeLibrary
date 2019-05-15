package com.example.forev.mycodelibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.View;

import butterknife.OnClick;

/**
 * 本类主要是测试ConstraintLayout的使用方式。主要体现在XML页面。
 */
public class AppCompatActivitySub extends BaseActivity {
    private ConstraintSet mConset1;
    private ConstraintSet mConset2;
    private ConstraintLayout mRoot;
    boolean isChanged = false;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initView() {
        mRoot = (ConstraintLayout) findViewById(R.id.rootView);

        mConset1 = new ConstraintSet();
        mConset2 = new ConstraintSet();

        mConset1.clone(mRoot);
        mConset2.clone(this, R.layout.activity_constraint_test_2);
    }

    @OnClick({R.id.rootView, R.id.mBtn, R.id.mBtn2})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rootView:
                if (isChanged){
                    TransitionManager.beginDelayedTransition(mRoot);
                    mConset1.applyTo(mRoot);
                    isChanged = false;
                } else {
                    AutoTransition transition = new AutoTransition();
                    transition.setDuration(300);
                    TransitionManager.beginDelayedTransition(mRoot,transition);
                    mConset2.applyTo(mRoot);
                    isChanged = true;
                }
                break;
            case R.id.mBtn:
                ViewListActivity.openActivity(this);
                break;
            case R.id.mBtn2:
                openActivity(ReflexDemoAct.class);
                break;
        }

    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, AppCompatActivitySub.class);
        activity.startActivity(intent);
    }
}
