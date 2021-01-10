package com.pity.baradopta.data.interactor.interfaces;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.pity.baradopta.data.modelos.Perro;
import com.pity.baradopta.ui.fragment_agregar_perro.interfaces.IAgregarPerroPresenter;

import java.util.ArrayList;

public interface IInicioInteractor {
    void logoutUser();
    ArrayList<Perro> getListPost();
    Perro getDogById(int position);
    void bringDogList(CallbackGetDogList callbackGetDogList);

    void createCurrentNewDog();
    void deleteCurrentNewDog();

    void uploadDog(String nombre, String descripcion, String genero, String edad, String tamanio, String castrado, String vacunado, ArrayList<Boolean> estados, IAgregarPerroPresenter.CallbackInteractor callbackInteractor);
    void setCurrentPathPhoto(Uri pathPhoto);
    Uri getCurrentPathPhoto();

    FirebaseUser getUserLogged();

    interface CallbackGetDogList {
        void onSuccesGetDogList();
        void onFailureDogList(String error);
    }


}
