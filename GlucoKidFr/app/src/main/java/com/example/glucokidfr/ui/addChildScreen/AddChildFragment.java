package com.example.glucokidfr.ui.addChildScreen;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class AddChildFragment extends Fragment {

    private TextInputEditText etInputCode;
    private MaterialButton btnSubmitCode;
    private ProgressBar progressBar;
    private TextView tvError;
    private AddChildViewModel viewModel;

    public AddChildFragment() {
        super(R.layout.add_child_screen);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etInputCode = view.findViewById(R.id.etInputCode);
        btnSubmitCode = view.findViewById(R.id.btnSubmitCode);
        progressBar = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.tvError);

        viewModel = new ViewModelProvider(this).get(AddChildViewModel.class);

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnSubmitCode.setEnabled(!isLoading);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(error);
            } else {
                tvError.setVisibility(View.GONE);
            }
        });

        viewModel.getAddChildResult().observe(getViewLifecycleOwner(), child -> {
            if (child != null) {
                Toast.makeText(getContext(), "Ребёнок успешно добавлен!", Toast.LENGTH_LONG).show();
                etInputCode.setText("");
            }
        });

        btnSubmitCode.setOnClickListener(v -> {
            String code = etInputCode.getText() != null ? etInputCode.getText().toString().trim() : "";

            if (code.isEmpty()) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Введите 6-значный код");
                return;
            }
            Long currentParentId = requireActivity().getSharedPreferences("AppPrefs", android.content.Context.MODE_PRIVATE)
                    .getLong("parentId", -1L);

            if (currentParentId != -1L) {
                tvError.setVisibility(View.GONE);
                viewModel.linkChild(code, currentParentId);

            } else {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Ошибка: вы не авторизованы как родитель");
            }
        });

            tvError.setVisibility(View.GONE);
    }
}