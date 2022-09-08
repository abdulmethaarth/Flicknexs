package com.webnexs.flicknexs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;


import com.webnexs.flicknexs.Adapter.HomeAdapter;
import com.webnexs.flicknexs.Api1.RetrofitSingleton;
import com.webnexs.flicknexs.Model.Genrepageresponse;
import com.webnexs.flicknexs.Model.HomeData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MoviecategorylistActivity extends AppCompatActivity {



    private ProgressBar progressBar;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<HomeData> dataList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moviecategorylist);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.backarrow);



        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(in);
            }
        });



        dataList = new ArrayList<HomeData>();

        adapter = new HomeAdapter(dataList, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        recyclerView = findViewById(R.id.rv_main);
        progressBar = findViewById(R.id.pb_home);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);



        Call<Genrepageresponse> responseCall = RetrofitSingleton.getInstance().getApi().getallMoviebyCategory();

        responseCall.enqueue(new Callback<Genrepageresponse>() {
            @Override
            public void onResponse(Call<Genrepageresponse> call, Response<Genrepageresponse> response) {
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
            public void onFailure(Call<Genrepageresponse> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

            }
        });



    }


}
