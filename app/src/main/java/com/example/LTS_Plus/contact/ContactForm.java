package com.example.LTS_Plus.contact;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import es.dmoral.toasty.Toasty;

public class ContactForm extends AppCompatActivity {

    private EditText Name, Email, Contact;
    private ProgressDialog pd; // Class-level ProgressDialog
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_form);

        // Hide ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Initialize Views
        Name = findViewById(R.id.Name);
        Email = findViewById(R.id.Email);
        Contact = findViewById(R.id.Contact);
        Button addBtn = findViewById(R.id.addBtn);

        // Initialize ProgressDialog
        pd = new ProgressDialog(this);
        pd.setMessage("Submitting your message...");

        // Firebase References
        reference = FirebaseDatabase.getInstance().getReference().child("ContactForm");
        FirebaseStorage.getInstance().getReference();

        // Add Button Click Listener
        addBtn.setOnClickListener(v -> checkContactForm());
    }

    private void checkContactForm() {
        String name = Name.getText().toString().trim();
        String email = Email.getText().toString().trim();
        String contact = Contact.getText().toString().trim();

        if (name.isEmpty()) {
            Name.setError("Please Enter Your Name");
            Name.requestFocus();
        } else if (email.isEmpty()) {
            Email.setError("Please Enter Your Email");
            Email.requestFocus();
        } else if (contact.isEmpty()) {
            Contact.setError("Please Enter Your Message");
            Contact.requestFocus();
        } else {
            insertData(name, email, contact);
        }
    }

    private void insertData(String name, String email, String contact) {
        pd.show(); // Show ProgressDialog

        String uniqueKey = reference.push().getKey();
        ContactData contactData = new ContactData(name, contact);

        assert uniqueKey != null;
        reference.child(uniqueKey).setValue(contactData)
                .addOnSuccessListener(aVoid -> {
                    pd.dismiss();
                    Toasty.success(ContactForm.this, "Message Sent Successfully", Toasty.LENGTH_SHORT).show();
                    clearFields();
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toasty.error(ContactForm.this, "Something Went Wrong: " + e.getMessage(), Toasty.LENGTH_SHORT).show();
                });
    }

    private void clearFields() {
        Name.setText("");
        Email.setText("");
        Contact.setText("");
    }
}
