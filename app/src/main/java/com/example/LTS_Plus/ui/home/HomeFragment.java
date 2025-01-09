package com.example.LTS_Plus.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.LTS_Plus.R;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private ViewPager2 viewPager;
    private final List<String> imageUrls = new ArrayList<>();
    private StorageReference storageReference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Initialize Firebase Storage
        FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // Initialize ViewPager2
        viewPager = view.findViewById(R.id.slider);

        // Fetch image URLs from Firebase
        fetchImageUrls();

        // Map functionality
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("geo:0,0?q=Literacy Tree School"));
            Intent chooser = Intent.createChooser(intent, "Launch Maps");
            startActivity(chooser);
        });

        return view;
    }

    // Fetch image URLs from Firebase Storage
    private void fetchImageUrls() {
        // Example: Assuming the images are stored in a "images" folder
        StorageReference imagesRef = storageReference.child("images");

        // List all items (images) in the folder
        // Handle error
        imagesRef.listAll().addOnSuccessListener(listResult -> {
            // Iterate through the list of items
            for (StorageReference item : listResult.getItems()) {
                // Get the download URL for each image
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    // Add the URL to the list
                    imageUrls.add(uri.toString());

                    // Set the ViewPager adapter dynamically
                    setViewPagerAdapter(imageUrls);
                });
            }
        }).addOnFailureListener(Throwable::printStackTrace);
    }

    // Set the images on the ViewPager dynamically
// Set the images on the ViewPager dynamically
    private void setViewPagerAdapter(List<String> imageUrls) {
        // Create an adapter for the ViewPager
        // Inside HomeFragment or the fragment where you're using ViewPager2
        ImagePagerAdapter adapter = new ImagePagerAdapter(getActivity(), imageUrls);
        viewPager.setAdapter(adapter);
    }

    // Adapter for ViewPager2
    private static class ImagePagerAdapter extends androidx.viewpager2.adapter.FragmentStateAdapter {
        private final List<String> imageUrls;

        // Constructor to pass FragmentActivity (context) to FragmentStateAdapter
        public ImagePagerAdapter(FragmentActivity activity, List<String> imageUrls) {
            super(activity);  // Passing the activity to the FragmentStateAdapter
            this.imageUrls = imageUrls;
        }

        @Override
        public int getItemCount() {
            return imageUrls.size();
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            return ImageFragment.newInstance(imageUrls.get(position)); // Pass the URL for the image
        }
    }

    // Fragment to display image
    public static class ImageFragment extends Fragment {

        private static final String IMAGE_URL = "image_url";

        public static ImageFragment newInstance(String imageUrl) {
            ImageFragment fragment = new ImageFragment();
            Bundle args = new Bundle();
            args.putString(IMAGE_URL, imageUrl);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);
            assert getArguments() != null;
            String imageUrl = getArguments().getString(IMAGE_URL);
            ImageView imageView = view.findViewById(R.id.imageView);

            // Load the image using Picasso
            Picasso.get().load(imageUrl).into(imageView);
            return view;
        }
    }
}
