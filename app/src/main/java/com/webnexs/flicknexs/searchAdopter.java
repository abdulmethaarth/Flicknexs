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

class searchAdopter extends RecyclerView.Adapter<searchAdopter.MyViewHolder> {



    private List<search> searchlist;
    private Context context;

    private static int currentPosition = 0;

    public searchAdopter(List<search> searchlist, Context context) {
        this.searchlist = searchlist;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url,id;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            image = (ImageView) view.findViewById(R.id.newuploadimg);
             name = (TextView) view.findViewById(R.id.newuploadname);
            url = (TextView) view.findViewById(R.id.views);

        }
    }

    public searchAdopter(List<search> searchlist) {
        this.searchlist = searchlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movi_cat1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        search ser = searchlist.get(position);
        holder.id.setText(ser.getId());
        holder.url.setText(ser.getUrl());
        holder.name.setText(ser.getName());


        Picasso.get().load(ser.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return searchlist.size();
    }



}

