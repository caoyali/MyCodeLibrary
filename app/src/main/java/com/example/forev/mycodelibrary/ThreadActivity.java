package com.example.forev.mycodelibrary;

import android.util.Log;
import android.view.View;

import butterknife.OnClick;

public class ThreadActivity extends BaseActivity {
    private static final String TAG = "ThreadActivity";
    volatile int  mA = 0;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_thread;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.mVoli})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mVoli:
                AddThread addThread = new AddThread();
                UnAddThread unAddThread = new UnAddThread();
                addThread.start();
                unAddThread.start();
                break;
        }
    }

    private class AddThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                mA++;
                Log.i(TAG, "加 mA = " + mA+"");
            }
        }
    }

    private class UnAddThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                mA--;
                Log.i(TAG, "减 mA = " + mA+"");
            }
        }
    }
}
