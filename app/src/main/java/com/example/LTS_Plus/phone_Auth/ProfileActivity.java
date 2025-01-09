package com.example.LTS_Plus.phone_Auth;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class ProfileActivity extends AppCompatActivity {


    EditText mviewusername;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    TextView mmovetoupdatprofile;

    FirebaseFirestore firebaseFirestore;

    ImageView mviewuserimageimageView;

    StorageReference storageReference;

    androidx.appcompat.widget.Toolbar mtoolbarofviewprofile;
    ImageButton mbackbuttonviewprofile;

    FirebaseStorage firebaseStorage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Objects.requireNonNull(getSupportActionBar()).hide();


        mviewusername = findViewById(R.id.viewusername);
        mmovetoupdatprofile = findViewById(R.id.movetoupdateprofile);
        mviewuserimageimageView = findViewById(R.id.viewuserimageimageView);
        mtoolbarofviewprofile = findViewById(R.id.toolbarofviewprofile);
        mbackbuttonviewprofile = findViewById(R.id.backbuttonofviewprofile);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        setSupportActionBar(mtoolbarofviewprofile);

        mbackbuttonviewprofile.setOnClickListener(v -> finish());

        storageReference = firebaseStorage.getReference();
        storageReference.child("Images").child(Objects.requireNonNull(firebaseAuth.getUid())).child("Profile Photo").getDownloadUrl().addOnSuccessListener(uri -> Picasso.get().load(uri).into(mviewuserimageimageView));


        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                UserProfile muserprofile = snapshot.getValue(UserProfile.class);
                assert muserprofile != null;
                mviewusername.setText(muserprofile.getUsername());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toasty.error(getApplicationContext(), "Failed To Fetch", Toasty.LENGTH_SHORT).show();

            }
        });

        mmovetoupdatprofile.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, UpdateProfile.class);
            intent.putExtra("username",mviewusername.getText().toString());
            startActivity(intent);
        });

    }
}
