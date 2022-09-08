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

class testinggenreAdopter extends RecyclerView.Adapter<testinggenreAdopter.MyViewHolder> {



    private List<newuploads> newuploadsList;
    private Context context;

    private static int currentPosition = 0;

    public testinggenreAdopter(List<newuploads> uploadlist, Context context) {
        this.newuploadsList = uploadlist;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url,id,ppvstatus;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.newuploadid);
            image = (ImageView) view.findViewById(R.id.newuploadimg);
             name = (TextView) view.findViewById(R.id.newuploadname);
            url = (TextView) view.findViewById(R.id.newuploadurl);
            ppvstatus=(TextView)view.findViewById(R.id.ppvstatus);

        }
    }

    public testinggenreAdopter(List<newuploads> newuploadsList) {
        this.newuploadsList = newuploadsList;
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



        newuploads upload = newuploadsList.get(position);
        holder.id.setText(upload.getId());
        holder.url.setText(upload.getUrl());
        holder.name.setText(upload.getName());
        holder.ppvstatus.setText(upload.getPpvstatus());


        Picasso.get().load(upload.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return newuploadsList.size();
    }



}

