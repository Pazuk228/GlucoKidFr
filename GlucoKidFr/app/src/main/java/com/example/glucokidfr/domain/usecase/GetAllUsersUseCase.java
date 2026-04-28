package com.example.glucokidfr.domain.usecase;

import com.example.glucokidfr.domain.UserRepository;
import com.example.glucokidfr.domain.entities.ItemUserEntity;
import com.example.glucokidfr.domain.entities.Status;

import java.util.function.Consumer;

public class GetAllUsersUseCase {
    private final UserRepository repository;

    public GetAllUsersUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(Consumer<Status<ItemUserEntity>> callback) {
        repository.getAllUsers(callback);
    }
}