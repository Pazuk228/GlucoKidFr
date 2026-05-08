package com.example.glucokidfr.ui.registerParent;

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

public class RegisterParentFragment extends Fragment {

    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etPatronymic;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnRegister;
    private MaterialButton btnLogin;
    private TextView tvError;
    private View progressBar;

    public RegisterParentFragment() {
        super(R.layout.registration);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etFirstName = view.findViewById(R.id.FirstName);
        etLastName = view.findViewById(R.id.LastName);
        etPatronymic = view.findViewById(R.id.Patronymic);
        etEmail = view.findViewById(R.id.Email);
        etPassword = view.findViewById(R.id.Password);
        btnRegister = view.findViewById(R.id.btnRegister);
        btnLogin = view.findViewById(R.id.btnLogin);
        tvError = view.findViewById(R.id.error);
        progressBar = view.findViewById(R.id.loading);

        btnRegister.setOnClickListener(v -> register());
        btnLogin.setOnClickListener(v -> {
            Navigation.findNavController(view).navigateUp();
        });
    }

    private void register() {
        String firstName = etFirstName.getText().toString().trim();
        String lastName = etLastName.getText().toString().trim();
        String patronymic = etPatronymic.getText().toString().trim();
        String phone = etEmail.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (firstName.isEmpty() || lastName.isEmpty() || phone.isEmpty() || password.isEmpty()) {
            showError("Заполните обязательные поля");
            return;
        }

        showLoading(true);
        hideError();

        UserRepositoryImpl repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());

        Parent parent = new Parent(null, firstName, patronymic, lastName, phone, password);

        repository.registerParent(parent, status -> {
            showLoading(false);

            if (status.getErrors() != null) {
                showError("Ошибка регистрации: " + status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                Toast.makeText(getContext(), "Регистрация успешна", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(requireView()).navigateUp();
            } else {
                showError("Неизвестная ошибка");
            }
        });
    }

    private void showLoading(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnRegister.setEnabled(!show);
    }

    private void showError(String message) {
        tvError.setVisibility(View.VISIBLE);
        tvError.setText(message);
    }

    private void hideError() {
        tvError.setVisibility(View.GONE);
    }
}