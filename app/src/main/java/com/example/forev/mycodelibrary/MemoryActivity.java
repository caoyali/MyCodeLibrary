package com.example.forev.mycodelibrary;

import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MemoryActivity extends BaseActivity{
    private static final String TAG = "MemoryActivity";
    @Override
    protected int getLayoutId() {
        return R.layout.activity_memory;
    }

    @Override
    protected void initView() {
        ArrayList arrayList = new ArrayList();
        for(int i = 0; i < 100; i++) {
            arrayList.add( i + "");
        }

        WeakReference softReference = new WeakReference(arrayList);
        arrayList = null;
        Log.i(TAG, softReference.get().toString());
        byte b[] = new byte[Integer.MAX_VALUE / 2];
        System.gc();
        Log.i(TAG, "gc 之后：" + softReference.get());
    }
}
