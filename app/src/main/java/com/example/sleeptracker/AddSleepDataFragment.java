package com.example.sleeptracker;

import android.util.Log;

import androidx.fragment.app.Fragment;

public class AddSleepDataFragment extends Fragment {

    private static final String TAG = "AddSleepDataFragment";
    
    public AddSleepDataFragment() {
        super(R.layout.fragment_add_sleep_data);
        Log.d(TAG, "AddSleepDataFragment: ");
    }
}