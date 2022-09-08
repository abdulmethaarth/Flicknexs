package com.webnexs.flicknexs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class MoviesAdopter extends RecyclerView.Adapter<MoviesAdopter.MyViewHolder> {



    private List<Movies> movislist;
    private Context context;

    private static int currentPosition = 0;

    public MoviesAdopter(List<Movies> moviesList, Context context) {
        this.movislist = moviesList;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url,id,language,genre,year,runningtime;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            image = (ImageView) view.findViewById(R.id.newuploadimg);
             name = (TextView) view.findViewById(R.id.newuploadname);
            url = (TextView) view.findViewById(R.id.views);
            language = (TextView) view.findViewById(R.id.language);
            genre = (TextView) view.findViewById(R.id.genre);
            year = (TextView) view.findViewById(R.id.movieyear);
            runningtime = (TextView) view.findViewById(R.id.runningtime);


        }
    }

    public MoviesAdopter(List<Movies> movislist) {
        this.movislist = movislist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_details, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        Movies mov = movislist.get(position);
        holder.id.setText(mov.getId());
        holder.url.setText(mov.getUrl());
        holder.name.setText(mov.getName());
        holder.runningtime.setText(mov.getRunningtime());
        holder.year.setText(mov.getYear());
        holder.language.setText(mov.getLanguage());
        holder.genre.setText(mov.getGenre());

    }


    @Override
    public int getItemCount() {
        return movislist.size();
    }



}

