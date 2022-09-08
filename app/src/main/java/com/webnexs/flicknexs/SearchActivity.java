package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
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


public class SearchActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {

    EditText searchtext;
    ImageView searchimage,cross;
    String searchurl=Base_URL+"search";

    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/images/";

    String moviecaturl=Base_URL+"movie_category";

    String videodetailsurl=Base_URL+"movie";
    String audiodetailsurl= Base_URL+"audio";

    private List<search> searchlist = new ArrayList<search>();
    searchAdopter searchadopter;

    private List<searchAudio> searchaudiolist = new ArrayList<searchAudio>();
    searchAudioAdopter searchaudioadopter;


    RecyclerView searchdetailsrecycler,genrerecycler,titlerecyclerrview,searchaudiodetailsrecycler;
    ProgressBar searchprogress,genreprogress;

    private List<genre> genrelist = new ArrayList<genre>();
    genreAdopter genreadopter;

    ArrayAdapter arrayAdapter;
    TextView text;

    private List<searchtitle> titlelist = new ArrayList<searchtitle>();
    TitlesearchAdopter titleadopter;

    private List<searchtitle> titlelist1 = new ArrayList<searchtitle>();
    TitlesearchAdopter titleadopter1;

    Button moviesbutton,seriesbutton,videosbutton;

    LinearLayout genrelayout,titlelayout,detaillayout;
    TextView txt;
    ImageView spinner_arrow;



    String[] country = { "Videos", "Audios"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );
        final String user_role = prefs.getString(ssharedpreferences.role,null );

        spinner_arrow = (ImageView)findViewById(R.id.spinner_arrow);
        final Spinner spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spin.setAdapter(aa);




        searchtext = (EditText) findViewById(R.id.searchtext);
        searchimage = (ImageView) findViewById(R.id.search);
        cross = (ImageView) findViewById(R.id.cross);
        searchprogress = (ProgressBar) findViewById(R.id.searchprogress);
        text =(TextView)findViewById(R.id.text);
        genrelayout=(LinearLayout)findViewById(R.id.genre);
        titlelayout=(LinearLayout)findViewById(R.id.searchnamelayout);
        detaillayout=(LinearLayout)findViewById(R.id.detailslayout);
        txt=(TextView)findViewById(R.id.storecat);



        moviesbutton=(Button)findViewById(R.id.movies);
        videosbutton=(Button)findViewById(R.id.videos);

        genrerecycler = (RecyclerView) findViewById(R.id.genrerecycler);
        genrerecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        genreadopter = new genreAdopter(genrelist);


        searchdetailsrecycler = (RecyclerView) findViewById(R.id.searchdetails);
        searchdetailsrecycler.setLayoutManager(new GridLayoutManager(this, 2));
        searchadopter = new searchAdopter(searchlist);




        titlerecyclerrview = (RecyclerView) findViewById(R.id.searchnamedetails);
        titlerecyclerrview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        titleadopter = new TitlesearchAdopter(titlelist);



        spinner_arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spin.performClick();
            }
        });
        moviesbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent video=new Intent(SearchActivity.this,VideoPageActivity.class);
                startActivity(video);
            }
        });

        videosbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(SearchActivity.this,AudioPageActivity.class);
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
                        Intent video=new Intent(SearchActivity.this,VideoPageActivity.class);
                        startActivity(video);
                        break;

                    case R.id.navigation_audio:
                        Intent audio=new Intent(SearchActivity.this,AudioPageActivity.class);
                        startActivity(audio);
                        break;
                }

                return false;
            }
        });



        searchtext.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                searchlist.clear();
                cross.setVisibility(View.VISIBLE);
                searchimage.setVisibility(View.GONE);
                searchprogress.setVisibility(View.VISIBLE);
                titlelist.clear();
                genrelayout.setVisibility(View.GONE);
                titlelayout.setVisibility(View.VISIBLE);


                StringRequest request4 = new StringRequest(Request.Method.POST, searchurl, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            searchprogress.setVisibility(View.GONE);


                            if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                if (txt.getText().toString() == "Videos") {
                                    JSONArray dataArray1 = jsonObject.getJSONArray("movies");

                                    for (int j = 0; j < dataArray1.length(); j++) {

                                        JSONObject dataobj = dataArray1.getJSONObject(j);

                                        String videoname = dataobj.getString("title");


                                        searchtitle ser = new searchtitle();

                                        ser.setName(videoname);

                                        titlelist.add(ser);
                                        titleadopter.notifyDataSetChanged();


                                    }
                                } else {

                                    JSONArray dataArray1 = jsonObject.getJSONArray("audios");

                                    for (int j = 0; j < dataArray1.length(); j++) {

                                        JSONObject dataobj = dataArray1.getJSONObject(j);

                                        String videoname = dataobj.getString("title");


                                        searchtitle ser = new searchtitle();

                                        ser.setName(videoname);

                                        titlelist.add(ser);
                                        titleadopter.notifyDataSetChanged();

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
                        Toast.makeText(SearchActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }
                }) {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> parameters = new HashMap<String, String>();
                        parameters.put("searchdata", searchtext.getText().toString());
                        return parameters;
                    }
                };

                RequestQueue queue4 = Volley.newRequestQueue(SearchActivity.this);
                queue4.add(request4);


                titlerecyclerrview.setHasFixedSize(true);

                titlerecyclerrview.setItemAnimator(new DefaultItemAnimator());

                titlerecyclerrview.setAdapter(titleadopter);


            }
        });



        titlerecyclerrview.addOnItemTouchListener(
                new RecyclerItemClickListener(SearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {
                if(titlelist.size()>position){
                    if (titlelist.get(position)!= null){

                        titlelayout.setVisibility(View.GONE);
                        detaillayout.setVisibility(View.VISIBLE);
                        genrelayout.setVisibility(View.GONE);

                        StringRequest request4 = new StringRequest(Request.Method.POST, searchurl, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);


                                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                        if (txt.getText().toString() == "Videos") {

                                            JSONArray dataArray1 = jsonObject.getJSONArray("movies");
                                            for (int i = 0; i < dataArray1.length(); i++) {

                                                JSONObject dataobj = dataArray1.getJSONObject(i);

                                                String videoname = dataobj.getString("title");
                                                String vidid = dataobj.getString("id");
                                                String vidgenre = dataobj.getString("slug");
                                                String vidimage = dataobj.getString("image");
                                                String ppvstatus = dataobj.getString("ppv_status");
                                                String image = Imageurl + vidimage;


                                                search ser = new search();

                                                ser.setName(videoname);
                                                ser.setId(vidid);
                                                ser.setImage(image);
                                                ser.setUrl(vidgenre);
                                                ser.setPpvstatus(ppvstatus);
                                                searchlist.add(ser);
                                                searchadopter.notifyDataSetChanged();

                                            }
                                        } else {

                                            JSONArray dataArray = jsonObject.getJSONArray("audios");
                                            for (int j = 0; j < dataArray.length(); j++) {

                                                JSONObject dataobj1 = dataArray.getJSONObject(j);

                                                String videoname = dataobj1.getString("title");
                                                String vidid = dataobj1.getString("id");
                                                String vidgenre = dataobj1.getString("mp3_url");
                                                String vidimage = dataobj1.getString("image");
                                                String ppvstatus = dataobj1.getString("ppv_status");
                                                String image = Imageurl + vidimage;


                                                search ser = new search();
                                                ser.setName(videoname);
                                                ser.setId(vidid);
                                                ser.setImage(image);
                                                ser.setUrl(vidgenre);
                                                ser.setPpvstatus(ppvstatus);
                                                searchlist.add(ser);
                                                searchadopter.notifyDataSetChanged();

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
                                Toast.makeText(SearchActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() throws AuthFailureError {
                                Map<String, String> parameters = new HashMap<String, String>();
                                parameters.put("searchdata",titlelist.get(position).getName());
                                return parameters;
                            }
                        };


                        RequestQueue queue4 = Volley.newRequestQueue(SearchActivity.this);
                        queue4.add(request4);


                        searchdetailsrecycler.setHasFixedSize(true);

                        searchdetailsrecycler.setItemAnimator(new DefaultItemAnimator());

                        searchdetailsrecycler.setAdapter(searchadopter);




                    }
                }

                    }
                })
        );


        searchdetailsrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(SearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                if(searchlist.size()>position){
                if (searchlist.get(position)!= null){
                if (searchlist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {

                    StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired") ) {

                                    Intent in=new Intent(SearchActivity.this,payperviewenableActivity.class);
                                    in.putExtra("id",searchlist.get(position).getId());
                                    startActivity(in);

                                }
                                else if(jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now"))
                                {
                                    Intent in=new Intent(SearchActivity.this,payperviewenableActivity.class);
                                    in.putExtra("id",searchlist.get(position).getId());
                                    startActivity(in);
                                }
                                else {
                                    Intent in = new Intent(SearchActivity.this, MoviesActivity.class);
                                    in.putExtra("id", searchlist.get(position).getId());
                                    startActivity(in);
                                }


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(SearchActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("id", searchlist.get(position).getId());
                            parameters.put("user_id", user_id);
                            return parameters;
                        }
                    };
                    RequestQueue queue4 = Volley.newRequestQueue(SearchActivity.this);
                    queue4.add(request4);

                }
                else {
                    Intent intent = new Intent(SearchActivity.this, MoviesActivity.class);
                    intent.putExtra("id", searchlist.get(position).getId());
                    startActivity(intent);
                }


                }
                } }
                })
        );



        StringRequest moviecatrequest = new StringRequest(Request.Method.GET, moviecaturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("movie_categories");
                        for (int i = 0; i < dataArray.length(); i++) {



                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String moviecatid = dataobj.getString("id");
                            String moviecatname = dataobj.getString("name");
                            String moviecatparentid=dataobj.getString("parent_id");


                            genre movie = new genre();
                            movie.setId(moviecatid);
                            movie.setName(moviecatname);
                            movie.setUrl(moviecatparentid);

                            genrelist.add(movie);
                            genreadopter.notifyDataSetChanged();

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SearchActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue moviecatqueue = Volley.newRequestQueue(SearchActivity.this);
        moviecatqueue.add(moviecatrequest);



        genrerecycler.setHasFixedSize(true);


        genrerecycler.setItemAnimator(new DefaultItemAnimator());

        genrerecycler.setAdapter(genreadopter);



        genrerecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(SearchActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                        if(genrelist.size()>position){
                            if (genrelist.get(position)!= null){
                                Intent intent = new Intent(SearchActivity.this, MovieCategoryActivity.class);
                                intent.putExtra("id",genrelist.get(position).getId());
                                startActivity(intent);

                            }
                        }

                    }
                })
        );




        cross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchlist.clear();
                searchtext.setText("");
                searchimage.setVisibility(View.VISIBLE);
                cross.setVisibility(View.GONE);
                titlelayout.setVisibility(View.GONE);
                detaillayout.setVisibility(View.GONE);
                genrelayout.setVisibility(View.VISIBLE);
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
       txt.setText(country[i]);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


