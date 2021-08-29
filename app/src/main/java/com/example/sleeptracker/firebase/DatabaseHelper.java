package com.example.sleeptracker.firebase;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.sleeptracker.Sleep;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class DatabaseHelper {
    private static final String TAG = "DatabaseHelper";

    private static DatabaseHelper helperInstance = new DatabaseHelper();

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = mAuth.getCurrentUser();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DocumentReference userDocRef = db.collection(DbConstants.COLL_USERS).document(user.getUid());

    private Query mQuery;

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

    public MutableLiveData<List<Sleep>> getQueriedSleepData(){
        MutableLiveData<List<Sleep>> sleepsLiveData = new MutableLiveData<>();
        List<Sleep> sleepQueryResult = new ArrayList<>();

        mQuery.get().addOnCompleteListener(task -> {
           if(task.isSuccessful()){
               for(QueryDocumentSnapshot document : task.getResult()){
                   sleepQueryResult.add(new Sleep(document));
               }
               sleepsLiveData.setValue(sleepQueryResult);
           } else {
               Log.d(TAG, "getQueriedSleepData: ", task.getException());
           }
        });

        return sleepsLiveData;
    }

    public void setQuery(Date startDate, Date endDate, long limit){
        mQuery = userDocRef.collection(DbConstants.COLL_SLEEPS);
        mQuery = mQuery.whereGreaterThanOrEqualTo(DbConstants.SLEEP_FIELD_START_TIME, startDate);
        mQuery = mQuery.whereLessThanOrEqualTo(DbConstants.SLEEP_FIELD_START_TIME, endDate);
        mQuery = mQuery.limit(limit);
    }

    public void updateAuthData(){
        user = mAuth.getCurrentUser();
        userDocRef = db.collection(DbConstants.COLL_USERS).document(user.getUid());
    }

}