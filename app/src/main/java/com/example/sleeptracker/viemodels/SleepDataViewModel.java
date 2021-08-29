package com.example.sleeptracker.viemodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sleeptracker.Sleep;
import com.example.sleeptracker.firebase.DatabaseHelper;
import com.example.sleeptracker.ui.fragments.DatePickerFragment;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class SleepDataViewModel extends ViewModel {

    private MutableLiveData<List<Sleep>> sleepQueryResult;
    private DatabaseHelper db = DatabaseHelper.getInstance();

    private MutableLiveData<Date> queryStartDate = new MutableLiveData<>();
    private MutableLiveData<Date> queryEndDate = new MutableLiveData<>();
    private DatePickerFragment.MyDatePickerInterface startDateListener;
    private DatePickerFragment.MyDatePickerInterface endDateListener;

    public SleepDataViewModel() {
        initListeners();
        initQueryDates();
        updateDbQuery();
        if(sleepQueryResult == null){
            sleepQueryResult = new MutableLiveData<>();
            loadQueriedSleepData();
        }
    }

    private void initQueryDates() {
        // Create a date with time as 00:00.000
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);

        queryEndDate.setValue(c.getTime());

        // Make the start time one week earlier
        c.set(Calendar.WEEK_OF_YEAR, c.get(Calendar.WEEK_OF_YEAR) - 1);
        queryStartDate.setValue(c.getTime());
    }

    public MutableLiveData<List<Sleep>> getQueriedSleepData() {
        loadQueriedSleepData();
        return sleepQueryResult;
    }

    private void loadQueriedSleepData() {
        sleepQueryResult = db.getQueriedSleepData();
    }

    public MutableLiveData<Date> getQueryStartDate() {
        return queryStartDate;
    }

    public MutableLiveData<Date> getQueryEndDate() {
        return queryEndDate;
    }

    // Man, do I not like this.
    private void initListeners(){
        startDateListener = (((year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.setTime(queryStartDate.getValue());
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            queryStartDate.setValue(c.getTime());
            updateDbQuery();
        }));
        endDateListener = (((year, month, day) -> {
            Calendar c = Calendar.getInstance();
            c.setTime(queryEndDate.getValue());
            c.set(Calendar.YEAR, year);
            c.set(Calendar.MONTH, month);
            c.set(Calendar.DAY_OF_MONTH, day);
            queryEndDate.setValue(c.getTime());
            updateDbQuery();
        }));
    }

    public DatePickerFragment.MyDatePickerInterface getStartDateListener() {
        return startDateListener;
    }

    public DatePickerFragment.MyDatePickerInterface getEndDateListener() {
        return endDateListener;
    }

    private void updateDbQuery(){
        db.setQuery(queryStartDate.getValue(), queryEndDate.getValue(), 20);
    }
}