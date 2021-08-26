package com.example.sleeptracker.viemodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sleeptracker.Sleep;
import com.example.sleeptracker.firebase.DatabaseHelper;

import java.util.List;

public class SleepDataViewModel extends ViewModel {

    private MutableLiveData<List<Sleep>> everySleepData;
    private DatabaseHelper db = DatabaseHelper.getInstance();

    public SleepDataViewModel() {
        if(everySleepData == null){
            everySleepData = new MutableLiveData<>();
            loadSleepData();
        }
    }

    private void loadSleepData() {
        everySleepData = db.getEverySleepData();
    }

    public MutableLiveData<List<Sleep>> getEverySleepData() {
        loadSleepData();
        return everySleepData;
    }
}