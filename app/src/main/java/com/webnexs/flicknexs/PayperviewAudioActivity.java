package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.Callback;
import retrofit.RetrofitError;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;
import static com.webnexs.flicknexs.UserHomeActivity.mediaplayer;


public class PayperviewAudioActivity extends AppCompatActivity {


    ImageView downarrow;
    LinearLayout playbutton1 ,pausebutton1,audiolayout,toolbarlayout,relatedlayout,player,allaudio;


    TextView tt1,tt2;
    private String audiotitlle,audiourl;
    String body1, body, wer1, wer2, vol, body3, wer3;
    String name, id;
    TextView tt, volly, volid1,tx1,tx2,audtitle,audalbum;




     private SeekBar seekbar;
    private double startTime = 0;
    private double finalTime = 0;

    private Handler myHandler = new Handler();
    RecyclerView recyclerView2;
    LinearLayout wishlist,wishlistadd,favourite,favouriteadd,share;
    ImageView audioimage;


    String Audiourl=Base_URL+"album_audios";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/";
    String trendingaudiourl=Base_URL+"trendingaudios";
    String audiodetailsurl=Base_URL+"audio";



    private List<audioplayertitle> audioplaylist = new ArrayList<audioplayertitle>();
    private audiotitlefullAdopter audioplaytitleadopter1;

    MediaPlayer media;

    upnextAdopter upnextadopter;
    private List<Audiocatpage> relatedaudlist = new ArrayList<Audiocatpage>();

    ProgressBar upnextprogress,similaraudioprogress;
    RecyclerView upnextrecycler,similarrecycler;

    private List<Audiocat> similarlist = new ArrayList<Audiocat>();
    AudiocatAdopter similaradopter;




    String x;
    String audiocategory;
    int REQUEST_CODE = 0077;
    Button buyvideo;
    public String stripe_token;

    payperviewpaymentresponse signUpResponsesData;
    String media_type="audio";
    String user_id;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payperview_audio);



        tt1 = (TextView) findViewById(R.id.tt1);
        Intent in = getIntent();
        x = in.getStringExtra("id");



        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
          user_id = prefs.getString(ssharedpreferences.user_id,null );
        final String user_role = prefs.getString(ssharedpreferences.role,null );



        upnextprogress = (ProgressBar) findViewById(R.id.upnextprogress);
        upnextrecycler = (RecyclerView) findViewById(R.id.upnextrecycler);
        upnextrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        upnextadopter = new upnextAdopter(relatedaudlist);


        similaraudioprogress = (ProgressBar) findViewById(R.id.relatedaudioprogress);
        similarrecycler = (RecyclerView) findViewById(R.id.similaraudiosrecycler);
        similarrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similaradopter = new AudiocatAdopter(similarlist);

        audioplaytitleadopter1 = new audiotitlefullAdopter(audioplaylist);

        audioimage = (ImageView) findViewById(R.id.audioimage);



        tt2 = (TextView) findViewById(R.id.tt2);
        tt = (TextView) findViewById(R.id.tt);
        tx1 = (TextView) findViewById(R.id.first);
        tx2 = (TextView) findViewById(R.id.last);
        audtitle = (TextView) findViewById(R.id.audtitle);
        audalbum = (TextView) findViewById(R.id.audalbum);
        buyvideo=(Button)findViewById(R.id.buyvideo);


        buyvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), PaymentActivity.class);
                startActivityForResult(in, REQUEST_CODE);
            }
        });



        StringRequest audiorequest = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        JSONArray genre = jsonObject.getJSONArray("audio_category");


                        for (int j = 0; j < genre.length(); j++) {



                            JSONObject dataobj1 = genre.getJSONObject(j);

                            audiocategory = dataobj1.getString("name");



                        }

                        JSONArray dataArray = jsonObject.getJSONArray("audio_details");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String audioid = dataobj.getString("id");
                            String audioname = dataobj.getString("title");
                            String audioimage1 = dataobj.getString("image");
                            String audiomp3 = dataobj.getString("mp3_url");
                           // String audioalbum = dataobj.getString("slug");
                            String image1 = Imageurl + audioimage1;

                            tt1.setText(audioid);


                            Picasso.get().load(image1).into(audioimage);
                            audtitle.setText(audioname);
                            audalbum.setText(audiocategory);



                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PayperviewAudioActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("audioid", x);
                return parameters;
            }
        };
        RequestQueue audioqueue = Volley.newRequestQueue(PayperviewAudioActivity.this);
        audioqueue.add(audiorequest);
        audioqueue.getCache().clear();

        StringRequest audiocatrequest = new StringRequest(Request.Method.POST, Audiourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        JSONArray relatedaudio = jsonObject.getJSONArray("audio_albums");


                        for (int i = 0; i < relatedaudio.length(); i++) {


                            JSONObject dataobj = relatedaudio.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                            String watchimage = dataobj.getString("image");
                            String watchmp3 = dataobj.getString("mp3_url");
                            String duration=dataobj.getString("duration");
                            String image1 = Imageurl + watchimage;



                            int a= Integer.parseInt(duration);

                            int b=a%60;
                            int c=a/60;

                            int d = c % 60;
                            c = c/ 60;


                            String e = String.format("%2ds",b);
                            String f = String.format("%2dm",d);
                            String h= String.format("%2dh",c);


                            String g=h+" "+f+" "+e;

                            Audiocatpage vid = new Audiocatpage();
                            vid.setId(watchid);
                            vid.setName(watchname);
                            vid.setImage(image1);
                            vid.setDuration(g);
                            vid.setUrl(watchmp3);


                            relatedaudlist.add(vid);
                            upnextadopter.notifyDataSetChanged();


                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PayperviewAudioActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("audio_id", x);
                return parameters;
            }
        };
        final RequestQueue audiocatqueue = Volley.newRequestQueue(PayperviewAudioActivity.this);
        audiocatqueue.add(audiocatrequest);
        audiocatqueue.getCache().clear();

        upnextrecycler.setHasFixedSize(true);

        upnextrecycler.setItemAnimator(new DefaultItemAnimator());

        upnextrecycler.setAdapter(upnextadopter);


        StringRequest similarrequest = new StringRequest(Request.Method.GET, trendingaudiourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("trending_audios");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String audioid = dataobj.getString("id");
                            String audioname = dataobj.getString("title");
                            String audioimage = dataobj.getString("image");
                            String audiomp3 = dataobj.getString("mp3_url");
                            String ppvstatus=dataobj.getString("ppv_status");
                            String image1 = Imageurl + audioimage;

                            Audiocat aud = new Audiocat();
                            aud.setId(audioid);
                            aud.setName(audioname);
                            aud.setImage(image1);
                            aud.setUrl(audiomp3);
                            aud.setPpvstatus(ppvstatus);

                            similarlist.add(aud);
                            similaradopter.notifyDataSetChanged();


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(PayperviewAudioActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue similarqueue = Volley.newRequestQueue(PayperviewAudioActivity.this);
        similarqueue.add(similarrequest);
        similarqueue.getCache().clear();

        similarrecycler.setHasFixedSize(true);

        similarrecycler.setItemAnimator(new DefaultItemAnimator());

        similarrecycler.setAdapter(similaradopter);




        upnextrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(PayperviewAudioActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

        if(relatedaudlist.size()>position){
            if (relatedaudlist.get(position)!= null){

                StringRequest audiorequest = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                JSONArray genre = jsonObject.getJSONArray("audio_category");


                                for (int j = 0; j < genre.length(); j++) {



                                    JSONObject dataobj1 = genre.getJSONObject(j);

                                    audiocategory = dataobj1.getString("name");

                                }

                                JSONArray dataArray = jsonObject.getJSONArray("audio_details");
                                for (int i = 0; i < dataArray.length(); i++) {

                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    String audioid = dataobj.getString("id");
                                    String audioname = dataobj.getString("title");
                                    String audioimage1 = dataobj.getString("image");
                                    String audiomp3 = dataobj.getString("mp3_url");
                                    // String audioalbum = dataobj.getString("slug");
                                    String image1 = Imageurl + audioimage1;

                                    tt1.setText(audioid);


                                    Picasso.get().load(image1).into(audioimage);
                                    audtitle.setText(audioname);
                                    audalbum.setText(audiocategory);

                                    mediaplayer.stop();
                                    mediaplayer.reset();

                                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    try {
                                        mediaplayer.setDataSource(audiomp3);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                    try {
                                        mediaplayer.prepare();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }


                                    finalTime = mediaplayer.getDuration();
                                    startTime = mediaplayer.getCurrentPosition();

                                    mediaplayer.start();
                                                    //relatedid=audiolist.get(position).getDuration();
                                                    //relatedidd.setText(relatedid);

                                    tx2.setText(String.format("%02d : %02d ",
                                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                            finalTime)))
                                    );

                                    seekbar.setMax((int) finalTime);
                                    Runnable mUpdateSeekbar = new Runnable() {
                                        @Override
                                        public void run() {
                                            seekbar.setProgress(mediaplayer.getCurrentPosition());
                                            myHandler.postDelayed(this, 50);
                                        }
                                    };


                                    myHandler.postDelayed(mUpdateSeekbar, 0);
                                    long xxy = mediaplayer.getCurrentPosition();

                                    seekbar.setProgress((int) xxy);
                                    myHandler.postDelayed(UpdateSongTime, 1000);


                                    seekbar.setClickable(true);
                                    seekbar.setFocusable(true);
                                    seekbar.setEnabled(true);


                                    seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                                        int progressChangedValue = 0;

                                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                                            progressChangedValue = progress;
                                        }

                                        public void onStartTrackingTouch(SeekBar seekBar) {

                                        }

                                        public void onStopTrackingTouch(SeekBar seekBar) {

                                            mediaplayer.seekTo(progressChangedValue);
                                        }
                                    });

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(PayperviewAudioActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("audioid", relatedaudlist.get(position).getId());
                        return parameters;
                    }
                };
                RequestQueue audioqueue = Volley.newRequestQueue(PayperviewAudioActivity.this);
                audioqueue.add(audiorequest);
                audiocatqueue.getCache().clear();
            }
        }
                    }
                })
        );


        similarrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(PayperviewAudioActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                if(similarlist.size()>position){
                    if (similarlist.get(position)!= null){


                        if (similarlist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                            StringRequest request4 = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                            Intent in = new Intent(PayperviewAudioActivity.this, PayperviewAudioActivity.class);
                                            in.putExtra("id", similarlist.get(position).getId());
                                            startActivity(in);

                                        } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                            Intent in = new Intent(PayperviewAudioActivity.this, PayperviewAudioActivity.class);
                                            in.putExtra("id", similarlist.get(position).getId());
                                            startActivity(in);
                                        } else {
                                            Intent in = new Intent(PayperviewAudioActivity.this, MusicPlayerActivity.class);
                                            in.putExtra("id", similarlist.get(position).getId());
                                            startActivity(in);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(PayperviewAudioActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parameters = new HashMap<String, String>();
                                    parameters.put("audioid", similarlist.get(position).getId());
                                    parameters.put("user_id", user_id);
                                    return parameters;
                                }
                            };

                            RequestQueue queue4 = Volley.newRequestQueue(PayperviewAudioActivity.this);
                            queue4.add(request4);
                            queue4.getCache().clear();

                        } else {
                            Intent intent = new Intent(PayperviewAudioActivity.this, MusicPlayerActivity.class);
                            intent.putExtra("id", similarlist.get(position).getId());
                            startActivity(intent);
                        }


                    }
                        }

                    }
                })
        );

    }


    @Override
    protected void onActivityResult ( final int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == resultCode) {
            stripe_token = data.getStringExtra("stripe_token");

            if (user_id != null) {
                if (stripe_token.length() > 1) {


                    ApiClient.getClient().payperviewpayment(user_id,
                            stripe_token,media_type ,x,
                            new Callback<payperviewpaymentresponse>() {
                                @Override
                      public void success(payperviewpaymentresponse signUpResponse, retrofit.client.Response response) {
                                    signUpResponsesData = signUpResponse;


                                    Intent in= new Intent(PayperviewAudioActivity.this,MusicPlayerActivity.class);
                                    in.putExtra("id",x);
                                    startActivity(in);

                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(PayperviewAudioActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                                }

                            });
                }
            }

        }

    }


    @Override
    public void onBackPressed() {


       mediaplayer.stop();
        finish();


    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = mediaplayer.getCurrentPosition();
            tx1.setText(String.format("%02d : %02d ",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );
            seekbar.setProgress((int)startTime);
            myHandler.postDelayed(this, 100);
        }
    };



}
