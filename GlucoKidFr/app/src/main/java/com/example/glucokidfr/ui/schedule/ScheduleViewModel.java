package com.example.glucokidfr.ui.schedule;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.MealGroup;
import com.example.glucokidfr.domain.entities.SugarLevel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<List<MealGroup>> scheduleData = new MutableLiveData<>();
    private final List<SugarLevel> allRawHistory = new ArrayList<>();

    private final UserRepositoryImpl repository;

    public ScheduleViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<List<MealGroup>> getScheduleData() { return scheduleData; }

    public void loadSugarHistory(String childId, String currentDateYMD) {
        isLoading.setValue(true);
        error.setValue(null);

        repository.getSugarHistory(childId, status -> {
            isLoading.postValue(false);

            if (status.getErrors() != null) {
                error.postValue(status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                allRawHistory.clear();
                allRawHistory.addAll(status.getValue());

                filterByDate(currentDateYMD);
            }
        });
    }

    public void loadHistoryForParent(Long parentId, String currentDateYMD) {
        isLoading.setValue(true);
        error.setValue(null);

        repository.getChildren(String.valueOf(parentId), status -> {
            if (status.getErrors() != null) {
                isLoading.postValue(false);
                error.postValue("Ошибка загрузки детей: " + status.getErrors().getMessage());
            } else if (status.getValue() != null && !status.getValue().isEmpty()) {
                Long firstChildId = status.getValue().get(0).getId();
                loadSugarHistory(String.valueOf(firstChildId), currentDateYMD);
            } else {
                isLoading.postValue(false);
                error.postValue("У вас пока нет привязанных детей");
            }
        });
    }

    public void filterByDate(String dateYMD) {
        Map<String, List<SugarLevel>> map = new HashMap<>();

        for (SugarLevel sugar : allRawHistory) {
            if (sugar.getTime() != null && sugar.getTime().startsWith(dateYMD)) {

                String meal = sugar.getExtra();
                if (meal == null || meal.isEmpty()) meal = "Другое";

                if (!map.containsKey(meal)) {
                    map.put(meal, new ArrayList<>());
                }
                map.get(meal).add(sugar);
            }
        }

        List<MealGroup> result = new ArrayList<>();
        for (Map.Entry<String, List<SugarLevel>> entry : map.entrySet()) {
            result.add(new MealGroup(entry.getKey(), entry.getValue()));
        }

        scheduleData.postValue(result);
    }
}