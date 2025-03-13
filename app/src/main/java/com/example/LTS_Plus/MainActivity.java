package com.example.LTS_Plus;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.LTS_Plus.contact.ContactForm;
import com.example.LTS_Plus.developer.DeveloperActivity;
import com.example.LTS_Plus.ebook.EbookActivity;
import com.example.LTS_Plus.login.LoginActivity;
import com.example.LTS_Plus.student_list.StudentList;
import com.example.LTS_Plus.ui.faculty.FacultyFragment;
import com.example.LTS_Plus.ui.gallery.GalleryFragment;
import com.example.LTS_Plus.ui.home.HomeFragment;
import com.example.LTS_Plus.ui.notice.NoticeAdapter;
import com.example.LTS_Plus.video.VideoLecture;
import com.example.LTS_Plus.website.WebSiteActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final int FILE_PICKER_REQUEST_CODE = 1;
    private DrawerLayout drawerLayout;
    private FirebaseAuth auth;
    private CircleImageView studentImage; // For profile image
    private TextView studentName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Toolbar Setup
        Toolbar toolbar = findViewById(R.id.toolbarId); // Ensure this ID matches your Toolbar ID
        setSupportActionBar(toolbar); // Set the Toolbar as the action bar

        // Access views in the Toolbar
        studentImage = toolbar.findViewById(R.id.studentImage); // CircleImageView
        studentName = toolbar.findViewById(R.id.studentName); // TextView

        // Drawer and Navigation Setup
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Add the hamburger icon and sync the DrawerLayout with the Toolbar
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Update the Toolbar with user data
        fetchUserData();

        // Check for internet connectivity
        if (isConnected()) {
            showNoInternetDialog();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_drawer, menu); // Inflate the correct menu resource file
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        // Handle overflow menu item clicks
        if (id == R.id.navigation_video) {
            navigateToActivity(VideoLecture.class);
            return true;
        } else if (id == R.id.navigation_ebook) {
            navigateToActivity(EbookActivity.class);
            return true;
        } else if (id == R.id.navigation_website) {
            navigateToActivity(WebSiteActivity.class);
            return true;
        } else if (id == R.id.navigation_share) {
            shareApp();
            return true;
        } else if (id == R.id.navigation_rate) {
            openPlayStore();
            return true;
        } else if (id == R.id.navigation_admission) {
            // Handle admission form action
            Toast.makeText(this, "Admission Form clicked", Toast.LENGTH_SHORT).show();
            return true;
        } else if (id == R.id.student_list) {
            navigateToActivity(StudentList.class);
            return true;
        } else if (id == R.id.navigation_contact) {
            navigateToActivity(ContactForm.class);
            return true;
        } else if (id == R.id.navigation_developer) {
            navigateToActivity(DeveloperActivity.class);
            return true;
        } else if (id == R.id.navigation_theme) {
            showThemeSelectionDialog();
            return true;
        } else if (id == R.id.logout) {
            handleLogout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void fetchUserData() {
        if (auth.getCurrentUser() == null) {
            return; // No user logged in
        }

        String userId = Objects.requireNonNull(auth.getCurrentUser()).getUid(); // Get the current user's ID
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("users").document(userId).get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            String name = document.getString("name");
                            String profileImageUrl = document.getString("profileImageUrl");

                            // Update the Toolbar with the fetched data
                            if (name != null && profileImageUrl != null) {
                                studentName.setText(name);
                                Glide.with(this)
                                        .load(profileImageUrl)
                                        .placeholder(R.drawable.profile)
                                        .into(studentImage);
                            }
                        } else {
                            Log.d("Firestore", "No such document");
                        }
                    } else {
                        Log.e("Firestore", "Failed to fetch user data", task.getException());
                        Toast.makeText(this, "Failed to fetch user data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (auth.getCurrentUser() == null) {
            openLoginActivity();
        }
    }

    private void openLoginActivity() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.navigation_notice) {
            navigateToActivity(NoticeAdapter.class);
        } else if (id == R.id.navigation_home) {
            navigateToActivity(HomeFragment.class);
        } else if (id == R.id.navigation_faculty_) {
            navigateToActivity(FacultyFragment.class);
        } else if (id == R.id.navigation_gallery) {
            navigateToActivity(GalleryFragment.class);
        } else if (id == R.id.logout) {
            handleLogout();
        } else if (id == R.id.navigation_video) {
            navigateToActivity(VideoLecture.class);
        } else if (id == R.id.navigation_ebook) {
            navigateToActivity(EbookActivity.class);
        } else if (id == R.id.navigation_website) {
            navigateToActivity(WebSiteActivity.class);
        } else if (id == R.id.navigation_contact) {
            navigateToActivity(ContactForm.class);
        } else if (id == R.id.navigation_developer) {
            navigateToActivity(DeveloperActivity.class);
        } else if (id == R.id.student_list) {
            navigateToActivity(StudentList.class);
        } else if (id == R.id.navigation_theme) {
            showThemeSelectionDialog();
        } else if (id == R.id.navigation_share) {
            shareApp();
        } else if (id == R.id.navigation_rate) {
            openPlayStore();
        } else {
            Toast.makeText(this, "Feature not implemented yet", Toast.LENGTH_SHORT).show();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navigateToActivity(Class<?> destination) {
        Intent intent = new Intent(MainActivity.this, destination);
        startActivity(intent);
    }

    private void handleLogout() {
        auth.signOut();
        Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Reset the Toolbar to default values
        studentName.setText(getString(R.string.guest));
        studentImage.setImageResource(R.drawable.profile); // Default image

        openLoginActivity();
    }

    private void shareApp() {
        String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
        Intent sendInt = new Intent(Intent.ACTION_SEND);
        sendInt.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
        sendInt.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_app_message) + appLink);
        sendInt.setType("text/plain");
        startActivity(Intent.createChooser(sendInt, "Share"));
    }

    private void openPlayStore() {
        String appName = getPackageName();
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appName)));
        } catch (ActivityNotFoundException exception) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appName)));
        }
    }

    private boolean isConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            return activeNetwork == null || !activeNetwork.isConnectedOrConnecting();
        }
        return true;
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> {
                    if (isConnected()) showNoInternetDialog();
                })
                .setNegativeButton("Settings", (dialog, which) ->
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS))
                )
                .show();
    }

    private void showThemeSelectionDialog() {
        // Create an AlertDialog with theme options
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Theme")
                .setItems(new CharSequence[]{"Light", "Dark", "System Default"},
                        (dialog, which) -> {
                            switch (which) {
                                case 0:
                                    setLightTheme();
                                    break;
                                case 1:
                                    setDarkTheme();
                                    break;
                                case 2:
                                    setSystemDefaultTheme();
                                    break;
                            }
                        })
                .show();
    }

    private void setLightTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        applyThemeChanges();
    }

    private void setDarkTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        applyThemeChanges();
    }

    private void setSystemDefaultTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        applyThemeChanges();
    }

    private void applyThemeChanges() {
        recreate();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == FILE_PICKER_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri fileUri = data.getData();
            if (fileUri != null) {
                // Process the selected file URI
                Log.d("FilePicker", "Selected file: " + fileUri);
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("Exit App")
                    .setMessage("Are you sure you want to exit?")
                    .setPositiveButton("Yes", (dialog, which) -> super.onBackPressed())
                    .setNegativeButton("No", null)
                    .show();
        }
    }
}