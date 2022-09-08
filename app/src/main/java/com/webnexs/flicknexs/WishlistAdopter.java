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

class WishlistAdopter extends RecyclerView.Adapter<WishlistAdopter.MyViewHolder> {



    private List<wishlist> wishlistlilst;
    private Context context;

    private static int currentPosition = 0;

    public WishlistAdopter(List<wishlist> wishlistlilst, Context context) {
        this.wishlistlilst = wishlistlilst;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url,id;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            id = (TextView) view.findViewById(R.id.id);
            image = (ImageView) view.findViewById(R.id.newuploadimg);
             name = (TextView) view.findViewById(R.id.newuploadname);
            url = (TextView) view.findViewById(R.id.views);



        }
    }

    public WishlistAdopter(List<wishlist> wishlistlilst) {
        this.wishlistlilst = wishlistlilst;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wish_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {





        wishlist wish = wishlistlilst.get(position);
        holder.id.setText(wish.getId());
        holder.url.setText(wish.getUrl());
        holder.name.setText(wish.getName());



        Picasso.get().load(wish.getImage()).into(holder.image);
    }


    @Override
    public int getItemCount() {
        return wishlistlilst.size();
    }



}

