package com.webnexs.flicknexs;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;

//import com.webnexs.chhimbal.Api1.RetrofitClient;
//import com.webnexs.chhimbal.Model.SocailLoginUser;


public class SigninActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener  {


    public static String user_id,role,email,profile_image,username;
    EditText user, pass;
    TextView results,forgot;
    Button loginButton,register;
    ViewPager viewPager;
    ImageView logo;
    UserListResponse signUpResponsesData;

    public static final String Base_URL=" http://flicknexs.webnexs.org/api/v1/";

    String URL = Base_URL+"login";
    String social = Base_URL+"social_user";
    String fcmtoken=Base_URL+"profile_update_notification";
    String image_url;
    private LoginButton fbLogin;
    Button fb_btn;
    private CallbackManager callbackManager;

    private static final String TAG = "Abdul";
    private GoogleSignInClient googleSignInClient;
    private GoogleSignInAccount googleSignInAccount;
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    private ProgressDialog progress;
    private static final int RC_SIGN_IN = 1;
    String tkn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);



        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);

        progress=new ProgressDialog(this);
        progress.setMessage("Please wait..");
        progress.setCancelable(false);

        user = findViewById(R.id.editText2);
        pass = findViewById(R.id.editText3);
        loginButton = findViewById(R.id.button);
        forgot =findViewById(R.id.forgotpassword);
        register=findViewById(R.id.button1);
        logo=findViewById(R.id.logo);
        signInButton =(SignInButton) findViewById(R.id.sign_button);

        callbackManager = CallbackManager.Factory.create();
        fbLogin = (LoginButton) findViewById(R.id.loginButton);
        fb_btn = (Button) findViewById(R.id.fb_btn);
        screenLogo();

        fb_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fbLogin.performClick();
            }
        });
        fbLogin.setReadPermissions(Arrays.asList("email", "public_profile"));
        fbLogin.registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {

                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        //   Log.d(TAG, "onSuccess: " + loginResult);
                        if (AccessToken.getCurrentAccessToken() != null) {
                            RequestData();
                            progress.show();
                        }
                    }

                    @Override
                    public void onCancel() {
                        // Log.d(TAG, "onCancel: User cancelled sign-in");
                        Toast.makeText(SigninActivity.this, "cancel", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        //  Log.d(TAG, "onError: " + error);
                        Toast.makeText(SigninActivity.this, "error", Toast.LENGTH_SHORT).show();
                    }
                });

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
            }
        });

        SharedPreferences prefs = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE);
        if(prefs.getBoolean(ssharedpreferences.login, false)) {
            startActivity(new Intent(SigninActivity.this, UserHomeActivity.class));
            finish();
        }

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent in=new Intent(getApplicationContext(),SignupActivity.class);
                startActivity(in);



                // Show a message to the user that we are signing in.
                System.out.println("Signing in...");
            }
        });

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in= new Intent(getApplicationContext(),ForgotpasswordActivity.class);
                startActivity(in);
            }
        });
        loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                final String mobileno = user.getText().toString();
                final String password = pass.getText().toString();


                if (mobileno.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the User Name", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Enter the password", Toast.LENGTH_SHORT).show();
                }

                else
                {
                    StringRequest request = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                                    JSONArray dataArray = jsonObject.getJSONArray("user_details");
                                    for (int i = 0; i < dataArray.length(); i++) {

                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        user_id = dataobj.getString("user_id");
                                        role = dataobj.getString("role");
                                        username = dataobj.getString("username");
                                        email= dataobj.getString("email");
                                        profile_image = dataobj.getString("profile_image");
                                    }

                                    SharedPreferences.Editor editor = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE).edit();
                                    editor.putBoolean(ssharedpreferences.login,true);
                                    editor.putString(ssharedpreferences.user_id, user_id);
                                    editor.putString(ssharedpreferences.role,role);
                                    editor.putString(ssharedpreferences.email,email);
                                    editor.putString(ssharedpreferences.username,username);
                                    editor.apply();
                                    Intent goto_nxt = new Intent(SigninActivity.this, UserHomeActivity.class);
                                    startActivity(goto_nxt);


                                    tkn = FirebaseInstanceId.getInstance().getToken();

                                  Log.d("Appqqqq",tkn);
                                    Toast.makeText(getApplicationContext(),""+tkn,Toast.LENGTH_LONG).show();


                                    RequestQueue queue = Volley.newRequestQueue(SigninActivity.this);
                                    StringRequest request = new StringRequest(Request.Method.POST, fcmtoken, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {

                                            Toast.makeText(SigninActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                                            Log.i("My error", "" + error);
                                        }
                                    }) {
                                        @Override
                                        protected Map<String, String> getParams() throws AuthFailureError {

                                            Map<String, String> map = new HashMap<String, String>();
                                            map.put("token_id", tkn);
                                            map.put("user_id",user_id);


                                            return map;
                                        }
                                    };
                                    queue.add(request);
                                }
                                else if ((jsonObject.getString("status").equalsIgnoreCase("mismatch")))
                                {
                                    Toast.makeText(getApplicationContext(),"Check your password", Toast.LENGTH_SHORT).show();
                                }
                                else if((jsonObject.getString("status").equalsIgnoreCase("verifyemail")))
                                {
                                    Toast.makeText(getApplicationContext(),""+jsonObject.getString("note"), Toast.LENGTH_SHORT).show();
                                }

                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Invalid username and password", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            Toast.makeText(SigninActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("username", user.getText().toString());
                            parameters.put("password", pass.getText().toString());
                            return parameters;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(SigninActivity.this);
                    queue.add(request);
                    queue.getCache().clear();
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {

        if (result.isSuccess()) {
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
              /*  username1 = account.getDisplayName();
                email1 = account.getEmail();
                // userId.setText(account.getId());
                image_url = account.getPhotoUrl().toString();*/
              /*  } catch (NullPointerException e) {
                    image_url= "http://blessingtv.tv/vod/content/uploads/avatars/default.jpg";
                }*/
                SocialLogin(account);
            } else {
                Toast.makeText(getApplicationContext(), "Sign in cancel", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void screenLogo() {
        ApiClient.getClient().getUsersList(
                new Callback<UserListResponse>() {
                    @Override
                    public void success(UserListResponse signUpResponse, retrofit.client.Response response) {
                        signUpResponsesData = signUpResponse;

                        String x=signUpResponse.getImage_url();
                        String y=signUpResponse.getSettings();
                        String z=x+y;
                        Picasso.get().load(z).into(logo);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(SigninActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void SocialLogin(GoogleSignInAccount account) {
       final String username1 = account.getDisplayName();
       final String email1 = account.getEmail();
        final String id = account.getId();


        if (account.getPhotoUrl() != null) {
            Uri image = account.getPhotoUrl();
            image_url = image.toString();
        }else{
            image_url = "http://blessingtv.tv/vod/content/uploads/avatars/default.jpg";
        }
        StringRequest request = new StringRequest(Request.Method.POST, social, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString("status").equalsIgnoreCase("true")) {

                            JSONArray dataArray = jsonObject.getJSONArray("user_details");
                            for (int i = 0; i < dataArray.length(); i++) {

                                JSONObject dataobj = dataArray.getJSONObject(i);
                                user_id = dataobj.getString("id");
                                role = dataobj.getString("role");
                                username = dataobj.getString("username");
                                email = dataobj.getString("email");
                                profile_image = dataobj.getString("avatar");
                            }

                            SharedPreferences.Editor editor = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE).edit();
                            editor.putBoolean(ssharedpreferences.login, true);
                            editor.putString(ssharedpreferences.user_id, user_id);
                            editor.putString(ssharedpreferences.role, role);
                            editor.putString(ssharedpreferences.email, email);
                            editor.putString(ssharedpreferences.username, username);
                            editor.putString(ssharedpreferences.status, "true");
                            editor.apply();
                            editor.commit();
                            Intent intent = new Intent(SigninActivity.this, UserHomeActivity.class);
                            startActivity(intent);

                        }
                        else
                        {
                            Toast.makeText(getApplicationContext(),"Try again.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    Toast.makeText(SigninActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("email", email1);
                    parameters.put("url", image_url);
                    parameters.put("username", username1+id);
                    parameters.put("login_type", "google");
                    return parameters;
                }
            };
            RequestQueue queue = Volley.newRequestQueue(SigninActivity.this);
            queue.add(request);

    }

    private void RequestData() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                final JSONObject json = response.getJSONObject();
                try {
                    final String name = json.getString("name");
                    final String id = json.getString("id");
                    final String url =  json.getJSONObject("picture").getJSONObject("data").getString("url");

                    StringRequest request = new StringRequest(Request.Method.POST, social, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                if (jsonObject.getString("status").equalsIgnoreCase("true")) {
                                    progress.cancel();
                                    JSONArray dataArray = jsonObject.getJSONArray("user_details");
                                    for (int i = 0; i < dataArray.length(); i++) {

                                        JSONObject dataobj = dataArray.getJSONObject(i);
                                        user_id = dataobj.getString("id");
                                        role = dataobj.getString("role");
                                        username = dataobj.getString("username");
                                        email = dataobj.getString("email");
                                        profile_image = dataobj.getString("avatar");
                                    }

                                    SharedPreferences.Editor editor = getSharedPreferences(ssharedpreferences.My_preference_name, MODE_PRIVATE).edit();
                                    editor.putBoolean(ssharedpreferences.login, true);
                                    editor.putString(ssharedpreferences.user_id, user_id);
                                    editor.putString(ssharedpreferences.role, role);
                                    editor.putString(ssharedpreferences.email, email);
                                    editor.putString(ssharedpreferences.username, username);
                                    editor.putString(ssharedpreferences.status, "true");
                                    editor.apply();
                                    editor.commit();
                                    Intent intent = new Intent(SigninActivity.this, UserHomeActivity.class);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(getApplicationContext(),"Try again.", Toast.LENGTH_SHORT).show();
                                    progress.cancel();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
                            progress.cancel();
                            Toast.makeText(SigninActivity.this, "Check Your Internet Connection", Toast.LENGTH_LONG).show();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("email", "");
                            parameters.put("url", url);
                            parameters.put("username", name+id);
                            parameters.put("login_type", "facebook");
                            return parameters;
                        }
                    };
                    RequestQueue queue = Volley.newRequestQueue(SigninActivity.this);
                    queue.add(request);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,link,email,picture");
        request.setParameters(parameters);
        request.executeAsync();
    }
    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("EXIT", true);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }
}

