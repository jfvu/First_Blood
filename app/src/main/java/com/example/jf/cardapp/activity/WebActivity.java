package com.example.jf.cardapp.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.jf.cardapp.R;

public class WebActivity extends AppCompatActivity {
    private int ID;
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        Intent intent=getIntent();
        ID=intent.getIntExtra("ID",0);
        webView= (WebView) findViewById(R.id.webView01);
        String url = "http://60.205.177.241:8002/News/PreView?id="+ID;
        Log.e("ccc",url);
        webView.setWebViewClient(new MyWebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        openUrl(url);
        webView.loadUrl(url);
    }
    public void openUrl(String url)
    {
        webView.setInitialScale(80);
        webView.loadUrl(url);
    }
    private class MyWebViewClient extends WebViewClient
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url)
        {
            view.loadUrl(url);
            return true;
        }
    }
}
