package com.example.glucokidfr.ui.childrenList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.glucokidfr.R;
import com.example.glucokidfr.domain.entities.Child;
import com.example.glucokidfr.ui.child.ChildrenAdapter;

public class ChildrenListFragment extends Fragment {
    private ChildrenAdapter adapter;
    private ChildrenListViewModel viewModel;
    private Long parentId;

    public ChildrenListFragment() {
        super(R.layout.fragment_children_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        parentId = prefs.getLong("parentId", -1L);

        viewModel = new ViewModelProvider(this).get(ChildrenListViewModel.class);

        RecyclerView rv = view.findViewById(R.id.rvChildrenList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ChildrenAdapter(child -> showUnlinkDialog(child));
        rv.setAdapter(adapter);

        view.findViewById(R.id.btnSmallAddChild).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_childrenListFragment_to_addChildFragment);
        });

        viewModel.getChildren().observe(getViewLifecycleOwner(), children -> {
            if (children != null) {
                adapter.setChildren(children);
            }
        });

        viewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
            }
        });

        viewModel.getUnlinkSuccess().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                Toast.makeText(getContext(), "Ребенок успешно отвязан", Toast.LENGTH_SHORT).show();
                viewModel.resetUnlinkStatus();
            }
        });

        if (parentId != -1L) {
            viewModel.loadChildren(parentId);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (parentId != null && parentId != -1L && viewModel != null) {
            viewModel.loadChildren(parentId);
        }
    }

    private void showUnlinkDialog(Child child) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Отвязать ребенка")
                .setMessage("Вы уверены, что хотите отвязать аккаунт: " + child.getFirstName() + "?")
                .setPositiveButton("Да, отвязать", (dialog, which) -> {
                    if (parentId != -1L) {
                        viewModel.disconnectChild(parentId, child.getId());
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}