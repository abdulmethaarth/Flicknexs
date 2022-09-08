package com.webnexs.flicknexs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

class slideingadopter extends RecyclerView.Adapter<slideingadopter.MyViewHolder> {



    private List<sliding> slidelist;
    private Context context;

    private static int currentPosition = 0;

    public slideingadopter(List<sliding> slidelist, Context context) {
        this.slidelist = slidelist;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
    //    public TextView name, url,id;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
         //   id = (TextView) view.findViewById(R.id.id);
            image = (ImageView) view.findViewById(R.id.newuploadimg);
          //   name = (TextView) view.findViewById(R.id.newuploadname);
          //  url = (TextView) view.findViewById(R.id.views);

        }
    }

    public slideingadopter(List<sliding> slidelist) {
        this.slidelist = slidelist;
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



        sliding slide = slidelist.get(position);
      //  holder.id.setText(viddet.getId());
       // holder.url.setText(viddet.getUrl());
        //holder.name.setText(viddet.getName());


        Picasso.get().load(slide.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return slidelist.size();
    }



}

