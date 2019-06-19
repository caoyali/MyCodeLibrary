package com.example.forev.mycodelibrary.simpleView;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.forev.mycodelibrary.R;

public class EasyTestButton extends FrameLayout {
    private TextView mLeftTextView;
    private TextView mRightTextView;
    private ViewGroup mRootViewBgPanel;
    private ImageView mAnswerResultImg;

    public EasyTestButton(@NonNull Context context) {
        super(context);
        initView();
    }

    public EasyTestButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public EasyTestButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView(){
        View v = View.inflate(getContext(), R.layout.button_easy_test, null);
        mLeftTextView = v.findViewById(R.id.mLeftTextView);
        mRightTextView = v.findViewById(R.id.mRightTextView);
        mRootViewBgPanel = v.findViewById(R.id.mRootViewBgPanel);
        mAnswerResultImg = v.findViewById(R.id.mAnswerResultImg);
        addView(v);
    }

    private void setCorrectColor(){
        mLeftTextView.setBackground(getResources().getDrawable(R.drawable.bg_easy_text_button_correct));
        mRootViewBgPanel.setBackground(getResources().getDrawable(R.drawable.bg_easy_text_button_correct));
        mLeftTextView.setTextColor(getResources().getColor(R.color.easy_text_button_correct_bound));
        mRightTextView.setTextColor(getResources().getColor(R.color.easy_text_button_correct_bound));
    }

    private void setErrorColor(){
        mLeftTextView.setBackground(getResources().getDrawable(R.drawable.bg_easy_text_button_error));
        mRootViewBgPanel.setBackground(getResources().getDrawable(R.drawable.bg_easy_text_button_error));
        mLeftTextView.setTextColor(getResources().getColor(R.color.easy_text_button_error_bound));
        mRightTextView.setTextColor(getResources().getColor(R.color.easy_text_button_error_bound));
    }

    private void setNormalColor(){
        mLeftTextView.setBackground(getResources().getDrawable(R.drawable.bg_easy_text_button_normal));
        mRootViewBgPanel.setBackground(getResources().getDrawable(R.drawable.bg_easy_text_button_normal));
        mLeftTextView.setTextColor(getResources().getColor(R.color.easy_text_button_normal_bound));
        mRightTextView.setTextColor(getResources().getColor(R.color.easy_text_button_normal_bound));
    }

    public void setSelect(){
        setCorrectColor();
        mAnswerResultImg.setVisibility(GONE);
    }

    public void setCorrect(){
        setCorrectColor();
        mAnswerResultImg.setVisibility(VISIBLE);
        mAnswerResultImg.setImageResource(R.drawable.answer_icon_correct);
    }

    public void setError(){
        setErrorColor();
        mAnswerResultImg.setVisibility(VISIBLE);
        mAnswerResultImg.setImageResource(R.drawable.answer_icon_wrong);
    }

    public void setNormal(){
        setNormalColor();
        mAnswerResultImg.setVisibility(GONE);
    }

    /**
     * 具体针对的情况是设置答对正确人数
     * @param s
     */
    public void setRightText(String s){
        if(null == s){
            mRightTextView.setText("");
            mRightTextView.setVisibility(GONE);
        } else {
            mRightTextView.setVisibility(VISIBLE);
            mRightTextView.setText(s);
            setCorrectColor();
        }
    }

    /**
     * 支持根据文案大小采取正确的背景，
     * 防止描线边界错乱
     * @param s
     */
    public void setLeftText(String s){
        if (null != s){
            mLeftTextView.setText(s);
        }
    }
}
