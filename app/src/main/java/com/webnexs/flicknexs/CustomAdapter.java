package com.webnexs.flicknexs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomAdapter extends BaseAdapter {

    Context context;

    LayoutInflater inflter;
    private List<fliperutils> fliperutils1;




    public CustomAdapter(Context applicationContext, List<fliperutils> fliperlist) {
    }



    @Override
    public int getCount() {
       return fliperutils1.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.fliper_layout, null);
        TextView fruitName = (TextView) view.findViewById(R.id.fruitName);
         final ImageView fruitImage = (ImageView) view.findViewById(R.id.fruitImage);
       // fruitName.setText(sliderImg[i]);
       String xx=fliperutils1.get(i).getSliderImageUrl();
        Picasso.get().load(xx).into(fruitImage);




        return view;
    }
}
