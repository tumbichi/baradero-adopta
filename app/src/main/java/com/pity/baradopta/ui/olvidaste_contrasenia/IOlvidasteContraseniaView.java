package com.pity.baradopta.ui.olvidaste_contrasenia;

import com.pity.baradopta.ui.base.IBaseView;

public interface IOlvidasteContraseniaView extends IBaseView {

    void navagateToLogin();
    void showError(String messageError);
    void showMessage(String msj);
    void showProgressBar();
    void hideProgressBar();
}
