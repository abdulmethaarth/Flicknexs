package com.webnexs.flicknexs;

import android.app.Activity;
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
import com.razorpay.Checkout;
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
import static com.webnexs.flicknexs.SigninActivity.email;


public class SubscribeActivity extends AppCompatActivity {

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
    RelativeLayout pro;
    LinearLayout page;
    ImageView logo;
    String plan;
    CheckBox checkBox;

    String ispaymenturl = Base_URL+"isPaymentEnable";
    String URL = Base_URL+"register";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subscribe);
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
                    if (jsonObject.getString("is_payment").equalsIgnoreCase("1")) {

                       // checkBox.setVisibility(View.GONE);

                        Intent in = getIntent();
                        final String x = in.getStringExtra("username");
                        final String y = in.getStringExtra("password");
                        final String z = in.getStringExtra("email");

                        checkBox.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if(user_id == null) {
                                    if (checkBox.isChecked()) {


                                        StringRequest watchrequest = new StringRequest(Request.Method.POST, URL, new com.android.volley.Response.Listener<String>() {
                                            @Override
                                            public void onResponse(String response) {


                                                try {
                                                    JSONObject jsonObject = new JSONObject(response);
                                                    if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                                        Intent in = new Intent(getApplicationContext(), SigninActivity.class);
                                                        startActivity(in);

                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }

                                        }, new com.android.volley.Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError volleyError) {
                                                Toast.makeText(SubscribeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                                            }
                                        }) {
                                            @Override
                                            protected Map<String, String> getParams() throws AuthFailureError {
                                                Map<String, String> parameters = new HashMap<String, String>();
                                                parameters.put("username", x);
                                                parameters.put("password", y);
                                                parameters.put("email", z);
                                                parameters.put("skip", String.valueOf(0));
                                                return parameters;
                                            }
                                        };

                                        RequestQueue watchqueue = Volley.newRequestQueue(SubscribeActivity.this);
                                        watchqueue.add(watchrequest);


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
                Toast.makeText(SubscribeActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                return parameters;
            }
        };

        RequestQueue queue4 = Volley.newRequestQueue(SubscribeActivity.this);
        queue4.add(request4);


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
                    public void success(UserListResponse signUpResponse, retrofit.client.Response response) {
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
                        // if error occurs in network transaction then we can get the error in this method.Toast.makeText(SubscribeActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }

                });


        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        user_id = prefs.getString(ssharedpreferences.user_id, null);
        final String user_role = prefs.getString(ssharedpreferences.role, null);


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
                        new RecyclerItemClickListener(SubscribeActivity.this, new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {

                                if (data.size() > position) {
                                    if (data.get(position) != null) {

                                        plan = data.get(position).getPlans_name();
                                       String price = data.get(position).getPrice();
                                        String displayName = data.get(position).getDisplay_name();
                                        Intent in = new Intent(getApplicationContext(), PaymentActivity.class);
                                        /*in.putExtra("period", data.get(position).getDisplay_name());
                                        in.putExtra("amonut", data.get(position).getPrice());*/
                                        startActivityForResult(in, REQUEST_CODE);
                                        startPayment(price,displayName,plan);
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

    private void startPayment(String price, String displayName, String plan) {
        Intent in = getIntent();
        final String username = in.getStringExtra("username");
        final String email = in.getStringExtra("email");

        double a = Double.parseDouble(price);
        double payment = a*100;
        final Activity activity = this;

        final Checkout co = new Checkout();

        try {
            JSONObject options = new JSONObject();
            options.put("name", "Flicknexs");
            options.put("description", "Total Amount");
            //You can omit the image option to fetch the image from dashboard
            options.put("image", "https://tranznexs.com/images/app_icon.png");
            options.put("currency", "INR");
            options.put("amount", payment);

            JSONObject preFill = new JSONObject();
            preFill.put("email", email);
            preFill.put("contact", "1234567890");

            options.put("prefill", preFill);
            // Toast.makeText(this, "response "+preFill+ " "+options, Toast.LENGTH_LONG).show();

            co.open(activity, options);
        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /*final ProgressDialog progressBar = new ProgressDialog(this);
        progressBar.setCancelable(true);//you can cancel it by pressing back button
        progressBar.setMessage("Please wait...");
        progressBar.setCancelable(false);

        if (requestCode == resultCode) {
            stripe_token = data.getStringExtra("stripe_token");

        //    Toast.makeText(this, ""+stripe_token+" "+user_id+" "+plan, Toast.LENGTH_SHORT).show();

            if(user_id !=null) {
                if (stripe_token.length() > 1) {
                    progressBar.show();
                    ApiClient.getClient().payment(user_id,
                            stripe_token, plan,
                            new Callback<paymentresponse>() {
                                @Override
                                public void success(paymentresponse signUpResponse, Response response) {
                                    signUpResponsesData = signUpResponse;

                                    progressBar.dismiss();
                                   *//* SharedPreferences.Editor editor = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE).edit();
                                    editor.putBoolean(ssharedpreferences.login, true);
                                    editor.putString(ssharedpreferences.user_id, signUpResponse.getId());
                                    editor.putString(ssharedpreferences.role, signUpResponse.getRole());
                                    editor.putString(ssharedpreferences.email, signUpResponse.getEmail());
                                    editor.putString(ssharedpreferences.username, signUpResponse.getUsername());
                                    editor.apply();
                                    editor.commit();*//*
                                    Intent in = new Intent(getApplicationContext(), UserHomeActivity.class);
                                    startActivity(in);
                                }

                                @Override
                                public void failure(RetrofitError error) {
                                    Toast.makeText(SubscribeActivity.this, "Check Your Internet Connections", Toast.LENGTH_LONG).show();
                                    progressBar.dismiss();
                                }

                            });
                }
            }

        }*/
    }

    @Override
    public void onBackPressed() {
        Intent in = new Intent(getApplicationContext(), UserHomeActivity.class);
        startActivity(in);
    }

}