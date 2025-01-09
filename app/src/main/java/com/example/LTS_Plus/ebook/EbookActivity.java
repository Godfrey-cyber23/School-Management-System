package com.example.LTS_Plus.ebook;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.LTS_Plus.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EbookActivity extends AppCompatActivity {

    private RecyclerView ebookRecyler;
    private DatabaseReference reference;
    private List<EbookData> list;
    private EbookAdapter adapter;

    private ShimmerFrameLayout shimmerFrameLayout;
    private EditText search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Book");

        ebookRecyler = findViewById(R.id.recyclerView);
        shimmerFrameLayout = findViewById(R.id.shimmer_view_container);
        search = findViewById(R.id.searchText);

        reference = FirebaseDatabase.getInstance().getReference().child("pdf");
        reference.keepSynced(true);

        list = new ArrayList<>();
        getData();
    }

    private void getData() {
        shimmerFrameLayout.startShimmer();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear(); // Clear old data to prevent duplication
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    EbookData data = snapshot.getValue(EbookData.class);
                    if (data != null) {
                        list.add(data);
                    }
                }
                adapter = new EbookAdapter(EbookActivity.this, list);
                ebookRecyler.setLayoutManager(new LinearLayoutManager(EbookActivity.this));
                ebookRecyler.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                search.setVisibility(View.VISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                shimmerFrameLayout.stopShimmer();
                shimmerFrameLayout.setVisibility(View.GONE);
                Toast.makeText(EbookActivity.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });
    }

    private void filter(String text) {
        ArrayList<EbookData> filteredList = new ArrayList<>();
        for (EbookData item : list) {
            if (item.getPdfTitle() != null && item.getPdfTitle().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.FilteredList(filteredList);
    }

    @Override
    protected void onPause() {
        if (shimmerFrameLayout.isShimmerStarted()) {
            shimmerFrameLayout.stopShimmer();
        }
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }
}
