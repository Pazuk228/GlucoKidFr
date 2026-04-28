package com.example.glucokidfr.domain.entities;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class ItemUserEntity {
    @NonNull
    private final String name;
    @NonNull
    private final String id;

    public ItemUserEntity(@NonNull String name, @NonNull String id) {
        this.name = name;
        this.id = id;
    }
    public @NonNull String getName() {
        return name;
    }

    public @NonNull String getId() {
        return id;
    }

}
