package com.example.glucokidfr.ui.loginParent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginParentFragment extends Fragment {

    private TextInputEditText etPhone;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;
    private MaterialButton btnRegister;
    private TextView tvError;
    private View progressBar;
    private LoginParentViewModel viewModel;

    public LoginParentFragment() {
        super(R.layout.login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etPhone = view.findViewById(R.id.Email);
        etPassword = view.findViewById(R.id.Password);
        btnLogin = view.findViewById(R.id.btnRegister);
        btnRegister = view.findViewById(R.id.btnLogin);
        tvError = view.findViewById(R.id.error);
        progressBar = view.findViewById(R.id.loading);

        viewModel = new ViewModelProvider(this).get(LoginParentViewModel.class);

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnLogin.setEnabled(!isLoading);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(error);
            } else {
                tvError.setVisibility(View.GONE);
            }
        });

        viewModel.getLoginResult().observe(getViewLifecycleOwner(), parent -> {
            if (parent != null) {
                Toast.makeText(getContext(), "Добро пожаловать, " + parent.getFirstName(), Toast.LENGTH_LONG).show();
            }
        });

        btnLogin.setOnClickListener(v -> {
            String phone = etPhone.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (phone.isEmpty() || password.isEmpty()) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText("Заполните все поля");
                return;
            }

            tvError.setVisibility(View.GONE);
            viewModel.login(phone, password);
        });

        btnRegister.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_loginParentFragment_to_registerParentFragment);
        });
    }
}