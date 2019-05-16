package com.example.forev.mycodelibrary;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.FloatEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.UpdateAppearance;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.example.forev.mycodelibrary.animation.MyRotateAnimation;

import butterknife.BindView;
import butterknife.OnClick;

public class AnimitionScaleAct extends BaseActivity {
    @BindView(R.id.image)
    ImageView imageView;
    @BindView(R.id.hahaha)
    ImageView animatorXMLDemo;
    @BindView(R.id.tweenAnim)
    ImageView mTweenAnim;
    @BindView(R.id.objectAnimator)
    ImageView mObjAnimator;
    @BindView(R.id.objectAnimator1)
    ImageView mObjectAnomator1;
    @BindView(R.id.animatopnSet)
    ImageView mAnimatorSet;

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

        /**
         * 属性动画，本质上是几个强大的计算程序，可以根据自定义的插值器或者其他的算法，计算出目前的
         * 进展阶段，或者目前应该移动到什么地方，或者目前你的alpha应该计算出是多少。
         * 其次作用才是针对对象的操作，及调用对应的setValueName()之类的方法。
         * 至于你到底要采用什么展示的效果，这个得看你调用View API的技能了。这是View来做的！别想着
         * 一个基本的Animator连画都给你画好。（XML除外哈，估计有对应的类）。
         */

        /**
         * 另外一点可以看出，属性动画的解耦效果是杠杠的，计算与绘制分离！
         */

        //属性动画--原始类valueAnimator的使用方法。
        //具有多种初始化方式，可以不绑定target，也就是此处的ImageView， 其最主要的目的是计算动画的整个过程中，
        //每个阶段根据规则的出来的一个值。
        //次要作用是，你可以通过绑定ImageView来默认对这个view进行操作。当然你如果想非常灵活的用，可以通过回调
        //根据目前到了哪个过程，按照UI给定的效果，调用相应的Translation。
        //这里面的 -500 ~ 0 需要说明一下， 此处的开始处，针对的坐标是被操纵界面的左上角点。而位置是相对位置！！
        ValueAnimator valueAnimator = ValueAnimator.ofObject(new FloatEvaluator(), -500, 0);
        valueAnimator.setDuration(2000);
        valueAnimator.setTarget(mObjAnimator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //这个值是Animator 结合你开始设定的范围，及时间，来算出来的。
                float animatedValue = (float) animation.getAnimatedValue();
                //这个值好像是进行到几分之几的描述
                float per = animation.getAnimatedFraction();
                mObjAnimator.setTranslationX(animatedValue);
                mObjAnimator.setAlpha(per);
            }
        });
        valueAnimator.start();


        //valueAnimator子类--ObjectAnimator。
        //在父类的基础上，可以更便捷的调用相应view相应的事务。
        //其中控制的属性名称，如XXX，指的是你的被控制Object，必须有一个对应的setXXX方法,这样动画才会通过对应的
        //set方法，设置你这个Object的属性值。
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mObjectAnomator1, "scaleX",  1.0f, 0.8f, 0.6f, 0.0f, 0.5f, 1.0f, 2.0f);
        animator1.setRepeatCount(3);
        animator1.setDuration(1000);
        animator1.start();

        playAnimatorSet();

        mAnimatorSet.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (MotionEvent.ACTION_DOWN == event.getAction()){
                    if (set.isRunning()){
                        set.cancel();
                    }
                    set.start();
                }
                return false;
            }
        });
    }

    AnimatorSet set = new AnimatorSet();
    private void playAnimatorSet(){
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mAnimatorSet, "alpha", 0.0f, 0.5f, 1.0f);
        objectAnimator.setDuration(300);

        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mAnimatorSet, "scaleX", 1.0f, 0.8f, 0.6f, 0.0f, 0.5f, 1.0f);
        objectAnimator1.setDuration(300);

        ObjectAnimator objectAnimator3 = ObjectAnimator.ofFloat(mAnimatorSet, "scaleY", 1.0f, 0.8f, 0.6f, 0.0f, 0.5f, 1.0f);
        objectAnimator3.setDuration(300);
        set.play(objectAnimator).with(objectAnimator1).with(objectAnimator3);
        set.start();
    }

    @OnClick({R.id.animatopnSet, R.id.button2})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.button2:
                openActivity(ViewGroupAnimatorActivity.class);
                break;
        }
    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, AnimitionScaleAct.class);
        activity.startActivity(intent);
    }
}
