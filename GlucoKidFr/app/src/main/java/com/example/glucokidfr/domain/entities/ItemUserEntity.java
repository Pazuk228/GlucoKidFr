package com.example.glucokidfr.domain.entities;

import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class ItemUserEntity {
    @NonNull
    private final String name;
    @NonNull
    private final Long id;

    public ItemUserEntity(@NonNull String name, @NonNull Long id) {
        this.name = name;
        this.id = id;
    }
    public @NonNull String getName() {
        return name;
    }

    public @NonNull Long getId() {
        return id;
    }

}
