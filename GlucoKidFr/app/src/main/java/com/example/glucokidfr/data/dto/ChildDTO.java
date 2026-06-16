package com.example.glucokidfr.data.dto;

import androidx.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

public class ChildDTO {
    @Nullable
    @SerializedName("id")
    public Long id;
    @SerializedName("firstName")
    @Nullable
    public String firstName;
    @SerializedName("secondName")
    @Nullable
    public String secondName;
    @SerializedName("lastName")
    @Nullable
    public String lastName;
    @SerializedName("phoneNumber")
    @Nullable
    public String phone;
    @SerializedName("password")
    @Nullable
    public String password;
}
