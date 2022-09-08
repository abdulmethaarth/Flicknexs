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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;
import static com.webnexs.flicknexs.UserHomeActivity.mediaplayer;


public class payperviewlistcopyActivity extends AppCompatActivity {


    paypervideoAdopter wishlistAdopter;
    payperaudioAdopter wishlistaudioadopter;
    private List<payperaudio> wishlistList1 = new ArrayList<payperaudio>();
    private List<paypervideo> wishlistList = new ArrayList<paypervideo>();
    RecyclerView watchlistrecycle,watchlistaudiorecycle;
    ProgressBar watchlistprogress,watchlistaudioprogress;

    String wishlisturl=Base_URL+"getPPV";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/images/";
    String videodetailsurl=Base_URL+"movie";
    String audiodetailsurl=Base_URL+"audio";
     String paytotime;
     String payid;
    Date currentTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payperviewcopy);


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );


        watchlistprogress=(ProgressBar)findViewById(R.id.watchlistprogress);
        watchlistrecycle = (RecyclerView) findViewById(R.id.watchlilstrecycle);
        watchlistrecycle.setLayoutManager(new GridLayoutManager(this, 2));
        wishlistAdopter = new paypervideoAdopter(wishlistList);


        watchlistaudioprogress=(ProgressBar)findViewById(R.id.watchlistaudioprogress);
        watchlistaudiorecycle = (RecyclerView) findViewById(R.id.watchlistaudiorecycler);
        watchlistaudiorecycle.setLayoutManager(new GridLayoutManager(this, 2));
        wishlistaudioadopter = new payperaudioAdopter(wishlistList1);


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



                        JSONArray dataArray = jsonObject.getJSONArray("payperview_movie");
                        for (int i = 0; i < dataArray.length(); i++) {



                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String watchid = dataobj.getJSONObject("movie").getString("id");
                            String watchname = dataobj.getJSONObject("movie").getString("title");
                            String watchimage=dataobj.getJSONObject("movie").getString("image");
                            String watchmp4=dataobj.getJSONObject("movie").getString("mp4_url");
                            String ppvstatus=dataobj.getJSONObject("movie").getString("ppv_status");
                            String image1=Imageurl+watchimage;

                            String status=dataobj.getString("status");


                            paypervideo wish = new paypervideo();
                            wish.setId(watchid);
                            wish.setName(watchname);
                            wish.setImage(image1);
                            wish.setUrl(watchmp4);
                            wish.setExpire(status);
                            wish.setPpvstatus(ppvstatus);

                            wishlistList.add(wish);
                            wishlistAdopter.notifyDataSetChanged();



                        }


                        JSONArray dataArray1 = jsonObject.getJSONArray("payperview_audio");
                        for (int i = 0; i < dataArray.length(); i++) {


                            /*newuploadname = "";
                            newuploadid = "";*/
                            JSONObject dataobj = dataArray1.getJSONObject(i);

                            String status=dataobj.getString("status");

                            String watchid = dataobj.getJSONObject("audio").getString("id");
                            String watchname = dataobj.getJSONObject("audio").getString("title");
                            String watchimage=dataobj.getJSONObject("audio").getString("image");
                            String watchmp4=dataobj.getJSONObject("audio").getString("mp3_url");
                            String ppvstatus=dataobj.getJSONObject("audio").getString("ppv_status");

                            String image1=Imageurl+watchimage;

                            payperaudio wishaud = new payperaudio();
                            wishaud.setId(watchid);
                            wishaud.setName(watchname);
                            wishaud.setImage(image1);
                            wishaud.setUrl(watchmp4);
                            wishaud.setExpire(status);
                            wishaud.setPpvstatus(ppvstatus);


                            wishlistList1.add(wishaud);
                            wishlistaudioadopter.notifyDataSetChanged();

                            // newuploadsprogress.setVisibility(View.GONE);


                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(payperviewlistcopyActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", user_id);
                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(payperviewlistcopyActivity.this);
        watchqueue.add(watchrequest);
        watchqueue.getCache().clear();

        watchlistrecycle.setHasFixedSize(true);

        watchlistrecycle.setItemAnimator(new DefaultItemAnimator());

        watchlistrecycle.setAdapter(wishlistAdopter);

        watchlistaudiorecycle.setHasFixedSize(true);

        watchlistaudiorecycle.setItemAnimator(new DefaultItemAnimator());

        watchlistaudiorecycle.setAdapter(wishlistaudioadopter);




        watchlistrecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(payperviewlistcopyActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
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

                                        Intent in=new Intent(payperviewlistcopyActivity.this,payperviewenableActivity.class);
                                        in.putExtra("id",wishlistList.get(position).getId());
                                        startActivity(in);

                                    }
                                    else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                                    {
                                        Intent in=new Intent(payperviewlistcopyActivity.this,payperviewenableActivity.class);
                                        in.putExtra("id",wishlistList.get(position).getId());
                                        startActivity(in);
                                    }

                                    else
                                        {
                                            Intent in=new Intent(payperviewlistcopyActivity.this,MoviesActivity.class);
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
                                Toast.makeText(payperviewlistcopyActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

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

                        RequestQueue queue4 = Volley.newRequestQueue(payperviewlistcopyActivity.this);
                        queue4.add(request4);
                        queue4.getCache().clear();

                    }
                    else {
                        Intent intent = new Intent(payperviewlistcopyActivity.this, MoviesActivity.class);
                        intent.putExtra("id", wishlistList.get(position).getId());
                        startActivity(intent);
                    }




                }
                }

                    }
                })
        );


        watchlistaudiorecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(payperviewlistcopyActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
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

                                                    Intent in=new Intent(payperviewlistcopyActivity.this,PayperviewAudioActivity.class);
                                                    in.putExtra("id",wishlistList1.get(position).getId());
                                                    startActivity(in);

                                                }
                                                else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                                                {
                                                    Intent in=new Intent(payperviewlistcopyActivity.this,PayperviewAudioActivity.class);
                                                    in.putExtra("id",wishlistList1.get(position).getId());
                                                    startActivity(in);
                                                }

                                                else
                                                {
                                                    Intent in=new Intent(payperviewlistcopyActivity.this,MoviesActivity.class);
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
                                            Toast.makeText(payperviewlistcopyActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
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

                                    RequestQueue queue4 = Volley.newRequestQueue(payperviewlistcopyActivity.this);
                                    queue4.add(request4);
                                    queue4.getCache().clear();

                                }
                                else {
                                    Intent intent = new Intent(payperviewlistcopyActivity.this, MusicPlayerActivity.class);
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
