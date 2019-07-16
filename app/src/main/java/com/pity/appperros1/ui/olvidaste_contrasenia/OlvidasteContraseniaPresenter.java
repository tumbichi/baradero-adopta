package com.pity.appperros1.ui.olvidaste_contrasenia;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.OlvidasteContraseniaInteractor;
import com.pity.appperros1.data.interactor.interfaces.IOlvidasteContraseniaInteractor;

import java.util.regex.Pattern;

public class OlvidasteContraseniaPresenter extends BasePresenter<IOlvidasteContraseniaView>
        implements IOlvidasteContraseniaPresenter, IOlvidasteContraseniaInteractor.OnFinishListener {

    private OlvidasteContraseniaInteractor mIteractor;

    public OlvidasteContraseniaPresenter(Context context, OlvidasteContraseniaInteractor interactor) {
        super(context);
        this.mIteractor = interactor;
    }

    @Override
    public void enviarMailDeRecuperacionA(String email) {

        mView.showProgressBar();

        if (TextUtils.isEmpty(email)){
            mView.showError("El email esta vacio");
            mView.hideProgressBar();
            return;
        }

        if(!validarEmail(email)){
            mView.showError("El email no es valido");
            mView.hideProgressBar();
            return;
        }

        mIteractor.enviarEmailFromFirebaseTo(email, this);

    }

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    @Override
    public void onSuccess() {
        if (isViewAttached()) {
            mView.showMessage("Por favor revise su email para reestablecer la contraseña");
            mView.navagateToLogin();
            mView.hideProgressBar();

        }
    }

    @Override
    public void onFailed(Exception e) {
        if (isViewAttached()) {
            mView.showError(e.getMessage());
            mView.hideProgressBar();
        }
    }
}
