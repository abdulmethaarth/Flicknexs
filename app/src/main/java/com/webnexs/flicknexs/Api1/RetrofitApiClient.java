package com.webnexs.flicknexs.Api1;


import com.webnexs.flicknexs.Model.Genrepageresponse;
import com.webnexs.flicknexs.Model.HomeBodyResponse;

import retrofit2.Call;
import retrofit2.http.GET;

//import com.webnexs.chhimbal.Model.SocailLoginUser;


public interface RetrofitApiClient {


    @GET("v1/get_genre")
    Call<HomeBodyResponse> getMovieByCategory();

    @GET("v1/get_allgenre")
    Call<Genrepageresponse> getallMoviebyCategory();

  /*  @POST("v1/social_user")
    @FormUrlEncoded
    Call<SocailLoginUser> social_login(@Field("email_id") String email_id,
                                       @Field("url") String url,
                                       @Field("username") String username);*/
}
