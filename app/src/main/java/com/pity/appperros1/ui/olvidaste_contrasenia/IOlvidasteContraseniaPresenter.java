package com.pity.appperros1.ui.olvidaste_contrasenia;

import com.pity.appperros1.base.IBasePresenter;

public interface IOlvidasteContraseniaPresenter extends IBasePresenter<IOlvidasteContraseniaView> {

        void enviarMailDeRecuperacionA(String email);
}
