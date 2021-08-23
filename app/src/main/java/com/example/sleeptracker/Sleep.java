package com.example.sleeptracker;

import android.util.Log;

import java.util.Date;

public class Sleep {

    private static final String TAG = "Sleep";

    private Date startTime;
    private Date endTime;
    private long duration; // in seconds

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

}
