package com.example.forev.mycodelibrary;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.forev.mycodelibrary.utils.DialogFragmentUtils;

public class ViewListActivity extends BaseActivity implements View.OnClickListener{

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_list;
    }

    @Override
    protected void initView() {
        TextView loadingDialog = (TextView) findViewById(R.id.loading_dialog);
        loadingDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragmentUtils.showCommonLoadingDialog(getApplicationContext(),
                        getFragmentManager(), "hahah");
            }
        });

        TextView constriantLayout = (TextView) findViewById(R.id.constriant_layout);
        constriantLayout.setOnClickListener(this);
    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, ViewListActivity.class);
        activity.startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.constriant_layout:
                AppCompatActivitySub.openActivity(this);
                break;
        }
    }
}
