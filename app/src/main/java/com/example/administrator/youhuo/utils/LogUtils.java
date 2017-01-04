package com.example.administrator.youhuo.utils;

import android.util.Log;

import com.example.administrator.youhuo.MyApplication;


/**
 * Created by admin on 2016/12/27.
 */

public class LogUtils {
    public static void log(String tag, String value){
        if(MyApplication.isDebug)
        Log.e(tag,value);
    }
}
