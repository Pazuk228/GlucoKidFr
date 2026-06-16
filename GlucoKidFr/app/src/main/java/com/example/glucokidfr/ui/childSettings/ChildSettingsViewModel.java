package com.example.glucokidfr.ui.childSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Child;

public class ChildSettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Child> childData = new MutableLiveData<>();
    private final UserRepositoryImpl repository;

    public ChildSettingsViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<Boolean> getUpdateSuccess() { return updateSuccess; }
    public LiveData<String> getError() { return error; }
    public LiveData<Child> getChildData() { return childData; }

    public void loadChildData(Long childId) {
        isLoading.setValue(true);
        repository.getChild(childId, status -> {
            isLoading.postValue(false);
            if (status.getErrors() != null) {
                error.postValue("Ошибка загрузки: " + status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                childData.postValue(status.getValue());
            }
        });
    }

    public void updateChildData(Long childId, String newName) {
        isLoading.setValue(true);
        error.setValue(null);
        updateSuccess.setValue(false);

        if (newName == null || newName.trim().isEmpty()) {
            isLoading.setValue(false);
            error.setValue("Имя не может быть пустым");
            return;
        }

        repository.updateChildName(childId, newName, status -> {
            isLoading.postValue(false);
            if (status.getErrors() != null) {
                error.postValue("Ошибка: " + status.getErrors().getMessage());
            } else {
                updateSuccess.postValue(true);
            }
        });
    }
}