package com.pity.baradopta.data.interactor.interfaces;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.InstanceIdResult;
import com.pity.baradopta.data.SimpleCallback;
import com.pity.baradopta.data.modelos.Usuario;
import com.pity.baradopta.data.DataCallback;

public interface ILoginInteractor {

    interface LoginFacebookCallback {
        void onSuccessFacebook(FirebaseUser currentUser, String token);
        void onFailedFacebook(String error);
    }

    void sendLogin(String user, String pass, OnCompleteListener<AuthResult> onComplete);
    void logoutUser();
    void requestFacebookAccessToken(AccessToken token, LoginFacebookCallback listener);
    void saveDataOfLoginWithFacebook(FirebaseUser currentUser, SimpleCallback callbackUpdate);
    void requestServerToken(OnCompleteListener<InstanceIdResult> onCompleteListener);
    void attachLoggedUser(String token, DataCallback<Usuario> callbackAttachUser);
    boolean isUserLogged();
}
