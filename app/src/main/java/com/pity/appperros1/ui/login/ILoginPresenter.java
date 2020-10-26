package com.pity.appperros1.ui.login;

import android.app.Activity;

import com.pity.appperros1.ui.base.IBasePresenter;

public interface ILoginPresenter extends IBasePresenter<ILoginView> {

    void loginUserWith(String mail, String password);

    void manageLoginWithFacebook();

    void checkUserLogged();
}
