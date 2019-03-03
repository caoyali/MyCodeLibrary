package com.example.forev.mycodelibrary.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.forev.mycodelibrary.R;
import com.example.forev.mycodelibrary.animation.MyRotateAnimation;

public class AnimitionScaleAct extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_animition_scale;
    }

    @Override
    protected void initView() {
        ImageView imageView= (ImageView) findViewById(R.id.image);
        MyRotateAnimation animation = new MyRotateAnimation();
        imageView.startAnimation(animation);
    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, AnimitionScaleAct.class);
        activity.startActivity(intent);
    }
}
