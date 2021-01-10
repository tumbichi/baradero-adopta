package com.pity.baradopta.ui.olvidaste_contrasenia;

import android.content.Context;
import android.text.TextUtils;
import android.util.Patterns;

import com.pity.baradopta.ui.base.BasePresenter;
import com.pity.baradopta.data.interactor.implementation.OlvidasteContraseniaInteractor;
import com.pity.baradopta.data.interactor.interfaces.IOlvidasteContraseniaInteractor;

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

        view.showProgressBar();

        if (TextUtils.isEmpty(email)){
            view.showError("El email esta vacio");
            view.hideProgressBar();
            return;
        }

        if(!validarEmail(email)){
            view.showError("El email no es valido");
            view.hideProgressBar();
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
            view.showMessage("Por favor revise su email para reestablecer la contraseña");
            view.navagateToLogin();
            view.hideProgressBar();

        }
    }

    @Override
    public void onFailed(Exception e) {
        if (isViewAttached()) {
            view.showError(e.getMessage());
            view.hideProgressBar();
        }
    }
}
