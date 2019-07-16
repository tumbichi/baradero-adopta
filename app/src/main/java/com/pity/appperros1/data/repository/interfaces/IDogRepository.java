package com.pity.appperros1.data.repository.interfaces;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.modelos.PerroModel;

import java.util.ArrayList;

public interface IDogRepository {
    void uploadPhoto(Uri path, CallbackUploadPhoto callbackUploadPhoto);
    void uploadPerro(PerroModel perro, FirebaseUser currentUser, CallbackUploadDog callback);
    void getDogList(CallbackDogList callbackDogList);


    interface CallbackUploadPhoto {
        void onSuccessUploadPhoto(String url, String fecha);
        void onFailureUploadPhoto(String messageError);
    }
    interface CallbackUploadDog {
        void onSuccessUploadDog();
        void onFailureUploadDog(String messasgeError);
    }
    interface CallbackDogList {
        void onSuccesGetDogList(ArrayList<PerroModel> dogList);
        void onFailureGetDogList(String error);
    }

}
