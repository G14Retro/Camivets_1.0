package com.example.camivets.ui.planes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PlanesViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public PlanesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Planes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}