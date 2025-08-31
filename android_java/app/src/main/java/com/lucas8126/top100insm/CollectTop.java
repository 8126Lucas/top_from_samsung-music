package com.lucas8126.top100insm;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import androidx.annotation.Nullable;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class CollectTop extends Service {
    private boolean isAuthenticated = false;
    private Handler mainThreadHandler = null;

    @Override
    public void onCreate() {
        super.onCreate();
        // System.out.println("🔧 Build type: " + (BuildConfig.DEBUG ? "DEBUG" : "RELEASE"));
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
            Constraints constraints = new Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build();
            OneTimeWorkRequest work_request = new OneTimeWorkRequest.Builder(MusicProcessor.class)
                    .setConstraints(constraints)
                    .build();
            WorkManager.getInstance(getApplicationContext()).enqueue(work_request);
            WorkManager.getInstance(getApplicationContext())
                    .getWorkInfoByIdLiveData(work_request.getId())
                    .observeForever(work_info -> {
                        if (work_info != null) {
                            switch (work_info.getState()) {
                                case ENQUEUED:
                                    NotificationCentral.showNotification(this, "⌛ Enqueued");
                                    break;
                                case RUNNING:
                                    NotificationCentral.showNotification(this, "🚗 Running");
                                    break;
                                case SUCCEEDED:
                                    NotificationCentral.showNotification(this, "☁ JSON file uploaded successfully!");
                                    stopSelf();
                                    break;
                                case FAILED:
                                    NotificationCentral.showNotification(this, "❌ JSON file upload failed!");
                                    stopSelf();
                                    break;
                            }
                        }
                    });
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {return null;}
}
