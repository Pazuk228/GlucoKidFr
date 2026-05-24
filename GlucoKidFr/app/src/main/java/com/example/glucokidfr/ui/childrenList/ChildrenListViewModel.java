package com.example.glucokidfr.ui.childrenList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.glucokidfr.data.dto.UserRepositoryImpl;
import com.example.glucokidfr.data.network.RetrofitClient;
import com.example.glucokidfr.domain.entities.Child;

import java.util.List;

public class ChildrenListViewModel extends ViewModel {

    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<List<Child>> children = new MutableLiveData<>();
    private final MutableLiveData<Boolean> unlinkSuccess = new MutableLiveData<>();

    private final UserRepositoryImpl repository;

    public ChildrenListViewModel() {
        repository = new UserRepositoryImpl(RetrofitClient.getInstance().getApiService());
    }

    public LiveData<Boolean> getIsLoading() { return isLoading; }
    public LiveData<String> getError() { return error; }
    public LiveData<List<Child>> getChildren() { return children; }
    public LiveData<Boolean> getUnlinkSuccess() { return unlinkSuccess; }

    public void loadChildren(Long parentId) {
        isLoading.setValue(true);
        repository.getChildren(String.valueOf(parentId), status -> {
            isLoading.postValue(false);
            if (status.getErrors() != null) {
                error.postValue(status.getErrors().getMessage());
            } else {
                children.postValue(status.getValue());
            }
        });
    }

    public void disconnectChild(Long parentId, Long childId) {
        isLoading.setValue(true);
        repository.disconnectChild(parentId, childId, status -> {
            isLoading.postValue(false);
            if (status.getErrors() != null) {
                error.postValue(status.getErrors().getMessage());
            } else {
                unlinkSuccess.postValue(true);
                loadChildren(parentId);
            }
        });
    }
    public void resetUnlinkStatus() {
        unlinkSuccess.setValue(null);
    }
}