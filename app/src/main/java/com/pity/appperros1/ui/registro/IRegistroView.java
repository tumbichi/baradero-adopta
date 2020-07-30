package com.pity.appperros1.ui.registro;

import com.pity.appperros1.ui.base.IBaseView;

public interface IRegistroView extends IBaseView {

    void showProgressBar();
    void hideProgressBar();
    void showError(String messageError);
    void showToastMessage(String msj);
    void finishRegistro();
}
