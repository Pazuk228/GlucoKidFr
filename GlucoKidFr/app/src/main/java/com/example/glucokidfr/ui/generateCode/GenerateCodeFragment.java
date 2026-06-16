package com.example.glucokidfr.ui.generateCode;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;

public class GenerateCodeFragment extends Fragment {

    private TextView tvGeneratedCode;
    private MaterialButton btnGenerateCode;
    private ProgressBar progressBar;
    private TextView tvError;
    private GenerateCodeViewModel viewModel;
    private SharedPreferences prefs;

    public GenerateCodeFragment() {
        super(R.layout.child_generate_code);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        if (prefs.getBoolean("isLinked", false)) {
            Navigation.findNavController(view).navigate(R.id.action_generateCodeFragment_to_sendSugarChildFragment);
            return;
        }
        tvGeneratedCode = view.findViewById(R.id.tvGeneratedCode);
        btnGenerateCode = view.findViewById(R.id.btnGenerateCode);
        progressBar = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.tvError);

        viewModel = new ViewModelProvider(this).get(GenerateCodeViewModel.class);

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
            Long currentChildId = prefs.getLong("childId", -1L);
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
                prefs.edit().putBoolean("isLinked", true).apply();
                NavOptions options = new NavOptions.Builder()
                        .setPopUpTo(R.id.generateCodeFragment, true)
                        .build();

                Navigation.findNavController(v)
                        .navigate(R.id.action_generateCodeFragment_to_sendSugarChildFragment, null, options);
            });
        }
    }
}