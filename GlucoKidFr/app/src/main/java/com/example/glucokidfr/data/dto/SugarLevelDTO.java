package com.example.glucokidfr.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class SugarLevelDTO {
    @Nullable
    @SerializedName("id")
    public Long id;
    @SerializedName("childId") //ЕСЛИ ТЫ ЕЩЕ РАЗ ПОМЕНЯЕШЬ ИМЯ В БАЗЕ, ДНИЩЕ ПРОРВЕТ НЕ ПО-ДЕТСКИ
    @Nullable
    public Long idChild;
    @SerializedName("value")
    @Nullable
    public Double value;
    @SerializedName("time")
    @Nullable
    public String time;
    @SerializedName("extra")
    public String extra;
}
