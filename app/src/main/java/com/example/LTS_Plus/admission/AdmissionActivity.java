package com.example.LTS_Plus.admission;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class AdmissionActivity extends AppCompatActivity {

    private EditText childFirstName;
    private EditText childSurname;
    private EditText dateOfBirth;
    private EditText placeOfBirth;
    private EditText nationality;
    private EditText religion;
    private EditText childAge;
    private EditText fathersName;
    private EditText fathersContact;
    private EditText mothersName;
    private EditText mothersContact;
    private EditText emergencyContact;
    private EditText doctorsDetails;
    private EditText doctorContact;
    private ImageView childsImage;
    private final Spinner admissionClassSpinner;
    private Spinner childsGender;
    private Bitmap bitmap = null;
    private String selectedGrade;
    private String selectedGender;

    // Unused fields
    private EditText residentialAddress;
    private EditText dateOfEntry;
    private EditText questionOne;
    private EditText questionTwo;
    private EditText cardUpload;
    private EditText otherInfo;
    private EditText declaration;

    private DatabaseReference dbRef;
    private StorageReference storageRef;
    private ProgressDialog progressDialog;

    private static final int REQ = 1;

    public AdmissionActivity(Spinner admissionClassSpinner) {
        this.admissionClassSpinner = admissionClassSpinner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admission);

        // Initialize views
        childFirstName = findViewById(R.id.childFirstName);
        childSurname = findViewById(R.id.child_surname);
        dateOfBirth = findViewById(R.id.dateOfBirth);
        placeOfBirth = findViewById(R.id.placeOfBirth);
        nationality = findViewById(R.id.nationality);
        religion = findViewById(R.id.religion);
        childAge = findViewById(R.id.childsAge);
        childsGender = findViewById(R.id.childsGender);
        fathersName = findViewById(R.id.fathersName);
        fathersContact = findViewById(R.id.fathersContact);
        mothersName = findViewById(R.id.mothersName);
        mothersContact = findViewById(R.id.mothersContact);
        residentialAddress = findViewById(R.id.residentialAddress);
        emergencyContact = findViewById(R.id.emergencyContact);
        doctorsDetails = findViewById(R.id.doctorDetails);
        doctorContact = findViewById(R.id.doctorsContact);
        dateOfEntry = findViewById(R.id.dateOfEntry);
        questionOne = findViewById(R.id.question1);
        questionTwo = findViewById(R.id.question2);
        cardUpload = findViewById(R.id.cardUpload);
        otherInfo = findViewById(R.id.anyOtherInfo);
        declaration = findViewById(R.id.declarationName);

        Button submitAdmissionFormBtn = findViewById(R.id.btnSubmit);
        progressDialog = new ProgressDialog(this);

        // Set up Firebase references
        dbRef = FirebaseDatabase.getInstance().getReference().child("Admissions");
        storageRef = FirebaseStorage.getInstance().getReference().child("ChildImages");

        // Spinner setup
        setupGenderSpinner();
        setupClassSpinner();

        // Image selection
        childsImage = findViewById(R.id.passportPhotoUpload);
        childsImage.setOnClickListener(v -> openGallery());

        // Submit button listener
        submitAdmissionFormBtn.setOnClickListener(v -> validateForm());
    }

    private void setupGenderSpinner() {
        String[] genderOptions = {"Select Gender", "Male", "Female", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, genderOptions);
        childsGender.setAdapter(adapter);
        childsGender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGender = childsGender.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void setupClassSpinner() {
        String[] classOptions = {"Select Grade", "Baby Class", "Grade 1", "Grade 2", "Grade 3", "Grade 4", "Grade 5", "Grade 6", "Grade 7"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, classOptions);
        admissionClassSpinner.setAdapter(adapter);
        admissionClassSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedGrade = admissionClassSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void validateForm() {
        // Retrieve inputs
        String childFirstNameValue = this.childFirstName.getText().toString().trim();
        String childSurnameValue = childSurname.getText().toString().trim();
        String dateOfBirthValue = dateOfBirth.getText().toString().trim();
        String placeOfBirthValue = placeOfBirth.getText().toString().trim();
        String nationalityValue = nationality.getText().toString().trim();
        String religionValue = religion.getText().toString().trim();
        String childAgeValue = childAge.getText().toString().trim();
        String fathersNameValue = fathersName.getText().toString().trim();
        String fathersContactValue = fathersContact.getText().toString().trim();
        String mothersNameValue = mothersName.getText().toString().trim();
        String mothersContactValue = mothersContact.getText().toString().trim();
        String emergencyContactValue = emergencyContact.getText().toString().trim();
        String doctorsDetailsValue = doctorsDetails.getText().toString().trim();
        String doctorContactValue = doctorContact.getText().toString().trim();
        String residentialAddressValue = residentialAddress.getText().toString().trim();
        String dateOfEntryValue = dateOfEntry.getText().toString().trim();
        String questionOneValue = questionOne.getText().toString().trim();
        String questionTwoValue = questionTwo.getText().toString().trim();
        String cardUploadValue = cardUpload.getText().toString().trim();
        String otherInfoValue = otherInfo.getText().toString().trim();
        String declarationValue = declaration.getText().toString().trim();

        // Validate each field
        if (childFirstNameValue.isEmpty() || childSurnameValue.isEmpty()) {
            Toasty.error(this, "Child's name is required.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (residentialAddressValue.isEmpty()) {
            Toasty.error(this, "Residential Address is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (dateOfBirthValue.isEmpty()) {
            Toasty.error(this, "Date of Birth is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (placeOfBirthValue.isEmpty()) {
            Toasty.error(this, "Place of Birth is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (nationalityValue.isEmpty()) {
            Toasty.error(this, "Nationality is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (religionValue.isEmpty()) {
            Toasty.error(this, "Religion is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (childAgeValue.isEmpty() || !childAgeValue.matches("\\d+")) {
            Toasty.error(this, "Valid Age is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedGender.equals("Select Gender")) {
            Toasty.warning(this, "Please select a valid gender.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (selectedGrade.equals("Select Grade")) {
            Toasty.warning(this, "Please select a valid grade.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fathersNameValue.isEmpty() || fathersContactValue.isEmpty() || !fathersContactValue.matches("\\d{10}")) {
            Toasty.error(this, "Valid Father's details are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (mothersNameValue.isEmpty() || mothersContactValue.isEmpty() || !mothersContactValue.matches("\\d{10}")) {
            Toasty.error(this, "Valid Mother's details are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (emergencyContactValue.isEmpty() || !emergencyContactValue.matches("\\d{10}")) {
            Toasty.error(this, "Valid Emergency Contact is required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (doctorsDetailsValue.isEmpty() || doctorContactValue.isEmpty() || !doctorContactValue.matches("\\d{10}")) {
            Toasty.error(this, "Valid Doctor's details are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (bitmap == null) {
            Toasty.warning(this, "Please upload the child's image.", Toast.LENGTH_SHORT).show();
            return;
        }

        // If all validations pass
        Toasty.success(this, "Form validated successfully!", Toast.LENGTH_SHORT).show();
        uploadData(childFirstNameValue, childSurnameValue, dateOfBirthValue, placeOfBirthValue, nationalityValue,
                religionValue, childAgeValue, fathersNameValue, fathersContactValue, mothersNameValue,
                mothersContactValue, emergencyContactValue, doctorsDetailsValue, doctorContactValue,
                residentialAddressValue, dateOfEntryValue, questionOneValue, questionTwoValue, cardUploadValue,
                otherInfoValue, declarationValue);
    }

    private void uploadData(String childFirstName, String childSurname, String dateOfBirth, String placeOfBirth,
                            String nationality, String religion, String childAge, String fathersName,
                            String fathersContact, String mothersName, String mothersContact, String emergencyContact,
                            String doctorsDetails, String doctorContact, String residentialAddress,
                            String dateOfEntry, String questionOne, String questionTwo, String cardUpload,
                            String otherInfo, String declaration) {
        progressDialog.setMessage("Uploading data...");
        progressDialog.show();

        // Upload image to Firebase Storage
        StorageReference imageRef = storageRef.child(System.currentTimeMillis() + ".png");
        imageRef.putFile(getImageUri())
                .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    String imageUrl = uri.toString();

                    // Save all data including the image URL
                    saveToDatabase(childFirstName, childSurname, dateOfBirth, placeOfBirth, nationality, religion,
                            childAge, fathersName, fathersContact, mothersName, mothersContact, emergencyContact,
                            doctorsDetails, doctorContact, residentialAddress, dateOfEntry, questionOne,
                            questionTwo, cardUpload, otherInfo, declaration, imageUrl);
                }))
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toasty.error(AdmissionActivity.this, "Image upload failed.", Toast.LENGTH_SHORT).show();
                });
    }


    private Uri getImageUri() {
        String path = MediaStore.Images.Media.insertImage(
                getContentResolver(),
                bitmap,
                "Captured Image",
                null
        );
        return Uri.parse(path);
    }

    private void saveToDatabase(String childFirstName, String childSurname, String dateOfBirth,
                                String placeOfBirth, String nationality, String religion,
                                String childAge, String fathersName, String fathersContact,
                                String mothersName, String mothersContact, String emergencyContact,
                                String doctorsDetails, String doctorContact, String residentialAddress,
                                String dateOfEntry, String questionOne, String questionTwo,
                                String cardUpload, String otherInfo, String declaration, String imageUrl) {
        Map<String, Object> data = new HashMap<>();
        data.put("childFirstName", childFirstName);
        data.put("childSurname", childSurname);
        data.put("dateOfBirth", dateOfBirth);
        data.put("placeOfBirth", placeOfBirth);
        data.put("nationality", nationality);
        data.put("religion", religion);
        data.put("childAge", childAge);
        data.put("fathersName", fathersName);
        data.put("fathersContact", fathersContact);
        data.put("mothersName", mothersName);
        data.put("mothersContact", mothersContact);
        data.put("emergencyContact", emergencyContact);
        data.put("doctorsDetails", doctorsDetails);
        data.put("doctorContact", doctorContact);
        data.put("residentialAddress", residentialAddress);
        data.put("dateOfEntry", dateOfEntry);
        data.put("questionOne", questionOne);
        data.put("questionTwo", questionTwo);
        data.put("cardUpload", cardUpload);
        data.put("otherInfo", otherInfo);
        data.put("declaration", declaration);
        data.put("image", imageUrl);

        dbRef.push().setValue(data)
                .addOnCompleteListener(task -> {
                    progressDialog.dismiss();
                    if (task.isSuccessful()) {
                        Toasty.success(AdmissionActivity.this, "Admission data uploaded successfully.", Toast.LENGTH_SHORT).show();
                        clearFields();
                    } else {
                        Toasty.error(AdmissionActivity.this, "Failed to upload data.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void clearFields() {
        childFirstName.setText("");
        childSurname.setText("");
        dateOfBirth.setText("");
        placeOfBirth.setText("");
        nationality.setText("");
        religion.setText("");
        childAge.setText("");
        fathersName.setText("");
        fathersContact.setText("");
        mothersName.setText("");
        mothersContact.setText("");
        emergencyContact.setText("");
        doctorsDetails.setText("");
        doctorContact.setText("");
        residentialAddress.setText("");
        dateOfEntry.setText("");
        questionOne.setText("");
        questionTwo.setText("");
        cardUpload.setText("");
        otherInfo.setText("");
        declaration.setText("");

        childsGender.setSelection(0);  // Reset gender spinner to default (Select Gender)
        admissionClassSpinner.setSelection(0);  // Reset class spinner to default (Select Grade)

        childsImage.setImageResource(R.drawable.ic_person_flat);  // Reset image to default
        bitmap = null;  // Clear the bitmap (image)
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, REQ);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri uri = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                childsImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                // Replaced printStackTrace() with more robust logging
                Log.e("ImageError", "Error loading image from URI", e);
            }
        }
    }
}
