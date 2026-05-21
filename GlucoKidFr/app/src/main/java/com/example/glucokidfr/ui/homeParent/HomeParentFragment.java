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


        View btnSettings = view.findViewById(R.id.btnSettingsParent);

        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> {
                androidx.navigation.Navigation.findNavController(v)
                        .navigate(R.id.parentSettingsFragment);
            });
        }
    }
}