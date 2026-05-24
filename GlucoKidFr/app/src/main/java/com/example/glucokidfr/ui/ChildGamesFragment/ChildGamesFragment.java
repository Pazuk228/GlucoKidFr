package com.example.glucokidfr.ui.ChildGamesFragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glucokidfr.R;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class ChildGamesFragment extends Fragment {

    public ChildGamesFragment() {
        super(R.layout.fragment_child_games);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ImageView btnSettings = view.findViewById(R.id.btnSettings);
        btnSettings.setOnClickListener(v -> {
            androidx.navigation.Navigation.findNavController(v)
                    .navigate(R.id.childSettingsFragment);
        });

        List<FoodItem> healthyFoodList = new ArrayList<>();
        healthyFoodList.add(new FoodItem(R.drawable.molochka));
        healthyFoodList.add(new FoodItem(R.drawable.tea));
        healthyFoodList.add(new FoodItem(R.drawable.grechka));
        healthyFoodList.add(new FoodItem(R.drawable.apples));
        healthyFoodList.add(new FoodItem(R.drawable.bread));
        healthyFoodList.add(new FoodItem(R.drawable.tvorog));
        healthyFoodList.add(new FoodItem(R.drawable.cheese));
        healthyFoodList.add(new FoodItem(R.drawable.eggs));
        healthyFoodList.add(new FoodItem(R.drawable.filee));
        healthyFoodList.add(new FoodItem(R.drawable.kabachok));
        healthyFoodList.add(new FoodItem(R.drawable.kabachok_black));
        healthyFoodList.add(new FoodItem(R.drawable.kapysta_svet));
        healthyFoodList.add(new FoodItem(R.drawable.psheno));
        healthyFoodList.add(new FoodItem(R.drawable.smorodina));
        healthyFoodList.add(new FoodItem(R.drawable.sparsha));
        healthyFoodList.add(new FoodItem(R.drawable.tomato));

        List<FoodItem> junkFoodList = new ArrayList<>();
        junkFoodList.add(new FoodItem(R.drawable.img));


        RecyclerView rv = view.findViewById(R.id.rvFoodList);
        rv.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 2));

        FoodAdapter adapter = new FoodAdapter(new ArrayList<>());
        rv.setAdapter(adapter);


        MaterialButton btnHealthy = view.findViewById(R.id.btnHealthy);
        MaterialButton btnJunk = view.findViewById(R.id.btnJunk);

        btnHealthy.setOnClickListener(v -> {
            adapter.setItems(healthyFoodList);
            btnHealthy.setStrokeWidth(4);
            btnJunk.setStrokeWidth(0);
        });

        btnJunk.setOnClickListener(v -> {
            adapter.setItems(junkFoodList);
            btnJunk.setStrokeWidth(4);
            btnHealthy.setStrokeWidth(0);
        });
    }
}