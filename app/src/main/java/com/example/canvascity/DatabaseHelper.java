package com.example.canvascity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DatabaseHelper {

    private final DatabaseReference dbRef;

    public DatabaseHelper() {
        // Root -> Users
        dbRef = FirebaseDatabase.getInstance().getReference("Users");
    }

    public void saveUser(User user, DatabaseCallback callback) {
        if (user == null || user.getUid() == null) {
            if (callback != null) callback.onFailure("Invalid user data");
            return;
        }

        // Save user using UID as key
        dbRef.child(user.getUid())
                .setValue(user)
                .addOnSuccessListener(unused -> {
                    if (callback != null) callback.onSuccess("User saved successfully");
                })
                .addOnFailureListener(e -> {
                    if (callback != null) callback.onFailure(e.getMessage());
                });
    }

    public interface DatabaseCallback {
        void onSuccess(String message);
        void onFailure(String error);
    }
}
