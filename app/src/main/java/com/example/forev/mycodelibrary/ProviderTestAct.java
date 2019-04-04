package com.example.forev.mycodelibrary;

import android.view.View;

import butterknife.OnClick;

public class ProviderTestAct extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.act_test_provider;
    }

    @Override
    protected void initView() {
    }

    @OnClick({R.id.mUserConfig})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mUserConfig:
                openActivity(UserDocumentProviderTestAct.class);
                break;
        }
    }

}
