package com.example.administrator.youhuo.server;


import com.example.administrator.youhuo.model.HttpModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 2016/12/29.
 */

public interface HotService {
    @GET(HttpModel.CATEGORY_BRAND_HOT)
    Call<ResponseBody> getHot();
}
