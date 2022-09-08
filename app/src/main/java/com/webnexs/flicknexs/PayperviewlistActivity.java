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


public class PayperviewlistActivity extends AppCompatActivity {

    payperviewAdopter payperviewadopter;
    payperviewaudioAdopter payperviewaudioadopter;
    private List<payperviewaudio> payerperaudiolist = new ArrayList<payperviewaudio>();
    private List<payperview> paypervideolist = new ArrayList<payperview>();
    RecyclerView payperviewrecycle,payperaudiorecycle;
    ProgressBar payperaudioprogress,paypervideoprogress;

    String payperviewlisturl=Base_URL+"getPPV";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/";
    String videodetailsurl=Base_URL+"movie";
    String audioddetailsurl=Base_URL+"audio";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payperviewlist);

        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );


        paypervideoprogress=(ProgressBar)findViewById(R.id.payperviewvideoprogress);
        payperviewrecycle = (RecyclerView) findViewById(R.id.payperviewvideorecycle);
        payperviewrecycle.setLayoutManager(new GridLayoutManager(this, 2));
        payperviewadopter = new payperviewAdopter(paypervideolist);


        payperaudioprogress=(ProgressBar)findViewById(R.id.parperviewaudioprogress);
        payperaudiorecycle = (RecyclerView) findViewById(R.id.payperviewaudiorecycle);
        payperaudiorecycle.setLayoutManager(new GridLayoutManager(this, 2));
        payperviewaudioadopter = new payperviewaudioAdopter(payerperaudiolist);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.arrowback);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        StringRequest watchrequest = new StringRequest(Request.Method.POST, payperviewlisturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {




                        JSONArray dataArray = jsonObject.getJSONArray("payperview_movie");

                        for (int j = 0; j < dataArray.length(); j++) {

                            JSONObject dataobj = dataArray.getJSONObject(j);



                                String watchid = dataobj.getJSONObject("movie").getString("id");
                                String watchname = dataobj.getJSONObject("movie").getString("title");
                                String watchimage = dataobj.getJSONObject("movie").getString("image");
                                String watchmp4 = dataobj.getJSONObject("movie").getString("mp4_url");
                                String ppvstatus = dataobj.getJSONObject("movie").getString("ppv_status");
                                String image1 = Imageurl + watchimage;



                              /*  payperview pay = new payperview();
                                pay.setId(watchid);
                                pay.setName(watchname);
                                pay.setImage(image1);
                                pay.setUrl(watchmp4);
                                pay.setPpvstatus(ppvstatus);

                                paypervideolist.add(pay);
                                payperviewadopter.notifyDataSetChanged();
*/


                        }


                        JSONArray dataArray1 = jsonObject.getJSONArray("payperview_audio");
                        for (int i = 0; i < dataArray1.length(); i++) {

                            JSONObject dataobj = dataArray1.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                            String watchimage=dataobj.getString("image");
                            String watchmp4=dataobj.getString("mp3_url");
                            String ppvstatus=dataobj.getString("pay_status");
                            String image1=Imageurl+watchimage;

                            payperviewaudio wishaud = new payperviewaudio();
                            wishaud.setId(watchid);
                            wishaud.setName(watchname);
                            wishaud.setImage(image1);
                            wishaud.setUrl(watchmp4);
                            wishaud.setPpvstatus(ppvstatus);

                            payerperaudiolist.add(wishaud);
                            payperviewaudioadopter.notifyDataSetChanged();



                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PayperviewlistActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", user_id);
                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(PayperviewlistActivity.this);
        watchqueue.add(watchrequest);


        payperviewrecycle.setHasFixedSize(true);

        payperviewrecycle.setItemAnimator(new DefaultItemAnimator());

        payperviewrecycle.setAdapter(payperviewadopter);






    payperviewrecycle.addOnItemTouchListener(
            new RecyclerItemClickListener(PayperviewlistActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

            if(paypervideolist.size()>position){
            if (paypervideolist.get(position)!= null){
            if (paypervideolist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {


                StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired") ) {

                                Intent in=new Intent(PayperviewlistActivity.this,payperviewenableActivity.class);
                                in.putExtra("id",paypervideolist.get(position).getId());
                                startActivity(in);

                            }
                            else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                            {
                                Intent in=new Intent(PayperviewlistActivity.this,payperviewenableActivity.class);
                                in.putExtra("id",paypervideolist.get(position).getId());
                                startActivity(in);
                            }
                            else
                                {
                                    Intent in=new Intent(PayperviewlistActivity.this,MoviesActivity.class);
                                    in.putExtra("id",paypervideolist.get(position).getId());
                                    startActivity(in);
                                }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(PayperviewlistActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("id",paypervideolist.get(position).getId());
                        parameters.put("user_id",user_id);
                        return parameters;
                    }
                };
                RequestQueue queue4 = Volley.newRequestQueue(PayperviewlistActivity.this);
                queue4.add(request4);
            }


            }
            }
                    }
                })
        );


        payperaudiorecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(PayperviewlistActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                        if (payerperaudiolist.size() > position) {
                            if (payerperaudiolist.get(position) != null) {


                                if (payerperaudiolist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                                    StringRequest request4 = new StringRequest(Request.Method.POST,audioddetailsurl , new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired") ) {

                                                    Intent in=new Intent(PayperviewlistActivity.this,PayperviewAudioActivity.class);
                                                    in.putExtra("id",payerperaudiolist.get(position).getId());
                                                    startActivity(in);

                                                }
                                                else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                                                {
                                                    Intent in=new Intent(PayperviewlistActivity.this,PayperviewAudioActivity.class);
                                                    in.putExtra("id",payerperaudiolist.get(position).getId());
                                                    startActivity(in);
                                                }

                                                else
                                                    {
                                                    Intent in=new Intent(PayperviewlistActivity.this,MusicPlayerActivity.class);
                                                    in.putExtra("id",payerperaudiolist.get(position).getId());
                                                    startActivity(in);
                                                }

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(PayperviewlistActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("id",payerperaudiolist.get(position).getId());
                                            parameters.put("user_id",user_id);
                                            return parameters;
                                        }
                                    };
                                    RequestQueue queue4 = Volley.newRequestQueue(PayperviewlistActivity.this);
                                    queue4.add(request4);

                                }
                                else {
                                    Intent intent = new Intent(PayperviewlistActivity.this, MusicPlayerActivity.class);
                                    intent.putExtra("id", payerperaudiolist.get(position).getId());
                                    startActivity(intent);
                                }
                            }
                        }
                    }}));

    }
}
