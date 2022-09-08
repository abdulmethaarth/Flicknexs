package com.webnexs.flicknexs;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Callback;
import retrofit.RetrofitError;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class MyAccountActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    @SuppressLint("ResourceAsColor")
    LinearLayout subscriber,nonsubscriber,logout,changepass;
    String profileurl=Base_URL+"view_profile";
    String Imageurl=" http://flicknexs.webnexs.org/content/uploads/avatars/";
    TextView username,useremail,nextBilling;
    CircleImageView userimage;
    LinearLayout editicon;
    Button getsubscription;
    LinearLayout cancelpayment;

    private List<audioplayertitle> audioplaylist = new ArrayList<audioplayertitle>();
    private audiotitleAdopter audioplaytitleadopter;
    cancelpayment signUpResponsesData;

    private GoogleApiClient googleApiClient;
    private GoogleSignInOptions gso;
    String status = "false";
    String email,user_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_account);


        gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();


        subscriber =(LinearLayout)findViewById(R.id.subscriber);
        nonsubscriber=(LinearLayout)findViewById(R.id.register);
        logout=(LinearLayout)findViewById(R.id.logout);
        username=(TextView)findViewById(R.id.username);
        useremail=(TextView)findViewById(R.id.useremail);
        nextBilling=(TextView)findViewById(R.id.nextBilling);
        userimage=(CircleImageView)findViewById(R.id.image);
        changepass=(LinearLayout)findViewById(R.id.changepassword);
        editicon=(LinearLayout)findViewById(R.id.editicon);


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        user_id = prefs.getString(ssharedpreferences.user_id,null );
        status = prefs.getString(ssharedpreferences.status, null);
        email = prefs.getString(ssharedpreferences.email, null);
        String user_role=prefs.getString(ssharedpreferences.role,null);

        if(status == "true") {
            changepass.setVisibility(View.GONE);
        }else {
            changepass.setVisibility(View.VISIBLE);
        }

        cancelpayment=findViewById(R.id.cancelpayment);

        cancelpayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ApiClient.getClient().getcancelpayment(user_id,

                        new Callback<cancelpayment>() {
                            @Override
                            public void success(cancelpayment signUpResponse, retrofit.client.Response response) {
                                signUpResponsesData = signUpResponse;
                                SharedPreferences.Editor editor = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE).edit();
                                editor.putString(ssharedpreferences.role,"non_subscriber");
                                editor.apply();
                                Toast.makeText(getApplicationContext(),""+signUpResponsesData.getMessage(), Toast.LENGTH_LONG).show();
                                subscriber.setVisibility(View.GONE);
                                cancelpayment.setVisibility(View.GONE);
                                nonsubscriber.setVisibility(View.VISIBLE);

                            }

                            @Override
                            public void failure(RetrofitError error) {
                                Toast.makeText(MyAccountActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                            }

                        });


            }
        });



        audioplaytitleadopter = new audiotitleAdopter(audioplaylist);


        getsubscription=(Button)findViewById(R.id.getsubscription);

        getsubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(email.isEmpty()){
                    Toast.makeText(MyAccountActivity.this, "Email is empty, Please update your profile", Toast.LENGTH_LONG).show();
                    Intent in=new Intent(MyAccountActivity.this,EditProfileActivity.class);
                    startActivity(in);
                }
                else {
                    Intent in=new Intent(MyAccountActivity.this,SubscribeActivity.class);
                    startActivity(in);
                }

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
                        Intent video=new Intent(MyAccountActivity.this,VideoPageActivity.class);
                        startActivity(video);
                        break;

                    case R.id.navigation_audio:
                        Intent audio=new Intent(MyAccountActivity.this,AudioPageActivity.class);
                        startActivity(audio);
                        break;
                }

                return false;
            }
        });






        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.arrowback);


        editicon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MyAccountActivity.this,EditProfileActivity.class);
                startActivity(in);
            }
        });
        changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(MyAccountActivity.this,ChangePasswordActivity.class);
                startActivity(in);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, Context.MODE_PRIVATE);
                final SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                if(status =="true") {
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

                finish();
                Toast.makeText(getApplicationContext(), "User Successfully logged out", Toast.LENGTH_SHORT).show();

            }
        });

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in=new Intent(getApplicationContext(),UserHomeActivity.class);
                startActivity(in);

            }
        });

        if(user_role !=null) {

            if (user_role.equalsIgnoreCase("subscriber")) {

                subscriber.setVisibility(View.VISIBLE);
                nonsubscriber.setVisibility(View.GONE);
            } else {
                nonsubscriber.setVisibility(View.VISIBLE);
                subscriber.setVisibility(View.GONE);
            }
        }
        else
            {
                Intent in=new Intent(MyAccountActivity.this,SigninActivity.class);
                startActivity(in);
            }
        StringRequest request4 = new StringRequest(Request.Method.POST, profileurl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                        JSONArray dataArray = jsonObject.getJSONArray("user_details");
                        for (int i = 0; i < dataArray.length(); i++) {


                            JSONObject dataobj = dataArray.getJSONObject(i);

                            username.setText( dataobj.getString("username"));
                            useremail.setText(dataobj.getString("email"));
                            nextBilling.setText("Next Billing Date: "+dataobj.getString("subscription_ends_at"));
                            String userimage1=dataobj.getString("avatar");

                            String userimg=Imageurl+userimage1;


                            Picasso.get().load(userimg).into(userimage);

                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(MyAccountActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("user_id",user_id);
                return parameters;
            }
        };

        RequestQueue queue4 = Volley.newRequestQueue(MyAccountActivity.this);
        queue4.add(request4);
        queue4.getCache().clear();
    }

    @Override
    public void onBackPressed() {
        Intent in=new Intent(MyAccountActivity.this,UserHomeActivity.class);
        startActivity(in);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
