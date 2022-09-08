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


public class MovieCategoryActivity extends AppCompatActivity {


    moviecatAdopter moviecatadopter;
    private List<moviecat> movicatlist = new ArrayList<moviecat>();
    RecyclerView moviecatrecycler;
    ProgressBar moviecatprogress;

    String moviecaturl=Base_URL+"get_all_movie_category";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/images/";
    String inn;
    String genre1;
    String videodetailsurl= Base_URL+"movie";
    TextView no_data ;

    private List<audioplayertitle> audioplaylist = new ArrayList<audioplayertitle>();
    private audiotitleAdopter audioplaytitleadopter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_category);

        no_data = (TextView)findViewById(R.id.no_data);

        Intent in=getIntent();
        inn=in.getStringExtra("id");

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.backarrow);



        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
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
                        Intent video=new Intent(MovieCategoryActivity.this,VideoPageActivity.class);
                        startActivity(video);
                        break;

                    case R.id.navigation_audio:
                        Intent audio=new Intent(MovieCategoryActivity.this,AudioPageActivity.class);
                        startActivity(audio);
                        break;
                }

                return false;
            }
        });

        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);

        final String user_id = prefs.getString(ssharedpreferences.user_id,null );


        moviecatprogress=(ProgressBar)findViewById(R.id.moviecatprogress);
        moviecatrecycler = (RecyclerView) findViewById(R.id.moviecatrecycler);
        moviecatrecycler.setLayoutManager(new GridLayoutManager(this, 2));
        moviecatadopter = new moviecatAdopter(movicatlist);
        final TextView title=(TextView)findViewById(R.id.toolbar_title);


        StringRequest watchrequest = new StringRequest(Request.Method.POST, moviecaturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                try {

                    JSONObject jsonObject = new JSONObject(response);
                    ArrayList<String> list = new ArrayList<String>();
                   /* if(jsonObject.getString("movie_details").isEmpty()){
                        Toast.makeText(MovieCategoryActivity.this, "emptyyyyyyy", Toast.LENGTH_SHORT).show();
                    }else
*/

                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        no_data.setVisibility(View.GONE);
                        list.add(jsonObject.getString("movie_details"));
                        if (jsonObject.getString("message").equalsIgnoreCase("nodata")){
                            no_data.setVisibility(View.VISIBLE);

                        }
                        
                        JSONArray genre = jsonObject.getJSONArray("genre");


                        for (int j = 0; j < genre.length(); j++) {


                            JSONObject dataobj1 = genre.getJSONObject(j);

                            genre1 = dataobj1.getString("name");



                        }

                        JSONArray dataArray = jsonObject.getJSONArray("movie_details");
                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                            String watchimage = dataobj.getString("image");
                            String watchmp4 = dataobj.getString("mp4_url");
                            String ppvstatus=dataobj.getString("ppv_status");
                            String image1 = Imageurl + watchimage;

                            moviecat mov = new moviecat();
                            mov.setId(watchid);
                            mov.setName(watchname);
                            mov.setImage(image1);
                            mov.setUrl(watchmp4);
                            mov.setPpvstatus(ppvstatus);


                                 title.setText(genre1);
                            movicatlist.add(mov);
                            moviecatadopter.notifyDataSetChanged();

                        }

                    }
                    else {
                        Toast.makeText(MovieCategoryActivity.this, "No data found", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MovieCategoryActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("genre_movieid", inn);
                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(MovieCategoryActivity.this);
        watchqueue.add(watchrequest);


        moviecatrecycler.setHasFixedSize(true);

        moviecatrecycler.setItemAnimator(new DefaultItemAnimator());

        moviecatrecycler.setAdapter(moviecatadopter);




        moviecatrecycler.addOnItemTouchListener(
                new RecyclerItemClickListener(MovieCategoryActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, final int position) {

                if(movicatlist.size()>position){
                    if (movicatlist.get(position)!= null){
                        if (movicatlist.get(position).getPpvstatus().equalsIgnoreCase(String.valueOf(1))) {
                            StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(response);
                                        if (jsonObject.getString("ppv_status").equalsIgnoreCase("expired")) {

                                            Intent in = new Intent(MovieCategoryActivity.this, payperviewenableActivity.class);
                                            in.putExtra("id", movicatlist.get(position).getId());
                                            startActivity(in);
                                        } else if (jsonObject.getString("ppv_status").equalsIgnoreCase("pay_now")) {
                                            Intent in = new Intent(MovieCategoryActivity.this, payperviewenableActivity.class);
                                            in.putExtra("id", movicatlist.get(position).getId());
                                            startActivity(in);
                                        } else {
                                            Intent in = new Intent(MovieCategoryActivity.this, MoviesActivity.class);
                                            in.putExtra("id", movicatlist.get(position).getId());
                                            startActivity(in);
                                        }


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }

                                }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    Toast.makeText(MovieCategoryActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {
                                    Map<String, String> parameters = new HashMap<String, String>();
                                    parameters.put("id", movicatlist.get(position).getId());
                                    parameters.put("user_id", user_id);
                                    return parameters;
                                }
                            };

                            RequestQueue queue4 = Volley.newRequestQueue(MovieCategoryActivity.this);
                            queue4.add(request4);


                        } else {
                            Intent intent = new Intent(MovieCategoryActivity.this, MoviesActivity.class);
                            intent.putExtra("id", movicatlist.get(position).getId());
                            startActivity(intent);
                        }

                    }
                }

                    }
                })
        );



    }

    private boolean isArrayListEmpty(ArrayList<String> list) {
        if( list.size() == 0 )
            return true;

        return false;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
