package com.pity.appperros1.data.interactor.interfaces;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

public interface ILoginInteractor {

    interface OnLoginCallback {
        void onSuccess();
        void onFailed(String error);
        void onSuccessFacebook(FirebaseUser currentUser);
        void onFailedFacebook(String error);
    }



    void login(String user, String pass, OnLoginCallback listener);
    void handleFacebookAccessToken(AccessToken token, OnLoginCallback listener);
    void checkIfIsRegistedOnDatabase(FirebaseUser currentUser);
    boolean isUserLogged();
}
