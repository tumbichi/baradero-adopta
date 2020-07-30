package com.pity.appperros1.ui.registro;

import com.pity.appperros1.ui.base.IBasePresenter;

public interface IRegistroPresenter extends IBasePresenter<IRegistroView> {
    void sendRegistro(String email, String password1, String password2, String nombre, String apellido,String phoneNumber);
}
