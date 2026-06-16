package com.example.glucokidfr.ui.homeParent;

import android.os.Bundle;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.example.glucokidfr.R;
import com.example.glucokidfr.ui.ChildGamesFragment.FoodAdapter;
import com.example.glucokidfr.ui.ChildGamesFragment.FoodItem;
import com.google.android.material.button.MaterialButton;
import java.util.ArrayList;
import java.util.List;

public class HomeParentFragment extends Fragment {

    public HomeParentFragment() {
        super(R.layout.fragment_child_games);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View btnSettings = view.findViewById(R.id.btnSettings);
        if (btnSettings != null) {
            btnSettings.setOnClickListener(v -> {
                androidx.navigation.Navigation.findNavController(v)
                        .navigate(R.id.parentSettingsFragment);
            });
        }
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
        junkFoodList.add(new FoodItem(R.drawable.img_1));
        junkFoodList.add(new FoodItem(R.drawable.img_2));
        junkFoodList.add(new FoodItem(R.drawable.img_3));
        junkFoodList.add(new FoodItem(R.drawable.img_4));
        junkFoodList.add(new FoodItem(R.drawable.img_5));
        junkFoodList.add(new FoodItem(R.drawable.img_6));
        junkFoodList.add(new FoodItem(R.drawable.img_7));
        junkFoodList.add(new FoodItem(R.drawable.img_8));
        junkFoodList.add(new FoodItem(R.drawable.img_9));
        junkFoodList.add(new FoodItem(R.drawable.img_10));
        junkFoodList.add(new FoodItem(R.drawable.img_11));


        RecyclerView rv = view.findViewById(R.id.rvFoodList);
        if (rv != null) {
            rv.setLayoutManager(new androidx.recyclerview.widget.GridLayoutManager(getContext(), 2));
            FoodAdapter adapter = new FoodAdapter(new ArrayList<>());
            rv.setAdapter(adapter);

            MaterialButton btnHealthy = view.findViewById(R.id.btnHealthy);
            MaterialButton btnJunk = view.findViewById(R.id.btnJunk);

            if (btnHealthy != null && btnJunk != null) {
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
    }
}