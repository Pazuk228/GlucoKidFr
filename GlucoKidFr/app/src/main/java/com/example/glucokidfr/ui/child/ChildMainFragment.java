package com.example.glucokidfr.ui.child;

import android.content.Context;
import android.content.SharedPreferences;
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

            bottomNav.setItemIconTintList(null);
            NavigationUI.setupWithNavController(bottomNav, navController);

            view.post(() -> {
                SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                boolean isLinked = prefs.getBoolean("isLinked", false);

                if (isLinked) {
                    bottomNav.setVisibility(View.VISIBLE);
                    if (navController.getCurrentDestination() != null &&
                            navController.getCurrentDestination().getId() == R.id.generateCodeFragment) {
                        navController.navigate(R.id.sendSugarChildFragment);
                    }
                } else {
                    bottomNav.setVisibility(View.GONE);
                    navController.navigate(R.id.generateCodeFragment);
                }
            });

            bottomNav.setOnItemSelectedListener(item -> {
                SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                boolean currentlyLinked = prefs.getBoolean("isLinked", false);

                if (item.getItemId() == R.id.generateCodeFragment && currentlyLinked) {
                    navController.navigate(R.id.sendSugarChildFragment);
                    return true;
                }

                return NavigationUI.onNavDestinationSelected(item, navController);
            });
        }
    }
}