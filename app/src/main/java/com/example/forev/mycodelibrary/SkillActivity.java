package com.example.forev.mycodelibrary;

import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;

public class SkillActivity extends BaseActivity {
    @BindView(R.id.mLambda)
    Button mLambda;
    @Override
    protected int getLayoutId() {
        return R.layout.activity_skill;
    }

    @Override
    protected void initView() {
//        请将JDK升级至8以上
//        mLambda.setOnClickListener(
//                event -> Toast.makeText(this, getString(R.string.lambda), 3000).show());
    }
}
