package com.example.forev.mycodelibrary;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

import com.example.forev.mycodelibrary.utils.DialogFragmentUtils;

import butterknife.OnClick;

public class ViewListActivity extends BaseActivity {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_list;
    }

    @Override
    protected void initView() {
    }

    public static void openActivity(Activity activity) {
        Intent intent = new Intent(activity, ViewListActivity.class);
        activity.startActivity(intent);
    }

    @OnClick({R.id.loading_dialog, R.id.constriant_layout, R.id.mOpenNowAct, R.id.mCustomViewDemo,
    R.id.mAnswerCardTest, R.id.mPercent, R.id.mSurfaceView, R.id.motion_layout, R.id.mSurfaceShowYUV})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.constriant_layout:
                AppCompatActivitySub.openActivity(this);
                break;
            case R.id.loading_dialog:
                DialogFragmentUtils.showCommonLoadingDialog(getApplicationContext(),
                        getFragmentManager(), "hahah");
                break;
            case R.id.mOpenNowAct:
                openActivity(this);
                break;
            case R.id.mCustomViewDemo:
                openActivity(MyScrollViewActivity.class);
                break;
            case R.id.mAnswerCardTest:
                openActivity(AnswerCardAct.class);
                break;
            case R.id.mPercent:
                openActivity(PercentActivity.class);
                break;
            case R.id.mSurfaceView:
                openActivity(SurfaceViewActivity.class);
                break;
            case R.id.motion_layout:
                openActivity(MotionLayoutAct.class);
                break;
            case R.id.mSurfaceShowYUV:
                openActivity(SurfaceShowYUVActivity.class);
                break;
        }
    }
}
