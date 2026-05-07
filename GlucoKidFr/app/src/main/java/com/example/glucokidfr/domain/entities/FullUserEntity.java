package com.example.glucokidfr.domain.entities;

import androidx.annotation.Nullable;

import org.jspecify.annotations.NonNull;

public class FullUserEntity {
    @NonNull
    public final Long id;
    @NonNull
    public final String name;

    public FullUserEntity(@NonNull Long id, @NonNull String name) {
        this.id = id;
        this.name = name;
    }

}
