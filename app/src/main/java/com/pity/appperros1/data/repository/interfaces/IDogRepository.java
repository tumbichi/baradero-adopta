package com.pity.appperros1.data.repository.interfaces;

import android.net.Uri;

import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.data.modelos.Usuario;

import java.util.ArrayList;

public interface IDogRepository {
    void uploadPhoto(Uri path, CallbackUploadPhoto callbackUploadPhoto);
    void uploadPerro(Perro perro, Usuario currentUser, CallbackUploadDog callback);
    void getDogListP(CallbackDogList callbackDogList);
    void getDogList(CallbackDogList callbackDogList);
    void queryDogBy(String id, CallbackQueryDog callbackQueryDog);
    void updateDog(Perro perro, CallbackUploadDog callbackUpdateDog);


    interface CallbackUploadPhoto {
        void onSuccessUploadPhoto(String url, String fecha);
        void onFailureUploadPhoto(String messageError);
    }
    interface CallbackUploadDog {
        void onSuccessUploadDog();
        void onFailureUploadDog(String messasgeError);
    }
    interface CallbackDogList {
        void onSuccesGetDogList(ArrayList<Perro> dogList);
        void onFailureGetDogList(String error);
    }
    interface CallbackQueryDog {
        void onSucessQueryDog(Perro currentDog);
        void onFailureQueryDog(String msgError);
    }

}
