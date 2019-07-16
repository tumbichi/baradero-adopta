package com.pity.appperros1.data.interactor.interfaces;

public interface IRegistroInteractor {

    interface CallbackRegistro{
        void onSuccessfulRegistro();
        void onFailedRegistro(Exception e);
    }

    void agregarUsuarioToFirebase(String email, String contrasenia1, String nombre, String apellido,String telefono, CallbackRegistro callbackRegistro);

}
