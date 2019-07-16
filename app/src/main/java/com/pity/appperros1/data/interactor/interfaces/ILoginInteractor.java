package com.pity.appperros1.data.interactor.interfaces;

public interface ILoginInteractor {

    interface OnLoginFinishedListener {
        void onSuccess();
        void onFailed(Exception e);

    }

    void login(String user, String pass, OnLoginFinishedListener listener);
    boolean isUserLogged();
}
