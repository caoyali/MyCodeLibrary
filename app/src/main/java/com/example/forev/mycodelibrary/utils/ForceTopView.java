package com.example.forev.mycodelibrary.utils;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ForceTopView extends ConstraintLayout {



    public ForceTopView(Context context) {
        super(context);
    }

    public ForceTopView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ForceTopView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
