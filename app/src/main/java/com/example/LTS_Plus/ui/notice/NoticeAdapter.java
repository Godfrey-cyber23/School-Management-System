package com.example.LTS_Plus.ui.notice;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.LTS_Plus.R;
import com.example.LTS_Plus.full_image.FullImageView;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {

    private final Context context;
    private final ArrayList<NoticeData> list;


    public NoticeAdapter(Context context, ArrayList<NoticeData> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.newss, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {

      final NoticeData currentItem = list.get(position);

        holder.deleteNoticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        try {
            if (currentItem.getImage() != null)
                Glide.with(context).load(currentItem.getImage()).into(holder.deleteNoticeImage);
        } catch (Exception e) {
            Log.e("ErrorLoadingImage", "Exception occurred while loading image: " + e.getMessage(), e);
        }

        holder.deleteNoticeImage.setOnClickListener(v -> {
            Intent intent = new Intent(context, FullImageView.class);
            intent.putExtra("image",currentItem.getImage());
            context.startActivity(intent);
        });

        holder.ebookDownload.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(list.get(position).getImage()));
           context.startActivity(intent);
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class NoticeViewAdapter extends RecyclerView.ViewHolder {

        private final TextView deleteNoticeTitle;
        private final TextView date;
        private final TextView time;
        private final ImageView deleteNoticeImage;
        private final ImageView ebookDownload;


        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);
            deleteNoticeTitle = itemView.findViewById(R.id.deleteNoticeTitle);
            deleteNoticeImage = itemView.findViewById(R.id.deleteNoticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);


            this.ebookDownload = itemView.findViewById(R.id.ebookDownload);
        }
    }
}
