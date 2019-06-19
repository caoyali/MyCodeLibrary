package com.example.forev.mycodelibrary;



import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.example.forev.mycodelibrary.fragment.AnswerCardFra;

import butterknife.OnClick;

public class AnswerCardAct extends BaseActivity {
    AnswerCardFra answerCardFra;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_answer_card;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.mOpenBtn)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mOpenBtn:
                if (null == answerCardFra){
                    answerCardFra = new AnswerCardFra();
                    FragmentManager manager = getSupportFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.add(R.id.mAnswerCardPanel, answerCardFra)
                            .setCustomAnimations(R.anim.fra_slide_right_in, R.anim.fra_slide_left_out)
                            .addToBackStack("")
                            .commit();
                } else {
                    //做好销毁时崩溃处理，或者内存泄漏处理，此时为了节省时间不做处理
                    answerCardFra.hideFraWithAnimation(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            getSupportFragmentManager().beginTransaction()
                                    .remove(answerCardFra).commit();
                            answerCardFra = null;
                        }
                    });
                }

                break;
        }
    }
}
