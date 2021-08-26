package com.example.sleeptracker.viemodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.sleeptracker.Sleep;

import java.util.ArrayList;
import java.util.List;

public class SleepDataViewModel extends ViewModel {

    MutableLiveData<List<Sleep>> sleepsData;

    public SleepDataViewModel() {
        if(sleepsData == null){
            sleepsData = new MutableLiveData<>();
            loadSleepData();
        }
    }

    // TODO: load sleep data from Firestore
    private void loadSleepData() {
        List<Sleep> dummyData = new ArrayList<>();
        for(int i = 0; i < 8; i++){
            dummyData.add(new Sleep());
        }
        sleepsData.setValue(dummyData);
    }

    public MutableLiveData<List<Sleep>> getSleepsData(){
        return sleepsData;
    }
}