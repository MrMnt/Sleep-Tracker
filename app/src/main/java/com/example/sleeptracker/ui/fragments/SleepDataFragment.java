package com.example.sleeptracker.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sleeptracker.R;
import com.example.sleeptracker.Sleep;
import com.example.sleeptracker.SleepAdapter;
import com.example.sleeptracker.viemodels.SleepDataViewModel;

import java.util.ArrayList;
import java.util.List;

public class SleepDataFragment extends Fragment {
    private static final String TAG = "SleepDataFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SleepDataViewModel mViewModel;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        init();
        initViewModel();
    }

    private void init(){
        mAdapter = new SleepAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = getView().findViewById(R.id.recycler_view_sleep_data);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(requireActivity()).get(SleepDataViewModel.class);
        mViewModel.getSleepsData().observe(requireActivity(), sleepsData -> {
            ((SleepAdapter) mAdapter).updateSleepDataSet(sleepsData);
        });
    }

    public SleepDataFragment () {
        super(R.layout.fragment_sleep_data);
        Log.d(TAG, "SleepDataFragment: ");
    }
}