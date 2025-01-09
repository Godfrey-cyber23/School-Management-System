package com.example.LTS_Plus.ebook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LTS_Plus.R;

import java.util.ArrayList;
import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    private final Context context;
    private List<EbookData> list;

    public EbookAdapter(Context context, List<EbookData> list) {
        this.context = context;
        this.list = list != null ? list : new ArrayList<>();
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item_layout, parent, false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {
        EbookData ebook = list.get(position);
        if (ebook != null) {
            holder.ebookName.setText(ebook.getPdfTitle());
            setAnimation(holder.itemView);

            holder.itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, PdfViewerActivity.class);
                intent.putExtra("pdfUrl", ebook.getPdfUrl());
                context.startActivity(intent);
            });

            holder.ebookDownload.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(ebook.getPdfUrl()));
                context.startActivity(intent);
            });
        }
    }

    private void setAnimation(View view) {
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        if (view.getAnimation() == null) { // Only set animation if it's not already set
            view.startAnimation(animation);
        }
    }

    @Override
    public int getItemCount() {
        return list != null ? list.size() : 0;
    }

    public void FilteredList(ArrayList<EbookData> filteredList) {
        // If filteredList is not null, assign it to the 'list'; otherwise, assign a new empty ArrayList.
        list = filteredList != null ? filteredList : new ArrayList<>();

        // Notify the adapter that the data set has been updated.
        notifyDataSetChanged();
    }


    public static class EbookViewHolder extends RecyclerView.ViewHolder {

        private final TextView ebookName;
        private final ImageView ebookDownload;

        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);
            ebookName = itemView.findViewById(R.id.ebookName);
            ebookDownload = itemView.findViewById(R.id.ebookDownload);
        }
    }
}
