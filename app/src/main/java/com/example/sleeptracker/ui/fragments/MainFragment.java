package com.example.sleeptracker.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.sleeptracker.R;

public class MainFragment extends Fragment {

    private static final String TAG = "MainFragment";
    
    public MainFragment() {
        super(R.layout.fragment_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "MainFragment: ");
        initButtons();
    }

    private void initButtons(){
        // Add sleep data button
        getView().findViewById(R.id.btn_add_sleep)
                .setOnClickListener(v -> {
                    Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_addSleepDataFragment);
                });

        // View sleep data button
        getView().findViewById(R.id.btn_view_sleep)
                .setOnClickListener(v -> {
                    Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_sleepDataFragment);
                });
    }
}