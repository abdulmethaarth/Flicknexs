package com.webnexs.flicknexs;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;

public class TermsAndConditionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_condition);


        WebView webView = (WebView) findViewById(R.id.tc);

//Specify the URL you want to display//
        webView.loadUrl(" http://flicknexs.webnexs.org/page/terms-and-conditions");
    }

}
