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

class videocatogryAdopter extends RecyclerView.Adapter<videocatogryAdopter.MyViewHolder> {



    private List<videocategory> videocatlist;
    private Context context;

    private static int currentPosition = 0;

    public videocatogryAdopter(List<videocategory> videocatlist, Context context) {
        this.videocatlist = videocatlist;
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

    public videocatogryAdopter(List<videocategory> videocatlist) {
        this.videocatlist = videocatlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_category, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        videocategory vid = videocatlist.get(position);
        holder.id.setText(vid.getId());
        holder.url.setText(vid.getUrl());
        holder.name.setText(vid.getName());


        Picasso.get().load(vid.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return videocatlist.size();
    }



}

