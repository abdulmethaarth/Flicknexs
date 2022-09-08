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

class moviecatAdopter extends RecyclerView.Adapter<moviecatAdopter.MyViewHolder> {



    private List<moviecat> moviecatlist;
    private Context context;

    private static int currentPosition = 0;

    public moviecatAdopter(List<moviecat> moviecatlist, Context context) {
        this.moviecatlist = moviecatlist;
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

    public moviecatAdopter(List<moviecat> moviecatlist) {
        this.moviecatlist = moviecatlist;
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



        moviecat mov = moviecatlist.get(position);
        holder.id.setText(mov.getId());
        holder.url.setText(mov.getUrl());
        holder.name.setText(mov.getName());


        Picasso.get().load(mov.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return moviecatlist.size();
    }



}

