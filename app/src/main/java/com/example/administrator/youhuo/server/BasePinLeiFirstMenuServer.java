package com.example.administrator.youhuo.server;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2017/1/12.
 */

public interface BasePinLeiFirstMenuServer {
    @GET("{path}")
    Call<ResponseBody> getChildMenu(@Path("path") String path);
}
