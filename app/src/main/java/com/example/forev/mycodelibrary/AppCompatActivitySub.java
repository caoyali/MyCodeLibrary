package com.example.forev.mycodelibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * 本类主要是测试ConstraintLayout的使用方式。主要体现在XML页面。
 */
public class AppCompatActivitySub extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, AppCompatActivitySub.class);
        activity.startActivity(intent);
    }
}
