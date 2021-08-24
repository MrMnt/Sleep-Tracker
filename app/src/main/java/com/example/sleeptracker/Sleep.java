package com.example.sleeptracker;

import android.util.Log;

import com.example.sleeptracker.firebase.DbConstants;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sleep {

    private static final String TAG = "Sleep";

    private Date startTime;
    private Date endTime;
    private long duration; // in seconds
    private String docId; // document id in Firestore

    public Sleep(Date startTime, Date endTime){
        this.startTime = startTime;
        this.endTime = endTime;
        recalculateDuration();
    }
    public Sleep(){
        this.startTime = new Date();
        this.endTime = new Date();
        recalculateDuration();
    }
    // Firebase
    public Sleep(DocumentSnapshot doc){
        Map<String, Object> sleep = doc.getData();

        this.docId = doc.getId();
        Timestamp startTime = (Timestamp) sleep.get(DbConstants.SLEEP_FIELD_START_TIME);
        this.startTime = startTime.toDate();
        Timestamp endTime = (Timestamp) sleep.get(DbConstants.SLEEP_FIELD_END_TIME);
        this.endTime =  endTime.toDate();

        recalculateDuration();
    }

    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
        recalculateDuration();
    }

    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
        recalculateDuration();
    }

    private void recalculateDuration(){
        duration = (endTime.getTime() - startTime.getTime()) / 1000;
    }
    public String getDuration(){ return getDurationHours() + ":" + getDurationMinutes(); }
    public long getDurationHours(){ return duration / 3600; }
    public long getDurationMinutes(){ return (duration / 60) % 60; }

    public Map<String, Object> convertForFirestore(){
        Map<String, Object> sleep = new HashMap<>();

        sleep.put(DbConstants.SLEEP_FIELD_START_TIME, startTime);
        sleep.put(DbConstants.SLEEP_FIELD_END_TIME, endTime);
        sleep.put(DbConstants.SLEEP_FIELD_DURATION, duration);

        return sleep;
    }

}
