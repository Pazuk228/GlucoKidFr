package com.example.glucokidfr.ui.generateCode;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;

public class GenerateCodeFragment extends Fragment {

    private TextView tvGeneratedCode;
    private MaterialButton btnGenerateCode;
    private ProgressBar progressBar;
    private TextView tvError;
    private GenerateCodeViewModel viewModel;

    public GenerateCodeFragment() {
        super(R.layout.child_generate_code);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvGeneratedCode = view.findViewById(R.id.tvGeneratedCode);
        btnGenerateCode = view.findViewById(R.id.btnGenerateCode);
        progressBar = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.tvError);

        viewModel = new ViewModelProvider(this).get(GenerateCodeViewModel.class);

        boolean isLinked = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                .getBoolean("isLinked", false);

        if (isLinked) {
            androidx.navigation.Navigation.findNavController(view)
                    .navigate(R.id.action_generateCodeFragment_to_sendSugarChildFragment);
            return;
        }

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnGenerateCode.setEnabled(!isLoading);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(error);
            } else {
                tvError.setVisibility(View.GONE);
            }
        });

        viewModel.getGeneratedCode().observe(getViewLifecycleOwner(), code -> {
            if (code != null) {
                tvGeneratedCode.setText(code);
            }
        });

        btnGenerateCode.setOnClickListener(v -> {
            Long currentChildId = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                    .getLong("childId", -1L);

            if (currentChildId != -1L) {
                viewModel.generateCode(currentChildId);
            } else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Ошибка: вы не авторизованы");
            }
        });
        MaterialButton btnReady = view.findViewById(R.id.btnReady);

        if (btnReady != null) {
            btnReady.setOnClickListener(v -> {
                requireActivity().getSharedPreferences("AppPrefs", android.content.Context.MODE_PRIVATE)
                        .edit().putBoolean("isLinked", true).apply();

                androidx.navigation.Navigation.findNavController(v)
                        .navigate(R.id.action_generateCodeFragment_to_sendSugarChildFragment);
            });
        }
    }
}