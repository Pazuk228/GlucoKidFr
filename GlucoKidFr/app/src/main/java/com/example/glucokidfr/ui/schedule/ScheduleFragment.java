package com.example.glucokidfr.ui.schedule;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glucokidfr.R;

public class ScheduleFragment extends Fragment {

    private ScheduleAdapter adapter;
    private ScheduleViewModel viewModel;

    public ScheduleFragment() {
        super(R.layout.schedule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rvSchedule = view.findViewById(R.id.rvSchedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ScheduleAdapter();
        rvSchedule.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);

        viewModel.getScheduleData().observe(getViewLifecycleOwner(), mealGroups -> {
            if (mealGroups != null) {
                adapter.setMealGroups(mealGroups);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Ошибка: " + error, Toast.LENGTH_LONG).show();
            }
        });

        String currentChildId = "1";
        viewModel.loadSugarHistory(currentChildId);
    }
}