package com.example.forev.mycodelibrary;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.view.View;

import com.example.forev.mycodelibrary.service.ServiceTest;

import butterknife.OnClick;

public class MainActivity extends BaseActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.animation_btn, R.id.view, R.id.mSelectFileBtn, R.id.mReflex, R.id.mPermissionRequest,
    R.id.mHookTest, R.id.mProviderTest, R.id.mServiceTest})
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
                Intent intent1 = new Intent(this, ServiceTest.class);
                startService(intent1);
                Intent intent2 = new Intent(this, ServiceTest.class);
                startService(intent2);
                Intent intent3 = new Intent(this, ServiceTest.class);
                startService(intent3);
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
