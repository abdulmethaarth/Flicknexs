package com.webnexs.flicknexs.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.webnexs.flicknexs.Model.HomeData;
import com.webnexs.flicknexs.MovieCategoryActivity;
import com.webnexs.flicknexs.R;

import java.util.Arrays;
import java.util.List;


public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {


    private Context context;
    private List<HomeData> genre_movies;
    private MovieAdapter horizontalAdapter;
    private RecyclerView.RecycledViewPool recycledViewPool;

    public HomeAdapter(List<HomeData> genre_movies, Context context) {
        this.genre_movies = genre_movies;
        this.context = context;
        recycledViewPool = new RecyclerView.RecycledViewPool();

    }


    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View theView = LayoutInflater.from(context).inflate(R.layout.row_layout_home, parent, false);
        return new HomeViewHolder(theView);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, final int position) {

        holder.textViewCategory.setText(genre_movies.get(position).getGenreName());
        holder.textViewCategoryid.setText(genre_movies.get(position).getGenre_id());

        horizontalAdapter = new MovieAdapter(Arrays.asList(genre_movies.get(position).getMovies()), context);
        holder.recyclerViewHorizontal.setAdapter(horizontalAdapter);
        holder.recyclerViewHorizontal.setRecycledViewPool(recycledViewPool);




    }


    @Override
    public int getItemCount() {
        return genre_movies.size();

    }


    public class HomeViewHolder extends RecyclerView.ViewHolder {

        private RecyclerView recyclerViewHorizontal;
        private TextView textViewCategory;
        private TextView textViewCategoryid;

        private LinearLayoutManager horizontalManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        public HomeViewHolder(View itemView) {
            super(itemView);

            recyclerViewHorizontal = itemView.findViewById(R.id.home_recycler_view_horizontal);
            recyclerViewHorizontal.setHasFixedSize(true);
            recyclerViewHorizontal.setNestedScrollingEnabled(false);
            recyclerViewHorizontal.setLayoutManager(horizontalManager);
            recyclerViewHorizontal.setItemAnimator(new DefaultItemAnimator());

            textViewCategory = itemView.findViewById(R.id.tv_movie_category);
            textViewCategoryid = itemView.findViewById(R.id.tv_movie_category_id);



            textViewCategory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(context, MovieCategoryActivity.class);
                    intent.putExtra("id", textViewCategoryid.getText().toString());
                    context.startActivity(intent);
                }
            });

        }

    }
}
