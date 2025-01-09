package com.example.LTS_Plus.web_view;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.LTS_Plus.R;

public class WebViewActivity extends AppCompatActivity {

    String IntentURL, IntentTitle = "";

    ImageView imBack;
    TextView TVTitle;
    SwipeRefreshLayout swipeRefreshLayout;
    WebView webView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_acitivity);

        // Get URL and Title passed from the Intent
        IntentURL = getIntent().getStringExtra("URL");
        IntentTitle = getIntent().getStringExtra("Title");

        imBack = findViewById(R.id.imBack);
        TVTitle = findViewById(R.id.TVTitle);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        webView1 = findViewById(R.id.webView1);

        // Set up the back button
        imBack.setOnClickListener(v -> onBackPressed());

        // Set the Title of the WebView
        TVTitle.setText(IntentTitle);

        // Initial page load with swipe refresh
        swipeRefreshLayout.post(() -> {
            swipeRefreshLayout.setRefreshing(true);
            LoadPage(IntentURL);
        });

        // Handle swipe refresh
        swipeRefreshLayout.setOnRefreshListener(() -> LoadPage(IntentURL));
    }

    private void LoadPage(String Url) {
        // Set WebView client and Chrome client
        webView1.setWebViewClient(new MyBrowser());
        webView1.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int progress) {
                swipeRefreshLayout.setRefreshing(progress != 100);
            }
        });

        // Get WebView settings and configure for security
        WebSettings webSettings = webView1.getSettings();
        webSettings.setLoadsImagesAutomatically(true);
        webSettings.setJavaScriptEnabled(false);  // Enable only if necessary

        // Disable unnecessary settings for security
        webSettings.setAllowFileAccess(false); // Disable file access
        webSettings.setAllowContentAccess(false); // Disable content access
        webSettings.setDatabaseEnabled(false); // Disable database
        webSettings.setDomStorageEnabled(false); // Disable DOM storage
        webSettings.setGeolocationEnabled(false); // Disable geolocation

        // Check if URL is secure (e.g., starts with https://trusted.com)
        if (Url != null && Url.startsWith("https://trusted.com")) {
            webView1.loadUrl(Url);
        } else {
            // Handle insecure URL (you could show an error or block loading)
            showErrorPage();
        }
    }

    // Handle secure URLs and block unsafe ones
    private static class MyBrowser extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // Only allow loading secure URLs (you can modify the logic based on your app's needs)
            if (url.startsWith("https://trusted.com")) {
                view.loadUrl(url);
            } else {
                // Handle insecure URLs, e.g., show a warning or block the page
                view.loadUrl("about:blank"); // For example, load a blank page or show a warning
            }
            return true;
        }
    }

    // Show an error or block loading for insecure URLs
    private void showErrorPage() {
        // You can display an error page, a warning message, or handle accordingly
        webView1.loadUrl("file:///assets/error.html"); // Load an error page from assets
    }
}
