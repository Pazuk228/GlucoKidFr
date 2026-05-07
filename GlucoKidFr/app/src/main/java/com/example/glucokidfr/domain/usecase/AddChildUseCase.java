package com.example.glucokidfr.domain.usecase;

import com.example.glucokidfr.domain.UserRepository;
import com.example.glucokidfr.domain.entities.Child;
import com.example.glucokidfr.domain.entities.Status;

import org.jetbrains.annotations.NotNull;

import androidx.core.util.Consumer;

public class AddChildUseCase {
    private final UserRepository repository;

    public AddChildUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(@NotNull Child child, Consumer<Status<Child>> callback) {
        repository.addChild(child, callback);
    }
}
