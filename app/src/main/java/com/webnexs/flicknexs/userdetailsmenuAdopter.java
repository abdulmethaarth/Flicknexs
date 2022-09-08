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

class userdetailsmenuAdopter extends RecyclerView.Adapter<userdetailsmenuAdopter.MyViewHolder> {



    private List<userdetailsmenu> userdetailslist;
    private Context context;

    private static int currentPosition = 0;

    public userdetailsmenuAdopter(List<userdetailsmenu> userdetailslist, Context context) {
        this.userdetailslist = userdetailslist;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name1, name2;
        public ImageView image,dp;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.sidemenuimg);
             name1 = (TextView) view.findViewById(R.id.name1);
            name2 = (TextView) view.findViewById(R.id.name2);
            dp = (ImageView) view.findViewById(R.id.sidemendp);


        }
    }

    public userdetailsmenuAdopter(List<userdetailsmenu> userdetailslist) {
        this.userdetailslist = userdetailslist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.userdetails_menu, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        userdetailsmenu userdetails = userdetailslist.get(position);
        holder.name1.setText(userdetails.getName1());
        holder.name2.setText(userdetails.getName2());


        Picasso.get().load(userdetails.getImage()).into(holder.image);
        Picasso.get().load(userdetails.getImagedp()).into(holder.dp);

    }


    @Override
    public int getItemCount() {
        return userdetailslist.size();
    }



}

