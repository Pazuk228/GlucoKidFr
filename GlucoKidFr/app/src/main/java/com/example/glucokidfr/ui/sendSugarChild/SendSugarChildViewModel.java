package com.example.glucokidfr.ui.sendSugarChild;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.SugarLevel;

public class SendSugarChildViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> statusMessage = new MutableLiveData<>();

    private final UserRepositoryImpl repository;

    public SendSugarChildViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public LiveData<String> getStatusMessage() {
        return statusMessage;
    }

    public void addSugarLevel(String childId, double value, String time) {
        isLoading.setValue(true);

        SugarLevel sugarLevel = new SugarLevel(null, childId, value, time, "Замер");

        repository.addSugar(sugarLevel, status -> {
            isLoading.postValue(false);

            if (status.getErrors() != null) {
                statusMessage.postValue("Ошибка: " + status.getErrors().getMessage());
            } else {
                statusMessage.postValue("Успешно отправлено!");
            }
        });
    }
}