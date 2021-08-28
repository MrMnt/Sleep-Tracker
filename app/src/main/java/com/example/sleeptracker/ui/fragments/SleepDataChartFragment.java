package com.example.sleeptracker.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.sleeptracker.R;

public class SleepDataChartFragment extends Fragment {
    private static final String TAG = "SleepDataChartFragment";
    
    public SleepDataChartFragment() {
        super(R.layout.fragment_sleep_data_chart);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
    }
}
