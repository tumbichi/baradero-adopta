package com.pity.appperros1.ui.login;

import com.pity.appperros1.base.IBasePresenter;

public interface ILoginPresenter extends IBasePresenter<ILoginView> {

    void loginUserWith(String mail, String password);
}
