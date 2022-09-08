package com.webnexs.flicknexs;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.AspectRatioFrameLayout;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class LiveActivityaaa extends AppCompatActivity {


    String livestreamdetails=Base_URL+"single_livestream";
    String wishlistvid=Base_URL+"AddToWishlistMovie";
    String userwish=Base_URL+"ShowWishlist";
    String alsolikeurl=Base_URL+"alsolikeVideo";


    private List<Movies> movieslist = new ArrayList<Movies>();
    MoviesAdopter movadopter;

    private Handler myHandler = new Handler();

    private List<Thismaylike> thismaylikeslist = new ArrayList<Thismaylike>();
    ThismaylikeAdopter thismaylikeadopter;

    LinearLayout share;

    RecyclerView qualityrecycler;

    Dialog mBottomSheetDialog;

    QualityAdopter qualityAdopter;
    private List<quality> qulitylist = new ArrayList<quality>();


    LinearLayout watchlist;
    ImageView watchlistimg,watchlistaddedimg;

    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/";
    TextView videotext,videotitle;
    RecyclerView vieodetailsrecycler,videotailerrecycler,thismaylikerecycler;
    videodetailsadopter videosadopter;
    private String genre1,shareurl;

    private SimpleExoPlayer player;
    SimpleExoPlayerView simpleExoPlayerView;
    boolean fullscreen = false;
    String xx;
    String user_id;

    DataSource.Factory dataSourceFactory;
    ExtractorsFactory extractorsFactory;


    ImageView exoplay,exopause,exosettings,exoforward,exorewind,screenrotation,screenrotation1,vidback,exoreply;
    ProgressBar exoprogress;
    LinearLayout hidelayout1;
     String language;
     TextView currentdur,finaldur;
    private long startTime=0;
     long finalTime=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liveplay);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        videotext=(TextView)findViewById(R.id.videotext);
        videotitle=(TextView)findViewById(R.id.videotitle);
        watchlist=(LinearLayout)findViewById(R.id.watchlist);
        watchlistimg=(ImageView)findViewById(R.id.watchlistimg);
        watchlistaddedimg=(ImageView)findViewById(R.id.watchlistaddimg);
        share=(LinearLayout)findViewById(R.id.share);
        currentdur=(TextView)findViewById(R.id.currentdur);
        finaldur=(TextView)findViewById(R.id.duration);


        exoplay=(ImageView) findViewById(R.id.xplay);
        exopause=(ImageView) findViewById(R.id.xpause);
        exoreply=(ImageView) findViewById(R.id.xreply);
        exoforward=(ImageView) findViewById(R.id.xforward);
        exorewind=(ImageView) findViewById(R.id.xrewind);
        exosettings=(ImageView) findViewById(R.id.settings);
        exoprogress=(ProgressBar) findViewById(R.id.videobuffeering1);


        screenrotation=(ImageView)findViewById(R.id.screenrotation);
        hidelayout1=(LinearLayout)findViewById(R.id.hidelayout);
        screenrotation1=(ImageView)findViewById(R.id.screenrotation1);


        thismaylikerecycler = (RecyclerView) findViewById(R.id.thismayalsolike);
        thismaylikerecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        thismaylikeadopter = new ThismaylikeAdopter(thismaylikeslist);


        vidback=(ImageView)findViewById(R.id.vidback);


        vidback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                    screenrotation.setVisibility(View.VISIBLE);
                    screenrotation1.setVisibility(View.GONE);
                    hidelayout1.setVisibility(View.VISIBLE);

                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                            WindowManager.LayoutParams.FLAG_FULLSCREEN);


                }
                else
                {
                    player.stop();
                    Intent in=new Intent(LiveActivityaaa.this,UserHomeActivity.class);
                    startActivity(in);
                }
            }
        });



         dataSourceFactory =
                new DefaultDataSourceFactory(this, Util.getUserAgent(this, "CloudinaryExoplayer"));


          extractorsFactory = new DefaultExtractorsFactory();



        Intent in=getIntent();
         xx=in.getStringExtra("id");


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

        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
        } else {
            // In portrait
        }

        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
         user_id = prefs.getString(ssharedpreferences.user_id,null );
        final String user_role = prefs.getString(ssharedpreferences.role,null );

        vieodetailsrecycler = (RecyclerView) findViewById(R.id.videodetails);
        vieodetailsrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        movadopter = new MoviesAdopter(movieslist);

/*
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
                parameters.put("movie_id",xx);


                return parameters;
            }
        };

        RequestQueue queue2 = Volley.newRequestQueue(LiveActivityaaa.this);
        queue2.add(request);
        queue2.getCache().clear();

*/




        StringRequest request4 = new StringRequest(Request.Method.POST, livestreamdetails, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        JSONArray dataArray = jsonObject.getJSONArray("livestream_detail");



                        for (int i = 0; i < dataArray.length(); i++) {



                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String videoid = dataobj.getString("id");
                            String videoname = dataobj.getString("title");
                            String videosdmp4 = dataobj.getString("dacast_url");
                            String videodetails=dataobj.getString("description");


                                Movies mov = new Movies();
                                mov.setId(videoid);
                                mov.setName(videoname);
                                mov.setUrl(videosdmp4);
                                mov.setLanguage(language);
                                mov.setGenre(genre1);


                                movieslist.add(mov);
                                movadopter.notifyDataSetChanged();
                                videotext.setText(videodetails);



                            Uri videoUri = Uri.parse(videosdmp4);

                            final HlsMediaSource hlsMediaSource =
                                    new HlsMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

                            player.prepare(hlsMediaSource);
                            player.setPlayWhenReady(true);
                            finalTime=player.getDuration();

                            myHandler.postDelayed(UpdateSongTime, 1000);


                        }
                        }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LiveActivityaaa.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("livestream_id",xx);
                parameters.put("user_id",user_id);
                return parameters;
            }
        };

        RequestQueue queue4 = Volley.newRequestQueue(LiveActivityaaa.this);
        queue4.add(request4);
        queue4.getCache().clear();


        vieodetailsrecycler.setHasFixedSize(true);

        vieodetailsrecycler.setItemAnimator(new DefaultItemAnimator());

        vieodetailsrecycler.setAdapter(movadopter);

    }


    @Override
    protected void onStart() {
        super.onStart();
        initializePlayer();
    }

    private void initializePlayer() {
        LoadControl loadControl = new DefaultLoadControl(
                new DefaultAllocator(true, 16),
                VideoPlayerConfig.MIN_BUFFER_DURATION,
                VideoPlayerConfig.MAX_BUFFER_DURATION,
                VideoPlayerConfig.MIN_PLAYBACK_START_BUFFER,
                VideoPlayerConfig.MIN_PLAYBACK_RESUME_BUFFER, -1, true);

        TrackSelection.Factory adaptiveTrackSelection = new AdaptiveTrackSelection.Factory(new DefaultBandwidthMeter());
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getApplicationContext()),
                new DefaultTrackSelector(adaptiveTrackSelection), loadControl);


    /*    DefaultTrackSelector trackSelector = new DefaultTrackSelector();
        trackSelector.setParameters(
                trackSelector
                        .buildUponParameters()
                        .setMaxVideoSizeSd()
                        .setPreferredAudioLanguage("deu"));
        player = ExoPlayerFactory.newSimpleInstance(this, trackSelector);
*/

        simpleExoPlayerView = findViewById(R.id.exoplayer);
        simpleExoPlayerView.setPlayer(player);

        simpleExoPlayerView.setResizeMode(AspectRatioFrameLayout.RESIZE_MODE_FILL);


        player.addListener(new ExoPlayer.EventListener() {

            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {



                switch (playbackState) {
                    case Player.STATE_ENDED:

                        exoreply.setVisibility(View.VISIBLE);
                        exopause.setVisibility(View.GONE);
                        exorewind.setVisibility(View.GONE);
                        exoforward.setVisibility(View.GONE);

                     /*   player.seekTo(0);
                        player.setPlayWhenReady(true);
*/

                    case Player.STATE_READY:


                        exoprogress.setVisibility(View.GONE);
                        exopause.setVisibility(View.VISIBLE);

                        break;
                    case Player.STATE_BUFFERING:

                        exopause.setVisibility(View.GONE);
                        exoprogress.setVisibility(View.VISIBLE);

                        break;
                    case Player.STATE_IDLE:



                        break;

                }
            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });


        screenrotation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                screenrotation1.setVisibility(View.VISIBLE);
                screenrotation.setVisibility(View.GONE);

                hidelayout1.setVisibility(View.GONE);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });

        screenrotation1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                screenrotation.setVisibility(View.VISIBLE);
                screenrotation1.setVisibility(View.GONE);
                hidelayout1.setVisibility(View.VISIBLE);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        });



        exoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exopause.setVisibility(View.VISIBLE);
                exoplay.setVisibility(View.GONE);
                player.setPlayWhenReady(true);

            }
        });

        exoreply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exopause.setVisibility(View.VISIBLE);
                exoreply.setVisibility(View.GONE);
                exoplay.setVisibility(View.GONE);
                player.seekTo(0);
                player.setPlayWhenReady(true);
                exoforward.setVisibility(View.VISIBLE);
                exorewind.setVisibility(View.VISIBLE);

            }
        });


        exopause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.setPlayWhenReady(false);
                exopause.setVisibility(View.GONE);
                exoplay.setVisibility(View.VISIBLE);
            }
        });

        exorewind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                player.seekTo(player.getCurrentPosition() - 10000);


            }
        });
        exoforward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                player.seekTo(player.getCurrentPosition() + 10000);


            }
        });


        exosettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openBottomSheet();


            }
        });


/*
        StringRequest thismaylikerequest = new StringRequest(Request.Method.POST, alsolikeurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("movies");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String videoid = dataobj.getString("id");
                            String videoname = dataobj.getString("title");
                            String videoimage=dataobj.getString("mobile_image");
                            String videosdmp41=dataobj.getString("mp4_url");
                            String ppvstatus=dataobj.getString("ppv_status");

                            String videosimage1=Imageurl+videoimage;

                            Thismaylike thislike = new Thismaylike();
                            thislike.setId(videoid);
                            thislike.setName(videoname);
                            thislike.setImage(videosimage1);
                            thislike.setUrl(videosdmp41);
                            thislike.setPpvstatus(ppvstatus);

                            thismaylikeslist.add(thislike);
                            thismaylikeadopter.notifyDataSetChanged();


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LiveActivityaaa.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("movie_id",xx);
                return parameters;
            }
        };

        RequestQueue thismaylikequeue = Volley.newRequestQueue(LiveActivityaaa.this);
        thismaylikequeue.add(thismaylikerequest);
        thismaylikequeue.getCache().clear();



        thismaylikerecycler.setHasFixedSize(true);

        thismaylikerecycler.setItemAnimator(new DefaultItemAnimator());

        thismaylikerecycler.setAdapter(thismaylikeadopter);



        thismaylikerecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(LiveActivityaaa.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override public void onItemClick(View view, final int position) {


                if(thismaylikeslist.size()>position){
                    if (thismaylikeslist.get(position)!= null){

                        if (thismaylikeslist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                            StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired") ) {
                                            Intent in=new Intent(LiveActivityaaa.this,payperviewenableActivity.class);
                                            in.putExtra("id",thismaylikeslist.get(position).getId());
                                            startActivity(in);
                                        }
                                        else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                                        {
                                            Intent in=new Intent(LiveActivityaaa.this,payperviewenableActivity.class);
                                            in.putExtra("id",thismaylikeslist.get(position).getId());
                                            startActivity(in);
                                        }
                                        else
                                            {
                                                Intent in=new Intent(LiveActivityaaa.this, LiveActivityaaa.class);
                                                in.putExtra("id",thismaylikeslist.get(position).getId());
                                                startActivity(in);
                                            }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(LiveActivityaaa.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parameters = new HashMap<String, String>();
                                    parameters.put("id",thismaylikeslist.get(position).getId());
                                    parameters.put("user_id",user_id);
                                    return parameters;
                                }
                            };
                            RequestQueue queue4 = Volley.newRequestQueue(LiveActivityaaa.this);
                            queue4.add(request4);
                            queue4.getCache().clear();

                        }
                        else {
                             Intent intent = new Intent(LiveActivityaaa.this, LiveActivityaaa.class);
                                   intent.putExtra("id", thismaylikeslist.get(position).getId());
                                   startActivity(intent);
                        }


                    }
                }
                    }
                })
        );
*/



    }

    private void openBottomSheet() {

        View view = getLayoutInflater().inflate(R.layout.botton_sheet, null);


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );



        qualityrecycler =(RecyclerView)view.findViewById(R.id.quality);
        qualityrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        qualityAdopter = new QualityAdopter(qulitylist);




/*
        StringRequest watchrequest = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        qulitylist.clear();
                        JSONArray dataArray = jsonObject.getJSONArray("movieresolution");
                        for (int i = 0; i < dataArray.length(); i++) {



                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("movie_id");
                            String watchimage=dataobj.getString("quality");
                            String watchmp4=dataobj.getString("url");




                            quality wish = new quality();
                            wish.setId(watchimage);
                            wish.setName(watchname);
                            wish.setUrl(watchmp4);


                            qulitylist.add(wish);
                            qualityAdopter.notifyDataSetChanged();




                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(LiveActivityaaa.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", user_id);
                parameters.put("id",xx);

                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(LiveActivityaaa.this);
        watchqueue.add(watchrequest);
        watchqueue.getCache().clear();

        qualityrecycler.setHasFixedSize(true);

        qualityrecycler.setItemAnimator(new DefaultItemAnimator());

        qualityrecycler.setAdapter(qualityAdopter);


        qualityrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(LiveActivityaaa.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (qulitylist.size() > position) {
                            if (qulitylist.get(position) != null) {

                                mBottomSheetDialog.hide();

                               long xx=player.getContentPosition();

                                Uri videoUri = Uri.parse(qulitylist.get(position).getUrl());
                                MediaSource videoSource = new ExtractorMediaSource(videoUri,
                                        dataSourceFactory, extractorsFactory, null, null);



                                player.prepare(videoSource);
                                player.seekTo(xx);
                                player.setPlayWhenReady(true);

                            }
                        }

                    }
                })
        );

        mBottomSheetDialog = new Dialog(LiveActivityaaa.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();
*/

    }





    @Override
    public void onBackPressed() {

            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                screenrotation.setVisibility(View.VISIBLE);
                screenrotation1.setVisibility(View.GONE);
                hidelayout1.setVisibility(View.VISIBLE);

                getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);


            }
            else
            {
               player.stop();
               Intent in=new Intent(LiveActivityaaa.this,UserHomeActivity.class);
               startActivity(in);
            }

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
           // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            screenrotation1.setVisibility(View.VISIBLE);
            screenrotation.setVisibility(View.GONE);

            hidelayout1.setVisibility(View.GONE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN, WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);

          //  setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            screenrotation.setVisibility(View.VISIBLE);
            screenrotation1.setVisibility(View.GONE);
            hidelayout1.setVisibility(View.VISIBLE);

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @Override
    public void onStop()
    {
        super.onStop();
        player.stop();
    }

    private Runnable UpdateSongTime = new Runnable() {
        public void run() {
            startTime = player.getCurrentPosition();
            currentdur.setText(String.format("%02d : %02d ",
                    TimeUnit.MILLISECONDS.toMinutes((long) startTime),
                    TimeUnit.MILLISECONDS.toSeconds((long) startTime) -
                            TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.
                                    toMinutes((long) startTime)))
            );

            myHandler.postDelayed(this, 100);
        }
    };

}
