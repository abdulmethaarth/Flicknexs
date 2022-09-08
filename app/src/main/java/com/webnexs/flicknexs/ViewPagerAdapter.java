package com.webnexs.flicknexs;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class ViewPagerAdapter extends PagerAdapter implements Adapter {
    private Context context;
    private LayoutInflater layoutInflater;
    private List<SliderUtils> sliderImg;
    private ImageLoader imageLoader;
    TextView viewpagerid;

    String videodetailsurl=Base_URL+"movie";

    String livestreamdetails=Base_URL+"single_livestream";

    public ViewPagerAdapter(List sliderImg, Context context) {
        this.sliderImg = sliderImg;
        this.context = context;
    }


    @Override
    public int getCount() {
        return sliderImg.size();
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
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.custom_layout, null);


        SliderUtils utils = sliderImg.get(position);

        ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
        viewpagerid =(TextView)view.findViewById(R.id.view1);

       imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(utils.getSliderImageUrl(), ImageLoader.getImageListener(imageView, R.drawable.logo, android.R.drawable.ic_dialog_alert));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sliderImg.size()>position){
                    if (sliderImg.get(position)!= null){
                     /*   Intent intent = new Intent(context, MoviesActivity.class);
                        intent.putExtra("id",sliderImg.get(position).getId());
                        context.startActivity(intent);
*/

                     if (sliderImg.get(position).getType().equalsIgnoreCase("livestream")) {

                         if (sliderImg.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                             StringRequest request4 = new StringRequest(Request.Method.POST, livestreamdetails, new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {
                                     try {
                                         JSONObject jsonObject = new JSONObject(response);
                                         if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                             Intent in = new Intent(context, LivepaymentEnableActivity.class);
                                             in.putExtra("id", sliderImg.get(position).getId());
                                             context.startActivity(in);
                                         } else {
                                             Intent in = new Intent(context, LiveActivityaaa.class);
                                             in.putExtra("id", sliderImg.get(position).getId());
                                             context.startActivity(in);
                                         }


                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 }

                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError volleyError) {
                                     Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                 }
                             }) {
                                 @Override
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     Map<String, String> parameters = new HashMap<String, String>();
                                     parameters.put("livestream_id", sliderImg.get(position).getId());
                                     parameters.put("user_id", sliderImg.get(position).getUserid());
                                     return parameters;
                                 }
                             };

                             RequestQueue queue4 = Volley.newRequestQueue(context);
                             queue4.add(request4);


                         } else {
                             Intent intent = new Intent(context, LiveActivityaaa.class);
                             intent.putExtra("id", sliderImg.get(position).getId());
                             context.startActivity(intent);
                         }

                     }

                     else {

                         if (sliderImg.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                             StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {
                                     try {
                                         JSONObject jsonObject = new JSONObject(response);

                                         if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                             Intent in = new Intent(context, payperviewenableActivity.class);
                                             in.putExtra("id", sliderImg.get(position).getId());
                                             context.startActivity(in);

                                         } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                             Intent in = new Intent(context, payperviewenableActivity.class);
                                             in.putExtra("id", sliderImg.get(position).getId());
                                             context.startActivity(in);
                                         } else {
                                             Intent in = new Intent(context, MoviesActivity.class);
                                             in.putExtra("id", sliderImg.get(position).getId());
                                             context.startActivity(in);
                                         }

                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 }

                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError volleyError) {
                                     Toast.makeText(context, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                 }
                             }) {
                                 @Override
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     Map<String, String> parameters = new HashMap<String, String>();
                                     parameters.put("id", sliderImg.get(position).getId());
                                     parameters.put("user_id", sliderImg.get(position).getUserid());
                                     return parameters;
                                 }
                             };

                             RequestQueue queue4 = Volley.newRequestQueue(context);
                             queue4.add(request4);


                         } else {
                             Intent intent = new Intent(context, MoviesActivity.class);
                             intent.putExtra("id", sliderImg.get(position).getId());
                             context.startActivity(intent);
                         }
                     }

                    }
                }



            }
        });

        ViewPager vp = (ViewPager) container;
        vp.addView(view, 0);
        return view;

    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

        ViewPager vp = (ViewPager) container;
        View view = (View) object;
        vp.removeView(view);

    }
}

