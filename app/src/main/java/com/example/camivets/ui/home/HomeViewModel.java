package com.example.camivets.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        // Establece el texto real que deseas mostrar en tu fragmento
        mText.setValue("Clínica veterinaria 24 horas\nClínica especializada en perros y gatos con atención médica 24 horas, los 7 días de la semana en Bogotá.\nContamos con 5 sedes en la ciudad en Castilla, Engativá, Cedritos, Chicó y Chapinero. Realizamos un acompañamiento clínico ante cualquier emergencia o asesoría preventiva para la salud y bienestar de tu mascota.\nSomos reconocidos por haber atendido a más de 34.000 mascotas desde el año 2024. Contamos con personal calificado, equipos de alta tecnología y clínicas con más de 2.193 M2 dedicados a alargar la vida de tu mascota.");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
