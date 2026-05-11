package com.example.glucokidfr.data.utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.core.util.Consumer;

import com.example.glucokidfr.domain.entities.Status;

public class ToConsumer<SOURCE, DEST> implements Callback<SOURCE> {
    private final Consumer<Status<DEST>> callback;
    private final Mapper<SOURCE, DEST> mapper;

    public ToConsumer(Consumer<Status<DEST>> callback, Mapper<SOURCE, DEST> mapper) {
        this.callback = callback;
        this.mapper = mapper;
    }

    public interface Mapper<SOURCE, DEST> {
        DEST map(SOURCE source);
    }

    @Override
    public void onResponse(Call<SOURCE> call, Response<SOURCE> response) {
        DEST dest = null;
        if (response.body() != null) {
            dest = mapper.map(response.body());
        }
        callback.accept(new Status<>(response.code(), dest, null));
    }

    @Override
    public void onFailure(Call<SOURCE> call, Throwable throwable) {
        callback.accept(new Status<>(-1, null, throwable));
    }
}


//1;11;31