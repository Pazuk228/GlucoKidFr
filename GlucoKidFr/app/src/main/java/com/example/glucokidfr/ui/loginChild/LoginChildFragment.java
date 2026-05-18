package com.example.glucokidfr.ui.loginChild;

import android.content.Context;
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

public class LoginChildFragment extends Fragment {

    private TextInputEditText etEmail;
    private TextInputEditText etPassword;
    private MaterialButton btnLogin;
    private MaterialButton btnRegister;
    private TextView tvError;
    private View progressBar;
    private LoginChildViewModel viewModel;

    public LoginChildFragment() {
        super(R.layout.login);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etEmail = view.findViewById(R.id.Email);
        etPassword = view.findViewById(R.id.Password);
        btnLogin = view.findViewById(R.id.btnRegister);
        btnRegister = view.findViewById(R.id.btnLogin);
        tvError = view.findViewById(R.id.error);
        progressBar = view.findViewById(R.id.loading);

        viewModel = new ViewModelProvider(this).get(LoginChildViewModel.class);

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
            btnLogin.setEnabled(!isLoading);
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                tvError.setVisibility(View.VISIBLE);
                tvError.setText(error);
            }
        });

        viewModel.getLoginResult().observe(getViewLifecycleOwner(), child -> {
            if (child != null) {
                Toast.makeText(getContext(), "Привет, " + child.getFirstName(), Toast.LENGTH_SHORT).show();

                //requireActivity().getSharedPreferences("AppPrefs", android.content.Context.MODE_PRIVATE)
                //        .edit().putLong("childId", child.getId()).apply();
                requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                        .edit()
                        .putBoolean("isLoggedIn", true)
                        .putString("userRole", "child")
                        .putLong("childId", child.getId())
                        .apply();

                Navigation.findNavController(requireView())
                        .navigate(R.id.action_loginChildFragment_to_childMainFragment);
            }
        });

        btnLogin.setOnClickListener(v -> {
            String phone = etEmail.getText().toString().trim();
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
                    .navigate(R.id.action_loginChildFragment_to_registerChildFragment);
        });
    }
}