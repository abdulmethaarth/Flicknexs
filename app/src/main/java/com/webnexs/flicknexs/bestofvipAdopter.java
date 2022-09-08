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

class bestofvipAdopter extends RecyclerView.Adapter<bestofvipAdopter.MyViewHolder> {



    private List<bestofvip> bestofviplist;
    private Context context;

    private static int currentPosition = 0;

    public bestofvipAdopter(List<bestofvip> bestofviplist, Context context) {
        this.bestofviplist = bestofviplist;
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

    public bestofvipAdopter(List<bestofvip> bestofviplist) {
        this.bestofviplist = bestofviplist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.bestof_vip, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        bestofvip bestvip = bestofviplist.get(position);
        holder.id.setText(bestvip.getId());
        holder.url.setText(bestvip.getUrl());
        holder.name.setText(bestvip.getName());


        Picasso.get().load(bestvip.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return bestofviplist.size();
    }



}

