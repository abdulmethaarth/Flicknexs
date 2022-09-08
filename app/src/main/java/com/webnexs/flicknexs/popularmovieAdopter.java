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

class popularmovieAdopter extends RecyclerView.Adapter<popularmovieAdopter.MyViewHolder> {



    private List<popularmovie> popularmovielist;
    private Context context;

    private static int currentPosition = 0;

    public popularmovieAdopter(List<popularmovie> popularmovielist, Context context) {
        this.popularmovielist = popularmovielist;
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

    public popularmovieAdopter(List<popularmovie> popularmovielist) {
        this.popularmovielist = popularmovielist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_videos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        popularmovie popmovie = popularmovielist.get(position);
        holder.id.setText(popmovie.getId());
        holder.url.setText(popmovie.getUrl());
        holder.name.setText(popmovie.getName());


        Picasso.get().load(popmovie.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return popularmovielist.size();
    }


}

