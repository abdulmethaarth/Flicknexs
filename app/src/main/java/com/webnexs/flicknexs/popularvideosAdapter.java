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

class popularvideosAdapter extends RecyclerView.Adapter<popularvideosAdapter.MyViewHolder> {



    private List<popularvideos> popularlist;
    private Context context;

    private static int currentPosition = 0;

    public popularvideosAdapter(List<popularvideos> popularlist, Context context) {
        this.popularlist = popularlist;
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

    public popularvideosAdapter(List<popularvideos> popularlist) {
        this.popularlist = popularlist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.popular_videos, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        popularvideos popular = popularlist.get(position);
        holder.id.setText(popular.getId());
        holder.url.setText(popular.getUrl());
        holder.name.setText(popular.getName());


        Picasso.get().load(popular.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return popularlist.size();
    }



}

