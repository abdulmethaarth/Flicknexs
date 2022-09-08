package com.webnexs.flicknexs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class genreActivity extends AppCompatActivity {

    private List<newuploads> newuploadsList = new ArrayList<newuploads>();
    testinggenreAdopter newuploadsadapter;

    RecyclerView newuploadsrecycle;
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/";
    String latesturl=Base_URL+"latestmovies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genre);

       /// newuploadsrecycle =(RecyclerView)findViewById(R.id.testinggenre);

        newuploadsrecycle = (RecyclerView) findViewById(R.id.newuploadrecyler);
        newuploadsrecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        newuploadsadapter = new testinggenreAdopter(newuploadsList);


        StringRequest request4 = new StringRequest(Request.Method.GET, latesturl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("latest_movies");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            String newuploadid = dataobj.getString("id");
                            String newuploadname = dataobj.getString("title");
                            String newuploadsimage = dataobj.getString("image");
                            String newuploadmp4 = dataobj.getString("mp4_url");
                            String ppvstatus = dataobj.getString("ppv_status");
                            String image1 = Imageurl + newuploadsimage;

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
                Toast.makeText(genreActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue queue4 = Volley.newRequestQueue(genreActivity.this);
        queue4.add(request4);
        queue4.getCache().clear();


        newuploadsrecycle.setHasFixedSize(true);

        newuploadsrecycle.setItemAnimator(new DefaultItemAnimator());

        newuploadsrecycle.setAdapter(newuploadsadapter);





    }
}
