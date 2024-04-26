package com.example.camivets.ui.veterinarias;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VeterinariasViewModel extends ViewModel {
    private final MutableLiveData<String> mText;

    public VeterinariasViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Veterinarias fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}