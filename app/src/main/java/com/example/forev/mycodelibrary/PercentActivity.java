package com.example.forev.mycodelibrary;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.forev.mycodelibrary.utils.ScreenUtils;

public class PercentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        float height = ScreenUtils.getScreenHeigh(this);
        float width = ScreenUtils.getScreenWidth(this);
        float realRadio = width/height;
        float standardRadio = (float) (16.0 / 9.0);
        Log.d("PercentActivity", "realRadio = " + realRadio + " standardRadio = " + standardRadio);
        boolean isMoreThan = realRadio > standardRadio;
        Log.d("PercentActivity", "height = " + height + " width = " + width);
        Log.d("PercentActivity", "isMoreThan = " + (isMoreThan ?"true" :"false"));
        if (isMoreThan){
            setContentView(R.layout.activity_percent_1);
        } else {
            setContentView(R.layout.activity_percent);
        }
    }
}
