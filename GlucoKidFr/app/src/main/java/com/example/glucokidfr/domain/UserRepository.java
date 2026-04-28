package com.example.glucokidfr.domain;

import android.media.RouteListingPreference;

import com.example.glucokidfr.domain.entities.FullUserEntity;
import com.example.glucokidfr.domain.entities.ItemUserEntity;
import com.example.glucokidfr.domain.entities.Status;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public interface UserRepository {
    void getAllUsers(Consumer<Status<ItemUserEntity>> callback);
    void getUser(@NotNull String id, Consumer<Status<FullUserEntity>> callback);

}
