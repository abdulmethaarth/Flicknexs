package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;
import static com.webnexs.flicknexs.UserHomeActivity.mediaplayer;


public class WatchlistActivity extends AppCompatActivity {


    WishlistAdopter wishlistAdopter;
    WishlistaudioAdopter wishlistaudioadopter;
    private List<wishlistaudio> wishlistList1 = new ArrayList<wishlistaudio>();
    private List<wishlist> wishlistList = new ArrayList<wishlist>();
    RecyclerView watchlistrecycle,watchlistaudiorecycle;
    ProgressBar watchlistprogress,watchlistaudioprogress;

    String wishlisturl=Base_URL+"MyWishlistMovies";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/";
    String videodetailsurl=Base_URL+"movie";
    String audiodetailsurl=Base_URL+"audio";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watchlist);


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );


        watchlistprogress=(ProgressBar)findViewById(R.id.watchlistprogress);
        watchlistrecycle = (RecyclerView) findViewById(R.id.watchlilstrecycle);
        watchlistrecycle.setLayoutManager(new GridLayoutManager(this, 2));
        wishlistAdopter = new WishlistAdopter(wishlistList);


        watchlistaudioprogress=(ProgressBar)findViewById(R.id.watchlistaudioprogress);
        watchlistaudiorecycle = (RecyclerView) findViewById(R.id.watchlistaudiorecycler);
        watchlistaudiorecycle.setLayoutManager(new GridLayoutManager(this, 2));
        wishlistaudioadopter = new WishlistaudioAdopter(wishlistList1);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.arrowback);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        StringRequest watchrequest = new StringRequest(Request.Method.POST, wishlisturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        JSONArray dataArray = jsonObject.getJSONArray("my_wishlist_movies");
                        for (int i = 0; i < dataArray.length(); i++) {



                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                            String watchimage=dataobj.getString("image");
                            String watchmp4=dataobj.getString("mp4_url");
                            String ppvstatus=dataobj.getString("ppv_status");
                            String image1=Imageurl+watchimage;

                            wishlist wish = new wishlist();
                            wish.setId(watchid);
                            wish.setName(watchname);
                            wish.setImage(image1);
                            wish.setUrl(watchmp4);
                            wish.setPpvstatus(ppvstatus);

                            wishlistList.add(wish);
                            wishlistAdopter.notifyDataSetChanged();




                        }


                        JSONArray dataArray1 = jsonObject.getJSONArray("my_wishlist_audios");
                        for (int i = 0; i < dataArray1.length(); i++) {


                            JSONObject dataobj = dataArray1.getJSONObject(i);
                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                            String watchimage=dataobj.getString("image");
                            String watchmp4=dataobj.getString("mp3_url");
                            String ppvstatus=dataobj.getString("ppv_status");

                            String image1=Imageurl+watchimage;

                            wishlistaudio wishaud = new wishlistaudio();
                            wishaud.setId(watchid);
                            wishaud.setName(watchname);
                            wishaud.setImage(image1);
                            wishaud.setUrl(watchmp4);
                            wishaud.setPpvstatus(ppvstatus);

                            wishlistList1.add(wishaud);
                            wishlistaudioadopter.notifyDataSetChanged();



                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(WatchlistActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", user_id);
                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(WatchlistActivity.this);
        watchqueue.add(watchrequest);
        watchqueue.getCache().clear();

        watchlistrecycle.setHasFixedSize(true);

        watchlistrecycle.setItemAnimator(new DefaultItemAnimator());

        watchlistrecycle.setAdapter(wishlistAdopter);

        watchlistaudiorecycle.setHasFixedSize(true);

        watchlistaudiorecycle.setItemAnimator(new DefaultItemAnimator());

        watchlistaudiorecycle.setAdapter(wishlistaudioadopter);




        watchlistrecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(WatchlistActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                if(wishlistList.size()>position){
                if (wishlistList.get(position)!= null){

                    if (wishlistList.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {



                        StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired") ) {

                                        Intent in=new Intent(WatchlistActivity.this,payperviewenableActivity.class);
                                        in.putExtra("id",wishlistList.get(position).getId());
                                        startActivity(in);

                                    }
                                    else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                                    {
                                        Intent in=new Intent(WatchlistActivity.this,payperviewenableActivity.class);
                                        in.putExtra("id",wishlistList.get(position).getId());
                                        startActivity(in);
                                    }

                                    else
                                        {
                                            Intent in=new Intent(WatchlistActivity.this,MoviesActivity.class);
                                            in.putExtra("id",wishlistList.get(position).getId());
                                            startActivity(in);
                                        }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                            }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(WatchlistActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                parameters.put("id",wishlistList.get(position).getId());
                                parameters.put("user_id",user_id);
                                return parameters;
                            }
                        };

                        RequestQueue queue4 = Volley.newRequestQueue(WatchlistActivity.this);
                        queue4.add(request4);
                        queue4.getCache().clear();

                    }
                    else {
                        Intent intent = new Intent(WatchlistActivity.this, MoviesActivity.class);
                        intent.putExtra("id", wishlistList.get(position).getId());
                        startActivity(intent);
                    }


                }
                }

                    }
                })
        );


        watchlistaudiorecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(WatchlistActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        mediaplayer.stop();
                        mediaplayer.reset();

                        if (wishlistList1.size() > position) {
                            if (wishlistList1.get(position) != null) {

                                if (wishlistList1.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                                    StringRequest request4 = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired") ) {

                                                    Intent in=new Intent(WatchlistActivity.this,PayperviewAudioActivity.class);
                                                    in.putExtra("id",wishlistList1.get(position).getId());
                                                    startActivity(in);

                                                }
                                                else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                                                {
                                                    Intent in=new Intent(WatchlistActivity.this,PayperviewAudioActivity.class);
                                                    in.putExtra("id",wishlistList1.get(position).getId());
                                                    startActivity(in);
                                                }

                                                else
                                                {
                                                    Intent in=new Intent(WatchlistActivity.this,MoviesActivity.class);
                                                    in.putExtra("id",wishlistList1.get(position).getId());
                                                    startActivity(in);
                                                }



                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(WatchlistActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("id",wishlistList1.get(position).getId());
                                            parameters.put("user_id",user_id);
                                            return parameters;
                                        }
                                    };

                                    RequestQueue queue4 = Volley.newRequestQueue(WatchlistActivity.this);
                                    queue4.add(request4);
                                    queue4.getCache().clear();

                                }
                                else {
                                    Intent intent = new Intent(WatchlistActivity.this, MusicPlayerActivity.class);
                                    intent.putExtra("id", wishlistList1.get(position).getId());
                                    startActivity(intent);
                                }




                            }

                        } }
                }));



    }






    @Override
    public void onBackPressed() {
        finish();
    }
}
