package com.example.glucokidfr.ui.parent;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
public class SharedParentViewModel extends ViewModel {

    private final MutableLiveData<Long> selectedChildId = new MutableLiveData<>(-1L);
    public void selectChild(Long childId) {
        selectedChildId.setValue(childId);
    }
    public LiveData<Long> getSelectedChildId() {
        return selectedChildId;
    }
}