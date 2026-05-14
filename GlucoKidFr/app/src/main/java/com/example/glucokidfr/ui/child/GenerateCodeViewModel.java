package com.example.glucokidfr.ui.child;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;

public class GenerateCodeViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<String> generatedCode = new MutableLiveData<>();

    private final UserRepositoryImpl repository;

    public GenerateCodeViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<String> getGeneratedCode() { return generatedCode; }

    public void generateCode(Long childId) {
        isLoading.setValue(true);
        error.setValue(null);

        repository.generateConnectionCode(childId, status -> {
            isLoading.postValue(false);

            if (status.getErrors() != null) {
                error.postValue("Ошибка: " + status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                generatedCode.postValue(status.getValue());
            } else {
                error.postValue("Не удалось сгенерировать код");
            }
        });
    }
}
