package com.example.forev.mycodelibrary;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.FloatEvaluator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
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
    @BindView(R.id.objectAnimator)
    ImageView mObjAnimator;

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

        //这里面的 -500 ~ 0 需要说明一下， 此处的开始处，针对的坐标是被操纵界面的左上角点。而位置是相对位置！！
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new FloatEvaluator(), -500, 0);
        valueAnimator.setDuration(2000);
        valueAnimator.setTarget(mObjAnimator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这个值是Animator 结合你开始设定的范围，及时间，来算出来的。
                float animatedValue = (float) animation.getAnimatedValue();
                mObjAnimator.setTranslationX(animatedValue);
            }
        });
        valueAnimator.start();
    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, AnimitionScaleAct.class);
        activity.startActivity(intent);
    }
}
