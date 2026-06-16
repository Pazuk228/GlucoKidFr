package com.example.glucokidfr.ui.parentSettings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.ParentDTO;
import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Parent;

public class ParentSettingsViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<Boolean> updateSuccess = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final UserRepositoryImpl repository;
    private final MutableLiveData<Parent> parentData = new MutableLiveData<>();

    public ParentSettingsViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<Boolean> getUpdateSuccess() { return updateSuccess; }
    public LiveData<String> getError() { return error; }
    public LiveData<Parent> getParentData() { return parentData; }
    public void loadParentData(Long parentId) {
        isLoading.setValue(true);
        repository.getParent(parentId, status -> {
            isLoading.postValue(false);
            if (status.getErrors() != null) {
                error.postValue(status.getErrors().getMessage());
            } else if (status.getValue() != null) {
                parentData.postValue(status.getValue());
            }
        });
    }

    public void updateParentData(Long parentId, String firstName, String lastName, String secondName, String phone) {
        isLoading.setValue(true);

        ParentDTO dto = new ParentDTO();
        dto.firstName = firstName.isEmpty() ? null : firstName;
        dto.lastName = lastName.isEmpty() ? null : lastName;
        dto.secondName = secondName.isEmpty() ? null : secondName;
        dto.phone = phone.isEmpty() ? null : phone;

        repository.updateParent(parentId, dto, status -> {
            isLoading.postValue(false);
            if (status.getErrors() != null) {
                error.postValue(status.getErrors().getMessage());
            } else {
                updateSuccess.postValue(true);
            }
        });
    }
}