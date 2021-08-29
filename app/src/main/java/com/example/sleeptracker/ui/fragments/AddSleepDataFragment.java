package com.example.sleeptracker.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sleeptracker.MyDateFormat;
import com.example.sleeptracker.R;
import com.example.sleeptracker.Sleep;
import com.example.sleeptracker.viemodels.AddSleepDataViewModel;

public class AddSleepDataFragment extends Fragment {

    private static final String TAG = "AddSleepDataFragment";

    private TextView textSleepStart;
    private TextView textSleepEnd;
    private TextView textSleepDuration;

    private AddSleepDataViewModel mViewModel;
    private Sleep sleep;

    // Makes specifying the listener easier :)
    private enum TimeListener{ startTimeListener, endTimeListener }
    private enum DateListener{ startDateListener, endDateListener }

    public AddSleepDataFragment() {
        super(R.layout.fragment_add_sleep_data);
        Log.d(TAG, "AddSleepDataFragment: ");
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews();
        initModel();
    }

    private void initModel(){
        mViewModel = new ViewModelProvider(requireActivity()).get(AddSleepDataViewModel.class);

        mViewModel.getNewSleepData().observe(requireActivity(), newSleepData -> updateSleepValue(newSleepData));
    }

    private void updateSleepValue(Sleep newSleepData){
        sleep = newSleepData;
        updateViews(sleep);
    }

    private void updateViews(Sleep newSleepData){
        textSleepStart.setText(MyDateFormat.format1(newSleepData.getStartTime()));
        textSleepEnd.setText(MyDateFormat.format1(newSleepData.getEndTime()));
        textSleepDuration.setText(newSleepData.getDurationAsString());
    }

    private void initViews(){
        textSleepStart = getView().findViewById(R.id.text_sleep_start_time);
        textSleepEnd = getView().findViewById(R.id.text_sleep_end_time);
        textSleepDuration = getView().findViewById(R.id.text_sleep_duration);

        // Will not do anything else with these buttons so why bother creating their variables :).
        getView().findViewById(R.id.btn_set_sleep_start_time)
                .setOnClickListener(v -> showTimePickerDialog(TimeListener.startTimeListener));
        getView().findViewById(R.id.btn_set_sleep_end_time)
                .setOnClickListener(v -> showTimePickerDialog(TimeListener.endTimeListener));

        getView().findViewById(R.id.btn_set_sleep_start_date)
                .setOnClickListener(v -> showDatePickerDialog(DateListener.startDateListener));
        getView().findViewById(R.id.btn_set_sleep_end_date)
                .setOnClickListener(v -> showDatePickerDialog(DateListener.endDateListener));

        getView().findViewById(R.id.btn_save_sleep_data)
                .setOnClickListener(v -> mViewModel.save());
    }

    private void showTimePickerDialog(TimeListener listener){
        DialogFragment newFragment = null;

        if(listener == TimeListener.startTimeListener){
            newFragment = new TimePickerFragment(sleep.getStartTime(), mViewModel.getStartTimeListener());
        } else if (listener == TimeListener.endTimeListener){
            newFragment = new TimePickerFragment(sleep.getEndTime(), mViewModel.getEndTimeListener());
        }

        newFragment.show(getActivity().getSupportFragmentManager(), "TimePicker");
    }

    private void showDatePickerDialog(DateListener listener){
        DialogFragment newFragment = null;

        if(listener == DateListener.startDateListener){
            newFragment = new DatePickerFragment(sleep.getStartTime(), mViewModel.getStartDateListener());
        } else if (listener == DateListener.endDateListener){
            newFragment = new DatePickerFragment(sleep.getEndTime(), mViewModel.getEndDateListener());
        }

        newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
    }
}