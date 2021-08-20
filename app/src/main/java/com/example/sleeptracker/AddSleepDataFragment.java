package com.example.sleeptracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class AddSleepDataFragment extends Fragment {

    private static final String TAG = "AddSleepDataFragment";

    private TextView textSleepStart;
    private TextView textSleepEnd;
    private TextView textSleepDuration;

    public AddSleepDataFragment() {
        super(R.layout.fragment_add_sleep_data);
        Log.d(TAG, "AddSleepDataFragment: ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }

    private void initViews(){
        textSleepStart = getView().findViewById(R.id.text_sleep_start_time);
        textSleepEnd = getView().findViewById(R.id.text_sleep_end_time);
        textSleepDuration = getView().findViewById(R.id.text_sleep_duration);
    }
}