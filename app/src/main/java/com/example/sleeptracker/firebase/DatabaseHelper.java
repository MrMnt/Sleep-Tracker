package com.example.sleeptracker.firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.sleeptracker.Sleep;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DatabaseHelper {

    private static final String TAG = "DatabaseHelper";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO change to have constants and get the actual userId.
    DocumentReference userDocRef = db.document("users/" + user.getUid());

    public DatabaseHelper() {
        // Empty constructor.
    }

    public void addSleepData(Sleep sleep){
        userDocRef.collection("sleeps").add(sleep.convertForFirestore())
            .addOnSuccessListener(documentReference -> {
                Log.d(TAG, "addSleepData: all good");
            })
            .addOnFailureListener(e -> {
                Log.d(TAG, "addSleepData: " + e.toString());
            });
    }

    public void getSleepData(String docId){
        userDocRef.collection("sleeps").document(docId).get()
            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                            Sleep a = new Sleep(document);
                        } else {
                            Log.d(TAG, "No such document");
                        }
                    } else {
                        Log.d(TAG, "get failed with ", task.getException());
                    }
                }
            });
    }

}