package com.example.forev.mycodelibrary.com.example.forev.mycodelibrary.test;

import android.app.Activity;
import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import com.example.forev.mycodelibrary.MainActivity;
import com.example.forev.mycodelibrary.R;

public class MainActivityTest extends ActivityInstrumentationTestCase2 {
    private Activity mActivi;
    private TextView mAnimationBtn;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mActivi = getActivity();
        mAnimationBtn = mActivi.findViewById(R.id.animation_btn);
    }

    public void testAnimation(){
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        assertEquals("动画", mAnimationBtn.getText().toString());
//        mAnimationBtn.performLongClick();
    }
}
