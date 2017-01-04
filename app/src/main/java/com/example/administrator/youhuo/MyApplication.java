package com.example.administrator.youhuo;

import android.app.Application;

/**
 * Created by Administrator on 2016/12/27.
 */

public class MyApplication extends Application {

    public static MyApplication app;
    public static boolean isDebug=true;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }
}
