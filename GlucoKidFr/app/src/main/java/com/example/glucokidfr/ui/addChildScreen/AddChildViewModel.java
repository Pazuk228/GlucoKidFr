package com.example.glucokidfr.ui.addChildScreen;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Child;

public class AddChildViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Child> addChildResult = new MutableLiveData<>();

    private final UserRepositoryImpl repository;

    public AddChildViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<Child> getAddChildResult() { return addChildResult; }

    public void addChild(String phone) {
        isLoading.setValue(true);
        error.setValue(null);

        Child child = new Child(null, null, null, null, null, phone, null);

        repository.addChild(child, status -> {
            isLoading.postValue(false);

            if (status.getErrors() != null) {
                error.postValue("Ошибка: " + status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                addChildResult.postValue(status.getValue());
            } else {
                error.postValue("Не удалось добавить ребенка (код " + status.getStatusCode() + ")");
            }
        });
    }
}