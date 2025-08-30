package com.lucas8126.top100insm;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.util.ArrayList;
import java.util.List;

public class PermissionsHandler extends Activity {
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permissions_handler);
        requestPermissions();
    }
    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void requestPermissions() {
        String[] permissions = {
                Manifest.permission.READ_MEDIA_AUDIO,
                Manifest.permission.POST_NOTIFICATIONS
        };
        List<String> permissions_to_request = new ArrayList<>();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                permissions_to_request.add(permission);
            }
        }
        if(!permissions_to_request.isEmpty()) {
            boolean show_read_audio_warning = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_MEDIA_AUDIO);
            boolean show_post_notification_warning = ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.POST_NOTIFICATIONS);
            if(show_read_audio_warning || show_post_notification_warning) {
                new AlertDialog.Builder(this)
                        .setTitle("Permissions Required")
                        .setMessage("This app requires the permissions to function.")
                        .setPositiveButton("Ok", (dialog, which) -> {
                            ActivityCompat.requestPermissions(this,
                                    permissions_to_request.toArray(new String[0]),
                                    PERMISSION_REQUEST_CODE);
                        })
                        .setNegativeButton("Cancel", (dialog, which) -> finish())
                        .show();
            }
            else {
                ActivityCompat.requestPermissions(this,
                        permissions_to_request.toArray(new String[0]),
                        PERMISSION_REQUEST_CODE);
            }
        }
        else {
            startService();
        }
    }

    @Override
    public void onRequestPermissionsResult(int request_code, @NonNull String[] permissions, @NonNull int[] grant_results) {
        super.onRequestPermissionsResult(request_code, permissions, grant_results);
        if (request_code == PERMISSION_REQUEST_CODE) {
            List<String> denied_permissions = new ArrayList<>();
            for(int i = 0; i < permissions.length; i++) {
                if(grant_results[i] != PackageManager.PERMISSION_GRANTED) {
                    denied_permissions.add(permissions[i]);
                }
            }
            if(denied_permissions.isEmpty()) {
                startService();
            }
            else {
                finish();
            }
        }
    }

    private void startService() {
        Intent service_intent = new Intent(this, TopMusicCollection.class);
        try {
            startService(service_intent);
            System.out.println("TopMusicCollection service started.");
            finish();
        } catch (Exception e) {
            System.out.println("Failed to start TopMusicCollection service: " + e.getMessage());
            NotificationCentral.showNotification(this, "❌ Failed to start the app.");
            finish();
        }
    }
}
