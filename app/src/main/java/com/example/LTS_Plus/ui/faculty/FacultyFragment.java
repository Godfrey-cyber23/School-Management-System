package com.example.LTS_Plus.ui.faculty;

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

public class FacultyFragment extends Fragment {

    private RecyclerView mathematics, science, english, informationTechnology, creativeStudies, specialPaper1, specialPaper2, socialStudies;
    private LinearLayout mathNoData, sciNoData, engNoData, itNoData, ctsNoData, sp1NoData, sp2NoData, ssNoData;
    private List<TeacherData> listMath, listScience, listEnglish, listIT, listCTS, listSP1, listSP2, listSS;
    private TeacherAdapter adapter;

    private DatabaseReference reference, dbRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_faculty, container, false);

        mathematics = view.findViewById(R.id.mathematicsRecycler);
        science = view.findViewById(R.id.scienceRecycler);
        english = view.findViewById(R.id.englishRecycler);
        informationTechnology = view.findViewById(R.id.itRecycler);
        creativeStudies = view.findViewById(R.id.ctsRecycler); // Use 'ctsRecycler' for RecyclerView
        specialPaper1 = view.findViewById(R.id.specialPaper1Recycler);
        specialPaper2 = view.findViewById(R.id.specialPaper2Recycler);
        socialStudies = view.findViewById(R.id.socialStudiesRecycler);

        mathNoData = view.findViewById(R.id.mathNoData);
        sciNoData = view.findViewById(R.id.sciNoData);
        engNoData = view.findViewById(R.id.engNoData);
        itNoData = view.findViewById(R.id.itNoData);
        ctsNoData = view.findViewById(R.id.ctsNoData);
        sp1NoData = view.findViewById(R.id.sp1NoData);
        sp2NoData = view.findViewById(R.id.sp2NoData);
        ssNoData = view.findViewById(R.id.ssNoData);

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");

        reference.keepSynced(true);

        loadMathematics();
        loadScience();
        loadEnglish();
        loadInformationTechnology();
        loadCreativeStudies();
        loadSpecialPaper1();
        loadSpecialPaper2();
        loadSocialStudies();

        return view;
    }

    private void loadMathematics() {
        dbRef = reference.child("Mathematics");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listMath = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    mathNoData.setVisibility(View.VISIBLE);
                    mathematics.setVisibility(View.GONE);
                } else {
                    mathNoData.setVisibility(View.GONE);
                    mathematics.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        listMath.add(data);
                    }
                    mathematics.setHasFixedSize(true);
                    mathematics.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(listMath, getContext());
                    mathematics.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void loadScience() {
        dbRef = reference.child("Science");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listScience = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    sciNoData.setVisibility(View.VISIBLE);
                    science.setVisibility(View.GONE);
                } else {
                    sciNoData.setVisibility(View.GONE);
                    science.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        listScience.add(data);
                    }
                    science.setHasFixedSize(true);
                    science.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(listScience, getContext());
                    science.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void loadEnglish() {
        dbRef = reference.child("English");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listEnglish = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    engNoData.setVisibility(View.VISIBLE);
                    english.setVisibility(View.GONE);
                } else {
                    engNoData.setVisibility(View.GONE);
                    english.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        listEnglish.add(data);
                    }
                    english.setHasFixedSize(true);
                    english.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(listEnglish, getContext());
                    english.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void loadInformationTechnology() {
        dbRef = reference.child("Information Technology");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listIT = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    itNoData.setVisibility(View.VISIBLE);
                    informationTechnology.setVisibility(View.GONE);
                } else {
                    itNoData.setVisibility(View.GONE);
                    informationTechnology.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        listIT.add(data);
                    }
                    informationTechnology.setHasFixedSize(true);
                    informationTechnology.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(listIT, getContext());
                    informationTechnology.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void loadCreativeStudies() {
        dbRef = reference.child("Creative and Technology Studies");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listCTS = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    ctsNoData.setVisibility(View.VISIBLE);
                    creativeStudies.setVisibility(View.GONE);
                } else {
                    ctsNoData.setVisibility(View.GONE);
                    creativeStudies.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        listCTS.add(data);
                    }
                    creativeStudies.setHasFixedSize(true);
                    creativeStudies.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(listCTS, getContext());
                    creativeStudies.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSpecialPaper1() {
        dbRef = reference.child("Special Paper 1");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSP1 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    sp1NoData.setVisibility(View.VISIBLE);
                    specialPaper1.setVisibility(View.GONE);
                } else {
                    sp1NoData.setVisibility(View.GONE);
                    specialPaper1.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        listSP1.add(data);
                    }
                    specialPaper1.setHasFixedSize(true);
                    specialPaper1.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(listSP1, getContext());
                    specialPaper1.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSpecialPaper2() {
        dbRef = reference.child("Special Paper 2");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSP2 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    sp2NoData.setVisibility(View.VISIBLE);
                    specialPaper2.setVisibility(View.GONE);
                } else {
                    sp2NoData.setVisibility(View.GONE);
                    specialPaper2.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        listSP2.add(data);
                    }
                    specialPaper2.setHasFixedSize(true);
                    specialPaper2.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(listSP2, getContext());
                    specialPaper2.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSocialStudies() {
        dbRef = reference.child("Social Studies");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listSS = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    ssNoData.setVisibility(View.VISIBLE);
                    socialStudies.setVisibility(View.GONE);
                } else {
                    ssNoData.setVisibility(View.GONE);
                    socialStudies.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        listSS.add(data);
                    }
                    socialStudies.setHasFixedSize(true);
                    socialStudies.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(listSS, getContext());
                    socialStudies.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(requireContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }
}
