package com.pity.baradopta.ui.olvidaste_contrasenia;

import com.pity.baradopta.ui.base.IBasePresenter;

public interface IOlvidasteContraseniaPresenter extends IBasePresenter<IOlvidasteContraseniaView> {

        void enviarMailDeRecuperacionA(String email);
}
