package com.example.sleeptracker;

import com.example.sleeptracker.firebase.DbConstants;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Sleep {

    private static final String TAG = "Sleep";

    private Date startTime;  // When the user fell asleep
    private Date endTime;   // When the user woke up
    private long duration; // Time slept in seconds
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

    // Construct object from Firestore document
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
        // Date.getTime() returns time in ms, we need just the seconds.
        duration = (endTime.getTime() - startTime.getTime()) / 1000;
    }
    public String getDurationAsString(){
        return getDurationHours() + ":" + getDurationMinutes();
    }
    public long getDurationHours(){ return duration / 3600; }
    public long getDurationMinutes(){ return (duration / 60) % 60; }

    // Converts this sleep obj to store it in the database
    public Map<String, Object> convertForFirestore(){
        Map<String, Object> sleep = new HashMap<>();

        sleep.put(DbConstants.SLEEP_FIELD_START_TIME, startTime);
        sleep.put(DbConstants.SLEEP_FIELD_END_TIME, endTime);
        sleep.put(DbConstants.SLEEP_FIELD_DURATION, duration);

        return sleep;
    }

}
