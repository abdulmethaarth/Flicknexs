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

class AudiolistAdopter extends RecyclerView.Adapter<AudiolistAdopter.MyViewHolder> {



    private List<Audiolist> audiolist;
    private Context context;

    private static int currentPosition = 0;

    public AudiolistAdopter(List<Audiolist> audiolist, Context context) {
        this.audiolist = audiolist;
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

    public AudiolistAdopter(List<Audiolist> audiolist) {
        this.audiolist = audiolist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_uploads, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        Audiolist aud = audiolist.get(position);
        holder.id.setText(aud.getId());
        holder.url.setText(aud.getUrl());
        holder.name.setText(aud.getName());


        Picasso.get().load(aud.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return audiolist.size();
    }



}

