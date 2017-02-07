package com.example.administrator.youhuo.server;

import com.example.administrator.youhuo.model.HttpModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by guweihua on 2017/2/6.
 */

public interface HotbrandService {
    @GET(HttpModel.CATEGORY_BRAND_HOT)
    Call<ResponseBody> getHotBrand();
}
