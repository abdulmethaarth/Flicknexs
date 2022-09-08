package com.webnexs.flicknexs;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

class TitlesearchAdopter extends RecyclerView.Adapter<TitlesearchAdopter.MyViewHolder> {



    private List<searchtitle> titlelist;
    private Context context;

    private static int currentPosition = 0;

    public TitlesearchAdopter(List<searchtitle> titlelist, Context context) {
        this.titlelist = titlelist;
        this.context = context;
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name;

        public MyViewHolder(View view) {
            super(view);
             name = (TextView) view.findViewById(R.id.newuploadname);

        }
    }

    public TitlesearchAdopter(List<searchtitle> titlelist) {
        this.titlelist = titlelist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.title_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        //Picasso.get().load(videos.get(position).getGenre).into(holder.genre);



        searchtitle sertitle = titlelist.get(position);
        holder.name.setText(sertitle.getName());


    }


    @Override
    public int getItemCount() {
        return titlelist.size();
    }



}

