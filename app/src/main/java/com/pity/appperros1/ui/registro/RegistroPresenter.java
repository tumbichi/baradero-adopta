package com.pity.appperros1.ui.registro;

import android.content.Context;
import android.text.TextUtils;

import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.RegistroInteractor;
import com.pity.appperros1.data.interactor.interfaces.IRegistroInteractor;
import com.pity.appperros1.utils.UserUtils;

public class RegistroPresenter extends BasePresenter<IRegistroView>
        implements IRegistroPresenter, IRegistroInteractor.CallbackRegistro {

    private RegistroInteractor mInteractor;

    public RegistroPresenter(Context context) {
        super(context);
        this.mInteractor = new RegistroInteractor();
    }


    @Override
    public void sendRegistro(String email, String password1, String password2, String nombre, String apellido, String phoneNumber) {
        mView.showProgressBar();
        if (!validarRegistro(email, password1, password2, nombre, apellido, phoneNumber)){
            mView.hideProgressBar();
            return;
        }

        mInteractor.agregarUsuarioToFirebase(email, password1, nombre, apellido, phoneNumber,this);

    }

    private boolean validarRegistro(String email, String password1, String password2, String nombre, String apellido, String phoneNumber){
        if (TextUtils.isEmpty(email)){
            mView.showError("Por favor, ingrese un email");
            return false;
        }

        if (!UserUtils.isEmailValid(email)){
            mView.showError("El email ingresado no es valido");
            return false;
        }

        if(password1.length() < 6){
            mView.showError("La contraseña debe contener minimo 6 caracteres");
            return false;
        }

        if (!password1.equals(password2)){
            mView.showError("Las contraseñas no coinciden");
            return false;
        }

        if (TextUtils.isEmpty(nombre)){
            mView.showError("Por favor, ingrese un nombre");
            return false;
        }

        if (TextUtils.isEmpty(apellido)){
            mView.showError("Por favor, ingrese un apellido");
            return false;
        }

        if (TextUtils.isEmpty(phoneNumber)){
            mView.showError("Por favor, ingrese un numero de telefono");
            return false;
        }

        if (phoneNumber.length() != 10){
            mView.showError("Ingrese un numero valido, recurde NO incluir el 0 ni la caracterista de nuestro pais(+54)");
            return false;
        }

        return true;
    }


    @Override
    public void onSuccessfulRegistro() {
        if (isViewAttached()) {
            mView.showToastMessage("Registro exitoso! Por favor compruebe su email");
            mView.finishRegistro();
            mView.hideProgressBar();
        }
    }

    @Override
    public void onFailedRegistro(Exception e) {
        if (isViewAttached()) {
            mView.showError("Error " + e.getMessage() + " ,intente nuevamente");
            mView.hideProgressBar();
        }
    }
}
