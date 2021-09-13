package com.sosa.juegopelotita;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PelotitaViewModel extends ViewModel {
    private MutableLiveData<Coordenada> cordenada;

    public PelotitaViewModel(){
        cordenada = new MutableLiveData<>();


    }
    public LiveData<Coordenada> getCordenada() {
        return cordenada;
    }

    public void cordenadas(float X , float Y){
        cordenada.setValue(new Coordenada(X,Y));

    }
}
