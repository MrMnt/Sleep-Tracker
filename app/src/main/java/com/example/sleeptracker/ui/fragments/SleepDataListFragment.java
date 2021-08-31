package com.example.sleeptracker.ui.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sleeptracker.MyDateFormat;
import com.example.sleeptracker.R;
import com.example.sleeptracker.SleepAdapter;
import com.example.sleeptracker.viemodels.SleepDataViewModel;

import java.util.ArrayList;
import java.util.Date;

public class SleepDataListFragment extends Fragment {
    private static final String TAG = "SleepDataFragment";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private SleepDataViewModel mViewModel;
    private Button btnQueryStartDate, btnQueryEndDate;
    private Date queryStartDate, queryEndDate;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        
        initViews();
        initViewModel();
        initButtons();
    }

    private void initViews(){
        mAdapter = new SleepAdapter(new ArrayList<>());
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView = getView().findViewById(R.id.recycler_view_sleep_data);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        btnQueryStartDate = getView().findViewById(R.id.btn_query_start_date);
        btnQueryEndDate = getView().findViewById(R.id.btn_query_end_date);
    }

    private void initButtons(){
        btnQueryStartDate.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment(queryStartDate, mViewModel.getStartDateListener());
            newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
        });
        btnQueryEndDate.setOnClickListener(v -> {
            DialogFragment newFragment = new DatePickerFragment(queryEndDate, mViewModel.getEndDateListener());
            newFragment.show(getActivity().getSupportFragmentManager(), "DatePicker");
        });
    }

    private void initViewModel() {
        mViewModel = new ViewModelProvider(requireActivity()).get(SleepDataViewModel.class);
        mViewModel.getQueriedSleepData().observe(requireActivity(), sleepDataSet -> {
            ((SleepAdapter) mAdapter).updateSleepDataSet(sleepDataSet);
            // TODO: make notifying more performant
            mAdapter.notifyDataSetChanged();
        });

        mViewModel.getQueryStartDate().observe(requireActivity(), startDate -> {
            queryStartDate = startDate;
            btnQueryStartDate.setText(MyDateFormat.format2(startDate));
        });
        mViewModel.getQueryEndDate().observe(requireActivity(), endDate -> {
            queryEndDate = endDate;
            btnQueryEndDate.setText(MyDateFormat.format2(endDate));
        });
    }

    public SleepDataListFragment() {
        super(R.layout.fragment_sleep_data_list);
        Log.d(TAG, "SleepDataFragment: ");
    }
}