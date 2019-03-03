package com.example.forev.mycodelibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.forev.mycodelibrary.utils.LogUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends Activity {
    private Unbinder mUnbinder;
    protected abstract int getLayoutId();
    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.get().d(this + " onCreate()...");
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onStart() {
        LogUtil.get().d(this + " onStart()...");
        super.onStart();
    }

    @Override
    protected void onResume() {
        LogUtil.get().d(this + " onResume()...");

        super.onResume();
    }

    @Override
    protected void onPause() {
        LogUtil.get().d(this + " onPause()...");

        super.onPause();
    }

    @Override
    protected void onStop() {
        LogUtil.get().d(this + " onStop()...");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        LogUtil.get().d(this + " onDestroy()...");
        super.onDestroy();
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        LogUtil.get().d(this + " onNewIntent()...");
        super.onNewIntent(intent);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        LogUtil.get().d(this + " onRestoreInstanceState()...");
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        LogUtil.get().d(this + " onSaveInstanceState()...");
        super.onSaveInstanceState(outState);
    }
}
