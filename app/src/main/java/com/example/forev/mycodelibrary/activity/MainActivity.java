package com.example.forev.mycodelibrary.activity;

import android.view.View;
import android.widget.TextView;

import com.example.forev.mycodelibrary.R;

import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    TextView mAnimationBtn;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        mAnimationBtn = findViewById(R.id.animation_btn);
    }

    @OnClick({R.id.animation_btn,
    R.id.view})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animation_btn:
                AnimitionScaleAct.openActivity(this);
                break;
            case R.id.view:
                ViewListActivity.openActivity(this);
                break;
        }
    }

}
