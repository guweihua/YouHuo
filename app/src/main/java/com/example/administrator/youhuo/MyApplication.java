package com.example.administrator.youhuo;

import android.app.Application;

import com.example.administrator.youhuo.model.HttpModel;

import retrofit2.Retrofit;

/**
 * Created by Administrator on 2016/12/27.
 */

public class MyApplication extends Application {

    public static MyApplication app;
    public static boolean isDebug=true;
    public Retrofit retrofit;
    public Retrofit otherretrofit;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        retrofit=new Retrofit.Builder().baseUrl(HttpModel.BASE).build();
        otherretrofit=new Retrofit.Builder().baseUrl("http://123.206.47.205:8080/yoho/").build();
    }
}
