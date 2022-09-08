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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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

import static com.webnexs.flicknexs.SigninActivity.Base_URL;
import static com.webnexs.flicknexs.UserHomeActivity.mediaplayer;


public class MusicPlayerActivity extends AppCompatActivity {


    ImageView buttonpre, buttonnext,buttonpause1,buttonrepeat,buttonShuffle,buttonStart1,repeatvisible,greenshuffle,buttonStart,downarrow;
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
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/images/";

    String trendingaudiourl=Base_URL+"similarAudio";
    String previousaudio=Base_URL+"prev_audio";
    String audiodetailsurl=Base_URL+"audio";
    String nextaudiourl=Base_URL+"next_audio";
    String upnexturl= Base_URL+"upnextAudio";

    String checkfavnext=Base_URL+"checknextfavaudio";
    String checkfavpre=Base_URL+"checkprevfavaudio";



    private List<audioplayertitle> audioplaylist = new ArrayList<audioplayertitle>();
    private audiotitlefullAdopter audioplaytitleadopter1;

    MediaPlayer media;

    upnextAdopter upnextadopter;
    private List<Audiocatpage> relatedaudlist = new ArrayList<Audiocatpage>();

    ProgressBar upnextprogress,similaraudioprogress;
    RecyclerView upnextrecycler,similarrecycler;

    private List<Audiocat> similarlist = new ArrayList<Audiocat>();
    AudiocatAdopter similaradopter;

    String wishlistvid=Base_URL+"AddToWishlistMovie";
    String userwish=Base_URL+"ShowWishlist";

    LinearLayout watchlist;
    ImageView watchlistimg,watchlistaddedimg;
    String x;
    String audiocategory;
    private String shareurl;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_player);



        tt1 = (TextView) findViewById(R.id.tt1);
        Intent in = getIntent();
        x = in.getStringExtra("id");

        watchlist=(LinearLayout)findViewById(R.id.watchlist);
        watchlistimg=(ImageView)findViewById(R.id.watchlistimg);
        watchlistaddedimg=(ImageView)findViewById(R.id.watchlistaddimg);
        share=(LinearLayout)findViewById(R.id.share1);

        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );
        final String user_role = prefs.getString(ssharedpreferences.role,null );


        StringRequest request = new StringRequest(Request.Method.POST, userwish, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        if(jsonObject.getString("wish_count").equalsIgnoreCase("1"))
                        {
                            watchlistaddedimg.setVisibility(View.VISIBLE);
                            watchlistimg.setVisibility(View.GONE);
                        }
                        else
                        {
                            watchlistaddedimg.setVisibility(View.GONE);
                            watchlistimg.setVisibility(View.VISIBLE);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id",user_id);
                parameters.put("audio_id",x);


                return parameters;
            }
        };

        RequestQueue queue2 = Volley.newRequestQueue(MusicPlayerActivity.this);
        queue2.add(request);


        watchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, wishlistvid, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                    Toast.makeText(getApplicationContext(),""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    watchlistimg.setVisibility(View.GONE);
                                    watchlistaddedimg.setVisibility(View.VISIBLE);

                                }
                                else if (jsonObject.getString("status").equalsIgnoreCase("false")) {

                                    Toast.makeText(getApplicationContext(),""+jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                                    watchlistaddedimg.setVisibility(View.GONE);
                                    watchlistimg.setVisibility(View.VISIBLE);

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(MusicPlayerActivity.this, "You are not a registered user", Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("user_id",user_id );
                            parameters.put("audio_id",x );
                            return parameters;
                        }
                    };

                    RequestQueue queue = Volley.newRequestQueue(MusicPlayerActivity.this);
                    queue.add(stringRequest);

                }

            }
        });
        upnextprogress = (ProgressBar) findViewById(R.id.upnextprogress);
        upnextrecycler = (RecyclerView) findViewById(R.id.upnextrecycler);
        upnextrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        upnextadopter = new upnextAdopter(relatedaudlist);
        upnextadopter.notifyDataSetChanged();


        similaraudioprogress = (ProgressBar) findViewById(R.id.relatedaudioprogress);
        similarrecycler = (RecyclerView) findViewById(R.id.similaraudiosrecycler);
        similarrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        similaradopter = new AudiocatAdopter(similarlist);
        similaradopter.notifyDataSetChanged();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent myIntent = new Intent(Intent.ACTION_SEND);
                myIntent.setType("text/plain");
                String shareBody = "I am watching flicknexs videos";
                String shareSub = shareurl;
                myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
                myIntent.putExtra(Intent.EXTRA_TEXT, shareSub);
                startActivity(Intent.createChooser(myIntent, "Share using"));
            }
        });


        audioplaytitleadopter1 = new audiotitlefullAdopter(audioplaylist);


        buttonStart1 = (ImageView) findViewById(R.id.button51);
        buttonpre = (ImageView) findViewById(R.id.previous);
        buttonnext = (ImageView) findViewById(R.id.next);

        buttonpause1 = (ImageView) findViewById(R.id.button61);

        audioimage = (ImageView) findViewById(R.id.audioimage);


        repeatvisible = findViewById(R.id.repeatvisible);
        buttonShuffle = (ImageView) findViewById(R.id.shuffle);
        greenshuffle = (ImageView) findViewById(R.id.shufflevisible);
        buttonrepeat = (ImageView) findViewById(R.id.repeat);


        playbutton1 = findViewById(R.id.playbutton1);
        pausebutton1 = findViewById(R.id.pausebutton1);


        downarrow = (ImageView) findViewById(R.id.downarrow);


        tt2 = (TextView) findViewById(R.id.tt2);
        tt = (TextView) findViewById(R.id.tt);
        tx1 = (TextView) findViewById(R.id.first);
        tx2 = (TextView) findViewById(R.id.last);
        audtitle = (TextView) findViewById(R.id.audtitle);
        audalbum = (TextView) findViewById(R.id.audalbum);


        seekbar = (SeekBar) findViewById(R.id.seekBar);

        int max = mediaplayer.getDuration();


        downarrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaplayer.stop();
                finish();
            }
        });


        StringRequest audiorequest = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        shareurl=jsonObject.getString("audiourl");

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
                            String image1 = Imageurl + audioimage1;

                            tt1.setText(audioid);


                            Picasso.get().load(image1).into(audioimage);
                            audtitle.setText(audioname);
                            audalbum.setText(audiocategory);

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
                Toast.makeText(MusicPlayerActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("audioid", x);
                return parameters;
            }
        };
        RequestQueue audioqueue = Volley.newRequestQueue(MusicPlayerActivity.this);
        audioqueue.add(audiorequest);


        StringRequest audiocatrequest = new StringRequest(Request.Method.POST, upnexturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);



                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        upnextprogress.setVisibility(View.GONE);


                        JSONArray relatedaudio = jsonObject.getJSONArray("audio_albums");


                        for (int i = 0; i < relatedaudio.length(); i++) {


                            JSONObject dataobj = relatedaudio.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                            String watchimage = dataobj.getString("image");
                            String watchmp3 = dataobj.getString("mp3_url");
                            String duration=dataobj.getString("duration");
                            String ppvstatus=dataobj.getString("ppv_status");
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
                            vid.setPpvstatus(ppvstatus);


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
                Toast.makeText(MusicPlayerActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("audio_id", x);
                return parameters;
            }
        };
        RequestQueue audiocatqueue = Volley.newRequestQueue(MusicPlayerActivity.this);
        audiocatqueue.add(audiocatrequest);


        upnextrecycler.setHasFixedSize(true);

        upnextrecycler.setItemAnimator(new DefaultItemAnimator());

        upnextrecycler.setAdapter(upnextadopter);


        StringRequest similarrequest = new StringRequest(Request.Method.POST, trendingaudiourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("similarAudio");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String audioid = dataobj.getString("id");
                            String audioname = dataobj.getString("title");
                            String audioimage = dataobj.getString("image");
                            String audiomp3 = dataobj.getString("mp3_url");
                            String image1 = Imageurl + audioimage;

                            Audiocat aud = new Audiocat();
                            aud.setId(audioid);
                            aud.setName(audioname);
                            aud.setImage(image1);
                            aud.setUrl(audiomp3);

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
                Toast.makeText(MusicPlayerActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("audio_id",x);
                return parameters;
            }
        };

        RequestQueue similarqueue = Volley.newRequestQueue(MusicPlayerActivity.this);
        similarqueue.add(similarrequest);
        similarqueue.getCache().clear();

        similarrecycler.setHasFixedSize(true);

        similarrecycler.setItemAnimator(new DefaultItemAnimator());

        similarrecycler.setAdapter(similaradopter);


        buttonStart1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pausebutton1.setVisibility(View.VISIBLE);
                playbutton1.setVisibility(View.GONE);

                mediaplayer.start();


            }
        });

        buttonpause1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                buttonpause1.startAnimation(animFadeIn);
                pausebutton1.setVisibility(View.GONE);
                playbutton1.setVisibility(View.VISIBLE);
                mediaplayer.pause();

            }
        });


        buttonpre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                body = tt1.getText().toString();
                StringRequest request = new StringRequest(Request.Method.POST, checkfavpre, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {



                                if(jsonObject.getString("wish_audio_count").equalsIgnoreCase("1"))
                                {
                                    watchlistaddedimg.setVisibility(View.VISIBLE);
                                    watchlistimg.setVisibility(View.GONE);
                                }
                                else
                                {
                                    watchlistaddedimg.setVisibility(View.GONE);
                                    watchlistimg.setVisibility(View.VISIBLE);
                                }



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //   Toast.makeText(VideoActivity.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("user_id",user_id);
                        parameters.put("audio_id",tt1.getText().toString());


                        return parameters;
                    }
                };


                RequestQueue queue2 = Volley.newRequestQueue(MusicPlayerActivity.this);
                queue2.add(request);



                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                buttonpre.startAnimation(animFadeIn);


                StringRequest request1 = new StringRequest(Request.Method.POST, previousaudio, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            mediaplayer.stop();
                            mediaplayer.reset();



                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                                JSONArray genre = jsonObject.getJSONArray("category_details");


                                for (int j = 0; j < genre.length(); j++) {



                                    JSONObject dataobj1 = genre.getJSONObject(j);

                                    audiocategory = dataobj1.getString("name");



                                }

                                JSONArray dataArray = jsonObject.getJSONArray("audio_details");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    JSONObject dataobj = dataArray.getJSONObject(i);

                                    audiotitlle = dataobj.getString("title");
                                    audiourl = dataobj.getString("mp3_url");
                                    id = dataobj.getString("id");
                                    wer3 = dataobj.getString("image");
                                    body3 = dataobj.getString("slug");
                                    String ppvstatus=dataobj.getString("ppv_status");
                                    String image1 = Imageurl + wer3;

                                    tt1.setText(id);


                                    Picasso.get().load(image1).into(audioimage);
                                    audtitle.setText(audiotitlle);
                                    audalbum.setText(audiocategory);


                                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    try {
                                        mediaplayer.setDataSource(audiourl);

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


                                    seekbar.setMax((int) finalTime);

                                    tx2.setText(String.format("%02d : %02d ",
                                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                            finalTime)))
                                    );


                                    seekbar.setProgress((int) startTime);
                                    myHandler.postDelayed(UpdateSongTime, 1000);
                                    mediaplayer.start();


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MusicPlayerActivity.this, "Check your internet connection ", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("id", tt1.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(MusicPlayerActivity.this);
                queue.add(request1);


            }
        });


        buttonrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                buttonrepeat.startAnimation(animFadeIn);
                repeatvisible.setVisibility(View.VISIBLE);
                buttonrepeat.setVisibility(View.GONE);

                mediaplayer.setLooping(true);


            }
        });

        buttonnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                body = tt1.getText().toString();
                StringRequest request = new StringRequest(Request.Method.POST, checkfavnext, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                                if(jsonObject.getString("wish_audio_count").equalsIgnoreCase("1"))
                                {
                                    watchlistaddedimg.setVisibility(View.VISIBLE);
                                    watchlistimg.setVisibility(View.GONE);
                                }
                                else
                                {
                                    watchlistaddedimg.setVisibility(View.GONE);
                                    watchlistimg.setVisibility(View.VISIBLE);
                                }



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //   Toast.makeText(VideoActivity.this, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("user_id",user_id);
                        parameters.put("audio_id",tt1.getText().toString());
                        return parameters;
                    }
                };


                RequestQueue queue2 = Volley.newRequestQueue(MusicPlayerActivity.this);
                queue2.add(request);



                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                buttonnext.startAnimation(animFadeIn);


                StringRequest request1 = new StringRequest(Request.Method.POST, nextaudiourl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            mediaplayer.stop();
                            mediaplayer.reset();
                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {



                                JSONArray genre = jsonObject.getJSONArray("category_details");


                                for (int j = 0; j < genre.length(); j++) {



                                    JSONObject dataobj1 = genre.getJSONObject(j);

                                    audiocategory = dataobj1.getString("name");



                                }

                                String cat = jsonObject.getString("audio_cat_name");
                                JSONArray dataArray = jsonObject.getJSONArray("audio_details");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    audiotitlle = dataobj.getString("title");
                                    audiourl = dataobj.getString("mp3_url");
                                    id = dataobj.getString("id");
                                    wer3 = dataobj.getString("image");
                                    body3 = dataobj.getString("slug");
                                    String image1 = Imageurl + wer3;


                                    tt1.getText().toString();

                                    Picasso.get().load(image1).into(audioimage);
                                    audtitle.setText(audiotitlle);
                                    audalbum.setText(audiocategory);


                                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    try {
                                        mediaplayer.setDataSource(audiourl);

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


                                    seekbar.setMax((int) finalTime);

                                    tx2.setText(String.format("%02d : %02d ",
                                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                            finalTime)))
                                    );


                                    seekbar.setProgress((int) startTime);
                                    myHandler.postDelayed(UpdateSongTime, 1000);
                                    mediaplayer.start();


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MusicPlayerActivity.this, "Check your internet connection ", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("id", tt1.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(MusicPlayerActivity.this);
                queue.add(request1);


            }
        });

        buttonrepeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                buttonrepeat.startAnimation(animFadeIn);
                repeatvisible.setVisibility(View.VISIBLE);
                buttonrepeat.setVisibility(View.GONE);

                mediaplayer.setLooping(true);


            }
        });

        repeatvisible.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                repeatvisible.startAnimation(animFadeIn);
                repeatvisible.setVisibility(View.GONE);
                buttonrepeat.setVisibility(View.VISIBLE);

                mediaplayer.setLooping(false);
            }
        });

        greenshuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fadein);
                greenshuffle.startAnimation(animFadeIn);

                greenshuffle.setVisibility(View.GONE);
                buttonShuffle.setVisibility(View.VISIBLE);

                mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(MediaPlayer mp) {

                        mediaplayer.stop();
                    }
                });

            }
        });


        mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(final MediaPlayer theMediaPlayer) {
                body = tt1.getText().toString();
                mediaplayer.stop();
                mediaplayer.reset();


                StringRequest request1 = new StringRequest(Request.Method.POST, nextaudiourl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            mediaplayer.stop();
                            mediaplayer.reset();
                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                                JSONArray genre = jsonObject.getJSONArray("category_details");


                                for (int j = 0; j < genre.length(); j++) {



                                    JSONObject dataobj1 = genre.getJSONObject(j);

                                    audiocategory = dataobj1.getString("name");



                                }


                                String cat = jsonObject.getString("audio_cat_name");
                                JSONArray dataArray = jsonObject.getJSONArray("audio_details");

                                for (int i = 0; i < dataArray.length(); i++) {

                                    JSONObject dataobj = dataArray.getJSONObject(i);


                                    audiotitlle = dataobj.getString("title");
                                    audiourl = dataobj.getString("mp3_url");
                                    id = dataobj.getString("id");
                                    wer3 = dataobj.getString("image");
                                    body3 = dataobj.getString("slug");
                                    String image1 = Imageurl + wer3;


                                    tt1.getText().toString();


                                    Picasso.get().load(image1).into(audioimage);
                                    audtitle.setText(audiotitlle);
                                    audalbum.setText(audiocategory);


                                    mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                    try {
                                        mediaplayer.setDataSource(audiourl);

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


                                    seekbar.setMax((int) finalTime);

                                    tx2.setText(String.format("%02d : %02d ",
                                            TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                            TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                                    TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                            finalTime)))
                                    );


                                    seekbar.setProgress((int) startTime);
                                    myHandler.postDelayed(UpdateSongTime, 1000);
                                    mediaplayer.start();


                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Toast.makeText(MusicPlayerActivity.this, "Check your internet connection ", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("id", tt1.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue queue = Volley.newRequestQueue(MusicPlayerActivity.this);
                queue.add(request1);
            }


        });



        buttonShuffle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Animation animFadeIn = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fadein);
                buttonShuffle.startAnimation(animFadeIn);

                greenshuffle.setVisibility(View.VISIBLE);
                buttonShuffle.setVisibility(View.GONE);


                mediaplayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    public void onCompletion(final MediaPlayer theMediaPlayer) {
                        body = tt1.getText().toString();
                        mediaplayer.reset();


                        StringRequest request1 = new StringRequest(Request.Method.POST, nextaudiourl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObject = new JSONObject(response);

                                    mediaplayer.stop();
                                    mediaplayer.reset();
                                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                                        JSONArray genre = jsonObject.getJSONArray("category_details");


                                        for (int j = 0; j < genre.length(); j++) {



                                            JSONObject dataobj1 = genre.getJSONObject(j);

                                            audiocategory = dataobj1.getString("name");



                                        }


                                        String cat = jsonObject.getString("audio_cat_name");
                                        JSONArray dataArray = jsonObject.getJSONArray("audio_details");

                                        for (int i = 0; i < dataArray.length(); i++) {

                                            JSONObject dataobj = dataArray.getJSONObject(i);


                                            audiotitlle = dataobj.getString("title");
                                            audiourl = dataobj.getString("mp3_url");
                                            id = dataobj.getString("id");
                                            wer3 = dataobj.getString("image");
                                            body3 = dataobj.getString("slug");
                                            String image1 = Imageurl + wer3;


                                            tt1.getText().toString();


                                            Picasso.get().load(image1).into(audioimage);
                                            audtitle.setText(audiotitlle);
                                            audalbum.setText(audiocategory);


                                            mediaplayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                                            try {
                                                mediaplayer.setDataSource(audiourl);

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


                                            seekbar.setMax((int) finalTime);

                                            tx2.setText(String.format("%02d : %02d ",
                                                    TimeUnit.MILLISECONDS.toMinutes((long) finalTime),
                                                    TimeUnit.MILLISECONDS.toSeconds((long) finalTime) -
                                                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long)
                                                                    finalTime)))
                                            );


                                            seekbar.setProgress((int) startTime);
                                            myHandler.postDelayed(UpdateSongTime, 1000);
                                            mediaplayer.start();


                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }

                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                Toast.makeText(MusicPlayerActivity.this, "Check your internet connection ", Toast.LENGTH_LONG).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                parameters.put("id", tt1.getText().toString());
                                return parameters;
                            }
                        };

                        RequestQueue queue = Volley.newRequestQueue(MusicPlayerActivity.this);
                        queue.add(request1);
                    }


                });


            }
        });


        upnextrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(MusicPlayerActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                        mediaplayer.stop();
                        mediaplayer.reset();
                        if(relatedaudlist.size()>position){
                            if (relatedaudlist.get(position)!= null) {

                                if (relatedaudlist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                                    StringRequest audiorequest = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {


                                            try {
                                                JSONObject jsonObject = new JSONObject(response);

                                                if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                                    if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                                        Intent in = new Intent(MusicPlayerActivity.this, PayperviewAudioActivity.class);
                                                        in.putExtra("id", relatedaudlist.get(position).getId());
                                                        startActivity(in);

                                                    } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                                        Intent in = new Intent(MusicPlayerActivity.this, PayperviewAudioActivity.class);
                                                        in.putExtra("id", relatedaudlist.get(position).getId());
                                                        startActivity(in);
                                                    } else {

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
                                                            pausebutton1.setVisibility(View.VISIBLE);
                                                            playbutton1.setVisibility(View.GONE);

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
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(MusicPlayerActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

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
                                    RequestQueue audioqueue = Volley.newRequestQueue(MusicPlayerActivity.this);
                                    audioqueue.add(audiorequest);

                                }

                                else {

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
                                        pausebutton1.setVisibility(View.VISIBLE);
                                        playbutton1.setVisibility(View.GONE);
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
                                            Toast.makeText(MusicPlayerActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("audioid", relatedaudlist.get(position).getId());
                                            return parameters;
                                        }
                                    };
                                    RequestQueue audioqueue = Volley.newRequestQueue(MusicPlayerActivity.this);
                                    audioqueue.add(audiorequest);
                                }

                            }
                        }

                    }
                })
        );


        similarrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(MusicPlayerActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                        mediaplayer.stop();

                        if(similarlist.size()>position){
                            if (similarlist.get(position)!= null){



                                StringRequest audiorequest = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {


                                        try {
                                            JSONObject jsonObject = new JSONObject(response);

                                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                                    Intent in = new Intent(MusicPlayerActivity.this, PayperviewAudioActivity.class);
                                                    in.putExtra("id", similarlist.get(position).getId());
                                                    startActivity(in);

                                                } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                                    Intent in = new Intent(MusicPlayerActivity.this, PayperviewAudioActivity.class);
                                                    in.putExtra("id", similarlist.get(position).getId());
                                                    startActivity(in);
                                                } else {

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
                                                        pausebutton1.setVisibility(View.VISIBLE);
                                                        playbutton1.setVisibility(View.GONE);
                                                        mediaplayer.start();

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
                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }

                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError volleyError) {
                                        Toast.makeText(MusicPlayerActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {

                                        Map<String, String> parameters = new HashMap<String, String>();
                                        parameters.put("audioid", similarlist.get(position).getId());
                                        parameters.put("user_id",user_id);
                                        return parameters;
                                    }
                                };
                                RequestQueue audioqueue = Volley.newRequestQueue(MusicPlayerActivity.this);
                                audioqueue.add(audiorequest);
                            }
                        }

                    }
                })
        );

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
