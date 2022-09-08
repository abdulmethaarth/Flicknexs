package com.webnexs.flicknexs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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


public class VideocategoryActivity extends AppCompatActivity {

    vidcatAdopter vidcatadopter;
    private List<vidcat> vidcatlist = new ArrayList<vidcat>();
    RecyclerView vidcatrecycler;
    ProgressBar vidcatprogress;

    String vidcaturl=Base_URL+"get_all_video_category";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/images/";
    String inn;
    String genre1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videocategory);




        Intent in=getIntent();
        inn=in.getStringExtra("id");






        final TextView title=(TextView)findViewById(R.id.toolbar_title);


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.arrowback);

        StringRequest watchrequest = new StringRequest(Request.Method.POST, vidcaturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray genre = jsonObject.getJSONArray("category_details");

                        for (int j = 0; j < genre.length(); j++) {
                            JSONObject dataobj1 = genre.getJSONObject(j);
                            genre1 = dataobj1.getString("name");


                        }

                        JSONArray dataArray = jsonObject.getJSONArray("video_details");
                        for (int i = 0; i < dataArray.length(); i++) {


                            /*newuploadname = "";
                            newuploadid = "";*/
                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String watchid = dataobj.getString("id");
                            String watchname = dataobj.getString("title");
                            String watchimage = dataobj.getString("image");
                            String watchmp4 = dataobj.getString("mp4_url");
                            String image1 = Imageurl + watchimage;

                            vidcat vid = new vidcat();
                            vid.setId(watchid);
                            vid.setName(watchname);
                            vid.setImage(image1);
                            vid.setUrl(watchmp4);


                                title.setText(genre1);
                            vidcatlist.add(vid);
                            vidcatadopter.notifyDataSetChanged();


                        }
                    }
                    } catch(JSONException e){
                        e.printStackTrace();
                    }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(VideocategoryActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("video_categoryid", inn);
                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(VideocategoryActivity.this);
        watchqueue.add(watchrequest);
        watchqueue.getCache().clear();


        vidcatrecycler.setHasFixedSize(true);

        vidcatrecycler.setItemAnimator(new DefaultItemAnimator());

        vidcatrecycler.setAdapter(vidcatadopter);





    }
}
