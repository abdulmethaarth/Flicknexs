package com.webnexs.flicknexs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

class trendingadopter extends RecyclerView.Adapter<trendingadopter.MyViewHolder> {



    private List<trendingmovies> trendingmoviesList;
    private Context context;

    private static int currentPosition = 0;

    public trendingadopter(List<trendingmovies> trendingmoviesList, Context context) {
        this.trendingmoviesList = trendingmoviesList;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url,id;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.newuploadid);
            image = (ImageView) view.findViewById(R.id.newuploadimg);
             name = (TextView) view.findViewById(R.id.newuploadname);
            url = (TextView) view.findViewById(R.id.newuploadurl);

        }
    }

    public trendingadopter(List<trendingmovies> trendingmoviesList) {
        this.trendingmoviesList = trendingmoviesList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_uploads1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        trendingmovies movie = trendingmoviesList.get(position);
        holder.id.setText(movie.getId());
        holder.url.setText(movie.getUrl());
        holder.name.setText(movie.getName());


        Picasso.get().load(movie.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return trendingmoviesList.size();
    }



}

