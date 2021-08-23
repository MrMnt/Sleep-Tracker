package com.example.sleeptracker;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sleeptracker.viemodels.AddSleepDataViewModel;

public class AddSleepDataFragment extends Fragment {

    private static final String TAG = "AddSleepDataFragment";

    private TextView textSleepStart;
    private TextView textSleepEnd;
    private TextView textSleepDuration;

    private AddSleepDataViewModel mViewModel;

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

        mViewModel.getNewSleepData().observe(requireActivity(), newSleepData -> updateViews(newSleepData));
    }

    private void updateViews(Sleep newSleepData){
        textSleepStart.setText(newSleepData.getStartTime().toString());
        textSleepEnd.setText(newSleepData.getEndTime().toString());
        textSleepDuration.setText(newSleepData.getDuration());
    }

    private void initViews(){
        textSleepStart = getView().findViewById(R.id.text_sleep_start_time);
        textSleepEnd = getView().findViewById(R.id.text_sleep_end_time);
        textSleepDuration = getView().findViewById(R.id.text_sleep_duration);

        getView().findViewById(R.id.btn_set_sleep_start_time)
                .setOnClickListener(v -> showTimePickerDialog(TimeListener.startTimeListener));
        getView().findViewById(R.id.btn_set_sleep_end_time)
                .setOnClickListener(v -> showTimePickerDialog(TimeListener.endTimeListener));

        getView().findViewById(R.id.btn_set_sleep_start_date)
                .setOnClickListener(v -> showDatePickerDialog(DateListener.startDateListener));
        getView().findViewById(R.id.btn_set_sleep_end_date)
                .setOnClickListener(v -> showDatePickerDialog(DateListener.endDateListener));
    }

    private void showTimePickerDialog(TimeListener listener){
        DialogFragment newFragment = null;

        if(listener == TimeListener.startTimeListener){
            newFragment = new TimePickerFragment(mViewModel.getStartTimeListener());
        } else if (listener == TimeListener.endTimeListener){
            newFragment = new TimePickerFragment(mViewModel.getEndTimeListener());
        }

        newFragment.show(getActivity().getSupportFragmentManager(), "TimePicker");
    }

    private void showDatePickerDialog(DateListener listener){
        DialogFragment newFragment = null;

        if(listener == DateListener.startDateListener){
            newFragment = new DatePickerFragment(mViewModel.getStartDateListener());
        } else if (listener == DateListener.endDateListener){
            newFragment = new DatePickerFragment(mViewModel.getEndDateListener());
        }

        newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
    }
}