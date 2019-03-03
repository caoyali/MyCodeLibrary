package com.example.forev.mycodelibrary.utils;

import android.text.TextUtils;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by forev on 2018/11/4.
 */

public class TimeUtil {
    private static class TimeUtilHolder{
        public static final TimeUtil sInstance = new TimeUtil();
    }

    public static TimeUtil get() {
        return TimeUtilHolder.sInstance;
    }

    public String parseTime(long time, String format) {
        if (TextUtils.isEmpty(format)) {
            return "";
        }
        if (time <= 0) {
            return "";
        }
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(format);
            Date date = new Date(time);
            return dateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
