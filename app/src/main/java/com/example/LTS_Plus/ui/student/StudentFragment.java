package com.example.LTS_Plus.ui.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LTS_Plus.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class StudentFragment extends Fragment {

    private RecyclerView grade1, grade2, grade3, grade4, grade5,grade6,grade7;
    private LinearLayout grade1NoData, grade2NoData, grade3NoData, grade4NoData, grade5NoData,grade6NoData,grade7NoData;
    private List<StudentData> list, list1, list2, list3, list4, list5,list6;
    private StudentAdapter adapter;

    private DatabaseReference reference, dbRef;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student, container, false);


        grade1 = view.findViewById(R.id.grade1);
        grade2 = view.findViewById(R.id.grade2);
        grade3 = view.findViewById(R.id.grade3);
        grade4 = view.findViewById(R.id.grade4);
        grade5  = view.findViewById(R.id.grade5);
        grade6 = view.findViewById(R.id.grade6);
        grade7 = view.findViewById(R.id.grade7);

        grade1NoData = view.findViewById(R.id.grade1NoData);
        grade2NoData = view.findViewById(R.id.grade2NoData);
        grade3NoData = view.findViewById(R.id.grade3NoData);
        grade4NoData = view.findViewById(R.id.grade4NoData);
        grade5NoData = view.findViewById(R.id.grade5NoData);
        grade6NoData = view.findViewById(R.id.grade6NoData);
        grade7NoData = view.findViewById(R.id.grade7NoData);
        reference = FirebaseDatabase.getInstance().getReference().child("student");

        reference.keepSynced(true);

        grade1();
        grade2();
        grade3();
        grade4();
        grade5();
        grade6();
        grade7();

        return view;
    }



    private void grade1() {
        dbRef = reference.child("Grade 1");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    grade1NoData.setVisibility(View.VISIBLE);
                    grade1.setVisibility(View.GONE);
                }else {


                    grade1NoData.setVisibility(View.GONE);
                    grade1.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        StudentData data = snapshot.getValue(StudentData.class);
                        list.add(data);
                    }
                    grade1.setHasFixedSize(true);
                    grade1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new StudentAdapter(list, getContext());
                    grade1.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });

    }

    private void grade2() {
        dbRef = reference.child("Grade 2");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    grade2NoData.setVisibility(View.VISIBLE);
                    grade2.setVisibility(View.GONE);
                }else {


                    grade2NoData.setVisibility(View.GONE);
                    grade2.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        StudentData data = snapshot.getValue(StudentData.class);
                        list1.add(data);
                    }
                    grade2.setHasFixedSize(true);
                    grade2.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new StudentAdapter(list1, getContext());
                    grade2.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });

    }

    private void grade3() {
        dbRef = reference.child("Grade 3");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    grade3NoData.setVisibility(View.VISIBLE);
                    grade3.setVisibility(View.GONE);
                }else {


                    grade3NoData.setVisibility(View.GONE);
                    grade3.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        StudentData data = snapshot.getValue(StudentData.class);
                        list2.add(data);
                    }
                    grade3.setHasFixedSize(true);
                    grade3.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new StudentAdapter(list2, getContext());
                    grade3.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });

    }

    private void grade4() {
        dbRef = reference.child("Grade 4");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    grade4NoData.setVisibility(View.VISIBLE);
                    grade4.setVisibility(View.GONE);
                }else {
                    grade4NoData.setVisibility(View.GONE);
                    grade4.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        StudentData data = snapshot.getValue(StudentData.class);
                        list3.add(data);
                    }
                    grade4.setHasFixedSize(true);
                    grade4.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new StudentAdapter(list3, getContext());
                    grade4.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });

    }

    private void grade5() {
        dbRef = reference.child("Grade 5");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4 = new ArrayList<>();
                if (!dataSnapshot.exists()){
                    grade5NoData.setVisibility(View.VISIBLE);
                    grade5.setVisibility(View.GONE);
                }else {
                    grade5NoData.setVisibility(View.GONE);
                    grade5.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                        StudentData data = snapshot.getValue(StudentData.class);
                        list4.add(data);
                    }
                    grade5.setHasFixedSize(true);
                    grade5.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new StudentAdapter(list4, getContext());
                    grade5.setAdapter(adapter);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });

    }

    private void grade6() {
        dbRef = reference.child("Grade 6");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list5 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    grade6NoData.setVisibility(View.VISIBLE);
                    grade6.setVisibility(View.GONE);
                } else {
                    grade6NoData.setVisibility(View.GONE);
                    grade6.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StudentData data = snapshot.getValue(StudentData.class);
                        list5.add(data);
                    }

                    // Set up the RecyclerView with the correct adapter and layout manager
                    grade6.setHasFixedSize(true);
                    grade6.setLayoutManager(new LinearLayoutManager(requireContext())); // Set the LayoutManager
                    grade6.setAdapter(new StudentAdapter(list5, requireContext())); // Set the adapter for RecyclerView
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void grade7() {
        dbRef = reference.child("Grade 7");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list6 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    grade7NoData.setVisibility(View.VISIBLE);
                    grade7.setVisibility(View.GONE);
                } else {
                    grade7NoData.setVisibility(View.GONE);
                    grade7.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StudentData data = snapshot.getValue(StudentData.class);
                        list6.add(data);
                    }

                    // Set up the RecyclerView with the correct adapter and layout manager
                    grade7.setHasFixedSize(true);
                    grade7.setLayoutManager(new LinearLayoutManager(requireContext())); // Set the LayoutManager
                    grade7.setAdapter(new StudentAdapter(list6, requireContext())); // Set the adapter for RecyclerView
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }
}

