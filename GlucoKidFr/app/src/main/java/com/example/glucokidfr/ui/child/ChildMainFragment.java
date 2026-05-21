package com.example.glucokidfr.ui.child;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
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

            navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
                if (destination.getId() == R.id.sendSugarChildFragment || destination.getId() == R.id.generateCodeFragment) {
                    MenuItem item = bottomNav.getMenu().findItem(R.id.generateCodeFragment);
                    if (item != null) item.setChecked(true);
                }
            });

            view.post(() -> {
                SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                boolean isLinked = prefs.getBoolean("isLinked", false);
                bottomNav.setVisibility(View.VISIBLE);

                if (isLinked) {
                    if (navController.getCurrentDestination() != null &&
                            navController.getCurrentDestination().getId() == R.id.generateCodeFragment) {
                        navController.navigate(R.id.sendSugarChildFragment);
                    }
                }
            });

            bottomNav.setOnItemSelectedListener(item -> {
                SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                boolean currentlyLinked = prefs.getBoolean("isLinked", false);
                int itemId = item.getItemId();

                NavOptions options = new NavOptions.Builder()
                        .setLaunchSingleTop(true)
                        .setRestoreState(true)
                        .setPopUpTo(navController.getGraph().getStartDestinationId(), false, true)
                        .build();

                if (itemId == R.id.generateCodeFragment || itemId == R.id.sendSugarChildFragment) {
                    if (currentlyLinked) {
                        navController.navigate(R.id.sendSugarChildFragment, null, options);
                    } else {
                        navController.navigate(R.id.generateCodeFragment, null, options);
                        Toast.makeText(getContext(), "Необходимо привязать аккаунт!", Toast.LENGTH_SHORT).show();
                    }
                    return true;
                }

                return NavigationUI.onNavDestinationSelected(item, navController);
            });
        }
    }
}