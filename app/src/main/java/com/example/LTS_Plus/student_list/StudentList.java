package com.example.LTS_Plus.student_list;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class StudentList extends AppCompatActivity {

    private RecyclerView grade1, grade2, grade3, grade4, grade5, grade6, grade7;
    private LinearLayout grade1NoData, grade2NoData, grade3NoData, grade4NoData, grade5NoData, grade6NoData, grade7NoData;
    private List<StudentListData> list, list1, list2, list3, list4, list5, list6;
    private StudentListAdapter adapter;

    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Students List");

        grade1 = findViewById(R.id.grade1);
        grade2 = findViewById(R.id.grade2);
        grade3 = findViewById(R.id.grade3);
        grade4 = findViewById(R.id.grade4);
        grade5 = findViewById(R.id.grade5);
        grade6 = findViewById(R.id.grade6);
        grade7 = findViewById(R.id.grade7);

        EditText searchText = findViewById(R.id.searchText);

        grade1NoData = findViewById(R.id.grade1NoData);
        grade2NoData = findViewById(R.id.grade2NoData);
        grade3NoData = findViewById(R.id.grade3NoData);
        grade4NoData = findViewById(R.id.grade4NoData);
        grade5NoData = findViewById(R.id.grade5NoData);
        grade6NoData = findViewById(R.id.grade6NoData);
        grade7NoData = findViewById(R.id.grade7NoData);

        reference = FirebaseDatabase.getInstance().getReference().child("student");

        grade1();
        grade2();
        grade3();
        grade4();
        grade5();
        grade6();
        grade7();

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void grade1() {
        dbRef = reference.child("Grade 1");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    grade1NoData.setVisibility(View.VISIBLE);
                    grade1.setVisibility(View.GONE);
                } else {
                    grade1NoData.setVisibility(View.GONE);
                    grade1.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StudentListData data = snapshot.getValue(StudentListData.class);
                        list.add(data);
                    }
                    grade1.setHasFixedSize(true);
                    grade1.setLayoutManager(new LinearLayoutManager(getApplication()));
                    adapter = new StudentListAdapter(list, getApplication());
                    grade1.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getApplicationContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void grade2() {
        dbRef = reference.child("Grade 2");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list1 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    grade2NoData.setVisibility(View.VISIBLE);
                    grade2.setVisibility(View.GONE);
                } else {
                    grade2NoData.setVisibility(View.GONE);
                    grade2.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StudentListData data = snapshot.getValue(StudentListData.class);
                        list1.add(data);
                    }
                    grade2.setHasFixedSize(true);
                    grade2.setLayoutManager(new LinearLayoutManager(getApplication()));
                    adapter = new StudentListAdapter(list1, getApplication());
                    grade2.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getApplicationContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void grade3() {
        dbRef = reference.child("Grade 3");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list2 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    grade3NoData.setVisibility(View.VISIBLE);
                    grade3.setVisibility(View.GONE);
                } else {
                    grade3NoData.setVisibility(View.GONE);
                    grade3.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StudentListData data = snapshot.getValue(StudentListData.class);
                        list2.add(data);
                    }
                    grade3.setHasFixedSize(true);
                    grade3.setLayoutManager(new LinearLayoutManager(getApplication()));
                    adapter = new StudentListAdapter(list2, getApplication());
                    grade3.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getApplicationContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void grade4() {
        dbRef = reference.child("Grade 4");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list3 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    grade4NoData.setVisibility(View.VISIBLE);
                    grade4.setVisibility(View.GONE);
                } else {
                    grade4NoData.setVisibility(View.GONE);
                    grade4.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StudentListData data = snapshot.getValue(StudentListData.class);
                        list3.add(data);
                    }
                    grade4.setHasFixedSize(true);
                    grade4.setLayoutManager(new LinearLayoutManager(getApplication()));
                    adapter = new StudentListAdapter(list3, getApplication());
                    grade4.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getApplicationContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }

    private void grade5() {
        dbRef = reference.child("Grade 5");
        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list4 = new ArrayList<>();
                if (!dataSnapshot.exists()) {
                    grade5NoData.setVisibility(View.VISIBLE);
                    grade5.setVisibility(View.GONE);
                } else {
                    grade5NoData.setVisibility(View.GONE);
                    grade5.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        StudentListData data = snapshot.getValue(StudentListData.class);
                        list4.add(data);
                    }
                    grade5.setHasFixedSize(true);
                    grade5.setLayoutManager(new LinearLayoutManager(getApplication()));
                    adapter = new StudentListAdapter(list4, getApplication());
                    grade5.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getApplicationContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
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
                        StudentListData data = snapshot.getValue(StudentListData.class);
                        list5.add(data);
                    }
                    grade6.setHasFixedSize(true);
                    grade6.setLayoutManager(new LinearLayoutManager(getApplication()));
                    adapter = new StudentListAdapter(list5, getApplication());
                    grade6.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getApplicationContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
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
                        StudentListData data = snapshot.getValue(StudentListData.class);
                        list6.add(data);
                    }
                    grade7.setHasFixedSize(true);
                    grade7.setLayoutManager(new LinearLayoutManager(getApplication()));
                    adapter = new StudentListAdapter(list6, getApplication());
                    grade7.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toasty.error(getApplicationContext(), databaseError.getMessage(), Toasty.LENGTH_SHORT).show();
            }
        });
    }



    private void filter(String text) {
        ArrayList<StudentListData> filtered_list = new ArrayList<>();
        for (StudentListData item : list) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filtered_list.add(item);
            }
        }
        adapter.FilteredList(filtered_list);
    }
}
