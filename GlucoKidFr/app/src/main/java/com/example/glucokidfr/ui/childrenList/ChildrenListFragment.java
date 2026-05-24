package com.example.glucokidfr.ui.childrenList;

import android.app.AlertDialog;
import android.content.Context;
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
import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Child;
import com.example.glucokidfr.ui.child.ChildrenAdapter;
import com.example.glucokidfr.ui.parent.SharedParentViewModel;

public class ChildrenListFragment extends Fragment {
    private ChildrenAdapter adapter;
    private SharedParentViewModel sharedViewModel;

    public ChildrenListFragment() {
        super(R.layout.fragment_children_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedParentViewModel.class);

        RecyclerView rv = view.findViewById(R.id.rvChildrenList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new ChildrenAdapter(new ChildrenAdapter.OnChildInteractionListener() {
            @Override
            public void onUnlinkClick(Child child) {
                showUnlinkDialog(child);
            }

            @Override
            public void onChildClick(Child child) {
                sharedViewModel.selectChild(child.getId());
                Toast.makeText(getContext(), child.getFirstName() + " выбран", Toast.LENGTH_SHORT).show();
            }
        });

        rv.setAdapter(adapter);

        view.findViewById(R.id.btnSmallAddChild).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_childrenListFragment_to_addChildFragment);
        });

        loadChildren();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadChildren();
    }

    private void loadChildren() {
        Long parentId = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
                .getLong("parentId", -1L);

        if (parentId != -1L) {
            new UserRepositoryImpl(RetrofitClient.getInstance().getApiService())
                    .getChildren(String.valueOf(parentId), status -> {
                        if (status.getValue() != null) {
                            adapter.setChildren(status.getValue());
                        }
                    });
        }
    }

    private void showUnlinkDialog(Child child) {
        new AlertDialog.Builder(requireContext())
                .setTitle("Отвязать ребенка")
                .setMessage("Вы уверены, что хотите отвязать аккаунт: " + child.getFirstName() + "?")
                .setPositiveButton("Да, отвязать", (dialog, which) -> {
                    Long parentId = requireActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE).getLong("parentId", -1L);
                    if (parentId != -1L) {
                        new UserRepositoryImpl(RetrofitClient.getInstance().getApiService())
                                .disconnectChild(parentId, child.getId(), status -> {
                                    if (status.getErrors() == null) {
                                        Toast.makeText(getContext(), "Ребенок отвязан", Toast.LENGTH_SHORT).show();
                                        loadChildren();
                                    }
                                });
                    }
                })
                .setNegativeButton("Отмена", null)
                .show();
    }
}