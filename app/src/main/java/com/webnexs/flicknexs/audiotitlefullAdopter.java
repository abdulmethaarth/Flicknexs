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

class audiotitlefullAdopter extends RecyclerView.Adapter<audiotitlefullAdopter.MyViewHolder> {



    private List<audioplayertitle> audiotitle;
    private Context context;

    private static int currentPosition = 0;

    public audiotitlefullAdopter(List<audioplayertitle> heroList, Context context) {
        this.audiotitle = heroList;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, url,duration;
        public ImageView genre;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.audiotitle);
            genre = (ImageView) view.findViewById(R.id.audioimage1);
             duration = (TextView) view.findViewById(R.id.audiotitlecat);
          //  url = (TextView) view.findViewById(R.id.text2);


        }
    }

    public audiotitlefullAdopter(List<audioplayertitle> audiotitle) {
        this.audiotitle = audiotitle;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.audioplayertitlefull, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        audioplayertitle movie = audiotitle.get(position);
        holder.title.setText(movie.getTitle());
        holder.duration.setText(movie.getCatitle());
        Picasso.get().load(movie.getGenre()).into(holder.genre);
    }


    @Override
    public int getItemCount() {
        return audiotitle.size();
    }



}

