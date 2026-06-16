package com.example.glucokidfr.ui.loginChild;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Child;

public class LoginChildViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Child> loginResult = new MutableLiveData<>();

    private final UserRepositoryImpl repository;

    public LoginChildViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Child> getLoginResult() {
        return loginResult;
    }

    public void login(String phone, String password) {
        isLoading.setValue(true);

        repository.loginChild(phone, password, status -> {
            isLoading.postValue(false);

            if (status.getErrors() != null) {
                error.postValue("Ошибка: " + status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                loginResult.postValue(status.getValue());
            } else {
                error.postValue("Неверный телефон или пароль");
            }
        });
    }
}