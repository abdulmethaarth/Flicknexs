package com.webnexs.flicknexs;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
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

import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class NotificationActivity extends AppCompatActivity {


    String notificationurl=Base_URL+"get_user_notification";
    RecyclerView recyclerView;
    CardView cardView;
    private audiotitleAdopter audioplaytitleadopter;
    private List<audioplayertitle> audioplaylist = new ArrayList<audioplayertitle>();
    CardView cardview;
    FrameLayout frame;
    LinearLayout nonotify;

    private notificationAdopter notificationadp;
    private List<notification> notificationlist = new ArrayList<notification>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);


        nonotify=(LinearLayout)findViewById(R.id.nonotify);



        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        String audioiid = prefs.getString(ssharedpreferences.user_id,null );


        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.blackarraow);


        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               finish();
            }
        });


        recyclerView = (RecyclerView) findViewById(R.id.notification);

        notificationadp = new notificationAdopter(notificationlist);
        cardView = findViewById(R.id.card_view);



        final String user_id = prefs.getString(ssharedpreferences.user_id,null );
        String email1 =prefs.getString(ssharedpreferences.email,null);
        String username =prefs.getString(ssharedpreferences.username,null);

        StringRequest relatedrequest = new StringRequest(Request.Method.POST, notificationurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        //  String z1= jsonObject.getString("category_name");
                        JSONArray dataArray = jsonObject.getJSONArray("user_notifications");
                        for (int i = 0; i < dataArray.length(); i++) {

                            JSONObject dataobj = dataArray.getJSONObject(i);



                            notification notify = new notification();
                            notify.setTitle(dataobj.getString("title"));
                            notify.setYear(dataobj.getString("message"));

                            notificationlist.add(notify);

                        }

                    }

                    else
                    {
                        nonotify.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                notificationadp.notifyDataSetChanged();
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(NotificationActivity.this, "Check Your internet Connection" , Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id", user_id);
                return parameters;
            }
        };


        RequestQueue relatedqueue = Volley.newRequestQueue(NotificationActivity.this);
        relatedqueue.add(relatedrequest);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        recyclerView.setAdapter(notificationadp);



    }
}
