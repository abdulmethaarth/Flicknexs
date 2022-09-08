package com.webnexs.flicknexs;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.InputStream;

import retrofit.Callback;
import retrofit.RetrofitError;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT =5000;
    spalashscreenlist signUpResponsesData;
    ImageView logo;
    LinearLayout splashlinear;
    private InputStream is;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        logo=(ImageView)findViewById(R.id.splash);


       
        ApiClient.getClient().getsplash(
                new Callback<spalashscreenlist>() {
                    @Override
                    public void success(spalashscreenlist signUpResponse, retrofit.client.Response response) {
                        signUpResponsesData = signUpResponse;
                        // Toast.makeText(getApplicationContext(),signUpResponse.getImage_url(),Toast.LENGTH_LONG).show();

                        String x=signUpResponse.getImage_url();
                        Picasso.get().load(x).into(logo);

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(SplashScreenActivity.this, error.toString(), Toast.LENGTH_LONG).show();

                    }

                });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent =new Intent(SplashScreenActivity.this, SigninActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },SPLASH_TIME_OUT);

    }
}
