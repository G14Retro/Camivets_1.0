package com.example.camivets.ui.sedes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SedesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public SedesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is sedes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}