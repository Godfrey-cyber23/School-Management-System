package com.example.LTS_Plus.ui.faculty;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.LTS_Plus.R;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {
    private final List<TeacherData> list;
    private final Context context;

    public TeacherAdapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout, parent, false);
        return new TeacherViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder,  int position) {

        TeacherData item = list.get(position);
        holder.name.setText(item.getName());
        holder.phone.setText(item.getPhone());
        holder.post.setText(item.getPost());
        holder.jon.setText(item.getJon());

        setAnimation(holder.itemView);


        try {
            Glide.with(context)
                    .load(item.getImage())
                    .placeholder(R.drawable.profile)
                    .into(holder.imageView);
        } catch (Exception e) {
            Log.e("ImageLoadError", "Error loading image", e); // Log the exception with more context
        }

    }


    private void setAnimation(View view){
        Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
        view.setAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class TeacherViewAdapter extends RecyclerView.ViewHolder {

        private final TextView name;
        private final TextView phone;
        private final TextView post;
        private final TextView jon;
        private final ImageView imageView;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.teacherName);
            phone = itemView.findViewById(R.id.teacherPhone);
            post = itemView.findViewById(R.id.teacherPost);
            jon = itemView.findViewById(R.id.teacherJon);

            imageView = itemView.findViewById(R.id.teacherImage);
        }
    }
}
