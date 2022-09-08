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

class AudiocatPageAdopter extends RecyclerView.Adapter<AudiocatPageAdopter.MyViewHolder> {



    private List<Audiocatpage> audiocatlist;
    private Context context;

    private static int currentPosition = 0;

    public AudiocatPageAdopter(List<Audiocatpage> audiocatllist, Context context) {
        this.audiocatlist = audiocatllist;
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

    public AudiocatPageAdopter(List<Audiocatpage> audiocatlist) {
        this.audiocatlist = audiocatlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movi_cat, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        Audiocatpage aud = audiocatlist.get(position);
        holder.id.setText(aud.getId());
        holder.url.setText(aud.getUrl());
        holder.name.setText(aud.getName());


        Picasso.get().load(aud.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return audiocatlist.size();
    }



}

