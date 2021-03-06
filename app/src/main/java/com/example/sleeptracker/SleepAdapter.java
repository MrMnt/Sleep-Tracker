package com.example.sleeptracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.List;

// TODO: a user should be able to click on a card to change its properties
public class SleepAdapter extends RecyclerView.Adapter<SleepAdapter.SleepViewHolder> {

    private List<Sleep> sleepsDataSet;

    // Provide a reference to the type of views that you are using (custom ViewHolder).
    public static class SleepViewHolder extends RecyclerView.ViewHolder{
        private final TextView textDuration;
        private final TextView textStartTime;
        private final TextView textEndTime;

        public SleepViewHolder(@NonNull View itemView) {
            super(itemView);

            textDuration = itemView.findViewById(R.id.text_duration);
            textStartTime = itemView.findViewById(R.id.text_start_time);
            textEndTime = itemView.findViewById(R.id.text_end_time);
        }

        public TextView getTextDuration() {
            return textDuration;
        }

        public TextView getTextStartTime() {
            return textStartTime;
        }

        public TextView getTextEndTime() {
            return textEndTime;
        }
    }

    // Initialize the dataset of the Adapter.
    public SleepAdapter(List<Sleep> dataSet){
        sleepsDataSet = dataSet;
    }
    public void updateSleepDataSet(List<Sleep> dataSet) {
        sleepsDataSet = dataSet;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public SleepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Create a new view, which defines the UI of the list item

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sleep_data_item, parent, false);

        return new SleepViewHolder(view);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(@NonNull SleepViewHolder holder, int position) {
        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        Sleep sleep = sleepsDataSet.get(position);
        holder.getTextDuration().setText(sleep.getDurationAsString2());

        holder.getTextStartTime().setText(MyDateFormat.format1(sleep.getStartTime()));
        holder.getTextEndTime().setText(MyDateFormat.format1(sleep.getEndTime()));
    }

    @Override
    public int getItemCount() {
        return sleepsDataSet.size();
    }
}
