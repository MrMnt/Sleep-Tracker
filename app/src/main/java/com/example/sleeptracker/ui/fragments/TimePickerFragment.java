package com.example.sleeptracker.ui.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimePickerFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {

    private static final String TAG = "TimePickerFragment";

    // Currently chosen time by the user
    private Date mDate;
    private MyTimePickerInterface mListener;

    public TimePickerFragment(Date mDate, MyTimePickerInterface mListener) {
        this.mDate = mDate;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        c.setTime(mDate);
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute, true);
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        // Do something with the time chosen by the user
        if(mListener != null) mListener.onTimeSet(hourOfDay, minute);
    }

    public interface MyTimePickerInterface {
        void onTimeSet(int hourOfDay, int minute);
    }
}
