package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
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


public class VideoPageActivity extends AppCompatActivity {


    videopageAdopter videopageadopter;
    private List<videopage> videopagelist = new ArrayList<videopage>();
    RecyclerView videopagerecycler;
    ProgressBar videopageprogress;

    String videopageurl=Base_URL+"movies_list";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/images/";
    String videodetailsurl=Base_URL+"movie";
    String inn;
    String genre1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_page);



        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );
        final String user_role = prefs.getString(ssharedpreferences.role,null );

        BottomNavigationView navigation=(BottomNavigationView)findViewById(R.id.navigation);


        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {


                switch (menuItem.getItemId()) {


                    case R.id.navigation_home:

                        Intent in = new Intent(getApplicationContext(), UserHomeActivity.class);
                        startActivity(in);
                        break;

                    case R.id.navigation_search:

                        Intent in3 = new Intent(getApplicationContext(), SearchActivity.class);
                        startActivity(in3);
                        break;

                    case R.id.navigation_profile:
                            Intent in1=new Intent(getApplicationContext(),MyAccountActivity.class);
                            startActivity(in1);

                        break;

                    case R.id.navigation_video:
                        Intent video=new Intent(VideoPageActivity.this,VideoPageActivity.class);
                        startActivity(video);



                    case R.id.navigation_audio:
                        Intent audio=new Intent(VideoPageActivity.this,AudioPageActivity.class);
                        startActivity(audio);
                        break;

                }

                return false;
            }
        });



        videopageprogress=(ProgressBar)findViewById(R.id.videopageprogress);
        videopagerecycler = (RecyclerView) findViewById(R.id.videopagerecycler);
        videopagerecycler.setLayoutManager(new GridLayoutManager(this, 2));
        videopageadopter = new videopageAdopter(videopagelist);
        final TextView title=(TextView)findViewById(R.id.toolbar_title);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.arrowback);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(getApplicationContext(),UserHomeActivity.class);
                startActivity(in);

            }
        });
        StringRequest watchrequest = new StringRequest(Request.Method.GET, videopageurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {




                        JSONArray dataArray = jsonObject.getJSONArray("movies_list");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String vidid = dataobj.getString("id");
                            String vidname = dataobj.getString("title");
                            String vidmage = dataobj.getString("image");
                            String vidurl = dataobj.getString("mp4_url");
                            String ppvstatus=dataobj.getString("ppv_status");
                            String image1 = Imageurl + vidmage;

                            videopage vidpage = new videopage();
                            vidpage.setId(vidid);
                            vidpage.setName(vidname);
                            vidpage.setImage(image1);
                            vidpage.setUrl(vidurl);
                            vidpage.setPpvstatus(ppvstatus);


                            videopagelist.add(vidpage);
                            videopageadopter.notifyDataSetChanged();


                        }
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(VideoPageActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(VideoPageActivity.this);
        watchqueue.add(watchrequest);



        videopagerecycler.setHasFixedSize(true);

        videopagerecycler.setItemAnimator(new DefaultItemAnimator());

        videopagerecycler.setAdapter(videopageadopter);

        videopagerecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(VideoPageActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                        if(videopagelist.size()>position){
                            if (videopagelist.get(position)!= null){


                                if (videopagelist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {



                                    StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);

                                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired") ) {
                                                    Intent in=new Intent(VideoPageActivity.this,payperviewenableActivity.class);
                                                    in.putExtra("id",videopagelist.get(position).getId());
                                                    startActivity(in);
                                                }
                                                else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                                                {
                                                    Intent in=new Intent(VideoPageActivity.this,payperviewenableActivity.class);
                                                    in.putExtra("id",videopagelist.get(position).getId());
                                                    startActivity(in);
                                                }
                                                else
                                                {
                                                    Intent in=new Intent(VideoPageActivity.this,MoviesActivity.class);
                                                    in.putExtra("id",videopagelist.get(position).getId());
                                                    Toast.makeText(VideoPageActivity.this, ""+videopagelist.get(position).getId(), Toast.LENGTH_LONG).show();
                                                    startActivity(in);
                                                }



                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(VideoPageActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("id",videopagelist.get(position).getId());
                                            parameters.put("user_id",user_id);
                                            return parameters;
                                        }
                                    };

                                    RequestQueue queue4 = Volley.newRequestQueue(VideoPageActivity.this);
                                    queue4.add(request4);


                                }

                                else{
                                    Intent in=new Intent(VideoPageActivity.this,MoviesActivity.class);
                                    in.putExtra("id",videopagelist.get(position).getId());
                                    startActivity(in);
                                }


                            }
                        }

                    }
                })
        );


    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(getApplicationContext(),UserHomeActivity.class);
        startActivity(in);
    }
}
