package com.example.sleeptracker.firebase;

import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.sleeptracker.Sleep;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public final class DatabaseHelper {
    private static final String TAG = "DatabaseHelper";

    private static DatabaseHelper helperInstance = new DatabaseHelper();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    // TODO: BUG - make sure user gets updates when a change (sign out etc.) happens
    private FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userDocRef = db.collection(DbConstants.COLL_USERS).document(user.getUid());

    private DatabaseHelper() {
        // Empty constructor.
    }
    public static DatabaseHelper getInstance(){
        return helperInstance;
    }

    public void addSleepData(Sleep sleep){
        userDocRef.collection(DbConstants.COLL_SLEEPS).add(sleep.convertForFirestore())
            .addOnSuccessListener(documentReference -> {
                Log.d(TAG, "addSleepData: all good");
            })
            .addOnFailureListener(e -> {
                Log.d(TAG, "addSleepData: " + e.toString());
            });
    }

    public void getSleepData(String docId){
        userDocRef.collection(DbConstants.COLL_SLEEPS).document(docId).get()
            .addOnCompleteListener(task -> {
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
            });
    }
    public MutableLiveData<List<Sleep>> getEverySleepData(){
        MutableLiveData<List<Sleep>> sleepsLiveData = new MutableLiveData<>();
        List<Sleep> sleepsData = new ArrayList<>();

        userDocRef.collection(DbConstants.COLL_SLEEPS).get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()){
                        for(QueryDocumentSnapshot document : task.getResult()){
                            Sleep s = new Sleep(document);
                            sleepsData.add(s);
                        }
                        sleepsLiveData.setValue(sleepsData);
                    } else {
                        Log.d(TAG, "Error getting documents", task.getException());
                    }
                });

        return sleepsLiveData;
    }

}