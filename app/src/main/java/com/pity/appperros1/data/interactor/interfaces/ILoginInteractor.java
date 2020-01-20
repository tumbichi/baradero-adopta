package com.pity.appperros1.data.interactor.interfaces;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

public interface ILoginInteractor {

    interface OnLoginCallback {
        void onSuccess(String token);
        void onFailed(String error);
        void onSuccessFacebook(FirebaseUser currentUser, String token);
        void onFailedFacebook(String error);
    }



    void login(String user, String pass, OnLoginCallback listener);
    void handleFacebookAccessToken(AccessToken token, OnLoginCallback listener);
    void checkIfIsRegistedOnDatabase(FirebaseUser currentUser);
    void attachLoggedUser(String userID, String token);
    boolean isUserLogged();
}
