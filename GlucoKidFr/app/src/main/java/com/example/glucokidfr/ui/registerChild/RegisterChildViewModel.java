package com.example.glucokidfr.ui.registerChild;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Child;

public class RegisterChildViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Child> registerResult = new MutableLiveData<>();

    private final UserRepositoryImpl repository;

    public RegisterChildViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Child> getRegisterResult() {
        return registerResult;
    }

    public void register(String firstName, String lastName, String patronymic, String email, String password) {
        isLoading.setValue(true);

        Child child = new Child(null, null, firstName, patronymic, lastName, email, password);

        repository.registerChild(child, status -> {
            isLoading.postValue(false);

            if (status.getErrors() != null) {
                error.postValue("Ошибка: " + status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                registerResult.postValue(status.getValue());
            } else {
                error.postValue("Неизвестная ошибка");
            }
        });
    }
}