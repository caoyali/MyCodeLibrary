package com.example.forev.mycodelibrary;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.BindView;

/**
 * 反射demo
 */
public class ReflexDemoAct extends BaseActivity {
    @BindView(R.id.mPointPictureImg)
    ImageView mPointPictureImg;
    @BindView(R.id.mTransDemo)
    ImageView mTransDemo;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_reflex_demo;
    }

    @Override
    protected void initView() {
    }
}
