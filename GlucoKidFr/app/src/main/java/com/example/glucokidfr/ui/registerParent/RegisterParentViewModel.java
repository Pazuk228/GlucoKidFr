package com.example.glucokidfr.ui.registerParent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Parent;

public class RegisterParentViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Parent> registerResult = new MutableLiveData<>();

    private final UserRepositoryImpl repository;

    public RegisterParentViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<Parent> getRegisterResult() { return registerResult; }

    public void register(String firstName, String lastName, String patronymic, String phone, String password) {
        isLoading.setValue(true);
        error.setValue(null);

        Parent parent = new Parent(null, firstName, patronymic, lastName, phone, password);

        repository.registerParent(parent, status -> {
            isLoading.postValue(false);
            if (status.getErrors() != null) {
                error.postValue("Ошибка: " + status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                registerResult.postValue(status.getValue());
            } else {
                error.postValue("Ошибка регистрации. Код: " + status.getStatusCode());
            }
        });
    }
}