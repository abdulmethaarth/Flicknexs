package com.webnexs.flicknexs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class PrivacypolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacypolicy);



        WebView webView = (WebView) findViewById(R.id.privacy);

//Specify the URL you want to display//
        webView.loadUrl(" http://flicknexs.webnexs.org/page/about-us");
    }
}
