package com.example.glucokidfr.ui.homeParent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.glucokidfr.MainActivity;
import com.example.glucokidfr.R;

public class HomeParentFragment extends Fragment {

    public HomeParentFragment() {
        super(R.layout.home_screen_parent);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView btnSettings = view.findViewById(R.id.btnSettingsParent);

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