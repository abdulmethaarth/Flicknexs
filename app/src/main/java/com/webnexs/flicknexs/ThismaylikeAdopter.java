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

class ThismaylikeAdopter extends RecyclerView.Adapter<ThismaylikeAdopter.MyViewHolder> {



    private List<Thismaylike> thismaylikelilst;
    private Context context;

    private static int currentPosition = 0;

    public ThismaylikeAdopter(List<Thismaylike> thismaylikelilst, Context context) {
        this.thismaylikelilst = thismaylikelilst;
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

    public ThismaylikeAdopter(List<Thismaylike> thismaylikelilst) {
        this.thismaylikelilst = thismaylikelilst;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.new_uploads1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        Thismaylike thislike = thismaylikelilst.get(position);
        holder.id.setText(thislike.getId());
        holder.url.setText(thislike.getUrl());
        holder.name.setText(thislike.getName());


        Picasso.get().load(thislike.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return thismaylikelilst.size();
    }



}

