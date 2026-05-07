package com.example.glucokidfr.domain.usecase;

import com.example.glucokidfr.domain.UserRepository;
import com.example.glucokidfr.domain.entities.Parent;
import com.example.glucokidfr.domain.entities.Status;

import org.jetbrains.annotations.NotNull;

import androidx.core.util.Consumer;

public class GetParentUseCase {
    private final UserRepository repository;

    public GetParentUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(@NotNull Long id, Consumer<Status<Parent>> callback) {
        repository.getParent(id, callback);
    }
}
