package com.lucas8126.top100insm;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public abstract class CollectTop extends Service {
    private Handler mainThreadHandler;
    private boolean isAuthenticated = false;

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
        mainThreadHandler = new Handler(Looper.getMainLooper());
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() == null) {
            auth.signInAnonymously().addOnSuccessListener(auth_result -> {
                isAuthenticated = true;
                System.out.println("✅ Authenticated successfully!");
                System.out.println("🚀 About to call startWork()");
                startWork();
                System.out.println("✅ startWork() called");
            }).addOnFailureListener(exception -> {
                System.out.println("❌ Authentication failed!");
                System.out.println("Error details: " + exception.getMessage());
                stopSelf();
            });
        }
        else {
            System.out.println("✅ Authenticated successfully!");
            isAuthenticated = true;
            System.out.println("🚀 About to call startWork()");
            startWork();
            System.out.println("✅ startWork() called");
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
                    System.out.println("⌛ Loading playlist...");
                    songs = GetMusicData.getMusicData(this);
                    if (songs == null) {
                        System.out.println("❌ Failed to load playlist. Stopping service.");
                        stopSelf();
                        return;
                    }
                    System.out.println("✅ " + songs.size() + " songs found!");
                    File json_file = SongsToJSON.writeJSONFile(songs, getApplicationContext());
                    System.out.println("💾 JSON file saved successfully!");
                    UploadToFirebase.cloudJSON(json_file, new FirebaseCallback() {
                        @Override
                        public void onSuccess() {
                            System.out.println("⬆️ JSON file uploaded successfully!");
                        }
                        @Override
                        public void onFailure() {
                            System.out.println("❌ JSON file upload failed!");
                        }
                    });
                } catch (FileNotFoundException error) {
                    System.out.println("❌ M3U file not found: " + error.getMessage());
                } finally {
                    stopSelf();
                }
            }).start();
        }
        else {
            System.out.println("⌛ Still authenticating, please wait...");
        }
    }

//    private void showToast(final String message) {
//        mainThreadHandler.post(() -> Toast.makeText(CollectTop.this, message, Toast.LENGTH_LONG).show());
//    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}
}
