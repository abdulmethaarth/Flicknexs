package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;


import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class payperviewenableActivity extends AppCompatActivity {


    String videodetailsurl = Base_URL+"movie";

    String alsolikeurl=Base_URL+"alsolikeVideo";

    String payperamounturl=Base_URL+"ppv_details";



    private List<Movies> movieslist = new ArrayList<Movies>();
    MoviesAdopter movadopter;

    private List<Thismaylike> thismaylikeslist = new ArrayList<Thismaylike>();
    ThismaylikeAdopter thismaylikeadopter;

    LinearLayout share;
    public String stripe_token;


    LinearLayout watchlist;
    ImageView watchlistimg, watchlistaddedimg;
    //  TextView idd;
    //private WebnexsVideoPlayerWidget videoPlayerWidget;
    String Imageurl = " http://flicknexs.webnexs.org/content/uploads/";
    TextView videotext, videotitle;
    RecyclerView vieodetailsrecycler, videotailerrecycler, thismaylikerecycler;
    videodetailsadopter videosadopter;
    private String genre1, shareurl;
    ImageView payperviewenable;
     String language;
     Button buyvideo;
    int REQUEST_CODE = 0077;
    TextView media_type;

    String user_id;

    String xx,payamount;
     payperviewpaymentresponse signUpResponsesData;
     String media="movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payperview);


        videotext = (TextView) findViewById(R.id.videotext);
        videotitle = (TextView) findViewById(R.id.videotitle);
        watchlist = (LinearLayout) findViewById(R.id.watchlist);
        watchlistimg = (ImageView) findViewById(R.id.watchlistimg);
        watchlistaddedimg = (ImageView) findViewById(R.id.watchlistaddimg);
        share = (LinearLayout) findViewById(R.id.share);
        payperviewenable = (ImageView) findViewById(R.id.payperrviewenable);
        buyvideo = (Button) findViewById(R.id.buyvideo);
        media_type=(TextView)findViewById(R.id.storedata);
        media_type.setText("movies");


        thismaylikerecycler = (RecyclerView) findViewById(R.id.thismayalsolike);
        thismaylikerecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        thismaylikeadopter = new ThismaylikeAdopter(thismaylikeslist);


        Intent in = getIntent();
         xx = in.getStringExtra("id");


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
         user_id= prefs.getString(ssharedpreferences.user_id, null);
        final String user_role = prefs.getString(ssharedpreferences.role, null);

        vieodetailsrecycler = (RecyclerView) findViewById(R.id.videodetails);
        vieodetailsrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        movadopter = new MoviesAdopter(movieslist);


        buyvideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in = new Intent(getApplicationContext(), payperviewpaymentActivity.class);
                startActivityForResult(in, REQUEST_CODE);
            }
        });


        StringRequest request4 = new StringRequest(Request.Method.POST, videodetailsurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        shareurl = jsonObject.getString("movieurl");

                        language = jsonObject.getString("languages");

                     //   payamount=jsonObject.getString("ppv_amount");

                        JSONArray genre = jsonObject.getJSONArray("genre");


                        for (int j = 0; j < genre.length(); j++) {


                            JSONObject dataobj1 = genre.getJSONObject(j);

                            genre1 = dataobj1.getString("name");


                        }
                        JSONArray dataArray = jsonObject.getJSONArray("movie_details");


                        for (int i = 0; i < dataArray.length(); i++) {


                            /*newuploadname = "";
                            newuploadid = "";*/
                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String videoid = dataobj.getString("id");
                            String videoname = dataobj.getString("title");
                            String videoimage = dataobj.getString("duration");
                            String videosdmp4 = dataobj.getString("mp4_url");
                            String videodetails = dataobj.getString("description");
                            String videotype = dataobj.getString("access");
                            String videolang = dataobj.getString("language");
                            String videoyear = dataobj.getString("year");
                            String image = dataobj.getString("image");
                            String v1 = Imageurl + image;


                            Picasso.get().load(v1).into(payperviewenable);

                           // buyvideo.setText("Purchase  Rs."+payamount);

                            int a = Integer.parseInt(videoimage);

                            int b = a % 60;
                            int c = a / 60;

                            int d = c % 60;
                            c = c / 60;


                            String e = String.format("%2ds", b);
                            String f = String.format("%2dm", d);
                            String h = String.format("%2dh", c);


                            String g = h + " " + f + " " + e;


                            Movies mov = new Movies();
                            mov.setId(videoid);
                            mov.setName(videoname);
                            mov.setUrl(videosdmp4);
                            mov.setRunningtime(g);
                            mov.setLanguage(language);
                            mov.setYear(videoyear);
                            mov.setGenre(genre1);


                            movieslist.add(mov);
                            movadopter.notifyDataSetChanged();
                            videotext.setText(videodetails);
                            //  videotitle.setText(videoname);


                        }
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(payperviewenableActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("id", xx);
                return parameters;
            }
        };

        RequestQueue queue4 = Volley.newRequestQueue(payperviewenableActivity.this);
        queue4.add(request4);


        vieodetailsrecycler.setHasFixedSize(true);

        vieodetailsrecycler.setItemAnimator(new DefaultItemAnimator());

        vieodetailsrecycler.setAdapter(movadopter);


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
                            String videoimage=dataobj.getString("image");
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
                Toast.makeText(payperviewenableActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("movie_id",xx);
                return parameters;
            }
        };

        RequestQueue thismaylikequeue = Volley.newRequestQueue(payperviewenableActivity.this);
        thismaylikequeue.add(thismaylikerequest);



        thismaylikerecycler.setHasFixedSize(true);

        thismaylikerecycler.setItemAnimator(new DefaultItemAnimator());

        thismaylikerecycler.setAdapter(thismaylikeadopter);




        StringRequest request5 = new StringRequest(Request.Method.GET, payperamounturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        String xx=jsonObject.getString("ppv_amount");
                        String yy=jsonObject.getString("ppv_hours");

                        String zz=xx+"/"+yy+" hrs";

                        buyvideo.setText("purchase Rs." +zz);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(payperviewenableActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue queue5 = Volley.newRequestQueue(payperviewenableActivity.this);
        queue5.add(request5);


    }

    @Override
    protected void onActivityResult ( final int requestCode, int resultCode, Intent data){
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == resultCode) {
        stripe_token = data.getStringExtra("stripe_token");

        if (user_id != null) {
            if (stripe_token.length() > 1) {

                //Toast.makeText(getApplicationContext(),""+stripe_token,Toast.LENGTH_LONG).show();

                Log.e("",stripe_token);

                String media_id = media_type.getText().toString();
                ApiClient.getClient().payperviewpayment(user_id,
                        stripe_token,media_id,xx,
                        new Callback<payperviewpaymentresponse>() {
                    @Override
                    public void success(payperviewpaymentresponse signUpResponse, retrofit.client.Response response) {
                        signUpResponsesData = signUpResponse;
                        Intent in= new Intent(payperviewenableActivity.this,MoviesActivity.class);
                        in.putExtra("id",xx);
                        startActivity(in);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(payperviewenableActivity.this, error.toString(), Toast.LENGTH_LONG).show();
                    }

                });
                    }
                }

            }

        }



        @Override
    public void onBackPressed() {
      finish();

    }


}
