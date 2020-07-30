package com.pity.appperros1.ui.olvidaste_contrasenia;

import com.pity.appperros1.ui.base.IBaseView;

public interface IOlvidasteContraseniaView extends IBaseView {

    void navagateToLogin();
    void showError(String messageError);
    void showMessage(String msj);
    void showProgressBar();
    void hideProgressBar();
}
