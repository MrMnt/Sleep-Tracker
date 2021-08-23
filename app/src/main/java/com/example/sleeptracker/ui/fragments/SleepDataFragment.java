package com.example.sleeptracker.ui.fragments;

import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.sleeptracker.R;

public class SleepDataFragment extends Fragment {

    private static final String TAG = "SleepDataFragment";
    
    public SleepDataFragment () {
        super(R.layout.fragment_sleep_data);
        Log.d(TAG, "SleepDataFragment: ");
    }
}