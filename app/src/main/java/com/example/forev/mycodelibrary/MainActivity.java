package com.example.forev.mycodelibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    TextView mAnimationBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mAnimationBtn = findViewById(R.id.animation_btn);
        mAnimationBtn.setOnClickListener(this);
        findViewById(R.id.view).setOnClickListener(this);
    }


    @Override
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
