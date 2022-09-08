package com.webnexs.flicknexs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

class QualityAdopter extends RecyclerView.Adapter<QualityAdopter.MyViewHolder> {



    private List<quality> qualityList;
    private Context context;

    private static int currentPosition = 0;

    public QualityAdopter(List<quality> qualityList, Context context) {
        this.qualityList = qualityList;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url,id;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);

             name = (TextView) view.findViewById(R.id.newuploadname);
            url = (TextView) view.findViewById(R.id.views);



        }
    }

    public QualityAdopter(List<quality> qualityList) {
        this.qualityList = qualityList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quality_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {





        quality wish = qualityList.get(position);
        holder.id.setText(wish.getName());
        holder.url.setText(wish.getUrl());
        holder.name.setText(wish.getId());




    }


    @Override
    public int getItemCount() {
        return qualityList.size();
    }


}

