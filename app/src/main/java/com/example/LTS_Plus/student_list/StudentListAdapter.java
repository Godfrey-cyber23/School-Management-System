package com.example.LTS_Plus.student_list;

import android.content.Context;
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

import java.util.List;

public class StudentListAdapter extends RecyclerView.Adapter<StudentListAdapter.StudentListViewHolder> {

    private final List<StudentListData> list;
    private final Context context;

    public StudentListAdapter(List<StudentListData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_item_layout, parent, false);
        return new StudentListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentListAdapter.StudentListViewHolder holder, int position) {
        StudentListData item = list.get(position);
        holder.name.setText(item.getName());
        holder.phone.setText(item.getPhone());
        holder.address.setText(item.getAddress());

        try {
            Glide.with(context).load(item.getImage()).placeholder(R.drawable.profile).into(holder.imageView);
        } catch (Exception e) {
            Log.e("StudentListAdapter", "Error loading image: " + e.getMessage(), e);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Use specific methods for list changes instead of notifyDataSetChanged()

    // Update the list with a filtered list
    public void FilteredList(List<StudentListData> filteredList) {
        if (filteredList == null || filteredList.isEmpty()) {
            return;
        }

        // Handle changes to the list more efficiently
        int oldSize = list.size();
        list.clear();
        list.addAll(filteredList);

        if (oldSize > 0) {
            notifyItemRangeRemoved(0, oldSize);  // Notify that the previous list is removed
        }

        notifyItemRangeInserted(0, filteredList.size()); // Notify new items are added
    }

    public static class StudentListViewHolder extends RecyclerView.ViewHolder {

        private final TextView name, phone, address;
        private final ImageView imageView;

        public StudentListViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.studentName);
            phone = itemView.findViewById(R.id.studentPhone);
            address = itemView.findViewById(R.id.studentAddress);
            imageView = itemView.findViewById(R.id.studentImage);
        }
    }
}
