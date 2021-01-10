package com.pity.baradopta.ui.login;


import android.app.Activity;

import com.pity.baradopta.ui.base.IBaseView;

public interface ILoginView extends IBaseView {

    void showSplashScreen();

    void hideSplashScreen();

    void showProgressBar();

    void hideProgressBar();

    void enabledFacebookButton();

    void disableFacebookButton();

    void toast(String message);

    void navigateTo(Class activity);

    void navigateToInicio();

    Activity provideActivity();
}
