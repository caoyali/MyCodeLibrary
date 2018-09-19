package com.example.forev.mycodelibrary;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

public abstract class BaseActivity extends Activity {

    protected abstract int getLayoutId();
    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
//        ButterKnife.bind(this);
        initView();
    }
}
