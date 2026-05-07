package com.example.glucokidfr.data.dto;

import com.example.glucokidfr.data.network.ApiService;
import com.example.glucokidfr.data.utils.ToConsumer;
import com.example.glucokidfr.domain.UserRepository;
import com.example.glucokidfr.domain.entities.Child;
import com.example.glucokidfr.domain.entities.ItemUserEntity;
import com.example.glucokidfr.domain.entities.Parent;
import com.example.glucokidfr.domain.entities.Status;
import com.example.glucokidfr.domain.entities.SugarLevel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import androidx.core.util.Consumer;

public class UserRepositoryImpl implements UserRepository {
    private final ApiService apiService;
    public UserRepositoryImpl(ApiService apiService) {
        this.apiService = apiService;
    }
    @Override
    public void getAllParent(@NotNull Long id, Consumer<Status<List<ItemUserEntity>>> callback) {
        apiService.getAllParents().enqueue(new ToConsumer<List<ParentDTO>, List<ItemUserEntity>>(
                callback,
                parentDTOList -> {
                    List<ItemUserEntity> result = new ArrayList<>();
                    for (ParentDTO dto : parentDTOList) {
                        String fullName = (dto.firstName != null ? dto.firstName : "") +
                                " " +
                                (dto.lastName != null ? dto.lastName : "");
                        result.add(new ItemUserEntity(
                                fullName.trim(),
                                dto.id != null ? dto.id : 0L
                        ));
                    }
                    return result;
                }
        ));
    }
    @Override
    public void getParent(@NotNull Long id, Consumer<Status<Parent>> callback) {

    }

    @Override
    public void registerParent(@NotNull Parent parent, Consumer<Status<Parent>> callback) {

    }

    @Override
    public void getChildren(@NotNull String parentId, Consumer<Status<List<Child>>> callback) {

    }

    @Override
    public void addChild(@NotNull Child child, Consumer<Status<Child>> callback) {

    }

    @Override
    public void deleteChild(@NotNull String childId, Consumer<Status<Void>> callback) {

    }

    @Override
    public void getSugarHistory(@NotNull String childId, Consumer<Status<List<SugarLevel>>> callback) {

    }

    @Override
    public void addSugar(@NotNull SugarLevel sugar, Consumer<Status<SugarLevel>> callback) {

    }

    @Override
    public void updateSugar(@NotNull String sugarId, @NotNull SugarLevel sugar, Consumer<Status<SugarLevel>> callback) {

    }

    @Override
    public void deleteSugar(@NotNull String sugarId, Consumer<Status<Void>> callback) {

    }

}
