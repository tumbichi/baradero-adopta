package com.pity.appperros1.ui.login;


import com.pity.appperros1.base.IBaseView;

public interface ILoginView extends IBaseView {

    void showProgressBar();
    void hideProgressBar();

    void enabledFacebookButton();
    void disableFacebookButton();

    void showMessage(String message);
    void navigateTo(Class activity);
    void navigateToInicio();

}
