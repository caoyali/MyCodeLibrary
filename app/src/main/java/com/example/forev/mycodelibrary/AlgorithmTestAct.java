package com.example.forev.mycodelibrary;

import android.view.View;

import butterknife.OnClick;

public class AlgorithmTestAct extends BaseActivity {
    @Override
    protected int getLayoutId() {
        return R.layout.activity_algorithm_test;
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.mSortAlgorithm})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.mSortAlgorithm:
                openActivity(SortAlgorithmAct.class);
            break;
        }
    }
}
