package com.lucas8126.top100insm;

import android.net.Uri;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.File;

public class UploadToFirebase {
    public interface FirebaseCallback {
        void onSuccess();
        void onFailure();
    }
    public static void cloudJSON(File json_file, FirebaseCallback callback) {
        try {
            FirebaseStorage storage = FirebaseStorage.getInstance();
            StorageReference storage_reference = storage.getReference();
            StorageReference json_ref = storage_reference.child("MOST_LISTENED.json");
            Uri json_uri = Uri.fromFile(json_file);
            UploadTask upload_task = json_ref.putFile(json_uri);
            upload_task.addOnProgressListener(takeSnapshot -> {
                        // double progress = (100.0 * takeSnapshot.getBytesTransferred()) / takeSnapshot.getTotalByteCount();
                        // System.out.println("Upload is " + progress + "% done");
                    })
                    .addOnSuccessListener(task_snapshot -> callback.onSuccess())
                    .addOnFailureListener(exception -> callback.onFailure());
        } catch(Exception error) {
            // System.out.println("❌ Error uploading JSON file: " + error.getMessage());
            callback.onFailure();
        }
    }
}


