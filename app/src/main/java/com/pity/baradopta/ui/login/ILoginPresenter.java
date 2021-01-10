package com.pity.baradopta.ui.login;

import com.pity.baradopta.ui.base.IBasePresenter;

public interface ILoginPresenter extends IBasePresenter<ILoginView> {

    void loginUserWith(String mail, String password);

    void manageLoginWithFacebook();

    void checkUserLogged();
}
