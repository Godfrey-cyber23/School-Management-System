package com.example.LTS_Plus.ui.quiz;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class ScoreActivity extends AppCompatActivity {

    int score, total;
    FirebaseUser user;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scorce);
        score = getIntent().getIntExtra("score",0);
        total = getIntent().getIntExtra("total",0);

        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference();

        TextView scoreTxt = findViewById(R.id.score);
        TextView totalTxt = findViewById(R.id.total);

        scoreTxt.setText(String.valueOf(score));
        totalTxt.setText(String.valueOf(total));

        reference.child("Score").child(user.getUid()).child("score").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()){
                    score += Integer.parseInt(Objects.requireNonNull(dataSnapshot.getValue()).toString());
                }
                dataSnapshot.getRef().setValue(score);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}