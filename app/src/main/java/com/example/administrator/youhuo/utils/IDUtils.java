package com.example.administrator.youhuo.utils;

import com.example.administrator.youhuo.MyApplication;

/**
 * Created by Administrator on 2016/12/27.
 */

public class IDUtils {

    public static int getPackageID(String name,String type){
        return getId(name,type,MyApplication.app.getPackageName());
    }

    public static int getDrawableId(String name) {
        return getId(name, "mipmap", MyApplication.app.getPackageName());
    }

    public static int getId(String name, String type, String pm){
        return MyApplication.app.getResources().getIdentifier(name, type, pm);
    }
}
