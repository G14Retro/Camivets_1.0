package com.example.camivets.ui.servicios;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ServiciosViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ServiciosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is servicios fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}