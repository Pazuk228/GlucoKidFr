package com.example.glucokidfr.domain.usecase;

import com.example.glucokidfr.domain.UserRepository;
import com.example.glucokidfr.domain.entities.Status;
import com.example.glucokidfr.domain.entities.SugarLevel;

import org.jetbrains.annotations.NotNull;

import androidx.core.util.Consumer;

public class AddSugarUseCase {
    private final UserRepository repository;

    public AddSugarUseCase(UserRepository repository) {
        this.repository = repository;
    }

    public void execute(@NotNull SugarLevel sugar, Consumer<Status<SugarLevel>> callback) {
        repository.addSugar(sugar, callback);
    }
}