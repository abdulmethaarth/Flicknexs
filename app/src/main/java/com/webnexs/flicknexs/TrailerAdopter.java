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

class TrailerAdopter extends RecyclerView.Adapter<TrailerAdopter.MyViewHolder> {



    private List<Trailer> trailerlist;
    private Context context;

    private static int currentPosition = 0;

    public TrailerAdopter(List<Trailer> trailerlist, Context context) {
        this.trailerlist = trailerlist;
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

    public TrailerAdopter(List<Trailer> trailerlist) {
        this.trailerlist = trailerlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_video, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        Trailer trailer = trailerlist.get(position);
        holder.id.setText(trailer.getId());
        holder.url.setText(trailer.getUrl());
        holder.name.setText(trailer.getName());


        Picasso.get().load(trailer.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return trailerlist.size();
    }



}

