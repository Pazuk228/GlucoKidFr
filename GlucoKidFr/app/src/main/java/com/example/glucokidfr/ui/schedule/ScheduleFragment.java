package com.example.glucokidfr.ui.schedule;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.glucokidfr.R;
import com.example.glucokidfr.ui.parent.SharedParentViewModel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ScheduleFragment extends Fragment {

    private ScheduleAdapter adapter;
    private ScheduleViewModel viewModel;
    private SharedParentViewModel sharedViewModel;
    private TextView tvDate;
    private TextView tvEmptyState;
    private ProgressBar progressBar;

    private final SimpleDateFormat displayFormat = new SimpleDateFormat("dd MMMM", new Locale("ru"));
    private final SimpleDateFormat serverFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
    private Calendar currentCalendar;

    public ScheduleFragment() {
        super(R.layout.schedule);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvDate = view.findViewById(R.id.tvDate);
        tvEmptyState = view.findViewById(R.id.tvEmptyState);
        progressBar = view.findViewById(R.id.progressBar);
        ImageView btnCalendar = view.findViewById(R.id.btnCalendar);

        RecyclerView rvSchedule = view.findViewById(R.id.rvSchedule);
        rvSchedule.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ScheduleAdapter();
        rvSchedule.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(ScheduleViewModel.class);
        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedParentViewModel.class);

        currentCalendar = Calendar.getInstance();
        updateDateText();
        String todayServerFormat = serverFormat.format(currentCalendar.getTime());

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getScheduleData().observe(getViewLifecycleOwner(), mealGroups -> {
            if (mealGroups != null && !mealGroups.isEmpty()) {
                tvEmptyState.setVisibility(View.GONE);
                adapter.setMealGroups(mealGroups);
            } else {
                tvEmptyState.setVisibility(View.VISIBLE);
                tvEmptyState.setText(sharedViewModel.getSelectedChildId().getValue() != -1L ? "Нет замеров за эту дату" : "Выберите ребенка");
                adapter.setMealGroups(new ArrayList<>());
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), "Ошибка: " + error, Toast.LENGTH_LONG).show();
            }
        });

        btnCalendar.setOnClickListener(v -> showDatePicker());

        SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        long loggedInChildId = prefs.getLong("childId", -1L);
        long parentId = prefs.getLong("parentId", -1L);

        sharedViewModel.getSelectedChildId().observe(getViewLifecycleOwner(), selectedChildId -> {
            if (selectedChildId != -1L) {
                viewModel.loadSugarHistory(String.valueOf(selectedChildId), todayServerFormat);
            } else if (loggedInChildId != -1L) {
                viewModel.loadSugarHistory(String.valueOf(loggedInChildId), todayServerFormat);
            } else if (parentId != -1L) {
                adapter.setMealGroups(new ArrayList<>());
                tvEmptyState.setVisibility(View.VISIBLE);
                tvEmptyState.setText("Выберите ребенка");
            }
        });
    }

    private void updateDateText() {
        tvDate.setText(displayFormat.format(currentCalendar.getTime()));
    }

    private void showDatePicker() {
        DatePickerDialog dialog = new DatePickerDialog(requireContext(),
                (view, year, month, dayOfMonth) -> {
                    currentCalendar.set(year, month, dayOfMonth);
                    updateDateText();
                    String selectedServerFormat = serverFormat.format(currentCalendar.getTime());
                    long selectedChildId = sharedViewModel.getSelectedChildId().getValue() != null ? sharedViewModel.getSelectedChildId().getValue() : -1L;
                    long loggedInChildId = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).getLong("childId", -1L);

                    if (selectedChildId != -1L) {
                        viewModel.loadSugarHistory(String.valueOf(selectedChildId), selectedServerFormat);
                    } else if (loggedInChildId != -1L) {
                        viewModel.loadSugarHistory(String.valueOf(loggedInChildId), selectedServerFormat);
                    }
                },
                currentCalendar.get(Calendar.YEAR),
                currentCalendar.get(Calendar.MONTH),
                currentCalendar.get(Calendar.DAY_OF_MONTH)
        );
        dialog.show();
    }
}