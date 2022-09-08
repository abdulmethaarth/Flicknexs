package com.webnexs.flicknexs;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<plans> plans;

    public DataAdapter(ArrayList<plans> plans) {
        this.plans = plans;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_row, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.tv_name.setText("Rs."+plans.get(i).getPrice());
        viewHolder.tv_version.setText(plans.get(i).getId());
        viewHolder.tv_api_level.setText(plans.get(i).getDisplay_name());
        viewHolder.tv_title.setText(plans.get(i).getType());
        viewHolder.tv_interval.setText(plans.get(i).getBilling_interval());

    }

    @Override
    public int getItemCount() {
        return plans.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_name,tv_version,tv_api_level,tv_title,tv_interval;
        public ViewHolder(View view) {
            super(view);

            tv_name = (TextView)view.findViewById(R.id.tv_name);
            tv_version = (TextView)view.findViewById(R.id.tv_version);
            tv_api_level = (TextView)view.findViewById(R.id.tv_api_level);
            tv_title=(TextView)view.findViewById(R.id.aa);
            tv_interval=(TextView)view.findViewById(R.id.interval);



        }
    }

}
