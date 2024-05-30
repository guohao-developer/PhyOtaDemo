package com.phy.ota_demo;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.phy.ota_demo.basic.ActivityManager;
import com.phy.otalib.utils.EasySP;

public class OtaApp extends Application {

    @SuppressLint("StaticFieldLeak")
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        EasySP.init(context, EasySP.NAME);
    }

    public static ActivityManager getActivityManager() {
        return ActivityManager.getInstance();
    }
}
