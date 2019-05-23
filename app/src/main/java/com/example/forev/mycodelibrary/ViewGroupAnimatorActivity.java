package com.example.forev.mycodelibrary;


import android.view.View;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.forev.mycodelibrary.utils.ForceTopView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 这是一个简单的具有阻力的吸顶界面控件！
 */
public class ViewGroupAnimatorActivity extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.mRootView)
    ForceTopView mRootView;
    ListView listView;
    private boolean isReadyToScroll;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_view_group_animator;
    }

    @Override
    protected void initView() {
//        mRootView.findViewById(R.id.botton1).setOnClickListener(this);
//        mRootView.findViewById(R.id.botton2).setOnClickListener(this);
//        mRootView.findViewById(R.id.botton3).setOnClickListener(this);
        listView = findViewById(R.id.mListView);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            list.add("hahha"+ i);
        }
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,
               list );
        listView.setAdapter(arrayAdapter);

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

                //判断状态
                switch (scrollState) {
                    // 当不滚动时
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE:// 是当屏幕停止滚动时
//                        scrollFlag = false;
                        // 判断滚动到底部 、position是从0开始算起的
                        if (listView.getLastVisiblePosition() == (listView
                                .getCount() - 1)) {
                            //TODO

                        }
                        // 判断滚动到顶部
                        if (listView.getFirstVisiblePosition() == 0) {
//                            if(!isReadyToScroll){
//                                isReadyToScroll = true;
//                                return;
//                            }
//                            mRootView.moveTopViewIn();
                            mRootView.setChildRequestIntercept(true);
//                            isReadyToScroll = false;
                            //TODO
                        }

                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滚动时
//                        scrollFlag = true;
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING:
                        // 当用户由于之前划动屏幕并抬起手指，屏幕产生惯性滑动时，即滚动时
//                        scrollFlag = true;
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

//                //当滑动时
//                if (scrollFlag) {
//                    if (firstVisibleItem < lastVisibleItemPosition) {
//                        // 上滑
//                        //TODO
//                    } else if (firstVisibleItem > lastVisibleItemPosition) {
//                        // 下滑
//                        //TODO
//                    } else {
//                        return;
//                    }
//                    lastVisibleItemPosition = firstVisibleItem;//更新位置
//
//                }

            }
        });

    }

    public void onClick(View v){
        switch (v.getId()) {
//            case R.id.botton1:
//                Toast.makeText(this, "点击button1", 3000).show();
//                break;
//            case R.id.botton2:
//                Toast.makeText(this, "点击button2", 3000).show();
//                break;
//            case R.id.botton3:
//                Toast.makeText(this, "点击button3", 3000).show();
//                break;
        }

    }

}
