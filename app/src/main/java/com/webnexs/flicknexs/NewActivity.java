package com.webnexs.flicknexs;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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


public class NewActivity extends AppCompatActivity {

    RecyclerView qualityrecycler;
    QualityAdopter qualityAdopter;
    private List<quality> qulitylist = new ArrayList<quality>();
    String url=" http://flicknexs.webnexs.org/api/v1/movie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);


        Button bottomSheetButton = (Button)findViewById(R.id.open_close_sheet);
        bottomSheetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openBottomSheet();
            }
        });



    }

    private void openBottomSheet() {

        View view = getLayoutInflater().inflate(R.layout.botton_sheet, null);


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        final String user_id = prefs.getString(ssharedpreferences.user_id,null );



        qualityrecycler =(RecyclerView)view.findViewById(R.id.quality);
        qualityrecycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        qualityAdopter = new QualityAdopter(qulitylist);



        StringRequest watchrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {


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
                Toast.makeText(NewActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", String.valueOf(88));
                parameters.put("id", String.valueOf(64));

                return parameters;
            }
        };

        RequestQueue watchqueue = Volley.newRequestQueue(NewActivity.this);
        watchqueue.add(watchrequest);
        watchqueue.getCache().clear();

        qualityrecycler.setHasFixedSize(true);

        qualityrecycler.setItemAnimator(new DefaultItemAnimator());

        qualityrecycler.setAdapter(qualityAdopter);


        final Dialog mBottomSheetDialog = new Dialog(NewActivity.this, R.style.MaterialDialogSheet);
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setCancelable(true);
        mBottomSheetDialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        mBottomSheetDialog.getWindow().setGravity(Gravity.BOTTOM);
        mBottomSheetDialog.show();



    }
    }


