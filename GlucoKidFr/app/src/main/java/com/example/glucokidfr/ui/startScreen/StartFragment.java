package com.example.glucokidfr.ui.startScreen;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;

public class StartFragment extends Fragment {

    public StartFragment() {
        super(R.layout.choose_role);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        MaterialButton btnIParent = view.findViewById(R.id.btnIParent);
        MaterialButton btnIChild = view.findViewById(R.id.btnIChild);

        //к родителю
        btnIParent.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_startFragment_to_loginParentFragment);
        });

        //к ребёноку
        btnIChild.setOnClickListener(v -> {
            Navigation.findNavController(view)
                    .navigate(R.id.action_startFragment_to_loginChildFragment);
        });
    }
}
