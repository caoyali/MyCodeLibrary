package com.example.forev.mycodelibrary;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.widget.TextView;

import com.example.forev.mycodelibrary.R;

import com.example.forev.mycodelibrary.service.ServiceTest;

import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    TextView mAnimationBtn;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    protected void initView() {
        mAnimationBtn = findViewById(R.id.animation_btn);
    }

    @OnClick({R.id.animation_btn, R.id.view, R.id.mSelectFileBtn, R.id.mReflex, R.id.mPermissionRequest,
    R.id.mHookTest, R.id.mProviderTest, R.id.mServiceTest, R.id.mAlgorithmTest,
    R.id.mSkill, R.id.mThread, R.id.mMemory})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.animation_btn:
                openActivity(AnimitionScaleAct.class);
                break;
            case R.id.view:
                openActivity(ViewListActivity.class);
                break;
            case R.id.mSelectFileBtn:
                openActivity(DrawableDemoAct.class);
                break;
            case R.id.mReflex:
                openActivity(ReflexDemoAct.class);
                break;
            case R.id.mPermissionRequest:
                requestPermission();
                break;
            case R.id.mHookTest:
                openActivity(HookTestAct.class);
                break;
            case R.id.mProviderTest:
                openActivity(ProviderTestAct.class);
                break;
            case R.id.mServiceTest:
                openActivity(ServiceTestAct.class);
                break;
            case R.id.mAlgorithmTest:
                openActivity(AlgorithmTestAct.class);
                break;
            case R.id.mSkill:
                openActivity(SkillActivity.class);
                break;
            case R.id.mThread:
                openActivity(ThreadActivity.class);
                break;
            case R.id.mMemory:
                openActivity(MemoryActivity.class);
                break;
        }
    }

    private void requestPermission() {
        boolean isGranted = (ActivityCompat.checkSelfPermission(
                this, "com.example.forev.mycodelibrary.YAYALI")
                == PackageManager.PERMISSION_GRANTED);
        if (isGranted){
            return;
        } else {
            ActivityCompat.requestPermissions(this, new String[]{"com.example.forev.mycodelibrary.YAYALI"}, 1);
        }
    }

}
