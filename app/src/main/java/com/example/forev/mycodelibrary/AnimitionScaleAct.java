package com.example.forev.mycodelibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.forev.mycodelibrary.animation.MyRotateAnimation;

public class AnimitionScaleAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animition_scale);
        ImageView imageView= (ImageView) findViewById(R.id.image);
        MyRotateAnimation animation = new MyRotateAnimation();
        imageView.startAnimation(animation);
    }
    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, AnimitionScaleAct.class);
        activity.startActivity(intent);
    }
}
