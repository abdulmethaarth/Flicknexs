package com.webnexs.flicknexs;

import retrofit.Callback;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;


public interface ApiInterface {


    @FormUrlEncoded // annotation used in POST type requests
    @POST("/stripe_pay")     // API's endpoints
    public void payment(@Field("userid") String userid,
                        @Field("stripeToken") String stripeToken,
                        @Field("plan") String plan,
                        Callback<paymentresponse> callback);

    @GET("/settings")
    public void getUsersList(
            Callback<UserListResponse> callback);

    @GET("/splash")
    public void getsplash(
            Callback<spalashscreenlist> callback);

    @FormUrlEncoded
    @POST("/add_payperview")
    public void payperviewpayment(@Field("user_id") String name,
                                  @Field("stripeToken") String email,
                                  @Field("media_type") String plan,
                                  @Field("media_id") String media,
                                  Callback<payperviewpaymentresponse> callback);

    @FormUrlEncoded
    @POST("/livestream_payment")
    public void livepayment(@Field("user_id") String name,
                            @Field("stripeToken") String email,
                            @Field("livestream_id") String media,
                            Callback<livepaymentresponse> callback);

    @FormUrlEncoded
    @POST("/cancel_account")
    public void getcancelpayment(@Field("user_id") String name,
                                 Callback<cancelpayment> callback);

    @FormUrlEncoded
    @POST("/register")
    public void getregister(@Field("username") String name,
                            @Field("email") String email,
                            @Field("password") String password,
                            @Field("stripeToken") String token,
                            @Field("subscrip_plan") String plan,
                            @Field("skip") String skip1,
                            Callback<registerresponse> callback);



}
