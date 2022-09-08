package com.webnexs.flicknexs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.AdapterViewFlipper;
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


public class FliperActivity extends AppCompatActivity {

    private AdapterViewFlipper simpleAdapterViewFlipper;

    String bannerurl=Base_URL+"banners";
    List<fliperutils> fliperlist;
    CustomAdapter customadapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fliper);

        fliperlist = new ArrayList<>();



        simpleAdapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.simpleAdapterViewFlipper);


        StringRequest requestban = new StringRequest(Request.Method.GET, bannerurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


                        JSONArray dataArray = jsonObject.getJSONArray("banners");

                        for (int j = 0; j < dataArray.length(); j++) {


                            fliperutils flip = new fliperutils();


                            JSONObject dataobj = dataArray.getJSONObject(j);
                            String newuploadsimage=dataobj.getString("image");
                            String id=dataobj.getString("id");
                            String ppvstatus=dataobj.getString("ppv_status");
                            String image1=R.string.image_url+newuploadsimage;



                            flip.setSliderImageUrl(image1);
                           // flip.setId(id);
                           // flip.setPpvstatus(ppvstatus);
                            //flip.setUserid(user_id);


                            fliperlist.add(flip);

                            customadapter = new CustomAdapter(getApplicationContext(),fliperlist);

                            simpleAdapterViewFlipper.setAdapter(customadapter);


                        }


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }



        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(FliperActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue queueban = Volley.newRequestQueue(FliperActivity.this);
        queueban.add(requestban);


        simpleAdapterViewFlipper.setFlipInterval(3000);
        simpleAdapterViewFlipper.setAutoStart(true);


    }
}
