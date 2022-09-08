package com.webnexs.flicknexs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.webnexs.flicknexs.SigninActivity.Base_URL;


public class SignupSubscribeActivity extends AppCompatActivity {

    int REQUEST_CODE = 0077;
    public String stripe_token;
    Button paynow;
    String user_id;
    paymentresponse signUpResponsesData;
    private Object Context;
    private RecyclerView recyclerView;
    private ArrayList<plans> data;
    private DataAdapter adapter;
    UserListResponse signUpResponsesData1;
    registerresponse registerresponse1;
    RelativeLayout pro;
    LinearLayout page;
    ImageView logo;
    String plan;
    CheckBox checkBox;
    String x,y,z;

    String ispaymenturl = Base_URL+"isPaymentEnable";
    String URL = Base_URL+"register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe_payment);

        Intent in=getIntent();
         x=in.getStringExtra("username");
         y=in.getStringExtra("email");
         z=in.getStringExtra("password");

        initViews();

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setNavigationIcon(R.drawable.arrowback);
        pro = (RelativeLayout) findViewById(R.id.pro);
        checkBox = (CheckBox) findViewById(R.id.checkbox);
        page = (LinearLayout) findViewById(R.id.page);


        StringRequest request4 = new StringRequest(Request.Method.GET, ispaymenturl, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("is_payment").equalsIgnoreCase("0")) {


                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(user_id == null) {
                                    if (checkBox.isChecked()) {
                                        RequestQueue queue = Volley.newRequestQueue(SignupSubscribeActivity.this);
                                        StringRequest request = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {

                                                parseData(response);

                                            }
                                        }, new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                Toast.makeText(SignupSubscribeActivity.this, "Check your Internet connection", Toast.LENGTH_LONG).show();

                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {

                                                Map<String, String> map = new HashMap<String, String>();
                                                map.put("username", x);
                                                map.put("password", z);
                                                map.put("email",y);

                                                return map;
                                            }
                                        };
                                        queue.add(request);
                                    }


                                }
                                else
                                {
                                    Intent in=new Intent(getApplicationContext(),UserHomeActivity.class);
                                    startActivity(in);
                                }
                            }
                        });



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(SignupSubscribeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue queue4 = Volley.newRequestQueue(SignupSubscribeActivity.this);
        queue4.add(request4);
        queue4.getCache().clear();

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(getApplicationContext(), UserHomeActivity.class);
                startActivity(in);
            }
        });


        final ImageView logo = (ImageView) findViewById(R.id.logo1);


        ApiClient.getClient().getUsersList(
                new Callback<UserListResponse>() {
                    @Override
                    public void success(UserListResponse signUpResponse, Response response) {
                        signUpResponsesData1 = signUpResponse;


                        String x = signUpResponse.getImage_url();
                        String y = signUpResponse.getSettings();
                        String z = x + y;
                        Picasso.get().load(z).into(logo);
                        pro.setVisibility(View.GONE);
                        page.setVisibility(View.VISIBLE);


                    }

                    @Override
                    public void failure(RetrofitError error) {
                        // if error occurs in network transaction then we can get the error in this method.
                        Toast.makeText(SignupSubscribeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                    }

                });


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        user_id = prefs.getString(ssharedpreferences.user_id, null);
        final String user_role = prefs.getString(ssharedpreferences.role, null);


    }

    private void parseData(String response) {


        try {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                Intent in=new Intent(getApplicationContext(),SigninActivity.class);
                startActivity(in);

            }

            else if (jsonObject.getString("status").equalsIgnoreCase("false")) {

                if (jsonObject.getString("message").equalsIgnoreCase("Email id Already Exists")) {
                    Toast.makeText(getApplicationContext(), "Email Id already Exists", Toast.LENGTH_LONG).show();

                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Username already taken. Please choose aother username", Toast.LENGTH_LONG).show();

                }
            }

            else {
                Toast.makeText(getApplicationContext(), "You are already registered user", Toast.LENGTH_SHORT).show();
            }



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        recyclerView = (RecyclerView) findViewById(R.id.card_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        loadJSON();
    }

    private void loadJSON() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(" http://flicknexs.webnexs.org/api/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RequestInterface request = retrofit.create(RequestInterface.class);
        Call<JSONResponse> call = request.getJSON();
        call.enqueue(new retrofit2.Callback<JSONResponse>() {
            @Override
            public void onResponse(Call<JSONResponse> call, retrofit2.Response<JSONResponse> response) {
                JSONResponse jsonResponse = response.body();
                data = new ArrayList<>(Arrays.asList(jsonResponse.getPlans()));
                adapter = new DataAdapter(data);
                recyclerView.setAdapter(adapter);
                recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(SignupSubscribeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                if (data.size() > position) {
                                    if (data.get(position) != null) {
                                        plan = data.get(position).getPlans_name();
                                        Intent in = new Intent(getApplicationContext(), PaymentActivity.class);
                                        in.putExtra("period", data.get(position).getBilling_interval());
                                        in.putExtra("amonut", data.get(position).getPrice());
                                        startActivityForResult(in, REQUEST_CODE);
                                    }
                                }
                            }
                        }));
            }

            @Override
            public void onFailure(Call<JSONResponse> call, Throwable t) {
                Log.d("Error", t.getMessage());
            }
        });

    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);

        if (requestCode == resultCode) {
            stripe_token = data.getStringExtra("stripe_token");

            if (stripe_token.length() > 1) {
                        String skip1="0";
                progressBar.show();
                        ApiClient.getClient().getregister(x, y,z,
                                stripe_token, plan,skip1,
                                new Callback<registerresponse>() {
                                    @Override
                                    public void success(registerresponse signUpResponse, Response response) {
                                        registerresponse1 = signUpResponse;

                                            if (registerresponse1.getStatus().equalsIgnoreCase("true")) {
                                                progressBar.cancel();
                                                Intent in=new Intent(getApplicationContext(),SigninActivity.class);
                                                startActivity(in);
                                            }

                                            else if (registerresponse1.getStatus().equalsIgnoreCase("false")) {
                                                progressBar.cancel();
                                                if (registerresponse1.getMessage().equalsIgnoreCase("Email id Already Exists")) {

                                                    progressBar.cancel();
                                                   Intent in=new Intent(SignupSubscribeActivity.this,SignupActivity.class);
                                                    Toast.makeText(getApplicationContext(), "Email Id already Exists", Toast.LENGTH_LONG).show();
                                                    startActivity(in);
                                                }
                                                else {
                                                    progressBar.cancel();
                                                    Intent in=new Intent(SignupSubscribeActivity.this,SignupActivity.class);
                                                    //mobile.setError("Username already taken. Please choose aother username");
                                                    Toast.makeText(getApplicationContext(), "Username already taken. Please choose aother username", Toast.LENGTH_LONG).show();
                                                    startActivity(in);
                                                }
                                            }
                                            else {
                                                Toast.makeText(getApplicationContext(), "You are already registered user", Toast.LENGTH_SHORT).show();
                                            }
                                    }
                                    @Override
                                    public void failure(RetrofitError error) {
                                        progressBar.cancel();
                                       Toast.makeText(SignupSubscribeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                    }
                                });
                    }
                }
        }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(getApplicationContext(), UserHomeActivity.class);
        startActivity(in);
    }

}