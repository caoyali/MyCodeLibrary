package com.example.forev.mycodelibrary;

import android.app.Application;

import com.example.forev.mycodelibrary.utils.LogUtil;
import com.tencent.bugly.crashreport.CrashReport;

public class LibraryApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.get().d("LibraryApplication onCreate()...");
        CrashReport.initCrashReport(getApplicationContext(), "0e77a525d1", false);
    }
}
