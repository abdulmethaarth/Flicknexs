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

class videopageAdopter extends RecyclerView.Adapter<videopageAdopter.MyViewHolder> {



    private List<videopage> videopagelist;
    private Context context;

    private static int currentPosition = 0;

    public videopageAdopter(List<videopage> videopagelist, Context context) {
        this.videopagelist = videopagelist;
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

    public videopageAdopter(List<videopage> videopagelist) {
        this.videopagelist = videopagelist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_page, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        videopage vidpage = videopagelist.get(position);
        holder.id.setText(vidpage.getId());
        holder.url.setText(vidpage.getUrl());
        holder.name.setText(vidpage.getName());


        Picasso.get().load(vidpage.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return videopagelist.size();
    }



}

