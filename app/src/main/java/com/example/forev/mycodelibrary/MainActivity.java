package com.example.forev.mycodelibrary;
import android.os.Bundle;
import android.view.View;

import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.animation_btn, R.id.view, R.id.mSelectFileBtn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animation_btn:
                AnimitionScaleAct.openActivity(this);
                break;
            case R.id.view:
                ViewListActivity.openActivity(this);
                break;
            case R.id.mSelectFileBtn:
                openActivity(DrawableDemoAct.class);
                break;
        }
    }

}
