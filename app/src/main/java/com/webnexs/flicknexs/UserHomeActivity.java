package com.webnexs.flicknexs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;
import com.webnexs.flicknexs.Adapter.HomeAdapter;
import com.webnexs.flicknexs.Api1.RetrofitSingleton;
import com.webnexs.flicknexs.Model.HomeBodyResponse;
import com.webnexs.flicknexs.Model.HomeData;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit2.Call;


import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class UserHomeActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {



    String latesturl=Base_URL+"latestmovies";

    String moviecaturl=Base_URL+"movie_category";

    String bannerurl=Base_URL+"banners";

    String profileurl=Base_URL+"view_profile";

    String trendingmoviesurl=Base_URL+"trendingmovies";

    String latestaudiourl=Base_URL+"latestaudios";

    String trendingaudiourl=Base_URL+"trendingaudios";

    String profileimageurl=" http://flicknexs.webnexs.org/content/uploads/avatars/";

    String allalbumurl=Base_URL+"allAlbums";

    String videodetailsurl=Base_URL+"movie";

    String audiodetailsurl=Base_URL+"audio";

    String latestaudioenableurl=Base_URL+"mobile_latest_audio";

    String latestmovieenableurl=Base_URL+"mobile_latest_movie";

    String trendingaudioenableurl=Base_URL+"mobile_trending_audio";

    String trendingmovieenableurl=Base_URL+"mobile_trending_movie";

    String getalllivestreaminurl=Base_URL+"get_all_livestreams";

    String livestreamdetails=Base_URL+"single_livestream";

    TextView no_live_streaming,no_trending_video,no_latest_video,no_movie_category,no_latest_audio,no_trending_audio,no_album;



    public static final MediaPlayer mediaplayer= new MediaPlayer();




    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    CustomAdapter customadopter;


    RequestQueue rq;
    List<SliderUtils> sliderImg;
    ViewPagerAdapter viewPagerAdapter;


    RecyclerView moviecategoryrecycler,newuploadsrecycle,sidemenu1recycler,sidemenu2recycler,sidemenu3recycler,allalbumrecycler;
    RecyclerView audiolistrecycler,audiocatrecycler,livestreamrecyle;

    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/images/";
    String Imageurlaudio=" http://flicknexs.webnexs.org/content/uploads/images/";
    String moviecatimage=" http://flicknexs.webnexs.org/content/uploads/genres/";
    String audiocatimage=" http://flicknexs.webnexs.org/content/uploads/albums/";

    private String newuploadid;
    ProgressBar newuploadsprogress,livestreamprogress,moviecatprogress;
    ProgressBar popularmovieprogress,vidcatprogress,audiolistprogress,audiocatprogress;


    private List<newuploads> newuploadsList = new ArrayList<newuploads>();
    newuploadsAdapter newuploadsadapter;

    private List<livestream> livestreamlist = new ArrayList<livestream>();
    livestramAdapter livestramadapter;

    private List<Audiolist> audiolist = new ArrayList<Audiolist>();
    AudiolistAdopter audiolistadopter;

    private List<Audiocat> audiocatlist = new ArrayList<Audiocat>();
    AudiocatAdopter audiocatadopter;

    private List<allalbum> allalbumlist = new ArrayList<allalbum>();
    AllalbumAdopter allalbumadopter;


    private List<moviecatogry> movielist = new ArrayList<moviecatogry>();
    moviecatogryAdopter moviecatgoryadapter;


    private List<trendingmovies> trendinglist = new ArrayList<trendingmovies>();
    trendingadopter trendingadopter;
    RecyclerView trendingrecycler;
    ProgressBar trendingprogress;

    TextView sidemenuusername,sidemenuuseremail;
    ImageView sidemenudp;

    LinearLayout userdetails;

    List<sidemenu1> sidemenulist;
    List<sidemenu2> sidemenulist2;
    List<sidemenu3> sidemenulist3;



    DrawerLayout drawer_layout;

    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    UserListResponse signUpResponsesData;

    TextView viewpagerid;
    String user_id,user_role,social_email,image;
    String status = "false";

     LinearLayout latestaudios,latestvideos,trendingaudios,trendingvideos;



    private static final String TAG = "MainActivity";


    private ArrayList<HomeData> dataList;


    private ProgressBar progressBar,bannerprogress;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    Dialog popUP_dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        SharedPreferences.Editor editor = getSharedPreferences(Constant.MY_PREFS_NAME, MODE_PRIVATE).edit();
        editor.putString(Constant.notify_On, "on");
        editor.apply();

        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        dataList = new ArrayList<HomeData>();

        adapter = new HomeAdapter(dataList, this);
        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);


        recyclerView = findViewById(R.id.rv_main);
        progressBar = findViewById(R.id.pb_home);


        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        user_id = prefs.getString(ssharedpreferences.user_id, null);
        user_role = prefs.getString(ssharedpreferences.role, null);
        status = prefs.getString(ssharedpreferences.status, null);
        social_email = prefs.getString(ssharedpreferences.social_email, null);
        image = prefs.getString(ssharedpreferences.image, null);


        sidemenuuseremail = (TextView) findViewById(R.id.useremail);
        sidemenuusername = (TextView) findViewById(R.id.username);
        sidemenudp = (ImageView) findViewById(R.id.userdp);
        userdetails = (LinearLayout) findViewById(R.id.userdetails);
        viewpagerid = (TextView) findViewById(R.id.viewpagerid);

        latestaudios = (LinearLayout) findViewById(R.id.latestaudios);
        latestvideos = (LinearLayout) findViewById(R.id.latestmovies);
        trendingvideos = (LinearLayout) findViewById(R.id.trendingmovies);
        trendingaudios = (LinearLayout) findViewById(R.id.trendingaudios);

        bannerprogress=(ProgressBar)findViewById(R.id.bannerprogress);
        audiocatprogress=(ProgressBar)findViewById(R.id.audiocatprogress);



        userdetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(UserHomeActivity.this, MyAccountActivity.class);
                startActivity(in);
            }
        });


        sliderImg = new ArrayList<>();

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        sliderDotspanel = (LinearLayout) findViewById(R.id.SliderDots);


        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                 currentPage = 0;

                }

                viewPager.setCurrentItem(currentPage++, true);
                if(currentPage == sliderImg.size()) {

                    currentPage =0;
                }

            }



        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {


            }
        });


        StringRequest requestban = new StringRequest(Request.Method.GET, bannerurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        JSONArray dataArray1 = jsonObject.getJSONArray("banners_live");

                        for (int k = 0; k < dataArray1.length(); k++) {


                            SliderUtils sliderUtils = new SliderUtils();


                            JSONObject dataobj1 = dataArray1.getJSONObject(k);

                            String newuploadsimage = dataobj1.getString("image");
                            String id = dataobj1.getString("id");
                            String ppvstatus = dataobj1.getString("payment_status");
                            String mediatype=dataobj1.getString("type");
                            String image1 = Imageurlaudio + newuploadsimage;

                            sliderUtils.setSliderImageUrl(image1);
                            sliderUtils.setId(id);
                            sliderUtils.setPpvstatus(ppvstatus);
                            sliderUtils.setType(mediatype);
                            sliderUtils.setUserid(user_id);


                            sliderImg.add(sliderUtils);


                            viewPagerAdapter = new ViewPagerAdapter(sliderImg, UserHomeActivity.this);

                            viewPager.setAdapter(viewPagerAdapter);


                        }
                        JSONArray dataArray = jsonObject.getJSONArray("banners");

                        for (int j = 0; j < dataArray.length(); j++) {


                            SliderUtils sliderUtils = new SliderUtils();


                            JSONObject dataobj = dataArray.getJSONObject(j);

                            String newuploadsimage = dataobj.getString("image");
                            String id = dataobj.getString("id");
                            String ppvstatus = dataobj.getString("ppv_status");
                            String image1 = Imageurlaudio + newuploadsimage;



                            sliderUtils.setSliderImageUrl(image1);
                            sliderUtils.setId(id);
                            sliderUtils.setPpvstatus(ppvstatus);
                            sliderUtils.setUserid(user_id);


                            sliderImg.add(sliderUtils);


                            viewPagerAdapter = new ViewPagerAdapter(sliderImg, UserHomeActivity.this);

                            viewPager.setAdapter(viewPagerAdapter);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue queueban = Volley.newRequestQueue(UserHomeActivity.this);
        queueban.add(requestban);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.menu);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                drawer_layout.openDrawer(GravityCompat.START);
            }
        });

        drawer_layout = findViewById(R.id.drawer_layout);


        final ImageView logo = (ImageView) findViewById(R.id.logo);


        ApiClient.getClient().getUsersList(
                new Callback<UserListResponse>() {
                    @Override
                    public void success(UserListResponse signUpResponse, retrofit.client.Response response) {
                        signUpResponsesData = signUpResponse;

                        String x = signUpResponse.getImage_url();
                        String y = signUpResponse.getSettings();
                        String z = x + y;
                        Picasso.get().load(z).into(logo);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // if error occurs in network transaction then we can get the error in this method.
                        Toast.makeText(UserHomeActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }

                });

        newuploadsprogress = (ProgressBar) findViewById(R.id.newuploadprogress);


        audiolistprogress = (ProgressBar) findViewById(R.id.audioprogress);

        moviecatprogress = (ProgressBar) findViewById(R.id.moviecatprogress);

        trendingprogress = (ProgressBar) findViewById(R.id.moviecatogoryprogress);


        newuploadsrecycle = (RecyclerView) findViewById(R.id.newuploadrecyler);
        newuploadsrecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        newuploadsadapter = new newuploadsAdapter(newuploadsList);


        sidemenu1recycler = (RecyclerView) findViewById(R.id.sidemenu1);
        sidemenu1recycler.setHasFixedSize(true);
        sidemenu1recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sidemenu2recycler = (RecyclerView) findViewById(R.id.sidemenu2);
        sidemenu2recycler.setHasFixedSize(true);
        sidemenu2recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        sidemenu3recycler = (RecyclerView) findViewById(R.id.sidemenu3);
        sidemenu3recycler.setHasFixedSize(true);
        sidemenu3recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        moviecategoryrecycler = (RecyclerView) findViewById(R.id.moviecatgoryrecycler1);
        moviecategoryrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        moviecatgoryadapter = new moviecatogryAdopter(movielist);

        trendingrecycler = (RecyclerView) findViewById(R.id.moviecatgoryrecycler);
        trendingrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trendingadopter = new trendingadopter(trendinglist);

        audiolistrecycler = (RecyclerView) findViewById(R.id.audiorecycler);
        audiolistrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        audiolistadopter = new AudiolistAdopter(audiolist);

        audiocatrecycler = (RecyclerView) findViewById(R.id.audiocatrecycler);
        audiocatrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        audiocatadopter = new AudiocatAdopter(audiocatlist);

        allalbumrecycler = (RecyclerView) findViewById(R.id.audioalbumrecycler);
        allalbumrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        allalbumadopter = new AllalbumAdopter(allalbumlist);

        livestreamrecyle = (RecyclerView) findViewById(R.id.livestreamingrecycle);
        livestreamrecyle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        livestramadapter = new livestramAdapter(livestreamlist);


        sidemenulist = new ArrayList<>();
        sidemenulist2 = new ArrayList<>();
        sidemenulist3 = new ArrayList<>();


        no_album=(TextView)findViewById(R.id.no_album);
        no_latest_video=(TextView)findViewById(R.id.no_latest_video);
        no_trending_video=(TextView)findViewById(R.id.no_trending_video);
        no_latest_audio=(TextView)findViewById(R.id.no_latest_audio);
        no_trending_audio=(TextView)findViewById(R.id.no_trending_audio);
        no_movie_category=(TextView)findViewById(R.id.no_movie_category);
        no_live_streaming=(TextView)findViewById(R.id.no_live_streaming);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);


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

                        Intent in1 = new Intent(getApplicationContext(), MyAccountActivity.class);
                        startActivity(in1);

                        break;

                    case R.id.navigation_video:
                        Intent video = new Intent(UserHomeActivity.this, VideoPageActivity.class);
                        startActivity(video);
                        break;

                    case R.id.navigation_audio:
                        Intent audio = new Intent(UserHomeActivity.this, AudioPageActivity.class);
                        startActivity(audio);
                        break;
                }

                return false;
            }
        });

        StringRequest latestaudioenablerequest = new StringRequest(Request.Method.GET, latestmovieenableurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("mobile_latest_movie").equalsIgnoreCase(String.valueOf(1))) {
                        latestvideos.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue latestaudioenablequeue = Volley.newRequestQueue(UserHomeActivity.this);
        latestaudioenablequeue.add(latestaudioenablerequest);



        Call<HomeBodyResponse> responseCall = RetrofitSingleton.getInstance().getApi().getMovieByCategory();

        responseCall.enqueue(new retrofit2.Callback<HomeBodyResponse>() {
            @Override
            public void onResponse(Call<HomeBodyResponse> call, retrofit2.Response<HomeBodyResponse> response) {


                if(response.isSuccess()){

                    for (HomeData data : response.body().getGenreMovies()) {

                        dataList.add(data);

                    }
                    adapter.notifyDataSetChanged();
                }
                
                else{
                    Toast.makeText(UserHomeActivity.this, "No data", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<HomeBodyResponse> call, Throwable t) {



            }
        });


        StringRequest request4 = new StringRequest(Request.Method.GET, latesturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("latest_movies");

                        if(dataArray.length() ==0) {
                            newuploadsrecycle.setVisibility(View.GONE);
                            no_latest_video.setVisibility(View.VISIBLE);

                        }
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            newuploadid = dataobj.getString("id");
                            String newuploadname = dataobj.getString("title");
                            String newuploadsimage = dataobj.getString("image");
                            String newuploadmp4 = dataobj.getString("mp4_url");
                            String ppvstatus = dataobj.getString("ppv_status");
                            String image1 = Imageurl + newuploadsimage;


                            String active=dataobj.getString("active");



                                newuploads uploads = new newuploads();
                                uploads.setId(newuploadid);
                                uploads.setName(newuploadname);
                                uploads.setImage(image1);
                                uploads.setPpvstatus(ppvstatus);
                                uploads.setUrl(newuploadmp4);

                                newuploadsList.add(uploads);
                                newuploadsadapter.notifyDataSetChanged();

                            }
                        }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue queue4 = Volley.newRequestQueue(UserHomeActivity.this);
        queue4.add(request4);


        newuploadsrecycle.setHasFixedSize(true);

        newuploadsrecycle.setItemAnimator(new DefaultItemAnimator());

        newuploadsrecycle.setAdapter(newuploadsadapter);

        newuploadsrecycle.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {


                        if (newuploadsList.size() > position) {
                            if (newuploadsList.get(position) != null) {

                                if (newuploadsList.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {


                                    StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                                    Intent in = new Intent(UserHomeActivity.this, payperviewenableActivity.class);
                                                    in.putExtra("id", newuploadsList.get(position).getId());
                                                    startActivity(in);

                                                } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                                    Intent in = new Intent(UserHomeActivity.this, payperviewenableActivity.class);
                                                    in.putExtra("id", newuploadsList.get(position).getId());
                                                    startActivity(in);
                                                } else {
                                                    Intent in = new Intent(UserHomeActivity.this, MoviesActivity.class);
                                                    in.putExtra("id", newuploadsList.get(position).getId());
                                                    startActivity(in);
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("id", newuploadsList.get(position).getId());
                                            parameters.put("user_id", user_id);
                                            return parameters;
                                        }
                                    };

                                    RequestQueue queue4 = Volley.newRequestQueue(UserHomeActivity.this);
                                    queue4.add(request4);


                                } else {
                                    Intent intent = new Intent(UserHomeActivity.this, MoviesActivity.class);
                                    intent.putExtra("id", newuploadsList.get(position).getId());
                                    startActivity(intent);
                                }

                            }
                        }
                    }


                })
        );



        StringRequest livestramrequest = new StringRequest(Request.Method.GET, getalllivestreaminurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("livestreams");

                        if(dataArray.length() == 0) {

                            livestreamrecyle.setVisibility(View.GONE);
                            no_live_streaming.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            newuploadid = dataobj.getString("id");
                            String newuploadname = dataobj.getString("title");
                            String newuploadsimage = dataobj.getString("image");
                            String newuploadmp4 = dataobj.getString("dacast_url");
                            String ppvstatus = dataobj.getString("payment_status");
                            String image1 = Imageurl + newuploadsimage;

                            livestream uploads = new livestream();
                            uploads.setId(newuploadid);
                            uploads.setName(newuploadname);
                            uploads.setImage(image1);
                            uploads.setPpvstatus(ppvstatus);
                            uploads.setUrl(newuploadmp4);

                            livestreamlist.add(uploads);
                            livestramadapter.notifyDataSetChanged();


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue livequeue = Volley.newRequestQueue(UserHomeActivity.this);
        livequeue.add(livestramrequest);


        livestreamrecyle.setHasFixedSize(true);

        livestreamrecyle.setItemAnimator(new DefaultItemAnimator());

        livestreamrecyle.setAdapter(livestramadapter);


        livestreamrecyle.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                        if (livestreamlist.size() > position) {
                            if (livestreamlist.get(position) != null) {

                                if (livestreamlist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                                    StringRequest request4 = new StringRequest(Request.Method.POST, livestreamdetails, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                                    Intent in = new Intent(UserHomeActivity.this, LivepaymentEnableActivity.class);
                                                    in.putExtra("id", livestreamlist.get(position).getId());
                                                    startActivity(in);
                                                } else {
                                                    Intent in = new Intent(UserHomeActivity.this, LiveActivityaaa.class);
                                                    in.putExtra("id", livestreamlist.get(position).getId());
                                                    startActivity(in);
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("livestream_id", livestreamlist.get(position).getId());
                                            parameters.put("user_id", user_id);
                                            return parameters;
                                        }
                                    };

                                    RequestQueue queue4 = Volley.newRequestQueue(UserHomeActivity.this);
                                    queue4.add(request4);


                                } else {
                                    Intent intent = new Intent(UserHomeActivity.this, LiveActivityaaa.class);
                                    intent.putExtra("id", livestreamlist.get(position).getId());
                                    startActivity(intent);
                                }



                            }
                        }
                    }


                })
        );


        StringRequest latestmovieenablerequest = new StringRequest(Request.Method.GET, latestaudioenableurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("mobile_latest_audio").equalsIgnoreCase(String.valueOf(1))) {

                        latestaudios.setVisibility(View.VISIBLE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue latestmovieenablequeue = Volley.newRequestQueue(UserHomeActivity.this);
        latestmovieenablequeue.add(latestmovieenablerequest);


        StringRequest allalbumrequest = new StringRequest(Request.Method.GET, allalbumurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("audio_albums");

                        if(dataArray.length() ==0) {

                            allalbumrecycler.setVisibility(View.GONE);
                            no_album.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject dataobj = dataArray.getJSONObject(i);

                            newuploadid = dataobj.getString("id");
                            String newuploadname = dataobj.getString("albumname");
                            String newuploadsimage=dataobj.getString("album");
                            String newuploadmp4 = dataobj.getString("parent_id");
                            String image1=audiocatimage+newuploadsimage;

                            allalbum uploads = new allalbum();
                            uploads.setId(newuploadid);
                            uploads.setName(newuploadname);
                            uploads.setImage(image1);
                            uploads.setUrl(newuploadmp4);

                            allalbumlist.add(uploads);
                            allalbumadopter.notifyDataSetChanged();

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
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue allalbumqueue = Volley.newRequestQueue(UserHomeActivity.this);
        allalbumqueue.add(allalbumrequest);


        allalbumrecycler.setHasFixedSize(true);

        allalbumrecycler.setItemAnimator(new DefaultItemAnimator());

        allalbumrecycler.setAdapter(allalbumadopter);


        allalbumrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        if (allalbumlist.size() > position) {
                            if (allalbumlist.get(position) != null) {
                                Intent intent = new Intent(UserHomeActivity.this, AudioCategorypageActivity.class);
                                intent.putExtra("id", allalbumlist.get(position).getId());
                                startActivity(intent);

                            }
                        }

                    }
                })
        );


        StringRequest trendingaudioenablerequest = new StringRequest(Request.Method.GET, trendingmovieenableurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("mobile_trending_movie").equalsIgnoreCase(String.valueOf(1))) {

                        trendingvideos.setVisibility(View.VISIBLE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue trendingaudioenablequeue = Volley.newRequestQueue(UserHomeActivity.this);
        trendingaudioenablequeue.add(trendingaudioenablerequest);

        StringRequest trendingrequest = new StringRequest(Request.Method.GET, trendingmoviesurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        trendingprogress.setVisibility(View.GONE);

                        JSONArray dataArray = jsonObject.getJSONArray("trending_movies");

                        if(dataArray.length() == 0) {

                            trendingrecycler.setVisibility(View.GONE);
                            no_trending_video.setVisibility(View.VISIBLE);
                        }

                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String moviecatid = dataobj.getString("id");
                            String moviecatname = dataobj.getString("title");
                            String moviecatparentid = dataobj.getString("mp4_url");
                            String moviecatimg = dataobj.getString("image");
                            String ppvstatus = dataobj.getString("ppv_status");
                            String image1 = Imageurl + moviecatimg;

                            trendingmovies movie = new trendingmovies();
                            movie.setId(moviecatid);
                            movie.setName(moviecatname);
                            movie.setImage(image1);
                            movie.setUrl(moviecatparentid);
                            movie.setPpvstatus(ppvstatus);

                            trendinglist.add(movie);
                            trendingadopter.notifyDataSetChanged();

                            trendingprogress.setVisibility(View.GONE);


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue moviecatqueue = Volley.newRequestQueue(UserHomeActivity.this);
        moviecatqueue.add(trendingrequest);

        trendingrecycler.setHasFixedSize(true);

        trendingrecycler.setItemAnimator(new DefaultItemAnimator());

        trendingrecycler.setAdapter(trendingadopter);


        trendingrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {


                        if (trendinglist.size() > position) {
                            if (trendinglist.get(position) != null) {

                                if (trendinglist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                                    StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {
                                                    Intent in = new Intent(UserHomeActivity.this, payperviewenableActivity.class);
                                                    in.putExtra("id", trendinglist.get(position).getId());
                                                    startActivity(in);
                                                } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                                    Intent in = new Intent(UserHomeActivity.this, payperviewenableActivity.class);
                                                    in.putExtra("id", trendinglist.get(position).getId());
                                                    startActivity(in);
                                                } else {
                                                    Intent in = new Intent(UserHomeActivity.this, MoviesActivity.class);
                                                    in.putExtra("id", trendinglist.get(position).getId());
                                                    startActivity(in);
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("id", trendinglist.get(position).getId());
                                            parameters.put("user_id", user_id);
                                            return parameters;
                                        }
                                    };
                                    RequestQueue queue4 = Volley.newRequestQueue(UserHomeActivity.this);
                                    queue4.add(request4);


                                } else {
                                    Intent intent = new Intent(UserHomeActivity.this, MoviesActivity.class);
                                    intent.putExtra("id", trendinglist.get(position).getId());
                                    startActivity(intent);
                                }


                            }
                        }

                    }
                })
        );


        StringRequest moviecatrequest = new StringRequest(Request.Method.GET, moviecaturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        JSONArray dataArray = jsonObject.getJSONArray("movie_categories");

                        if(dataArray.length()==0) {

                            moviecategoryrecycler.setVisibility(View.GONE);
                            no_movie_category.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String moviecatid = dataobj.getString("id");
                            String moviecatname = dataobj.getString("name");
                            String moviecatparentid = dataobj.getString("parent_id");
                            String moviecatimg = dataobj.getString("image");
                            String image1 = moviecatimage + moviecatimg;

                            moviecatogry movie = new moviecatogry();
                            movie.setId(moviecatid);
                            movie.setName(moviecatname);
                            movie.setImage(image1);
                            movie.setUrl(moviecatparentid);

                            movielist.add(movie);
                            moviecatgoryadapter.notifyDataSetChanged();
                            moviecatprogress.setVisibility(View.GONE);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue moviecatqueue1 = Volley.newRequestQueue(UserHomeActivity.this);
        moviecatqueue1.add(moviecatrequest);



        moviecategoryrecycler.setItemAnimator(new DefaultItemAnimator());

        moviecategoryrecycler.setAdapter(moviecatgoryadapter);

        moviecategoryrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (movielist.size() > position) {
                            if (movielist.get(position) != null) {
                                Intent intent = new Intent(UserHomeActivity.this, MovieCategoryActivity.class);
                                intent.putExtra("id", movielist.get(position).getId());
                                startActivity(intent);
                            }
                        }

                    }
                })
        );


        StringRequest trendingvideoenablerequest = new StringRequest(Request.Method.GET, trendingaudioenableurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("mobile_trending_audio").equalsIgnoreCase(String.valueOf(1))) {


                        trendingaudios.setVisibility(View.VISIBLE);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue trendingvideoenablequeue = Volley.newRequestQueue(UserHomeActivity.this);
        trendingvideoenablequeue.add(trendingvideoenablerequest);


        StringRequest audiorequest = new StringRequest(Request.Method.GET, latestaudiourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    audiolistprogress.setVisibility(View.GONE);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("latestaudios");

                        if(dataArray.length()==0) {

                            audiolistrecycler.setVisibility(View.GONE);
                            no_latest_audio.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);
                            String audioid = dataobj.getString("id");
                            String audioname = dataobj.getString("title");
                            String audioimage = dataobj.getString("image");
                            String audiomp3 = dataobj.getString("mp3_url");
                            String ppvstatus = dataobj.getString("ppv_status");
                            String image1 = Imageurlaudio + audioimage;

                            Audiolist aud = new Audiolist();
                            aud.setId(audioid);
                            aud.setName(audioname);
                            aud.setImage(image1);
                            aud.setUrl(audiomp3);
                            aud.setPpvstatus(ppvstatus);
                            audiolist.add(aud);
                            audiolistadopter.notifyDataSetChanged();


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue audiolistqueue = Volley.newRequestQueue(UserHomeActivity.this);
        audiolistqueue.add(audiorequest);


        audiolistrecycler.setHasFixedSize(true);

        audiolistrecycler.setItemAnimator(new DefaultItemAnimator());

        audiolistrecycler.setAdapter(audiolistadopter);


        audiolistrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                        mediaplayer.stop();
                        mediaplayer.reset();

                        if (audiolist.size() > position) {
                            if (audiolist.get(position) != null) {

                                if (audiolist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                                    StringRequest request4 = new StringRequest(Request.Method.POST, audiodetailsurl, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                                    Intent in = new Intent(UserHomeActivity.this, PayperviewAudioActivity.class);
                                                    in.putExtra("id", audiolist.get(position).getId());
                                                    startActivity(in);

                                                } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                                    Intent in = new Intent(UserHomeActivity.this, PayperviewAudioActivity.class);
                                                    in.putExtra("id", audiolist.get(position).getId());
                                                    startActivity(in);
                                                } else {
                                                    Intent in = new Intent(UserHomeActivity.this, MusicPlayerActivity.class);
                                                    in.putExtra("id", audiolist.get(position).getId());
                                                    startActivity(in);
                                                }


                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError volleyError) {
                                            Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("audioid", audiolist.get(position).getId());
                                            parameters.put("user_id", user_id);
                                            return parameters;
                                        }
                                    };

                                    RequestQueue queue4 = Volley.newRequestQueue(UserHomeActivity.this);
                                    queue4.add(request4);


                                } else {
                                    Intent intent = new Intent(UserHomeActivity.this, MusicPlayerActivity.class);
                                    intent.putExtra("id", audiolist.get(position).getId());
                                    startActivity(intent);
                                }


                            }

                        }
                    }
                }));


        StringRequest audiocatrequest = new StringRequest(Request.Method.GET, trendingaudiourl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);

                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                        audiocatprogress.setVisibility(View.GONE);

                        JSONArray dataArray = jsonObject.getJSONArray("trending_audios");


                        if (dataArray.length()==0) {

                            audiocatrecycler.setVisibility(View.GONE);
                            no_trending_audio.setVisibility(View.VISIBLE);
                        }
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String audioid = dataobj.getString("id");
                            String audioname = dataobj.getString("title");
                            String audioimage = dataobj.getString("image");
                            String audiomp3 = dataobj.getString("mp3_url");
                            String ppvstatus = dataobj.getString("ppv_status");
                            String image1 = Imageurl + audioimage;

                            Audiocat aud = new Audiocat();
                            aud.setId(audioid);
                            aud.setName(audioname);
                            aud.setImage(image1);
                            aud.setUrl(audiomp3);
                            aud.setPpvstatus(ppvstatus);


                            audiocatlist.add(aud);
                            audiocatadopter.notifyDataSetChanged();


                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue audiocatqueue = Volley.newRequestQueue(UserHomeActivity.this);
        audiocatqueue.add(audiocatrequest);


        audiocatrecycler.setHasFixedSize(true);

        audiocatrecycler.setItemAnimator(new DefaultItemAnimator());

        audiocatrecycler.setAdapter(audiocatadopter);


        audiocatrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {


                        if (audiocatlist.size() > position) {
                            if (audiocatlist.get(position) != null) {


                                if (audiocatlist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                                    StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            try {
                                                JSONObject jsonObject = new JSONObject(response);
                                                if (jsonObject.getString("ppv_exist").equalsIgnoreCase(String.valueOf(0))) {

                                                    Intent in = new Intent(UserHomeActivity.this, payperviewenableActivity.class);
                                                    in.putExtra("id", audiocatlist.get(position).getId());
                                                    startActivity(in);

                                                } else {
                                                    Intent in = new Intent(UserHomeActivity.this, MoviesActivity.class);
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
                                            Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {
                                            Map<String, String> parameters = new HashMap<String, String>();
                                            parameters.put("id", audiocatlist.get(position).getId());
                                            parameters.put("user_id", user_id);
                                            return parameters;
                                        }
                                    };
                                    RequestQueue queue4 = Volley.newRequestQueue(UserHomeActivity.this);
                                    queue4.add(request4);

                                } else {
                                    Intent intent = new Intent(UserHomeActivity.this, MusicPlayerActivity.class);
                                    intent.putExtra("id", audiocatlist.get(position).getId());
                                    startActivity(intent);
                                }
                            }
                        }
                    }
                }));


        sidemenulist.add(
                new sidemenu1(
                        "Audios",
                        R.drawable.audios1,
                        "Click here to more audios"));


        sidemenulist.add(
                new sidemenu1(
                        "Videos",
                        R.drawable.tv,
                        "Click here to more Videos"));

        sidemenulist.add(
                new sidemenu1(
                        "Watchlist",
                        R.drawable.watchlist,
                        "Save To Watch Later"));

        sidemenulist.add(
                new sidemenu1(
                        "Categories",
                        R.drawable.genres,
                        "To view All video Categories"));


        sidemennuAdopter adapter = new sidemennuAdopter(sidemenulist);


        sidemenu1recycler.setAdapter(adapter);


        sidemenu1recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        if (position == 0) {

                            Intent audio = new Intent(UserHomeActivity.this, AudioPageActivity.class);
                            startActivity(audio);

                        }

                        if (position == 1) {


                            Intent in = new Intent(UserHomeActivity.this, VideoPageActivity.class);
                            startActivity(in);

                        }
                        if (position == 2) {


                            Intent in = new Intent(UserHomeActivity.this, WatchlistActivity.class);
                            startActivity(in);
                        }
                        if (position == 3) {

                            Intent in = new Intent(UserHomeActivity.this, MoviecategorylistActivity.class);
                            startActivity(in);
                        }


                    }
                })
        );


        sidemenu3recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {


                        if (position == 0) {


                            Intent in = new Intent(getApplicationContext(), FaqActivity.class);
                            startActivity(in);
                        }

                        if (position == 1) {


                            Intent in = new Intent(getApplicationContext(), TermsAndConditionActivity.class);
                            startActivity(in);

                        } else if (position == 2) {

                            final SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, Context.MODE_PRIVATE);
                            final SharedPreferences.Editor editor = prefs.edit();
                            editor.clear();
                            editor.apply();

                            if(status == "true") {
                                editor.clear();
                                editor.apply();
                                LoginManager.getInstance().logOut();
                            }
                            Auth.GoogleSignInApi.signOut(googleApiClient).setResultCallback(
                                    new ResultCallback<Status>() {
                                        @Override
                                        public void onResult(Status status) {
                                            if (status.isSuccess()){
                                                editor.clear();
                                                editor.apply();
                                                Intent in = new Intent(getApplicationContext(), SigninActivity.class);
                                                startActivity(in);
                                            }else{
                                                Toast.makeText(getApplicationContext(),"Session not close", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                            Intent in = new Intent(getApplicationContext(), SigninActivity.class);
                            startActivity(in);
                            Toast.makeText(getApplicationContext(), "User Successfully logged out", Toast.LENGTH_SHORT).show();

                        }

                        else if(position == 3) {

                            Intent in = new Intent(getApplicationContext(), AboutusActivity.class);
                            startActivity(in);
                        }

                    }
                })
        );


        sidemenu2recycler.addOnItemTouchListener(
                new RecyclerItemClickListener(UserHomeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if (position == 0) {
                            mediaplayer.stop();

                            if (user_role != null) {
                                if (user_role.equalsIgnoreCase("subscriber")) {
                                    Intent in = new Intent(UserHomeActivity.this, MyAccountActivity.class);
                                    startActivity(in);
                                } else {
                                    Intent in = new Intent(UserHomeActivity.this, SubscribeActivity.class);
                                    startActivity(in);
                                }
                            } else {
                                Intent in = new Intent(UserHomeActivity.this, SubscribeActivity.class);
                                startActivity(in);
                            }
                        }

                        if (position == 1) {

                            Intent in = new Intent(getApplicationContext(), payperviewlistcopyActivity.class);
                            startActivity(in);
                        }

                    }
                })
        );


        sidemenulist2.add(
                new sidemenu2(
                        "Premium",
                        R.drawable.premiumflick
                ));

        sidemenulist2.add(
                new sidemenu2(
                        "pay per view",
                        R.drawable.prizes
                ));


        sidemennu2Adopter adapter2 = new sidemennu2Adopter(sidemenulist2);

        //setting adapter to recyclerview
        sidemenu2recycler.setAdapter(adapter2);

        sidemenulist3.add(
                new sidemenu3(
                        "Faq",
                        R.drawable.helpflick
                ));


        sidemenulist3.add(
                new sidemenu3(
                        "Terms & Condition",
                        R.drawable.helpflick
                ));

        sidemenulist3.add(
                new sidemenu3(
                        "Logout",
                        R.drawable.helpflick
                ));

        sidemenulist3.add(
                new sidemenu3(
                        "About us",
                        R.drawable.helpflick
                ));


        sidemennu3Adopter adapter3 = new sidemennu3Adopter(sidemenulist3);
        sidemenu3recycler.setAdapter(adapter3);

        LinearLayout notification_setting = (LinearLayout)findViewById(R.id.notification_setting);
        notification_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUP_dialog = new Dialog(UserHomeActivity.this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
                popUP_dialog.setContentView(R.layout.pop_dialog);
                if (android.os.Build.VERSION.SDK_INT >= 21) {
                    popUP_dialog.getWindow().setStatusBarColor(Color.TRANSPARENT);
                }

                popUP_dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {

                    }
                });
                popUP_dialog.show();
                SharedPreferences prefs = getSharedPreferences(Constant.MY_PREFS_NAME, MODE_PRIVATE);
                // user_id = prefs.getString(Constants.user_id, "");
                final String state = prefs.getString(Constant.notify_On, "");

                final TextView notification_on = (TextView) popUP_dialog.findViewById(R.id.notification_on);
               final TextView notification_off = (TextView)popUP_dialog.findViewById(R.id.notification_off);
                notification_off.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = getSharedPreferences(Constant.MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString(Constant.notify_On, "off");
                        editor.apply();
                        popUP_dialog.cancel();

                    }
                });

                notification_on.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = getSharedPreferences(Constant.MY_PREFS_NAME, MODE_PRIVATE).edit();
                        editor.putString(Constant.notify_On, "on");
                        editor.apply();
                        popUP_dialog.cancel();

                    }
                });

                if(state.equalsIgnoreCase("off")){
                    notification_off.setBackgroundResource(R.drawable.onoff_bg);
                    notification_on.setBackgroundResource(R.drawable.onff_unselect);
                }else{
                    notification_on.setBackgroundResource(R.drawable.onoff_bg);
                    notification_off.setBackgroundResource(R.drawable.onff_unselect);
                }

                Button close_done = (Button)popUP_dialog.findViewById(R.id.close_done);
                close_done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popUP_dialog.cancel();
                    }
                });

            }
        });


        StringRequest viewprofilereq = new StringRequest(Request.Method.POST, profileurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("user_details");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);


                            sidemenuusername.setText(dataobj.getString("username"));
                            sidemenuuseremail.setText(dataobj.getString("email"));
                            String userimage1 = dataobj.getString("avatar");
                            String userimg = profileimageurl + userimage1;

                            Picasso.get().load(userimg).into(sidemenudp);
                            SharedPreferences.Editor editor = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE).edit();
                            editor.putString(ssharedpreferences.role,dataobj.getString("role"));
                            editor.apply();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(UserHomeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", user_id);


                return parameters;
            }
        };


        RequestQueue queue1 = Volley.newRequestQueue(UserHomeActivity.this);

        queue1.add(viewprofilereq);


    }



    @Override
    public void onBackPressed() {
        mediaplayer.stop();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
    }
    @Override
    protected void onStart() {
        super.onStart();
       /* OptionalPendingResult<GoogleSignInResult> opr= Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if(opr.isDone()){
            GoogleSignInResult result=opr.get();
            handleSignInResult(result);
        }else{
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(@NonNull GoogleSignInResult googleSignInResult) {
                    handleSignInResult(googleSignInResult);
                }
            });
        }*/
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            GoogleSignInAccount account=result.getSignInAccount();
            sidemenuusername.setText(account.getDisplayName());
            sidemenuuseremail.setText(account.getEmail());
            try{
                Picasso.get().load(account.getPhotoUrl()).into(sidemenudp);
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(),"image not found", Toast.LENGTH_LONG).show();
            }

        }else{

        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    public void notify(View view) {

        Intent in =new Intent(getApplicationContext(),NotificationActivity.class);
        startActivity(in);


    }
}

