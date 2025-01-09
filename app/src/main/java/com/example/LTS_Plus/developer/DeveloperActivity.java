package com.example.LTS_Plus.developer;

import static com.example.LTS_Plus.until.Utils.PrivacyPolicyUrl;
import static com.example.LTS_Plus.until.Utils.WebSite;

import android.content.Intent;
import android.os.Bundle;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;
import com.example.LTS_Plus.web_view.WebViewActivity;

public class DeveloperActivity extends AppCompatActivity {

    RelativeLayout RLWebsite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);

        RLWebsite = findViewById(R.id.RLWebsite);

        RLWebsite.setOnClickListener(v -> {
            Intent i = new Intent(DeveloperActivity.this, WebViewActivity.class);
            i.putExtra("URL", WebSite);
            i.putExtra("Title", "Godfrey");
            startActivity(i);
        });

        RLWebsite.setOnClickListener(v -> {
            Intent i = new Intent(DeveloperActivity.this, WebViewActivity.class);
            i.putExtra("URL", PrivacyPolicyUrl);
            i.putExtra("Title", "Privacy Policy");
            startActivity(i);
        });
    }
}