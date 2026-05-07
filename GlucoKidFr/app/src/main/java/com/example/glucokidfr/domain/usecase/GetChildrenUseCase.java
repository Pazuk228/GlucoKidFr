package com.example.glucokidfr.domain.usecase;

import com.example.glucokidfr.domain.UserRepository;
import com.example.glucokidfr.domain.entities.Child;
import com.example.glucokidfr.domain.entities.Status;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import androidx.core.util.Consumer;

public class GetChildrenUseCase {
    private final UserRepository repository;

    public GetChildrenUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(@NotNull String parentId, Consumer<Status<List<Child>>> callback) {
        repository.getChildren(parentId, callback);
    }
}