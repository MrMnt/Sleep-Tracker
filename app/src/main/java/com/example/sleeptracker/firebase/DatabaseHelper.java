package com.example.sleeptracker.firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.sleeptracker.Sleep;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class DatabaseHelper {

    private static final String TAG = "DatabaseHelper";

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // TODO change to have constants and get the actual userId.
    DocumentReference userDocRef = db.document("users/user1");

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

}
