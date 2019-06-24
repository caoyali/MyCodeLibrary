package com.example.forev.mycodelibrary.fragment;

import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridLayout;

import com.example.forev.mycodelibrary.R;
import com.example.forev.mycodelibrary.simpleView.EasyTestButton;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class AnswerCardFra extends BaseFragment {
    public static final int TYPE_HTML = 0x01;
    public static final int YYPE_NATIVE = 0x02;
    int mSwitchStatus = 0;
    private EasyTestButton mDemo;
    private ViewGroup mRootView;
    private GridLayout mGridLayout;
    private Button mCommitBtn;
    private Button mShowPerson;
    TestOrderHelper mTestOrderHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.fra_answer_card;
    }

    @Override
    protected void initView(View rootView) {
        mDemo = rootView.findViewById(R.id.mDemo);
        mRootView = rootView.findViewById(R.id.mRootView);
        mGridLayout = rootView.findViewById(R.id.mGridLayout);
        mCommitBtn = rootView.findViewById(R.id.mCommitBtn);
        mShowPerson = rootView.findViewById(R.id.mShowPerson);
        showFraWithAnimation();
        mockData();
        mDemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.mDemo:
                        if ((mSwitchStatus ^ 0) == 0) {
                            mDemo.setNormal();
                            mDemo.setLeftText("哈哈哈哈哈哈哈哈");
                        } else if ((mSwitchStatus ^ 1) == 0) {
                            mDemo.setCorrect();
                            mDemo.setRightText("oh my gold!!!");
                        } else {
                            mDemo.setError();
                            mSwitchStatus = 0;
                            break;
                        }
                        mSwitchStatus++;
                        break;
                }
            }
        });

        mCommitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestOrderHelper.showAnswerStatus();
            }
        });

        mShowPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTestOrderHelper.showAnswerPersonStatus(new AnswerPersionStatus());
            }
        });
    }

    private void mockData() {
        mTestOrderHelper = new TestOrderHelper(new TestObject());
        TestObject testObject = new TestObject();
        for (int i = 0; i < testObject.mTests.size(); i++) {
            EasyTestButton button = new EasyTestButton(getContext());
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.setMarginStart(26 * 3);
            params.setMarginEnd(26 * 3);
            params.setMargins(26 * 3, 10 * 3, 26 * 3, 10 * 3);
            button.setLayoutParams(params);
            button.setLeftText(testObject.mTests.get(i));
            mTestOrderHelper.addButtons(button, i);
            mGridLayout.addView(button);
        }
    }

    private void showFraWithAnimation() {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRootView, "alpha", 0.0f, 0.5f, 1.0f);
        objectAnimator.setDuration(300);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mRootView, "translationY", 1080, 500, 0);
        objectAnimator1.setDuration(300);
        set.play(objectAnimator).with(objectAnimator1);
        set.start();
    }

    public void hideFraWithAnimation(AnimatorListenerAdapter listener) {
        AnimatorSet set = new AnimatorSet();
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(mRootView, "alpha", 1.0f, 0.5f, 0.0f);
        objectAnimator.setDuration(300);
        ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(mRootView, "translationY", 0, 500, 1080);
        objectAnimator1.setDuration(300);
        set.play(objectAnimator).with(objectAnimator1);
        set.addListener(listener);
        set.start();
    }


    private abstract class ITestObject{}

    private class TestObject{
        public List<String> mTests;
        public List<Integer> mAnswer;

        public TestObject() {
            mTests = new ArrayList<>();
            mTests.add("A");
            mTests.add("B");
            mTests.add("C");
            mTests.add("D");

            mAnswer = new ArrayList<>();
            mAnswer.add(1);
            mAnswer.add(2);
        }
    }

    private class AnswerPersionStatus{
        public List<Integer> mAnswerPerson;

        public AnswerPersionStatus() {
            mAnswerPerson = new ArrayList<>();
            mAnswerPerson.add(180);
            mAnswerPerson.add(50);
            mAnswerPerson.add(0);
            mAnswerPerson.add(20);
        }
    }

    /**
     * 根据
     * @param <ITestObject>
     */
    private static class TestOrderHelper<ITestObject>{
        private boolean isSingleSelect;
        private TestObject mTestObjectData;
        private List<EasyTestButton> mButtonViews;
        private List<Integer> mSelectItemIndexes = new ArrayList<>();

        private View.OnClickListener mButtonClickListner = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dealChangeClickButton((EasyTestButton) v);
            }
        };

        public TestOrderHelper(TestObject testObjectData) {
            this.mTestObjectData = testObjectData;
            setSelectType(false);
        }

        private void addButtons(EasyTestButton button, int index){
            if (null == button || index < 0){
                return;
            }
            if (null == mButtonViews){
                mButtonViews = new ArrayList<>();
            }
            button.setTag(index);
            mButtonViews.add(button);
            button.setOnClickListener(mButtonClickListner);
        }

        private void setSelectType(boolean isSingleSelect){
            this.isSingleSelect = isSingleSelect;
        }

        private void dealChangeClickButton(EasyTestButton button){
            //根据单选双选规则，以及button携带的index确定 mSelectItemIndex数据中的内容
            int index = (int) button.getTag();
            if (isSingleSelect){
                mSelectItemIndexes.clear();
                mSelectItemIndexes.add(index);
            } else {
                boolean isItemAlreadySelected = false;
                Iterator<Integer> integerIterator = mSelectItemIndexes.iterator();
                while (integerIterator.hasNext()){
                    int i = integerIterator.next();
                    if (index == i){
                        integerIterator.remove();
                        isItemAlreadySelected = true;
                        break;
                    }
                }
                if (!isItemAlreadySelected){
                    mSelectItemIndexes.add(index);
                }
            }
            refreshViews();
        }

        /**
         * 根据mSelectItemIndex设定界面，仅仅针对学生选择题阶段。
         */
        private void refreshViews(){
            if (null == mButtonViews){
                return;
            }

            for (int i = 0; i < mButtonViews.size(); i++){
                EasyTestButton currentButton = mButtonViews.get(i);
                boolean isSelected = false;
                int currentButtonRealIndex = (int) currentButton.getTag();
                for (int k = 0; k < mSelectItemIndexes.size(); k++){
                    if (currentButtonRealIndex == mSelectItemIndexes.get(k)){
                        isSelected = true;
                        break;
                    }
                }
                if (isSelected){
                    currentButton.setSelect();
                } else {
                    currentButton.setNormal();
                }
            }
        }

        public List<Integer> getSelectItemIndex(){return mSelectItemIndexes;}

        public void showAnswerStatus(){
            if (null == mTestObjectData || null == mButtonViews){
                return;
            }

            boolean isInCorrectAnswer;
            boolean isInMyAnswer;
            EasyTestButton currentEasyTestButton;
            List<Integer> correctAnswers = mTestObjectData.mAnswer;

            if (null == correctAnswers || correctAnswers.isEmpty()){
                //如果数据中没有答案，那么不做任何处理，因为这是一个投票题，没有对错之分
                return;
            }

            for (int i = 0; i < mButtonViews.size(); i++){
                isInCorrectAnswer = false;
                isInMyAnswer = false;
                currentEasyTestButton = mButtonViews.get(i);
                for (Integer integer : correctAnswers){
                    if (integer == i){
                        isInCorrectAnswer = true;
                        break;
                    }
                }

                for (Integer integer : mSelectItemIndexes){
                    if (integer == i){
                        isInMyAnswer = true;
                        break;
                    }
                }

                if (isInCorrectAnswer){
                    currentEasyTestButton.setCorrect();
                    continue;
                }

                if (isInMyAnswer){
                    currentEasyTestButton.setError();
                    continue;
                }
                currentEasyTestButton.setNormal();
            }
        }

        public void showAnswerPersonStatus(AnswerPersionStatus answerPersionStatus){
            if (null == answerPersionStatus || null == answerPersionStatus.mAnswerPerson
                    || null == mButtonViews){
                return;
            }

            for (int i = 0; i < mButtonViews.size(); i++){
                EasyTestButton button = mButtonViews.get(i);
                button.setRightText(button.getContext().getString(R.string.fra_answer_card_answer_person_num,
                        answerPersionStatus.mAnswerPerson.get(i)));
            }
        }
    }
}
