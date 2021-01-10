package com.pity.baradopta.ui.registro;

import com.pity.baradopta.ui.base.IBasePresenter;

public interface IRegistroPresenter extends IBasePresenter<IRegistroView> {
    void handleValidationOfRegister(String email, String password1, String password2, String nombre, String apellido, String phoneNumber);
}
