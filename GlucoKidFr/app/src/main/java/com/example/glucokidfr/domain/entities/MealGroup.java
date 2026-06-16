package com.example.glucokidfr.domain.entities;

import java.util.List;

public class MealGroup {
    public String mealName;
    public List<SugarLevel> measurements;

    public MealGroup(String mealName, List<SugarLevel> measurements) {
        this.mealName = mealName;
        this.measurements = measurements;
    }
}