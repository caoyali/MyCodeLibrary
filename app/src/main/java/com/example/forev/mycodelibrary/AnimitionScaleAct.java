package com.example.forev.mycodelibrary;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.forev.mycodelibrary.animation.MyRotateAnimation;

import butterknife.BindView;

public class AnimitionScaleAct extends BaseActivity {
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.hahaha)
    ImageView animatorXMLDemo;
    @BindView(R.id.tweenAnim)
    ImageView mTweenAnim;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_animition_scale;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyRotateAnimation animation = new MyRotateAnimation();
        imageView.startAnimation(animation);
        Animator animator = AnimatorInflater.loadAnimator(this, R.animator.animator_xml_demo);
        animator.setTarget(animatorXMLDemo);
        animator.start();

        Animation tweenAnim = AnimationUtils.loadAnimation(this, R.anim.anim_demo);
        mTweenAnim.startAnimation(tweenAnim);
    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, AnimitionScaleAct.class);
        activity.startActivity(intent);
    }
}
