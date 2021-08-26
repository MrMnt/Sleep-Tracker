package com.example.sleeptracker.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sleeptracker.R;
import com.example.sleeptracker.Sleep;
import com.example.sleeptracker.SleepAdapter;

import java.util.ArrayList;
import java.util.List;

public class SleepDataFragment extends Fragment {
    private static final String TAG = "SleepDataFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        init();
    }

    private void init(){
        List<Sleep> dummyData = new ArrayList<>();
        for(int i = 0; i < 20; i++){
            dummyData.add(new Sleep());
        }

        mAdapter = new SleepAdapter(dummyData);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = getView().findViewById(R.id.recycler_view_sleep_data);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    public SleepDataFragment () {
        super(R.layout.fragment_sleep_data);
        Log.d(TAG, "SleepDataFragment: ");
    }
}