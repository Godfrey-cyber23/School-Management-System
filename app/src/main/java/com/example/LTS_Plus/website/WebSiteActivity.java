package com.example.LTS_Plus.website;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;

public class WebSiteActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_site);


//        getSupportActionBar().hide();

        webView=(WebView) findViewById(R.id.webViewId);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.bing.com/ck/a?!&&p=c7717489bbb23143b86b534354f33c49be5fe6715d79139e409f4ddfc4a7d6beJmltdHM9MTczMzc4ODgwMA&ptn=3&ver=2&hsh=4&fclid=2c23108a-1604-6cb0-0d31-043717046d95&psq=literacy+tree+school+makeni&u=a1aHR0cHM6Ly93d3cuZmFjZWJvb2suY29tL0xJVEVSQUNZVFJFRVNDSE9PTC8&ntb=1");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()){
            webView.goBack();
        }
        else {
            super.onBackPressed();
        }
    }
}