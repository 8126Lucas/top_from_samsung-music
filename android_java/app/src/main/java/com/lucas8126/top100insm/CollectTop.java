package com.lucas8126.top100insm;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public abstract class CollectTop extends Service {
    private boolean isAuthenticated = false;
    private Handler mainThreadHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationCentral.createNotificationChannel(this);
        FirebaseApp.initializeApp(this);
        mainThreadHandler = new Handler(Looper.getMainLooper());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            auth.signInAnonymously().addOnSuccessListener(auth_result -> {
                isAuthenticated = true;
                NotificationCentral.showNotification(this, "✅ Authenticated successfully!");
                startWork();
            }).addOnFailureListener(exception -> {
                NotificationCentral.showNotification(this, "❌ Authentication failed!");

                stopSelf();
            });
        }
        else {
            NotificationCentral.showNotification(this, "✅ Authenticated successfully!");
            isAuthenticated = true;
            startWork();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int start_id) {
        return START_NOT_STICKY;
    }

    private void startWork() {
        if(isAuthenticated) {
            new Thread(() -> {
                ArrayList<Song> songs;
                try {
                    NotificationCentral.showNotification(this, "⌛ Loading playlist...");
                    songs = GetMusicData.getMusicData(this);
                    if (songs == null) {
                        NotificationCentral.showNotification(this, "❌ Failed to load playlist. Stopping service.");
                        stopSelf();
                        return;
                    }
                    NotificationCentral.showNotification(this, "✅ " + songs.size() + " songs found!");
                    File json_file = SongsToJSON.writeJSONFile(songs, getApplicationContext());
                    NotificationCentral.showNotification(this, "⏰ Waiting for auth token to propagate...");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    UploadToFirebase.cloudJSON(json_file, new FirebaseCallback() {
                        @Override
                        public void onSuccess() {
                            NotificationCentral.showNotification(CollectTop.this, "⬆️ JSON file uploaded successfully!");
                        }
                        @Override
                        public void onFailure() {
                            NotificationCentral.showNotification(CollectTop.this, "❌ JSON file upload failed!");
                        }
                    });
                } catch (FileNotFoundException error) {
                    NotificationCentral.showNotification(this, "❌ M3U file not found: " + error.getMessage());
                } finally {
                    stopSelf();
                }
            }).start();
        }
        else {
            NotificationCentral.showNotification(this, "⌛ Still authenticating, please wait...");
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}
}
