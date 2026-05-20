package com.example.glucokidfr.ui.ChildGamesFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.glucokidfr.R;

public class ChildGamesFragment extends Fragment {

    public ChildGamesFragment() {
        super(R.layout.fragment_child_games);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView btnSettings = view.findViewById(R.id.btnSettings);

        btnSettings.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(v)
                    .navigate(R.id.childSettingsFragment);
        });
    }
}