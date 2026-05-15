package com.example.glucokidfr.ui.schedule;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glucokidfr.R;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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

        TextView tvDate = view.findViewById(R.id.tvDate);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM", new Locale("ru"));
        tvDate.setText(sdf.format(new Date()));
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

        SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        long childId = prefs.getLong("childId", -1L);
        long parentId = prefs.getLong("parentId", -1L);

        if (childId != -1L) {
            viewModel.loadSugarHistory(String.valueOf(childId));
        } else if (parentId != -1L) {
            viewModel.loadHistoryForParent(parentId);
        }
    }
}