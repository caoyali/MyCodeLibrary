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
public class DrawableDemoAct extends BaseActivity {
    @BindView(R.id.mPointPictureImg)
    ImageView mPointPictureImg;
    @BindView(R.id.mTransDemo)
    ImageView mTransDemo;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_drawable_demo;
    }

    @Override
    protected void initView() {
        Drawable drawable = mPointPictureImg.getDrawable();
        TransitionDrawable transitionDrawable = (TransitionDrawable) mTransDemo.getDrawable();
        transitionDrawable.startTransition(500);
    }

    @Override
    public boolean onCreatePanelMenu(int featureId, Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_demo, menu);
        return true;
    }

    @SuppressLint("WrongConstant")
    public void onClickEnum1(MenuItem item){
        Toast.makeText(this, "点击了menu1", 3000).show();
    }

}
