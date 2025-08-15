package com.lucas8126.top100insm;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.widget.Toast;
import androidx.annotation.Nullable;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public abstract class CollectTop extends Service {
    private Handler mainThreadHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        mainThreadHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int start_id) {
        new Thread(() -> {
            ArrayList<Song> songs;
            try {
                songs = GetMusicData.getMusicData(this);
                if (songs == null) {
                    showToast("❌ Failed to load playlist. Stopping service.");
                    stopSelf();
                    return;
                }
                showToast("✅ " + songs.size() + " songs found!");
                SongsToJSON.writeJSONFile(songs, getApplicationContext());
                showToast("💾 JSON file saved successfully!");
            } catch (FileNotFoundException error) {
                showToast("❌ M3U file not found: " + error.getMessage());
            } finally {
                stopSelf();
            }
        }).start();
        return START_NOT_STICKY;
    }

    // Helper method to show Toast on the main thread
    private void showToast(final String message) {
        mainThreadHandler.post(() -> Toast.makeText(CollectTop.this, message, Toast.LENGTH_LONG).show());
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}
}
