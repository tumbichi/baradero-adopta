package com.pity.appperros1.ui.fragment_agregar_perro.interfaces;

import android.content.Context;

import com.pity.appperros1.base.IBasePresenter;

public interface IAgregarPerroPresenter extends IBasePresenter<IAgregarPerroFragment> {

    void onAttach(Context context);
    void onHideFragment();
    void checkPermissionCamera();
    void checkPermissionGallery();


    void checkCurrentFields(String nombre, String descripcion, String genero, String edad,
                            String tamanio, String castrado, String vacunado, int estado);

    interface CallbackInteractor{
        void onSuccessUploadPhoto();
        void onFailureUploadPhoto(String msg);
        void onSuccesUploadDog();
        void onFailureUploadDog(String msg);
    }

}
