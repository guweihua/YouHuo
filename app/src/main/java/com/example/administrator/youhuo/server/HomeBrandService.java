package com.example.administrator.youhuo.server;

import com.example.administrator.youhuo.model.HttpModel;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2017/1/10.
 */

public interface HomeBrandService {

    @GET(HttpModel.BRAND_VALUE)
    Call<ResponseBody> getAllBrand();
}
