package com.example.LTS_Plus.ui.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LTS_Plus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class GalleryFragment extends Fragment {

    RecyclerView convonRecycler, otherRecycler, independenceRecycler;
    GalleryAdapter adapter;

    DatabaseReference reference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        convonRecycler = view.findViewById(R.id.convonRecycler);
        otherRecycler = view.findViewById(R.id.otherRecycler);
        independenceRecycler = view.findViewById(R.id.independenceRecycler);


        reference = FirebaseDatabase.getInstance().getReference().child("gallery");

        getConvoImage();

        getOtherImage();

        getIndepentImage();

        return  view;

    }

    private void getIndepentImage() {
        reference.child("Independence Day").addValueEventListener(new ValueEventListener() {

            final List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imageList);

                independenceRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                independenceRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });
    }

    private void getOtherImage() {
        reference.child("Other Events").addValueEventListener(new ValueEventListener() {

            final List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imageList);

                otherRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                otherRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });
    }

    private void getConvoImage() {

        reference.child("Convocation").addValueEventListener(new ValueEventListener() {

            final List<String> imageList = new ArrayList<>();

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String data = (String) snapshot.getValue();
                    imageList.add(data);
                }

                adapter = new GalleryAdapter(getContext(), imageList);

                convonRecycler.setLayoutManager(new GridLayoutManager(getContext(), 3));
                convonRecycler.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });
    }
}