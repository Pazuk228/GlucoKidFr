package com.example.glucokidfr.ui.childSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;

public class ChildSettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>(false);

    private final UserRepositoryImpl repository;

    public ChildSettingsViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<Boolean> getUpdateSuccess() { return updateSuccess; }

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
                android.util.Log.e("MY_DEBUG", "Ошибка сервера: " + status.getErrors().getMessage());
                error.postValue("Ошибка: " + status.getErrors().getMessage());
            } else {
                android.util.Log.d("MY_DEBUG", "Сервер ответил ОК, устанавливаю успех");
                updateSuccess.postValue(true);
            }
        });
    }
}