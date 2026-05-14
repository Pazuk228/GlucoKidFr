package com.example.glucokidfr.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class SugarLevelDTO {
    @Nullable
    @SerializedName("id")
    public Long id;
    @SerializedName("idChild")
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
