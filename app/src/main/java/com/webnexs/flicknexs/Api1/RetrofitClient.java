package com.webnexs.flicknexs.Api1;/*
package com.webnexs.chhimbal.Api1;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "https://chhimbal.com/api/";
    private static Retrofit retrofit = null;


    public static Retrofit getRetrofitInstance() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
     //   OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();


        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // service = retrofit.create(Api.class);
        return retrofit;
    }
    public static RetrofitApiClient getApiService() {
        return getRetrofitInstance().create(RetrofitApiClient.class);
    }
}

*/
