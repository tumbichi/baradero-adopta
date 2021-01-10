package com.pity.baradopta.data.interactor.interfaces;

import com.pity.baradopta.data.SimpleCallback;

public interface IRegistroInteractor {
    void registerUser(String email, String contrasenia1, String nombre, String apellido, String telefono, SimpleCallback callbackRegistro);
}
