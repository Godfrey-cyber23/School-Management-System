package com.example.LTS_Plus.ui.gallery;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.LTS_Plus.R;
import com.example.LTS_Plus.full_image.FullImageView;

import java.util.List;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.galleryViewAdapter> {

    private final Context context;
    private final List<String> images;


    public GalleryAdapter(Context context, List<String> images) {
        this.context = context;
        this.images = images;
    }

    @NonNull
    @Override
    public galleryViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_image, parent, false);
        return new galleryViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull galleryViewAdapter holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(images.get(position)).into(holder.imageView);
        holder.imageView.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",images.get(position));
            context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    public static class galleryViewAdapter extends RecyclerView.ViewHolder {

        final ImageView imageView;

        public galleryViewAdapter(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.image);
        }
    }

}
