package com.example.glucokidfr.domain;

import android.media.RouteListingPreference;

import com.example.glucokidfr.domain.entities.Child;
import com.example.glucokidfr.domain.entities.Parent;
import com.example.glucokidfr.domain.entities.Status;
import com.example.glucokidfr.domain.entities.SugarLevel;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import androidx.core.util.Consumer;

public interface UserRepository {
    void getParent(@NotNull Long id, Consumer<Status<Parent>> callback);
    void registerParent(@NotNull Parent parent, Consumer<Status<Parent>> callback);
    void getChildren(@NotNull String parentId, Consumer<Status<List<Child>>> callback);
    void addChild(@NotNull Child child, Consumer<Status<Child>> callback);
    void deleteChild(@NotNull String childId, Consumer<Status<Void>> callback);
    void getSugarHistory(@NotNull String childId, Consumer<Status<List<SugarLevel>>> callback);
    void addSugar(@NotNull SugarLevel sugar, Consumer<Status<SugarLevel>> callback);
    void updateSugar(@NotNull String sugarId, @NotNull SugarLevel sugar, Consumer<Status<SugarLevel>> callback);
    void deleteSugar(@NotNull String sugarId, Consumer<Status<Void>> callback);
    void registerChild(@NotNull Child child, Consumer<Status<Child>> callback);
    void loginChild(@NotNull String phone, @NotNull String password, Consumer<Status<Child>> callback);
    void loginParent(@NotNull String phone, @NotNull String password, Consumer<Status<Parent>> callback);

}
