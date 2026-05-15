package com.example.glucokidfr.ui.childrenList;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.glucokidfr.R;
import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.ui.child.ChildrenAdapter;

public class ChildrenListFragment extends Fragment {
    private ChildrenAdapter adapter;

    public ChildrenListFragment() {
        super(R.layout.fragment_children_list);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rv = view.findViewById(R.id.rvChildrenList);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ChildrenAdapter();
        rv.setAdapter(adapter);

        view.findViewById(R.id.btnSmallAddChild).setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_childrenListFragment_to_addChildFragment);
        });

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
}