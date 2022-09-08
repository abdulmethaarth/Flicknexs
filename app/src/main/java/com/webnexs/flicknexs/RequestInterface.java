package com.webnexs.flicknexs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RequestInterface {

    @GET("planslist")
    Call<JSONResponse> getJSON();

    @POST("stripe_pay")
    Call<JSONResponse> getJSON1();

}
