package com.example.LTS_Plus.video;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;

public class VideoLecture extends AppCompatActivity {
    private WebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_lecture);

        webView= findViewById(R.id.webViewId);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(false);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.youtube.com/channel/UCaLE871spHwdUzB0Q8Iux0w");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }else {
            super.onBackPressed();
        }
    }
}