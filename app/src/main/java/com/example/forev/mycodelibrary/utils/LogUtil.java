package com.example.forev.mycodelibrary.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by forev on 2018/11/4.
 */

public class LogUtil {
    private static final String TAG = "MyCodeLibrary";
    private static class LogUtilHolder{
        public static final LogUtil sInstance = new LogUtil();
    }

    public static LogUtil get() {
        return LogUtilHolder.sInstance;
    }

    public  void e (String msg) {
        Log.e(TAG, dealMsg(msg));
    }

    public  void d (String msg) {
        Log.d(TAG, dealMsg(msg));
    }
    public  void w (String msg) {
        Log.w(TAG, dealMsg(msg));
    }
    public  void v (String msg) {
        Log.v(TAG, dealMsg(msg));
    }

    public void i (String msg) {
        Log.i(TAG, dealMsg(msg));
    }
    private String dealMsg(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return "";
        }

        StringBuilder builder = new StringBuilder();
        String currentTime = TimeUtil.get().parseTime(System.currentTimeMillis(),
                "YY-MM-DD HH:mm:ss");
        long freeMemry = Runtime.getRuntime().freeMemory();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        builder.append("free memory=" + freeMemry + "\n")
                .append("thread=" + Thread.currentThread().getName() + " \n")
                .append("msg=" + msg);
        return builder.toString();
    }

    public void logSmallDiv(){
        Log.d(TAG,dealMsg("-------------------------------------------------------------------------") );
    }

    public void logBigDiv(){
        Log.d(TAG, dealMsg("==========================================================================="));
    }

    public void logSmallDiv(String simpleMsg){
        Log.d(TAG,dealMsg("-------------------------------------------------------------------------" + simpleMsg) );
    }

    public void logBigDiv(String simpleMsg){
        Log.d(TAG, dealMsg("==========================================================================="+ simpleMsg));
    }

}
