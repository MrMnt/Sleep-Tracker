package com.example.sleeptracker.viemodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sleeptracker.firebase.DatabaseHelper;
import com.example.sleeptracker.ui.fragments.DatePickerFragment;
import com.example.sleeptracker.Sleep;
import com.example.sleeptracker.ui.fragments.TimePickerFragment;

import java.util.Calendar;

public class AddSleepDataViewModel extends ViewModel {
    private static final String TAG = "AddSleepDataViewModel";

    // TODO: save the start time to the database if a user
    //  saves the time in the night and wants to continue in the morning.
    private MutableLiveData<Sleep> newSleepData;
    private TimePickerFragment.MyTimePickerInterface startTimeListener;
    private TimePickerFragment.MyTimePickerInterface endTimeListener;
    private DatePickerFragment.MyDatePickerInterface startDateListener;
    private DatePickerFragment.MyDatePickerInterface endDateListener;

    DatabaseHelper db = DatabaseHelper.getInstance();

    public AddSleepDataViewModel() {
        super();
        Log.d(TAG, "AddSleepDataViewModel: ");

        if(newSleepData == null){
            newSleepData = new MutableLiveData<>();
            loadNewSleepData();
        }

        initListeners();
    }

    public void save(){
         db.addSleepData(newSleepData.getValue());
    }

    public MutableLiveData<Sleep> getNewSleepData() {
        return newSleepData;
    }

    // Right now jus loads dummy data, but later will
    // load from Firebase cache (probably)
    private void loadNewSleepData(){
        newSleepData.setValue(new Sleep());
    }

    private void initListeners(){
        Calendar c = Calendar.getInstance();
        Sleep sleep = newSleepData.getValue();

        startTimeListener = ((hourOfDay, minute) -> {
            c.setTime(sleep.getStartTime());
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            sleep.setStartTime(c.getTime());
            newSleepData.setValue(sleep);
        });
        endTimeListener = ((hourOfDay, minute) -> {
            c.setTime(sleep.getEndTime());
            c.set(Calendar.HOUR_OF_DAY, hourOfDay);
            c.set(Calendar.MINUTE, minute);
            sleep.setEndTime(c.getTime());
            newSleepData.setValue(sleep);
        });

        startDateListener = ((year, month, day) -> {
            c.setTime(sleep.getStartTime());
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            sleep.setStartTime(c.getTime());
            newSleepData.setValue(sleep);
        });
        endDateListener = ((year, month, day) -> {
            c.setTime(sleep.getEndTime());
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            sleep.setEndTime(c.getTime());
            newSleepData.setValue(sleep);
        });
}

    public TimePickerFragment.MyTimePickerInterface getEndTimeListener() {
        return endTimeListener;
    }
    public TimePickerFragment.MyTimePickerInterface getStartTimeListener() {
        return startTimeListener;
    }

    public DatePickerFragment.MyDatePickerInterface getStartDateListener() {
        return startDateListener;
    }

    public DatePickerFragment.MyDatePickerInterface getEndDateListener() {
        return endDateListener;
    }
}
