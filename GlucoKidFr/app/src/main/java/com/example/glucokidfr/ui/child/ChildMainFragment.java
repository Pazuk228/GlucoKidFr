package com.example.glucokidfr.ui.child;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.glucokidfr.R;

public class ChildMainFragment extends Fragment {

    public ChildMainFragment() {
        super(R.layout.fragment_child_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        androidx.navigation.fragment.NavHostFragment navHostFragment =
                (androidx.navigation.fragment.NavHostFragment) getChildFragmentManager()
                        .findFragmentById(R.id.child_nav_host_fragment);

        if (navHostFragment != null) {
            androidx.navigation.NavController navController = navHostFragment.getNavController();
            com.google.android.material.bottomnavigation.BottomNavigationView bottomNav =
                    view.findViewById(R.id.child_bottom_navigation);

            bottomNav.setItemIconTintList(null);
            androidx.navigation.ui.NavigationUI.setupWithNavController(bottomNav, navController);

            bottomNav.setOnItemSelectedListener(item -> {

                boolean currentlyLinked = requireActivity().getSharedPreferences("AppPrefs", android.content.Context.MODE_PRIVATE)
                        .getBoolean("isLinked", false);

                if (item.getItemId() == R.id.generateCodeFragment) {
                    if (currentlyLinked) {

                        if (navController.getCurrentDestination() != null &&
                                navController.getCurrentDestination().getId() != R.id.sendSugarChildFragment) {

                            androidx.navigation.NavOptions options = new androidx.navigation.NavOptions.Builder()
                                    .setLaunchSingleTop(true)
                                    .setRestoreState(true)
                                    .setPopUpTo(navController.getGraph().getStartDestinationId(), false, true)
                                    .build();

                            navController.navigate(R.id.sendSugarChildFragment, null, options);
                        }
                        return true;
                    }
                }

                return androidx.navigation.ui.NavigationUI.onNavDestinationSelected(item, navController);
            });
        }
    }
}
