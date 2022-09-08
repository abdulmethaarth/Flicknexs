package com.webnexs.flicknexs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class AboutusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);

        WebView webView = (WebView) findViewById(R.id.aboutus);

        webView.loadUrl(" http://flicknexs.webnexs.org/page/about-us");
    }
}
