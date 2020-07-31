package com.pity.appperros1.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pity.appperros1.data.SimpleCallback;
import com.pity.appperros1.data.prefs.PreferencesManager;
import com.pity.appperros1.data.DataCallback;
import com.pity.appperros1.ui.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.LoginIteractor;
import com.pity.appperros1.data.interactor.interfaces.ILoginInteractor;
import com.pity.appperros1.data.modelos.Usuario;

import java.util.Arrays;

public class LoginPresenter extends BasePresenter<ILoginView> implements ILoginPresenter {

    private LoginIteractor interactor;
    private CallbackManager mCallbackManager;
    private DataCallback<Usuario> attachCallback;

    LoginPresenter(Context context, LoginIteractor interactor) {
        super(context);
        this.interactor = interactor;
        initAttachCallback();
        checkUserLogged();

        mCallbackManager = CallbackManager.Factory.create();
    }

    private void checkUserLogged(){
        if (interactor.isUserLogged()) {
            interactor.requestServerToken(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    if (!task.isSuccessful()) return;

                    String lastToken = PreferencesManager.getInstance().getToken();
                    String serverToken = task.getResult().getToken();

                    if (!isDeviceTokenValid(lastToken, serverToken)){
                        interactor.logoutUser();
                        return;
                    }

                    Log.d("LoginPresenter", "user loged: " + FirebaseAuth.getInstance().getCurrentUser().getUid());
                    interactor.attachLoggedUser(serverToken, attachCallback);
                }
            });
        }
    }

    private void initAttachCallback(){
        attachCallback = new DataCallback<Usuario>() {
            @Override
            public void onSuccess(Usuario usuario) {
                if (isViewAttached()){
                    view.navigateToInicio();
                    view.hideProgressBar();
                }
            }

            @Override
            public void onFailure(String error) {

            }
        };
    }

    private boolean isDeviceTokenValid(String deviceToken, String serverToken){
        return !deviceToken.isEmpty() && serverToken.equals(deviceToken);
    }

    @Override
    public void loginUserWith(String email, String password) {
        view.showProgressBar();

        if (TextUtils.isEmpty(email)) {
            onEmptyEmail();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            onEmptyPassword();
            return;
        }

        interactor.sendLogin(email, password, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    Log.e("LoginPresenter", "Cannot login user with email: " + email);
                    return;
                }
                FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("LoginPresenter", "" + task.getException().getMessage());
                            if (isViewAttached()) view.hideProgressBar();
                            return;
                        }
                        String token = task.getResult().getToken();

                        PreferencesManager.getInstance().setToken(token);
                        interactor.attachLoggedUser(token, attachCallback);
                    }
                });
            }
        });
    }

    @Override
    public void manageLoginWithFacebook() {
        view.disableFacebookButton();
        view.showProgressBar();
        onRequestContinueWithFacebook(view.provideActivity());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    private void onRequestContinueWithFacebook(Activity mActivity) {
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                interactor.requestFacebookAccessToken(loginResult.getAccessToken(), new ILoginInteractor.LoginFacebookCallback() {
                    @Override
                    public void onSuccessFacebook(FirebaseUser currentUser, String token) {
                        Log.d("LoginPresenter", "user " + currentUser.getUid() + " with token: " + token + "is loged");
                        interactor.saveDataOfLoginWithFacebook(currentUser, new SimpleCallback() {
                            @Override
                            public void onSuccess() {
                                Log.d("LoginPresenter", "onSuccesUpadateFacebookUser");
                                PreferencesManager.getInstance().setToken(token);
                                interactor.attachLoggedUser(token, attachCallback);
                            }

                            @Override
                            public void onFailure(String error) {

                            }
                        });

                    }

                    @Override
                    public void onFailedFacebook(String error) {
                        Log.e("LoginPresenter", error);
                    }
                });
            }

            @Override
            public void onCancel() {
                Log.e("LoginPresenter", "facebook:onCancel");
                view.hideProgressBar();
                view.enabledFacebookButton();
            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

    private void onEmptyEmail() {
        view.hideProgressBar();
        view.toast("Por favor, ingrese un mail");
    }

    private void onEmptyPassword() {
        view.hideProgressBar();
        view.toast("Por favor, ingrese una contraseña");
    }



}
