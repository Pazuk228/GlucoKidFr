package com.example.glucokidfr.ui.schedule;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.glucokidfr.R;
import com.example.glucokidfr.domain.entities.MealGroup;
import com.example.glucokidfr.domain.entities.SugarLevel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.ViewHolder> {

    private List<MealGroup> mealGroups = new ArrayList<>();

    public void setMealGroups(List<MealGroup> groups) {
        this.mealGroups = groups;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_schedule, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MealGroup group = mealGroups.get(position);
        holder.tvMealName.setText(group.mealName);

        holder.llContainer.removeAllViews();

        for (SugarLevel sugar : group.measurements) {
            View rowView = LayoutInflater.from(holder.itemView.getContext())
                    .inflate(R.layout.item_row_schedule, holder.llContainer, false);

            TextView tvTime = rowView.findViewById(R.id.tvTime);
            TextView tvSugarValue = rowView.findViewById(R.id.tvSugarValue);
            MaterialCardView cvSugarBg = rowView.findViewById(R.id.cvSugarBg);

            tvTime.setText(sugar.getTime());
            tvSugarValue.setText(String.valueOf(sugar.getValue()));

            if (sugar.getValue() < 3.9) {
                cvSugarBg.setCardBackgroundColor(Color.parseColor("#EAE054")); // Желтый#EAE054
            } else if (sugar.getValue() > 7.0) {
                cvSugarBg.setCardBackgroundColor(Color.parseColor("#FF0000")); // Красный#FF0000
            } else {
                cvSugarBg.setCardBackgroundColor(Color.parseColor("#68B83B")); // Зеленый68B83B
            }

            holder.llContainer.addView(rowView);
        }
    }

    @Override
    public int getItemCount() {
        return mealGroups.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMealName;
        LinearLayout llContainer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMealName = itemView.findViewById(R.id.tvMealName);
            llContainer = itemView.findViewById(R.id.llMeasurementsContainer);
        }
    }
}