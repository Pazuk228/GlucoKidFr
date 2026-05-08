package com.example.glucokidfr.ui.loginParent;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.glucokidfr.R;
import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Parent;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginParentFragment extends Fragment {

    private TextInputEditText etPhone;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;
    private MaterialButton btnRegister;
    private TextView tvError;
    private View progressBar;

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

        btnLogin.setOnClickListener(v -> login());
        btnRegister.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_loginParentFragment_to_registerParentFragment);
        });
    }

    private void login() {
        String phone = etPhone.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (phone.isEmpty() || password.isEmpty()) {
            showError("Заполните все поля");
            return;
        }

        showLoading(true);
        hideError();

        UserRepositoryImpl repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());

        repository.getParent(1L, status -> {
            showLoading(false);

            if (status.getErrors() != null) {
                showError("Ошибка входа: " + status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                Parent parent = status.getValue();
                Toast.makeText(getContext(), "Добро пожаловать, " + parent.getFirstName(), Toast.LENGTH_SHORT).show();
            } else {
                showError("Неверный телефон или пароль");
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!show);
    }

    private void showError(String message) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
    }

    private void hideError() {
        tvError.setVisibility(View.GONE);
    }
}