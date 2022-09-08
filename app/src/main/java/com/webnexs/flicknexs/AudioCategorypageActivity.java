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
import static com.webnexs.flicknexs.UserHomeActivity.mediaplayer;


public class AudioCategorypageActivity extends AppCompatActivity {

    AudiocatPageAdopter audpageadopter;
    private List<Audiocatpage> audiocatlist = new ArrayList<Audiocatpage>();

    RelatedaudiocatPageAdopter relatedaudpageadopter;
    private List<Audiocatpage> relatedaudlist = new ArrayList<Audiocatpage>();


    RecyclerView audiocatrecyler,relatedrecycler;
    ProgressBar audiocatprogress,relatedaudprogress;

    String Audiocaturl=Base_URL+"album_audios";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/images/";
    String inn;
    String genre1;
    String audiodetailsurl=Base_URL+"audio";

    RelativeLayout includelayout;

    private List<audioplayertitle> audioplaylist = new ArrayList<audioplayertitle>();
    private audiotitleAdopter audioplaytitleadopter;
    String user_id,user_role;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audiocategory);




        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        user_id = prefs.getString(ssharedpreferences.user_id,null );
        user_role = prefs.getString(ssharedpreferences.role,null );


        Intent in=getIntent();
        inn=in.getStringExtra("id");


        audiocatprogress=(ProgressBar)findViewById(R.id.audiocatprogress);
        audiocatrecyler = (RecyclerView) findViewById(R.id.vidcatrecycler);
        audiocatrecyler.setLayoutManager(new GridLayoutManager(this, 2));
        audpageadopter = new AudiocatPageAdopter(audiocatlist);


        relatedaudprogress=(ProgressBar)findViewById(R.id.relatedaudioprogress);
        relatedrecycler = (RecyclerView) findViewById(R.id.relateaudiorecycler);
        relatedrecycler.setLayoutManager(new GridLayoutManager(this, 2));
        relatedaudpageadopter = new RelatedaudiocatPageAdopter(relatedaudlist);



        final TextView title=(TextView)findViewById(R.id.toolbar_title);



        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.arrowback);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaplayer.stop();
                Intent in=new Intent(getApplicationContext(),AudioPageActivity.class);
                startActivity(in);
            }
        });



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
                        Intent video=new Intent(AudioCategorypageActivity.this,VideoPageActivity.class);
                        startActivity(video);
                        break;

                    case R.id.navigation_audio:
                        Intent audio=new Intent(AudioCategorypageActivity.this,AudioPageActivity.class);
                        startActivity(audio);
                        break;



                }

                return false;
            }
        });


        StringRequest watchrequest = new StringRequest(Request.Method.POST, Audiocaturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        JSONArray relatedaudio=jsonObject.getJSONArray("audio_albums");


                        for (int i = 0; i < relatedaudio.length(); i++) {


                            JSONObject dataobj = relatedaudio.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                             String watchimage = dataobj.getString("image");
                            String watchmp3 = dataobj.getString("mp3_url");
                            String ppvstatus=dataobj.getString("ppv_status");
                             String image1 = Imageurl + watchimage;

                            Audiocatpage vid = new Audiocatpage();
                            vid.setId(watchid);
                            vid.setName(watchname);
                            vid.setImage(image1);
                            vid.setUrl(watchmp3);
                            vid.setPpvstatus(ppvstatus);


                            title.setText(genre1);
                            relatedaudlist.add(vid);
                            relatedaudpageadopter.notifyDataSetChanged();


                        }





                        JSONArray dataArray = jsonObject.getJSONArray("album_first");


                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                            String watchimage = dataobj.getString("image");
                            String watchmp3 = dataobj.getString("mp3_url");
                            String ppvstatus=dataobj.getString("ppv_status");

                            String image1 = Imageurl + watchimage;

                            Audiocatpage vid = new Audiocatpage();
                            vid.setId(watchid);
                            vid.setName(watchname);
                            vid.setImage(image1);
                            vid.setUrl(watchmp3);
                            vid.setPpvstatus(ppvstatus);


                            title.setText(genre1);
                            audiocatlist.add(vid);
                            audpageadopter.notifyDataSetChanged();


                        }
                    }
                    } catch(JSONException e){
                        e.printStackTrace();
                    }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(AudioCategorypageActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("album_id", inn);
                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(AudioCategorypageActivity.this);
        watchqueue.add(watchrequest);


        audiocatrecyler.setHasFixedSize(true);

        audiocatrecyler.setItemAnimator(new DefaultItemAnimator());

        audiocatrecyler.setAdapter(audpageadopter);

        relatedrecycler.setHasFixedSize(true);

        relatedrecycler.setItemAnimator(new DefaultItemAnimator());

        relatedrecycler.setAdapter(relatedaudpageadopter);







        audiocatrecyler.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {


                 if (audiocatlist.size() > position) {
                     if (audiocatlist.get(position) != null) {

                         if (audiocatlist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                             StringRequest request4 = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
                                 @Override
                                 public void onResponse(String response) {
                                     try {
                                         JSONObject jsonObject = new JSONObject(response);
                                         if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {
                                             Intent in = new Intent(AudioCategorypageActivity.this, PayperviewAudioActivity.class);
                                             in.putExtra("id", audiocatlist.get(position).getId());
                                             startActivity(in);

                                         } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                             Intent in = new Intent(AudioCategorypageActivity.this, PayperviewAudioActivity.class);
                                             in.putExtra("id", audiocatlist.get(position).getId());
                                             startActivity(in);
                                         } else {
                                             Intent in = new Intent(AudioCategorypageActivity.this, MusicPlayerActivity.class);
                                             in.putExtra("id", audiocatlist.get(position).getId());
                                             startActivity(in);
                                         }


                                     } catch (JSONException e) {
                                         e.printStackTrace();
                                     }

                                 }

                             }, new Response.ErrorListener() {
                                 @Override
                                 public void onErrorResponse(VolleyError volleyError) {
                                     Toast.makeText(AudioCategorypageActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                 }
                             }) {
                                 @Override
                                 protected Map<String, String> getParams() throws AuthFailureError {
                                     Map<String, String> parameters = new HashMap<String, String>();
                                     parameters.put("audioid", audiocatlist.get(position).getId());
                                     parameters.put("user_id", user_id);
                                     return parameters;
                                 }
                             };

                             RequestQueue queue4 = Volley.newRequestQueue(AudioCategorypageActivity.this);
                             queue4.add(request4);

                         }
                         else {
                             Intent intent = new Intent(AudioCategorypageActivity.this, MusicPlayerActivity.class);
                             intent.putExtra("id", audiocatlist.get(position).getId());
                             startActivity(intent);
                         }


                     }
                 }
                    }
                }));


        relatedrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
        if (relatedaudlist.size() > position) {
            if (relatedaudlist.get(position) != null) {
                if (relatedaudlist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                    StringRequest request4 = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                    Intent in = new Intent(AudioCategorypageActivity.this, PayperviewAudioActivity.class);
                                    in.putExtra("id", relatedaudlist.get(position).getId());
                                    startActivity(in);

                                } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                    Intent in = new Intent(AudioCategorypageActivity.this, PayperviewAudioActivity.class);
                                    in.putExtra("id", relatedaudlist.get(position).getId());
                                    startActivity(in);
                                } else {
                                    Intent in = new Intent(AudioCategorypageActivity.this, MusicPlayerActivity.class);
                                    in.putExtra("id", relatedaudlist.get(position).getId());
                                    startActivity(in);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(AudioCategorypageActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("audioid", relatedaudlist.get(position).getId());
                            parameters.put("user_id", user_id);
                            return parameters;
                        }
                    };

                    RequestQueue queue4 = Volley.newRequestQueue(AudioCategorypageActivity.this);
                    queue4.add(request4);

                }
                else {
                    Intent intent = new Intent(AudioCategorypageActivity.this, MusicPlayerActivity.class);
                    intent.putExtra("id", relatedaudlist.get(position).getId());
                    startActivity(intent);
                }


            }
        } }}));


    }

    @Override
    public void onBackPressed() {

        Intent in=new Intent(getApplicationContext(),AudioPageActivity.class);
        startActivity(in);
    }
}
