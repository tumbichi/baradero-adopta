package com.pity.appperros1.data.interactor.interfaces;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.DataCallback;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;

public interface ILoginInteractor {

    interface LoginCallback {
        void onSuccess(String token);
        void onFailed(String error);
    }

    interface LoginFacebookCallback {
        void onSuccessFacebook(FirebaseUser currentUser, String token);
        void onFailedFacebook(String error);
    }

    void login(String user, String pass, LoginCallback listener);
    void logoutUser();
    void handleFacebookAccessToken(AccessToken token, LoginFacebookCallback listener);
    void handleDataOfLogin(FirebaseUser currentUser, IUserRepository.CallbackUserUpdate callbackUserUpdate);
    void attachLoggedUser(String token, DataCallback<Usuario> callbackAttachUser);
    boolean isUserLogged();
}
