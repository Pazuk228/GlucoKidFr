package com.example.glucokidfr.ui.ChildGamesFragment;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glucokidfr.R;

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

        RecyclerView rv = view.findViewById(R.id.rvFoodList);
        rv.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 2));

        List<FoodItem> foodList = new ArrayList<>();
        foodList.add(new FoodItem(R.drawable.molochka));
        foodList.add(new FoodItem(R.drawable.tea));
        foodList.add(new FoodItem(R.drawable.grechka));
        foodList.add(new FoodItem(R.drawable.apples));
        foodList.add(new FoodItem(R.drawable.bread));
        foodList.add(new FoodItem(R.drawable.tvorog));
        foodList.add(new FoodItem(R.drawable.cheese));
        foodList.add(new FoodItem(R.drawable.eggs));
        foodList.add(new FoodItem(R.drawable.filee));
        foodList.add(new FoodItem(R.drawable.kabachok));
        foodList.add(new FoodItem(R.drawable.kabachok_black));
        foodList.add(new FoodItem(R.drawable.kapysta_svet));
        foodList.add(new FoodItem(R.drawable.psheno));
        foodList.add(new FoodItem(R.drawable.smorodina));
        foodList.add(new FoodItem(R.drawable.sparsha));
        foodList.add(new FoodItem(R.drawable.tomato));


        rv.setAdapter(new FoodAdapter(foodList));
    }
}
