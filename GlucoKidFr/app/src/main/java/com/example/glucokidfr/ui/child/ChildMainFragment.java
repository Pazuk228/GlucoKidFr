package com.example.glucokidfr.ui.child;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.glucokidfr.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ChildMainFragment extends Fragment {

    public ChildMainFragment() {
        super(R.layout.fragment_child_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.child_nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView bottomNav = view.findViewById(R.id.child_bottom_navigation);
            NavigationUI.setupWithNavController(bottomNav, navController);
        }
    }
}
