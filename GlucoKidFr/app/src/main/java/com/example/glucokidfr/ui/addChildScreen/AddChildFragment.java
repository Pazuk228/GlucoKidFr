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

    private TextInputEditText etChildPhone;
    private MaterialButton btnAddChild;
    private ProgressBar progressBar;
    private TextView tvError;
    private AddChildViewModel viewModel;

    public AddChildFragment() {
        super(R.layout.add_child_screen);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etChildPhone = view.findViewById(R.id.etChildPhone);
        btnAddChild = view.findViewById(R.id.btnAddChild);
        progressBar = view.findViewById(R.id.progressBar);
        tvError = view.findViewById(R.id.tvError);

        viewModel = new ViewModelProvider(this).get(AddChildViewModel.class);

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnAddChild.setEnabled(!isLoading);
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
                etChildPhone.setText("");
            }
        });

        btnAddChild.setOnClickListener(v -> {
            String phone = etChildPhone.getText() != null ? etChildPhone.getText().toString().trim() : "";

            if (phone.isEmpty()) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Введите номер телефона");
                return;
            }

            tvError.setVisibility(View.GONE);
            viewModel.addChild(phone);
        });
    }
}