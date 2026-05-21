package com.example.glucokidfr.ui.parentSettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ParentSettingsFragment extends Fragment {

    private TextInputEditText etFirstName, etLastName, etSecondName, etPhone;
    private ParentSettingsViewModel viewModel;
    private SharedPreferences prefs;

    public ParentSettingsFragment() {
        super(R.layout.settings_parent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etFirstName = view.findViewById(R.id.etFirstName);
        etLastName = view.findViewById(R.id.etLastName);
        etSecondName = view.findViewById(R.id.etSecondName);
        etPhone = view.findViewById(R.id.etPhone);
        ProgressBar progressBar = view.findViewById(R.id.progressBar);

        prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(ParentSettingsViewModel.class);

        ImageView btnBack = view.findViewById(R.id.btnBack);
        btnBack.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        Long parentId = prefs.getLong("parentId", -1L);
        if (parentId != -1L) {
            viewModel.loadParentData(parentId);
        }

        viewModel.getParentData().observe(getViewLifecycleOwner(), parent -> {
            if (parent != null) {
                if (parent.getLastName() != null) etLastName.setText(parent.getLastName());
                if (parent.getFirstName() != null) etFirstName.setText(parent.getFirstName());
                if (parent.getSecondName() != null) etSecondName.setText(parent.getSecondName());
                if (parent.getPhone() != null) etPhone.setText(parent.getPhone());
            }
        });

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        viewModel.getUpdateSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Данные обновлены!", Toast.LENGTH_SHORT).show();
                Navigation.findNavController(view).navigateUp();
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) Toast.makeText(getContext(), "Ошибка: " + error, Toast.LENGTH_LONG).show();
        });

        MaterialButton btnSave = view.findViewById(R.id.btnSave);
        btnSave.setOnClickListener(v -> {
            if (parentId != -1L) {
                viewModel.updateParentData(
                        parentId,
                        etFirstName.getText().toString().trim(),
                        etLastName.getText().toString().trim(),
                        etSecondName.getText().toString().trim(),
                        etPhone.getText().toString().trim()
                );
            }
        });

        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();

            NavController rootNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            NavOptions options = new NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .build();
            rootNavController.navigate(R.id.startFragment, null, options);
        });
    }
}