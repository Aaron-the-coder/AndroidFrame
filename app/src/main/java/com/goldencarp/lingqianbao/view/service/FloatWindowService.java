package com.goldencarp.lingqianbao.view.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.goldencarp.lingqianbao.view.util.MyWindowManager;

public class FloatWindowService extends Service {

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyWindowManager.createSmallWindow(getApplicationContext());
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MyWindowManager.removeSmallWindow(getApplicationContext());
    }

}
