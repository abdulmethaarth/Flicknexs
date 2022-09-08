package com.webnexs.flicknexs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class notificationAdopter extends RecyclerView.Adapter<notificationAdopter.MyViewHolder> {



    private List<notification> notificationlist;
    private Context context;

    private static int currentPosition = 0;

    public notificationAdopter(List<notification> heroList, Context context) {
        this.notificationlist = heroList;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, url,duration,id;
        public ImageView genre,menu;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.text1);
          //  id = (TextView) view.findViewById(R.id.id);
           // genre = (ImageView) view.findViewById(R.id.iv2);
             duration = (TextView) view.findViewById(R.id.text3);
           // url = (TextView) view.findViewById(R.id.text2);
         //   menu = (ImageView) view.findViewById(R.id.menuimage);


        }
    }

    public notificationAdopter(List<notification> notificationlist) {
        this.notificationlist = notificationlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        notification movie = notificationlist.get(position);
        holder.title.setText(movie.getTitle());
        holder.duration.setText(movie.getYear());
      //  holder.duration.setText(movie.getDuration());
     //   holder.id.setText(movie.getTitle());

        //Glide.with(context).load(movie.getGenre()).into(holder.genre);
      //  Picasso.get().load(movie.getGenre()).into(holder.genre);
    }


    @Override
    public int getItemCount() {
        return notificationlist.size();
    }



}

