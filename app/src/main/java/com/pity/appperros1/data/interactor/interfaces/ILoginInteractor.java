package com.pity.appperros1.data.interactor.interfaces;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.InstanceIdResult;
import com.pity.appperros1.data.SimpleCallback;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.DataCallback;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;

public interface ILoginInteractor {

    interface LoginFacebookCallback {
        void onSuccessFacebook(FirebaseUser currentUser, String token);
        void onFailedFacebook(String error);
    }

    void login(String user, String pass, OnCompleteListener<AuthResult> onComplete);
    void logoutUser();
    void handleFacebookAccessToken(AccessToken token, LoginFacebookCallback listener);
    void handleDataOfLoginWithFacebook(FirebaseUser currentUser, SimpleCallback callbackUpdate);
    void requestServerToken(OnCompleteListener<InstanceIdResult> onCompleteListener);
    void attachLoggedUser(String token, DataCallback<Usuario> callbackAttachUser);
    boolean isUserLogged();
}
