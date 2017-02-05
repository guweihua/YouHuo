package com.example.administrator.youhuo.server;

import com.example.administrator.youhuo.model.HttpModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/1/12.
 */

public interface BasePinLeiSecondServer {
    @GET(HttpModel.CATEGORY_VALUE_URL)
    Call<ResponseBody>getSecond(@Query("categoryid")String id);
}
