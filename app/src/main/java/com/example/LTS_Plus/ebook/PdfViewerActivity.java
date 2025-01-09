package com.example.LTS_Plus.ebook;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.LTS_Plus.R;
import com.github.barteksc.pdfviewer.PDFView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PdfViewerActivity extends AppCompatActivity {

    private PDFView pdfView;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_viewer);

        // Fullscreen and ActionBar setup
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        // Get the PDF URL
        String url = getIntent().getStringExtra("pdfUrl");

        pdfView = findViewById(R.id.pdfView);
        progressBar = findViewById(R.id.pdfProgress);

        // Show progress bar while loading
        progressBar.setVisibility(View.VISIBLE);

        if (url != null) {
            // Check if file is cached
            File cachedFile = getCachedFile(url);

            if (cachedFile.exists()) {
                loadPdfFromFile(cachedFile);
            } else {
                downloadAndLoadPdf(url, cachedFile);
            }
        } else {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(this, "Invalid PDF URL", Toast.LENGTH_SHORT).show();
        }
    }

    private File getCachedFile(String url) {
        String fileName = url.substring(url.lastIndexOf('/') + 1);
        return new File(getCacheDir(), fileName);
    }

    private void loadPdfFromFile(File file) {
        progressBar.setVisibility(View.GONE);
        pdfView.fromFile(file)
                .password(null)
                .defaultPage(0)
                .enableSwipe(true)
                .swipeVertical(false)
                .enableDoubletap(true)
                .load();
    }

    private void downloadAndLoadPdf(String url, File outputFile) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            try {
                URL pdfUrl = new URL(url);
                HttpURLConnection connection = (HttpURLConnection) pdfUrl.openConnection();
                connection.connect();

                if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStream inputStream = new BufferedInputStream(connection.getInputStream());
                    try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                    runOnUiThread(() -> loadPdfFromFile(outputFile));
                } else {
                    handleDownloadError("Failed to connect to the server");
                }
            } catch (Exception e) {
                handleDownloadError(e.getMessage());
                Log.e("PdfViewerActivity", "Error downloading PDF", e);
            }
        });
    }

    private void handleDownloadError(String message) {
        runOnUiThread(() -> {
            progressBar.setVisibility(View.GONE);
            Toast.makeText(PdfViewerActivity.this, "Error: " + message, Toast.LENGTH_SHORT).show();
        });
    }
}
