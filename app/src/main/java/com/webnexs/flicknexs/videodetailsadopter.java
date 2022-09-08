package com.webnexs.flicknexs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class videodetailsadopter extends RecyclerView.Adapter<videodetailsadopter.MyViewHolder> {



    private List<videodetails> videolist;
    private Context context;

    private static int currentPosition = 0;

    public videodetailsadopter(List<videodetails> videolist, Context context) {
        this.videolist = videolist;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url,id,language,genre,year,runningtime;


        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
             name = (TextView) view.findViewById(R.id.newuploadname);
            url = (TextView) view.findViewById(R.id.views);
            language = (TextView) view.findViewById(R.id.language);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.movieyear);
            runningtime = (TextView) view.findViewById(R.id.runningtime);


        }
    }

    public videodetailsadopter(List<videodetails> videolist) {
        this.videolist = videolist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {




        videodetails viddet = videolist.get(position);
        holder.id.setText(viddet.getId());
        holder.url.setText(viddet.getUrl());
        holder.name.setText(viddet.getName());
        holder.runningtime.setText(viddet.getRunningtime());
        holder.year.setText(viddet.getYear());
        holder.language.setText(viddet.getLanguage());
        holder.genre.setText(viddet.getGenre());


    }


    @Override
    public int getItemCount() {
        return videolist.size();
    }



}

