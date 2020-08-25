package com.example.forev.mycodelibrary.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.example.forev.mycodelibrary.IService;

class AidlService extends Service {
//    IService.Stub stub = new
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
