package com.example.glucokidfr.data.dto;

import com.example.glucokidfr.data.network.ApiService;
import com.example.glucokidfr.data.utils.ToConsumer;
import com.example.glucokidfr.domain.UserRepository;
import com.example.glucokidfr.domain.entities.Child;
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
    public void getParent(@NotNull Long id, Consumer<Status<Parent>> callback) {
        apiService.getParent(id).enqueue(new ToConsumer<>(
                callback,
                dto -> new Parent(
                        dto.id,
                        dto.firstName,
                        dto.secondName,
                        dto.lastName,
                        dto.phone,
                        dto.password
                )
        ));
    }

    @Override
    public void loginParent(@NotNull String phone, @NotNull String password, Consumer<Status<Parent>> callback) {
        ParentDTO dto = new ParentDTO();
        dto.phone = phone;
        dto.password = password;

        apiService.loginParent(dto).enqueue(new ToConsumer<>(
                callback,
                responseDto -> new Parent(
                        responseDto.id,
                        responseDto.firstName,
                        responseDto.secondName,
                        responseDto.lastName,
                        responseDto.phone,
                        responseDto.password
                )
        ));
    }

    @Override
    public void registerParent(@NotNull Parent parent, Consumer<Status<Parent>> callback) {
        ParentDTO dto = new ParentDTO();
        dto.firstName = parent.getFirstName();
        dto.secondName = parent.getSecondName();
        dto.lastName = parent.getLastName();
        dto.phone = parent.getPhone();
        dto.password = parent.getPassword();

        apiService.registerParent(dto).enqueue(new ToConsumer<>(
                callback,
                responseDto -> new Parent(
                        responseDto.id,
                        responseDto.firstName,
                        responseDto.secondName,
                        responseDto.lastName,
                        responseDto.phone,
                        responseDto.password
                )
        ));
    }

    @Override
    public void getChildren(@NotNull String parentId, Consumer<Status<List<Child>>> callback) {
        apiService.getChildren(Long.parseLong(parentId)).enqueue(new ToConsumer<>(
                callback,
                dtoList -> {
                    List<Child> result = new ArrayList<>();
                    for (ChildDTO dto : dtoList) {
                        result.add(new Child(
                                dto.id,
                                null,
                                dto.firstName,
                                dto.secondName,
                                dto.lastName,
                                dto.phone,
                                dto.password
                        ));
                    }
                    return result;
                }
        ));
    }

    @Override
    public void addChild(@NotNull Child child, Consumer<Status<Child>> callback) {
        ChildDTO dto = new ChildDTO();
        dto.firstName = child.getFirstName();
        dto.secondName = child.getSecondName();
        dto.lastName = child.getLastName();
        dto.phone = child.getPhone();
        dto.password = child.getPassword();

        apiService.addChild(dto).enqueue(new ToConsumer<>(
                callback,
                responseDto -> new Child(
                        responseDto.id,
                        null,
                        responseDto.firstName,
                        responseDto.secondName,
                        responseDto.lastName,
                        responseDto.phone,
                        responseDto.password
                )
        ));
    }

    @Override
    public void deleteChild(@NotNull String childId, Consumer<Status<Void>> callback) {
    }

    @Override
    public void getSugarHistory(@NotNull String childId, Consumer<Status<List<SugarLevel>>> callback) {
        apiService.getSugarHistory(Long.parseLong(childId)).enqueue(new ToConsumer<>(
                callback,
                dtoList -> {
                    List<SugarLevel> result = new ArrayList<>();
                    for (SugarLevelDTO dto : dtoList) {
                        SugarLevel sugar = new SugarLevel(
                                null,
                                String.valueOf(dto.idChild),
                                dto.value != null ? dto.value : 0.0,
                                dto.time,
                                dto.extra
                        );
                        result.add(sugar);
                    }
                    return result;
                }
        ));
    }

    @Override
    public void addSugar(@NotNull SugarLevel sugar, Consumer<Status<SugarLevel>> callback) {
        SugarLevelDTO dto = new SugarLevelDTO();
        dto.idChild = Long.parseLong(sugar.getChildId());
        dto.value = sugar.getValue();
        dto.time = sugar.getTime();
        dto.extra = sugar.getExtra();

        apiService.addSugar(dto).enqueue(new ToConsumer<>(
                callback,
                responseDto -> new SugarLevel(
                        responseDto != null ? responseDto.id : null,
                        String.valueOf(dto.idChild),
                        dto.value,
                        dto.time,
                        dto.extra
                )
        ));
    }

    @Override
    public void updateSugar(@NotNull String sugarId, @NotNull SugarLevel sugar, Consumer<Status<SugarLevel>> callback) {
    }

    @Override
    public void deleteSugar(@NotNull String sugarId, Consumer<Status<Void>> callback) {
    }

    @Override
    public void registerChild(@NotNull Child child, Consumer<Status<Child>> callback) {
        ChildDTO dto = new ChildDTO();
        dto.firstName = child.getFirstName();
        dto.secondName = child.getSecondName();
        dto.lastName = child.getLastName();
        dto.phone = child.getPhone();
        dto.password = child.getPassword();

        apiService.registerChild(dto).enqueue(new ToConsumer<>(
                callback,
                responseDto -> new Child(
                        responseDto.id,
                        null,
                        responseDto.firstName,
                        responseDto.secondName,
                        responseDto.lastName,
                        responseDto.phone,
                        responseDto.password
                )
        ));
    }

    @Override
    public void loginChild(@NotNull String phone, @NotNull String password, Consumer<Status<Child>> callback) {
        ChildDTO dto = new ChildDTO();
        dto.phone = phone;
        dto.password = password;

        apiService.loginChild(dto).enqueue(new ToConsumer<>(
                callback,
                responseDto -> new Child(
                        responseDto.id,
                        null,
                        responseDto.firstName,
                        responseDto.secondName,
                        responseDto.lastName,
                        responseDto.phone,
                        responseDto.password
                )
        ));
    }

    @Override
    public void generateConnectionCode(@NotNull Long childId, Consumer<Status<String>> callback) {
        apiService.generateConnectionCode(childId).enqueue(new ToConsumer<>(
                callback,
                code -> code
        ));
    }

    @Override
    public void linkChildByCode(@NotNull String code, @NotNull Long parentId, Consumer<Status<Child>> callback) {
        apiService.linkChildByCode(parentId, code).enqueue(new ToConsumer<>(
                callback,
                responseDto -> new Child(
                        responseDto.id,
                        null,
                        responseDto.firstName,
                        responseDto.secondName,
                        responseDto.lastName,
                        responseDto.phone,
                        responseDto.password
                )
        ));
    }
    @Override
    public void updateChildName(@NotNull Long childId, @NotNull String newName, Consumer<Status<Child>> callback) {
        ChildDTO dto = new ChildDTO();
        dto.firstName = newName;

        apiService.updateChild(childId, dto).enqueue(new ToConsumer<ChildDTO, Child>(
                callback,
                (ChildDTO responseDto) -> new Child(
                        responseDto.id,
                        null,
                        responseDto.firstName,
                        responseDto.secondName,
                        responseDto.lastName,
                        responseDto.phone,
                        responseDto.password
                )
        ));
    }
    @Override
    public void updateParent(@NotNull Long parentId, @NotNull ParentDTO dto, Consumer<Status<Parent>> callback) {
        apiService.updateParent(parentId, dto).enqueue(new ToConsumer<>(
                callback,
                responseDto -> new Parent(
                        responseDto.id,
                        responseDto.firstName,
                        responseDto.secondName,
                        responseDto.lastName,
                        responseDto.phone,
                        responseDto.password
                )
        ));
    }
    @Override
    public void getChild(@NotNull Long id, Consumer<Status<Child>> callback) {
        apiService.getChild(id).enqueue(new ToConsumer<>(callback, dto ->
                new Child(dto.id,
                        null,
                        dto.firstName,
                        dto.secondName,
                        dto.lastName,
                        dto.phone,
                        dto.password
                )
        ));
    }
    public void disconnectChild(Long parentId, Long childId, Consumer<Status<Void>> callback) {
        apiService.disconnectChild(parentId, childId).enqueue(
                new ToConsumer<>(callback, response -> null)
        );
    }
}
