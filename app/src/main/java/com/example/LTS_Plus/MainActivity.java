package com.example.LTS_Plus;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.example.LTS_Plus.login.LoginActivity;

import com.example.LTS_Plus.admission.AdmissionActivity;
import com.example.LTS_Plus.contact.ContactForm;
import com.example.LTS_Plus.developer.DeveloperActivity;
import com.example.LTS_Plus.ebook.EbookActivity;
import com.example.LTS_Plus.result.ResultActivity;
import com.example.LTS_Plus.student_list.StudentList;
import com.example.LTS_Plus.ui.notice.NoticeAdapter;
import com.example.LTS_Plus.ui.quiz.StartActivity;
import com.example.LTS_Plus.website.WebSiteActivity;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();

        // Toolbar Setup
        Toolbar toolbar = findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);

        // Drawer and Navigation Setup
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        SharedPreferences sharedPreferences = getSharedPreferences("AppPreferences", Context.MODE_PRIVATE);

        // Check for internet connectivity
        if (isConnected()) {
            showNoInternetDialog();
        }
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
        } else if (id == R.id.navigation_quiz) {
            navigateToActivity(StartActivity.class);
        } else if (id == R.id.navigation_ebook) {
            navigateToActivity(EbookActivity.class);
        } else if (id == R.id.navigation_student_list) {
            navigateToActivity(StudentList.class);
        } else if (id == R.id.navigation_website) {
            navigateToActivity(WebSiteActivity.class);
        } else if (id == R.id.navigation_result) {
            navigateToActivity(ResultActivity.class);
        } else if (id == R.id.navigation_admission) {
            navigateToActivity(AdmissionActivity.class);
        } else if (id == R.id.navigation_contact) {
            navigateToActivity(ContactForm.class);
        } else if (id == R.id.navigation_developer) {
            navigateToActivity(DeveloperActivity.class);
        } else if (id == R.id.navigation_signature) {
            navigateToActivity(SignatureActivity.class);
        } else if (id == R.id.navigation_share) {
            shareApp(); // No change, as this doesn't navigate to an activity.
        } else if (id == R.id.navigation_rate) {
            openPlayStore(); // No change, as this doesn't navigate to an activity.
        }else if (id == R.id.navigation_logout) {
            handleLogout(); // Use the logout method here
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
            return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        }
        return false;
    }

    private void showNoInternetDialog() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setPositiveButton("Retry", (dialog, which) -> {
                    if (!isConnected()) showNoInternetDialog();
                })
                .setNegativeButton("Settings", (dialog, which) ->
                        startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS))
                )
                .show();
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
