package com.pity.baradopta.ui.registro;

import android.content.Context;
import android.text.TextUtils;

import com.pity.baradopta.data.SimpleCallback;
import com.pity.baradopta.ui.base.BasePresenter;
import com.pity.baradopta.data.interactor.implementation.RegistroInteractor;
import com.pity.baradopta.utils.UserUtils;

public class RegistroPresenter extends BasePresenter<IRegistroView> implements IRegistroPresenter {

    private RegistroInteractor interactor;

    public RegistroPresenter(Context context) {
        super(context);
        this.interactor = new RegistroInteractor();
    }

    @Override
    public void handleValidationOfRegister(String email, String password1, String password2, String nombre, String apellido, String phoneNumber) {
        if (!validateRegister(email, password1, password2, nombre, apellido, phoneNumber)){
            view.hideProgressBar();
            return;
        }

        interactor.registerUser(email, password1, nombre, apellido, phoneNumber, new SimpleCallback() {
            @Override
            public void onSuccess() {
                if (isViewAttached()) {
                    view.toast("Registro exitoso! Por favor compruebe su email");
                    view.hideProgressBar();
                    view.backToLogin();
                }
            }

            @Override
            public void onFailure(String error) {
                if (isViewAttached()) {
                    view.toast("Error " + error + " ,intente nuevamente");
                    view.hideProgressBar();
                }
            }
        });
    }

    private boolean validateRegister(String email, String password1, String password2, String nombre, String apellido, String phoneNumber){
        if (TextUtils.isEmpty(email)){
            view.toast("Por favor, ingrese un email");
            return false;
        }

        if (!UserUtils.isEmailValid(email)){
            view.toast("El email ingresado no es valido");
            return false;
        }

        if(password1.length() < 6){
            view.toast("La contraseña debe contener minimo 6 caracteres");
            return false;
        }

        if (!password1.equals(password2)){
            view.toast("Las contraseñas no coinciden");
            return false;
        }

        if (TextUtils.isEmpty(nombre)){
            view.toast("Por favor, ingrese un nombre");
            return false;
        }

        if (TextUtils.isEmpty(apellido)){
            view.toast("Por favor, ingrese un apellido");
            return false;
        }

        if (TextUtils.isEmpty(phoneNumber)){
            view.toast("Por favor, ingrese un numero de telefono");
            return false;
        }

        if (phoneNumber.length() != 10){
            view.toast("Ingrese un numero valido, recurde NO incluir el 0 ni la caracterista de nuestro pais(+54)");
            return false;
        }

        return true;
    }

}
