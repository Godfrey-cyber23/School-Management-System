package com.example.LTS_Plus.admission;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.LTS_Plus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

import es.dmoral.toasty.Toasty;

public class AdmissionActivity extends AppCompatActivity {

    private EditText ChildsName,
            studentDateOfBirth,fathersName, fatherNRC,
            fatherDateOfBirth, fatherPhone, motherName, motherNRC, motherDateOfBirth,
            districtField, subDistrictField, postCodeField, villageAddress,
            passingYear, educationBoard, attendancePercentage;

    private String childsName, birthCertificateNumber, dateOfBirth,
            fatherEnglishName, fatherNRCNumber, fatherDOB, fatherContact,
            motherEnglishName, motherNRCNumber, motherDOB,
            district, subDistrict, postCode, address,
            previousSchool, yearOfPassing, boardName, roll, result, attendanceRate, downloadUrl = "";

    private ImageView childsImage;
    private Spinner admissionClassSpinner, divisionSpinner, genderSpinner;
    private Button submitAdmissionFormBtn;
    private final int REQ = 1;
    private Bitmap bitmap = null;
    private String selectedGrade, selectedDivision, selectedGender;

    private ProgressDialog progressDialog;
    private StorageReference storageReference;
    private DatabaseReference reference, dbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);

        ChildsName = findViewById(R.id.ChildsName);
        studentDateOfBirth = findViewById(R.id.studentDateOfBirth);

        fathersName = findViewById(R.id.fathersName);
        fatherNRC = findViewById(R.id.fatherNRC);
        fatherDateOfBirth = findViewById(R.id.fatherDateOfBirth);
        fatherPhone = findViewById(R.id.fatherPhone);

        motherName = findViewById(R.id.motherName);
        motherNRC = findViewById(R.id.motherNRC);
        motherDateOfBirth = findViewById(R.id.motherDateOfBirth);

        districtField = findViewById(R.id.districtField);
        subDistrictField = findViewById(R.id.subDistrictField);
        postCodeField = findViewById(R.id.postCodeField);
        villageAddress = findViewById(R.id.villageAddress);

        passingYear = findViewById(R.id.passingYear);
        educationBoard = findViewById(R.id.educationBoard);
        attendancePercentage = findViewById(R.id.attendancePercentage);

        admissionClassSpinner = findViewById(R.id.admissionClassSpinner);
        divisionSpinner = findViewById(R.id.divisionSpinner);
        genderSpinner = findViewById(R.id.genderSpinner);

        childsImage = findViewById(R.id.childsImage);
        submitAdmissionFormBtn = findViewById(R.id.submitAdmissionFormBtn);
        progressDialog = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("Admission");
        storageReference = FirebaseStorage.getInstance().getReference();

        // Gender spinner
        String[] genderOptions = new String[]{"Select Gender", "Male", "Female", "Other"};
        genderSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderOptions));

        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = genderSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        // Division spinner
        String[] divisions = new String[]{"Select Division", "Rajshahi", "Rangpur", "Dhaka", "Khulna", "Barisal", "Sylhet", "Chittagong", "Mymensingh"};
        divisionSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, divisions));

        divisionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDivision = divisionSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });
// Class spinner
String[] classes = new String[]{
    "Select Grade", 
    "Baby Class",  
    "Grade 1", 
    "Grade 2", 
    "Grade 3", 
    "Grade 4", 
    "Grade 5", 
    "Grade 6", 
    "Grade 7"
};
admissionClassSpinner.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classes));

admissionClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedGrade = admissionClassSpinner.getSelectedItem().toString();
        if (selectedGrade.equals("Select Grade")) {
            Toasty.warning(AdmissionActivity.this, "Please select a valid grade", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) { }
});


        childsImage.setOnClickListener(v -> openGallery());

        submitAdmissionFormBtn.setOnClickListener(v -> validateForm());
    }

    private void validateForm() {
    childsName = ChildsName.getText().toString().trim();
    dateOfBirth = studentDateOfBirth.getText().toString().trim();

    fatherEnglishName = fathersName.getText().toString().trim();
    fatherNRCNumber = fatherNRC.getText().toString().trim();
    fatherDOB = fatherDateOfBirth.getText().toString().trim();
    fatherContact = fatherPhone.getText().toString().trim();

    motherEnglishName = motherName.getText().toString().trim();
    motherNRCNumber = motherNRC.getText().toString().trim();
    motherDOB = motherDateOfBirth.getText().toString().trim();

    if (childsName.isEmpty() || dateOfBirth.isEmpty()) {
        Toasty.error(this, "Please fill in all child's details.", Toast.LENGTH_SHORT).show();
        return;
    }

    if (selectedGrade.equals("Select Grade")) {
        Toasty.warning(this, "Please select a valid grade.", Toast.LENGTH_SHORT).show();
        return;
    }

    if (bitmap == null) {
        Toasty.warning(this, "Please upload a Child's image.", Toast.LENGTH_SHORT).show();
        return;
    }

    uploadData();
}


    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQ && resultCode == RESULT_OK) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            childsImage.setImageBitmap(bitmap);
        }
    }
}
