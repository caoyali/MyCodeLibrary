package com.example.forev.mycodelibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.forev.mycodelibrary.R;

/**
 * 本类主要是测试ConstraintLayout的使用方式。主要体现在XML页面。
 */
public class AppCompatActivitySub extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main2;
    }

    @Override
    protected void initView() {

    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, AppCompatActivitySub.class);
        activity.startActivity(intent);
    }
}
