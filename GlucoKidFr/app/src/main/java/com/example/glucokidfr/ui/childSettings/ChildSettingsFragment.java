package com.example.glucokidfr.ui.childSettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.NavOptions;

import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ChildSettingsFragment extends Fragment {

    private ImageView ivMainAvatar;
    private SharedPreferences prefs;
    private ChildSettingsViewModel viewModel;

    private TextInputEditText etName;
    private MaterialButton btnSaveData;

    public ChildSettingsFragment() {
        super(R.layout.setting_child);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        viewModel = new ViewModelProvider(this).get(ChildSettingsViewModel.class);

        ivMainAvatar = view.findViewById(R.id.ivMainAvatar);
        View cardAvatar = view.findViewById(R.id.cardAvatar);
        ImageView btnBack = view.findViewById(R.id.btnBack);
        etName = view.findViewById(R.id.etName);
        btnSaveData = view.findViewById(R.id.btnSaveData);
        MaterialButton btnLogout = view.findViewById(R.id.btnLogout);

        btnBack.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        int savedAvatarId = prefs.getInt("childAvatar", R.drawable.cat_orange);
        ivMainAvatar.setImageResource(savedAvatarId);

        String savedName = prefs.getString("childName", "");
        etName.setText(savedName);

        cardAvatar.setOnClickListener(v -> showAvatarDialog());

        viewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            btnSaveData.setEnabled(!isLoading);
            btnSaveData.setText(isLoading ? "Сохранение..." : "Сохранить данные");
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getUpdateSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success) {
                Toast.makeText(getContext(), "Данные успешно сохранены!", Toast.LENGTH_SHORT).show();
                prefs.edit().putString("childName", etName.getText().toString().trim()).apply();
            }
        });

        btnSaveData.setOnClickListener(v -> {
            Long currentChildId = prefs.getLong("childId", -1L);
            String newName = etName.getText().toString().trim();
            viewModel.updateChildData(currentChildId, newName);
        });

        btnLogout.setOnClickListener(v -> {
            prefs.edit().clear().apply();

            NavController rootNavController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);
            NavOptions options = new NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .build();
            rootNavController.navigate(R.id.startFragment, null, options);
        });
    }

    private void showAvatarDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        View dialogView = getLayoutInflater().inflate(R.layout.avatar_selection, null);
        builder.setView(dialogView);

        AlertDialog dialog = builder.create();

        if (dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        }
        dialog.show();

        dialogView.findViewById(R.id.btnAvatar1).setOnClickListener(v -> {
            changeAvatar(R.drawable.cat_orange);
            dialog.dismiss();
        });

        dialogView.findViewById(R.id.btnAvatar2).setOnClickListener(v -> {
            changeAvatar(R.drawable.lion_avatar);
            dialog.dismiss();
        });

        dialogView.findViewById(R.id.btnAvatar3).setOnClickListener(v -> {
            changeAvatar(R.drawable.fox_obkurenniy);
            dialog.dismiss();
        });
    }
    private void changeAvatar(int drawableResId) {
        ivMainAvatar.setImageResource(drawableResId);
        prefs.edit().putInt("childAvatar", drawableResId).apply();
    }
}