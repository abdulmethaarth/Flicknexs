package com.webnexs.flicknexs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;


import com.webnexs.flicknexs.Adapter.HomeAdapter;
import com.webnexs.flicknexs.Api1.RetrofitSingleton;
import com.webnexs.flicknexs.Model.HomeBodyResponse;
import com.webnexs.flicknexs.Model.HomeData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";


    private ArrayList<HomeData> dataList;


    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
        dataList = new ArrayList<HomeData>();

        adapter = new HomeAdapter(dataList, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        recyclerView = findViewById(R.id.rv_main);
        progressBar = findViewById(R.id.pb_home);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        Call<HomeBodyResponse> responseCall = RetrofitSingleton.getInstance().getApi().getMovieByCategory();

        responseCall.enqueue(new Callback<HomeBodyResponse>() {
            @Override
            public void onResponse(Call<HomeBodyResponse> call, Response<HomeBodyResponse> response) {
                progressBar.setVisibility(View.GONE);

                if(response.isSuccess()){
                   // Data data = (Data) response.body().getData().getData();
                     for (HomeData data : response.body().getGenreMovies()) {

                    dataList.add(data);
                     }
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<HomeBodyResponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

            }
        });



    }
}
