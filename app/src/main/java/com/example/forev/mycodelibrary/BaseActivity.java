package com.example.forev.mycodelibrary;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends FragmentActivity {
    private String TAG;
    private Unbinder mUnbinder;

    protected abstract int getLayoutId();
    protected abstract void initView();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        printInfo("onCreate()...");
        setContentView(getLayoutId());
        mUnbinder = ButterKnife.bind(this);
        initView();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        printInfo("onNewIntent()...");
    }

    @Override
    protected void onStart() {
        super.onStart();
        printInfo("onStart()...");
    }

    @Override
    protected void onResume() {
        super.onResume();
        printInfo("onResume()...");
    }

    @Override
    protected void onPause() {
        super.onPause();
        printInfo("onPause()...");
    }

    @Override
    protected void onStop() {
        super.onStop();
        printInfo("onStop()...");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        printInfo("onSaveInstanceState()...");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        printInfo("onRestoreInstanceState()...");
    }

    @Override
    protected void onDestroy() {
        mUnbinder.unbind();
        super.onDestroy();
        printInfo("onDestroy()...");
    }

    public void openActivity(Class activityClass) {
        Intent intent = new Intent(this, activityClass);
        this.startActivity(intent);
    }

    private void printInfo(String msg){
        if (null == TAG){
            TAG = this + getClass().getSimpleName();
        }
        Log.d(TAG, msg);
    }
}
