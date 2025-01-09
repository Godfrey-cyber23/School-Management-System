package com.example.LTS_Plus.full_image;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.LTS_Plus.R;
import com.github.chrisbanes.photoview.PhotoView;

public class FullImageView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image_view);

        PhotoView imageView = findViewById(R.id.imageView);

        String image = getIntent().getStringExtra("image");

        Glide.with(this).load(image).into(imageView);
    }
}