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

class EpisodeAdopter extends RecyclerView.Adapter<EpisodeAdopter.MyViewHolder> {



    private List<episodes> episodellist;
    private Context context;

    private static int currentPosition = 0;

    public EpisodeAdopter(List<episodes> episodellist, Context context) {
        this.episodellist = episodellist;
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

    public EpisodeAdopter(List<episodes> episodellist) {
        this.episodellist = episodellist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.seasonepisode, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        episodes eps = episodellist.get(position);
        holder.id.setText(eps.getId());
        holder.url.setText(eps.getUrl());
        holder.name.setText(eps.getName());


        Picasso.get().load(eps.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return episodellist.size();
    }



}

