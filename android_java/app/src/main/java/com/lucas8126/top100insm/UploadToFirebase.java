package com.lucas8126.top100insm;

import android.net.Uri;
import android.util.Log;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;

public class UploadToFirebase {
    public static void cloudJSON(File json_file, FirebaseCallback callback) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storage_reference = storage.getReference();
        StorageReference json_ref = storage_reference.child("MOST_LISTENED.json");
        Uri json_uri = Uri.fromFile(json_file);
        UploadTask upload_task = json_ref.putFile(json_uri);
        upload_task.addOnProgressListener(takeSnapshot -> {
                    double progress = (100.0 * takeSnapshot.getBytesTransferred()) / takeSnapshot.getTotalByteCount();
                    Log.d("UploadToFirebase", "Upload is " + progress + "% done");
                })
                .addOnSuccessListener(task_snapshot -> callback.onSuccess())
                .addOnFailureListener(exception -> callback.onFailure());
    }
}

interface FirebaseCallback {
    void onSuccess();
    void onFailure();
}

