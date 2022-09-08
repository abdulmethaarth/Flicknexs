package com.webnexs.flicknexs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class genreAdopter extends RecyclerView.Adapter<genreAdopter.MyViewHolder> {



    private List<genre> genrelist;
    private Context context;

    private static int currentPosition = 0;

    public genreAdopter(List<genre> genrelist, Context context) {
        this.genrelist = genrelist;
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

    public genreAdopter(List<genre> genrelist) {
        this.genrelist = genrelist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_search, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        genre movie = genrelist.get(position);
        holder.id.setText(movie.getId());
        holder.url.setText(movie.getUrl());
        holder.name.setText(movie.getName());


        //Picasso.get().load(R.drawable.right).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return genrelist.size();
    }



}

