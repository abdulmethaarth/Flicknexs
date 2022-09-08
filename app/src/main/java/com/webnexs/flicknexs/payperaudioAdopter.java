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

class payperaudioAdopter extends RecyclerView.Adapter<payperaudioAdopter.MyViewHolder> {



    private List<payperaudio> payperaudiolist;
    private Context context;

    private static int currentPosition = 0;

    public payperaudioAdopter(List<payperaudio> payperaudiolist, Context context) {
        this.payperaudiolist = payperaudiolist;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url,id,expire;
        public ImageView image;


        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            image = (ImageView) view.findViewById(R.id.newuploadimg);
             name = (TextView) view.findViewById(R.id.newuploadname);
             expire=(TextView)view.findViewById(R.id.newuploadurl);
            url = (TextView) view.findViewById(R.id.views);



        }
    }

    public payperaudioAdopter(List<payperaudio> payperaudiolist) {
        this.payperaudiolist = payperaudiolist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.payperview_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        payperaudio wish = payperaudiolist.get(position);
        holder.id.setText(wish.getId());
        holder.url.setText(wish.getUrl());
        holder.name.setText(wish.getName());
        holder.expire.setText(wish.getExpire());



        Picasso.get().load(wish.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return payperaudiolist.size();
    }



}

