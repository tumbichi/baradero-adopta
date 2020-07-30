package com.pity.appperros1.ui.login;


import android.app.Activity;

import com.pity.appperros1.ui.base.IBaseView;

public interface ILoginView extends IBaseView {

    void showProgressBar();
    void hideProgressBar();

    void enabledFacebookButton();
    void disableFacebookButton();

    void toast(String message);
    void navigateTo(Class activity);
    void navigateToInicio();

    Activity provideActivity();

}
