package com.pity.appperros1.ui.login;

import android.content.Context;
import android.text.TextUtils;

import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.LoginIteractor;
import com.pity.appperros1.data.interactor.interfaces.ILoginInteractor;

public class LoginPresenter extends BasePresenter<ILoginView>
        implements ILoginPresenter, ILoginInteractor.OnLoginFinishedListener {


    private LoginIteractor mIntereactor;

     LoginPresenter(Context context, LoginIteractor intereactor){
        super(context);
        this.mIntereactor = intereactor;
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
    public void onFailed(Exception e) {
         mView.hideProgressBar();
         mView.showMessage(e.getMessage());
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mIntereactor.isUserLogged()) mView.navigateToInicio();
    }




}
