package com.pity.appperros1.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.Nullable;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.LoginIteractor;
import com.pity.appperros1.data.interactor.interfaces.ILoginInteractor;

import java.util.Arrays;

public class LoginPresenter extends BasePresenter<ILoginView>
        implements ILoginPresenter, ILoginInteractor.OnLoginCallback, FacebookCallback<LoginResult> {


    private LoginIteractor mIntereactor;
    private CallbackManager mCallbackManager;

     LoginPresenter(Context context, LoginIteractor intereactor){
        super(context);
        this.mIntereactor = intereactor;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mIntereactor.isUserLogged()) mView.navigateToInicio();
        mCallbackManager = CallbackManager.Factory.create();
    }




    @Override
    public void loginUserWith(String email, String password) {
        mView.showProgressBar();

        if (TextUtils.isEmpty(email)){
            onEmptyEmail();
            return;
        }

        if (TextUtils.isEmpty(password)){
            onEmptyPassword();
            return;
        }

        mIntereactor.login(email, password, this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestContinueWithFacebook(Activity mActivity) {
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager, this);
    }

    @Override
    public void onSuccess() {
         if (isViewAttached()) {
             mView.navigateToInicio();
             mView.hideProgressBar();
         }
    }

    private void onEmptyEmail() {
         mView.hideProgressBar();
         mView.showMessage("Por favor, ingrese un mail");
    }

    private void onEmptyPassword() {
         mView.hideProgressBar();
         mView.showMessage("Por favor, ingrese una contraseña");
    }

    @Override
    public void onFailed(String e) {
         mView.hideProgressBar();
         mView.showMessage(e);
    }

    @Override
    public void onSuccessFacebook(FirebaseUser currentUser) {
        Log.e("presenter", "onSuccesFacebook");
        mIntereactor.checkIfIsRegistedOnDatabase(currentUser);
        if (isViewAttached()){
            mView.navigateToInicio();
        }
    }

    @Override
    public void onFailedFacebook(String error) {

    }




    @Override
    public void onSuccess(LoginResult loginResult) {
        mIntereactor.handleFacebookAccessToken(loginResult.getAccessToken(), this);
    }

    @Override
    public void onCancel() {
        mView.showMessage("Se cancelo el inicio por Facebook");
    }

    @Override
    public void onError(FacebookException error) {
        mView.showMessage(error.getMessage());
    }
}
