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

class sidemennuAdopter extends RecyclerView.Adapter<sidemennuAdopter.MyViewHolder> {



    private List<sidemenu1> sidemenulist;
    private Context context;

    private static int currentPosition = 0;

    public sidemennuAdopter(List<sidemenu1> sidemenulist, Context context) {
        this.sidemenulist = sidemenulist;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name1, name2;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);
            image = (ImageView) view.findViewById(R.id.sidemenuimg);
             name1 = (TextView) view.findViewById(R.id.name1);
            name2 = (TextView) view.findViewById(R.id.name2);

        }
    }

    public sidemennuAdopter(List<sidemenu1> sidemenulist) {
        this.sidemenulist = sidemenulist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.side_mennu1, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        sidemenu1 sidemenu = sidemenulist.get(position);
        holder.name1.setText(sidemenu.getName1());
        holder.name2.setText(sidemenu.getName2());


        Picasso.get().load(sidemenu.getImage()).into(holder.image);
/*
        holder.image.setImageDrawable(context.getResources().getDrawable(sidemenu.getImage()));
*/

    }


    @Override
    public int getItemCount() {
        return sidemenulist.size();
    }



}

