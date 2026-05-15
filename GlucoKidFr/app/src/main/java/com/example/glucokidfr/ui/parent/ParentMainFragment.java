package com.example.glucokidfr.ui.parent;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import com.example.glucokidfr.R;
import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ParentMainFragment extends Fragment {

    public ParentMainFragment() {
        super(R.layout.fragment_parent_main);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager()
                .findFragmentById(R.id.parent_nav_host_fragment);

        if (navHostFragment != null) {
            NavController navController = navHostFragment.getNavController();
            BottomNavigationView bottomNav = view.findViewById(R.id.bottom_navigation);
            bottomNav.setItemIconTintList(null);
            NavigationUI.setupWithNavController(bottomNav, navController);
            checkChildrenAndSetupMenu(bottomNav, navController);
        }
    }

    private void checkChildrenAndSetupMenu(BottomNavigationView bottomNav, NavController navController) {
        Long parentId = requireActivity().getSharedPreferences("AppPrefs", android.content.Context.MODE_PRIVATE)
                .getLong("parentId", -1L);

        new UserRepositoryImpl(RetrofitClient.getInstance().getApiService())
                .getChildren(String.valueOf(parentId), status -> {
                    if (status.getValue() != null && !status.getValue().isEmpty()) {
                        MenuItem addChildItem = bottomNav.getMenu().findItem(R.id.addChildFragment);
                        addChildItem.setIcon(R.drawable.cat_orange);

                        bottomNav.setOnItemSelectedListener(item -> {
                            if (item.getItemId() == R.id.addChildFragment) {
                                navController.navigate(R.id.childrenListFragment);
                                return true;
                            }
                            return NavigationUI.onNavDestinationSelected(item, navController);
                        });
                    }
                });
    }
}