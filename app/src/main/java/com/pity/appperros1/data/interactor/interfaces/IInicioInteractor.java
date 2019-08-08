package com.pity.appperros1.data.interactor.interfaces;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.modelos.PerroModel;
import com.pity.appperros1.ui.fragment_agregar_perro.interfaces.IAgregarPerroPresenter;

import java.util.ArrayList;

public interface IInicioInteractor {
    void logout();
    ArrayList<PerroModel> getListPost();
    PerroModel getDogById(int position);
    void bringDogList(CallbackGetDogList callbackGetDogList);

    void createCurrentNewDog();
    void deleteCurrentNewDog();

    void startUploadNewDog(String nombre, String descripcion, String genero, String edad, String tamanio, String castrado, String vacunado, ArrayList<Boolean> estados, IAgregarPerroPresenter.CallbackInteractor callbackInteractor);
    void setCurrentPathPhoto(Uri pathPhoto);
    Uri getCurrentPathPhoto();

    FirebaseUser getUserLogged();

    interface CallbackGetDogList {
        void onSuccesGetDogList();
        void onFailureDogList(String error);
    }


}
