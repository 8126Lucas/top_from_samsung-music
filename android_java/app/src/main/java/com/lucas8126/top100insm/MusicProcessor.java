package com.lucas8126.top100insm;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;
import com.google.firebase.auth.FirebaseAuth;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class MusicProcessor extends Worker {

    public MusicProcessor(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        try {
            // System.out.println("⌛ Loading playlist...");
            ArrayList<Song> songs = GetMusicData.getMusicData(getApplicationContext());
            if(songs == null) {
                return Result.failure();
            }
            NotificationCentral.showNotification(getApplicationContext(), "✅ " + songs.size() + " songs found!");
            File json_file = SongsToJSON.writeJSONFile(songs, getApplicationContext());
            CountDownLatch latch = new CountDownLatch(1);
            final Result[] result = {Result.retry()};
            FirebaseAuth auth = FirebaseAuth.getInstance();
            if(auth.getCurrentUser() == null) {
                return Result.failure();
            }
            auth.getCurrentUser().getIdToken(true)
                    .addOnCompleteListener(task -> {
                        if(task.isSuccessful()) {
                            // System.out.println("🎯 Token está pronto!");
                            UploadToFirebase.cloudJSON(json_file, new UploadToFirebase.FirebaseCallback() {
                                @Override
                                public void onSuccess() {
                                    result[0] = Result.success();
                                    latch.countDown();
                                }
                                @Override
                                public void onFailure() {
                                    result[0] = Result.retry();
                                    latch.countDown();
                                }
                            });
                        }
                        else {
                            result[0] = Result.retry();
                            latch.countDown();
                        }
                    });
            latch.await(60, TimeUnit.SECONDS);
            return result[0];
        } catch (FileNotFoundException error) {
            // System.out.println("❌ M3U file not found: " + error.getMessage());
            return Result.failure();
        } catch (Exception error) {
            // System.out.println("❌ An error occurred: " + error.getMessage());
            return Result.failure();
        }
    }
}
