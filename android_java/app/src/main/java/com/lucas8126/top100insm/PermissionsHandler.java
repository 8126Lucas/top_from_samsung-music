package com.lucas8126.top100insm;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class PermissionsHandler extends Activity {
    private static final int STORAGE_PERMISSION_REQUEST_CODE = 101;
    private static final String TAG = "PermissionsHandler";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_handler);
        requestPermissions();
    }
    private void requestPermissions() {
        String permission_to_request;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permission_to_request = Manifest.permission.READ_MEDIA_AUDIO;
        } else {
            permission_to_request = Manifest.permission.READ_EXTERNAL_STORAGE;
        }

        if (ContextCompat.checkSelfPermission(this, permission_to_request) != PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission not granted, requesting: " + permission_to_request);
            ActivityCompat.requestPermissions(this, new String[]{permission_to_request}, STORAGE_PERMISSION_REQUEST_CODE);
        } else {
            Log.d(TAG, "Permission already granted: " + permission_to_request);
            startService();
            finish();
        }
    }

    @Override
    public void onRequestPermissionsResult(int request_code, @NonNull String[] permissions, @NonNull int[] grant_results) {
        super.onRequestPermissionsResult(request_code, permissions, grant_results);
        if (request_code == STORAGE_PERMISSION_REQUEST_CODE) {
            if (grant_results.length > 0 && grant_results[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted by user.");
                startService();
            } else {
                Log.e(TAG, "Permission denied by user.");
                Toast.makeText(this, "Music access permission is required for this app to function.", Toast.LENGTH_LONG).show();
            }
            finish();
        }
    }

    private void startService() {
        Intent service_intent = new Intent(this, TopMusicCollection.class);
        try {
            startService(service_intent);
            Log.d(TAG, "TopMusicCollection service started.");
            Toast.makeText(this, "Starting music collection service...", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Failed to start TopMusicCollection service: " + e.getMessage());
            Toast.makeText(this, "Failed to start music service.", Toast.LENGTH_LONG).show();
        }
    }
}
