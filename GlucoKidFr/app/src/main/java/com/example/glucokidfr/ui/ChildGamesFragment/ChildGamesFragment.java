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

            requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                    .edit()
                    .clear()
                    .apply();

            androidx.navigation.NavController rootNavController =
                    androidx.navigation.Navigation.findNavController(requireActivity(), R.id.nav_host_fragment);

            androidx.navigation.NavOptions options = new androidx.navigation.NavOptions.Builder()
                    .setPopUpTo(R.id.nav_graph, true)
                    .build();

            rootNavController.navigate(R.id.startFragment, null, options);
        });
    }
}