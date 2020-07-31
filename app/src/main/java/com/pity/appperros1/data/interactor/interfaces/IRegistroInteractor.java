package com.pity.appperros1.data.interactor.interfaces;

import com.pity.appperros1.data.SimpleCallback;

public interface IRegistroInteractor {
    void registerUser(String email, String contrasenia1, String nombre, String apellido, String telefono, SimpleCallback callbackRegistro);
}
