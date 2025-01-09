package com.example.LTS_Plus.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.example.LTS_Plus.R;

import java.util.ArrayList;
import java.util.List;

public class AboutFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about, container, false);

        List<BranchModel> list = new ArrayList<>();
        list.add(new BranchModel(R.drawable.ic_computer, "IT", "IT started in 2017. Currently, there are 100 students."));
        list.add(new BranchModel(R.drawable.ic_settings, "Science", "Science started in 1970. Currently, there are 100 students."));

        BranchAdapter adapter = new BranchAdapter(requireContext(), list);
        ViewPager viewPager = view.findViewById(R.id.viewPage);
        if (viewPager != null) {
            viewPager.setAdapter(adapter);
        }

        ImageView imageView = view.findViewById(R.id.collage_image);
        ImageView map = view.findViewById(R.id.map);

        if (map != null) {
            map.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0,0?q=Literacy Tree School, Bonaventure"));
                Intent chooser = Intent.createChooser(intent, getString(R.string.launch_maps));
                startActivity(chooser);
            });
        }

        Glide.with(requireContext())
                .load("https://firebasestorage.googleapis.com/v0/b/admin-dash-42c58.appspot.com/o/Slid-Show%2Fupdate-school.jpg?alt=media&token=83214c12-a4a3-4061-8254-684739f7fc1f")
                .into(imageView);

        return view;
    }
}
