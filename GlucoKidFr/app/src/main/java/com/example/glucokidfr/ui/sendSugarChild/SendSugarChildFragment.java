package com.example.glucokidfr.ui.sendSugarChild;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SendSugarChildFragment extends Fragment {

    public SendSugarChildFragment() {
        super(R.layout.send_sugar_child);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText etSugarLevel = view.findViewById(R.id.etSugarLevel);
        TextView tvSugarStatus = view.findViewById(R.id.tvSugarStatus);
        ProgressBar pbLoadingSugar = view.findViewById(R.id.pbLoadingSugar);
        MaterialButton btnSubmit = view.findViewById(R.id.btnSubmitSugar);

        SendSugarChildViewModel viewModel = new ViewModelProvider(this).get(SendSugarChildViewModel.class);

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            pbLoadingSugar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnSubmit.setEnabled(!isLoading);
        });

        viewModel.getStatusMessage().observe(getViewLifecycleOwner(), message -> {
            tvSugarStatus.setVisibility(View.VISIBLE);
            tvSugarStatus.setText(message);
            if (message.contains("Ошибка")) {
                tvSugarStatus.setTextColor(android.graphics.Color.RED);
            } else {
                tvSugarStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50"));
                etSugarLevel.setText("");
            }
        });

        btnSubmit.setOnClickListener(v -> {
            String sugarText = etSugarLevel.getText().toString().replace(",", ".");

            if (sugarText.isEmpty()) {
                tvSugarStatus.setVisibility(View.VISIBLE);
                tvSugarStatus.setTextColor(android.graphics.Color.RED);
                tvSugarStatus.setText("Введите значение!");
                return;
            }

            try {
                double sugarValue = Double.parseDouble(sugarText);

                Long currentChildId = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                        .getLong("childId", -1L);

                if (currentChildId != -1L) {
                    String currentTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault()).format(new Date());

                    viewModel.addSugarLevel(String.valueOf(currentChildId), sugarValue, currentTime);
                } else {
                    tvSugarStatus.setVisibility(View.VISIBLE);
                    tvSugarStatus.setTextColor(android.graphics.Color.RED);
                    tvSugarStatus.setText("Ошибка: Ребенок не авторизован");
                }
            } catch (NumberFormatException e) {
                tvSugarStatus.setVisibility(View.VISIBLE);
                tvSugarStatus.setTextColor(android.graphics.Color.RED);
                tvSugarStatus.setText("Неверный формат числа");
            }
        });
    }
}