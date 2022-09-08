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
import android.widget.RelativeLayout;
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


public class AudioPageActivity extends AppCompatActivity {


    private List<Audiocat> audiocatlist = new ArrayList<Audiocat>();
    AudiopagecatAdopter audiopagecatadopter;
    RecyclerView audiopagerecycler;
    ProgressBar audiopageprogress;

    String audiocatlisturl=Base_URL+"allAlbums";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/albums/";

    private List<audioplayertitle> audioplaylist = new ArrayList<audioplayertitle>();
    private audiotitleAdopter audioplaytitleadopter;

    RelativeLayout includelayout1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_page);



        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        String audioiid = prefs.getString(ssharedpreferences.audioidd,null );
        String audiotitle = prefs.getString(ssharedpreferences.audiotitlee,null );
        String audioduration = prefs.getString(ssharedpreferences.audiodurationn,null );
        String audiocatecory = prefs.getString(ssharedpreferences.audiocategoryy,null );
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );


        //includelayout1=(RelativeLayout)findViewById(R.id.includelayout1);


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
                        Intent video=new Intent(AudioPageActivity.this,VideoPageActivity.class);
                        startActivity(video);
                        break;


                    case  R.id.navigation_audio:
                        Intent audio=new Intent(AudioPageActivity.this,AudioPageActivity.class);
                        startActivity(audio);
                        break;



                }

                return false;
            }
        });



        audiopageprogress=(ProgressBar)findViewById(R.id.videopageprogress);
        audiopagerecycler = (RecyclerView) findViewById(R.id.videopagerecycler);
        audiopagerecycler.setLayoutManager(new GridLayoutManager(this, 2));
        audiopagecatadopter = new AudiopagecatAdopter(audiocatlist);
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
        StringRequest watchrequest = new StringRequest(Request.Method.GET, audiocatlisturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {



                        JSONArray dataArray = jsonObject.getJSONArray("audio_albums");
                        for (int i = 0; i < dataArray.length(); i++) {


                            /*newuploadname = "";
                            newuploadid = "";*/
                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String vidid = dataobj.getString("id");
                            String vidname = dataobj.getString("albumname");
                           String vidmage = dataobj.getString("album");
                            String vidurl = dataobj.getString("slug");
                            String image1 = Imageurl + vidmage;

                            Audiocat aud = new Audiocat();
                            aud.setId(vidid);
                            aud.setName(vidname);
                            aud.setImage(image1);
                            aud.setUrl(vidurl);


                            audiocatlist.add(aud);
                            audiopagecatadopter.notifyDataSetChanged();


                        }
                    }
                } catch(JSONException e){
                    e.printStackTrace();
                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AudioPageActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(AudioPageActivity.this);
        watchqueue.add(watchrequest);
        watchqueue.getCache().clear();


        audiopagerecycler.setHasFixedSize(true);

        audiopagerecycler.setItemAnimator(new DefaultItemAnimator());

        audiopagerecycler.setAdapter(audiopagecatadopter);

        audiopagerecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(AudioPageActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if(audiocatlist.size()>position){
                            if (audiocatlist.get(position)!= null){
                                Intent intent = new Intent(AudioPageActivity.this, AudioCategorypageActivity.class);
                                intent.putExtra("id",audiocatlist.get(position).getId());
                                startActivity(intent);

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

