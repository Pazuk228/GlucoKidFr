package com.example.glucokidfr.domain.usecase;

import com.example.glucokidfr.domain.UserRepository;
import com.example.glucokidfr.domain.entities.FullUserEntity;
import com.example.glucokidfr.domain.entities.Status;

import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class GetUserByIdUseCase {
    private final UserRepository repository;

    public GetUserByIdUseCase(UserRepository repository) {
        this.repository = repository;
    }
    public void execute(@NotNull String id, Consumer<Status<FullUserEntity>> callback){
        repository.getUser(id, callback);
    }
}
