package com.webnexs.flicknexs;

import retrofit.RestAdapter;

public class ApiClient {

    public static ApiInterface getClient() {

        // change your base URL
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(" http://flicknexs.webnexs.org/api/v1")
                .build(); //Finally building the adapter

        //Creating object for our interface
        ApiInterface api = adapter.create(ApiInterface.class);
        return api; // return the APIInterface object
    }
}
