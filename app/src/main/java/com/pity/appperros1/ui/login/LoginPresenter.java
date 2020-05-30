package com.pity.appperros1.ui.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.LoginIteractor;
import com.pity.appperros1.data.interactor.interfaces.ILoginInteractor;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;

import java.util.Arrays;

import static android.content.Context.MODE_PRIVATE;

public class LoginPresenter extends BasePresenter<ILoginView> implements ILoginPresenter {

    private LoginIteractor mIntereactor;
    private CallbackManager mCallbackManager;
    private SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefernces", MODE_PRIVATE);

    LoginPresenter(Context context, LoginIteractor intereactor) {
        super(context);
        this.mIntereactor = intereactor;

        if (mIntereactor.isUserLogged()) {
            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                @Override
                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                    String lastToken = sharedPreferences.getString("token", "");
                    if (task.isSuccessful()) {
                        String token = task.getResult().getToken();

                        if (!lastToken.equals("") && lastToken.equals(token)) {
                            mIntereactor.attachLoggedUser(UserRepository.getInstance().currentFirebaseUser().getUid(), token, new IUserRepository.CallbackAttachUser() {
                                @Override
                                public void onUserAttached(Usuario user) {
                                    if (isViewAttached()) mView.navigateToInicio();

                                }
                            });

                        }else{
                            mCallbackManager = CallbackManager.Factory.create();
                            UserRepository.getInstance().logoutUser(mContext);
                        }
                    }
                }
            });
        }
        mCallbackManager = CallbackManager.Factory.create();
    }

    @Override
    public void loginUserWith(String email, String password) {
        mView.showProgressBar();

        if (TextUtils.isEmpty(email)) {
            onEmptyEmail();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            onEmptyPassword();
            return;
        }

        mIntereactor.login(email, password, new ILoginInteractor.LoginCallback() {
            @Override
            public void onSuccess(String token) {
                if (isViewAttached()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("token", token);
                    editor.apply();
                    mIntereactor.attachLoggedUser(UserRepository.getInstance().currentFirebaseUser().getUid(), token, new IUserRepository.CallbackAttachUser() {
                        @Override
                        public void onUserAttached(Usuario user) {
                            if (isViewAttached()){
                                mView.navigateToInicio();
                                mView.hideProgressBar();
                            }
                        }
                    });

                }
            }

            @Override
            public void onFailed(String error) {
                Log.e("LoginPresenter", error);
                if (isViewAttached()){
                    mView.hideProgressBar();
                    mView.showMessage(error);
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        mCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestContinueWithFacebook(Activity mActivity) {
        LoginManager.getInstance().logInWithReadPermissions(mActivity, Arrays.asList("email", "public_profile"));
        LoginManager.getInstance().registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("LoginPresenter", "onSuccesLoginFacebook");
                mIntereactor.handleFacebookAccessToken(loginResult.getAccessToken(), new ILoginInteractor.LoginFacebookCallback() {
                    @Override
                    public void onSuccessFacebook(FirebaseUser currentUser, String token) {
                        mIntereactor.checkIfIsRegistedOnDatabase(currentUser, new IUserRepository.CallbackUserUpdate() {
                            @Override
                            public void onSuccessUpdateUser() {
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("token", token);
                                editor.commit();

                                mIntereactor.attachLoggedUser(UserRepository.getInstance().currentFirebaseUser().getUid(), token, new IUserRepository.CallbackAttachUser() {
                                    @Override
                                    public void onUserAttached(Usuario user) {
                                        Log.i("UserAtacched", "El usuario " + user.getDisplayName() + " se ha enlazado");
                                        if (isViewAttached()) mView.navigateToInicio();
                                    }
                                });
                            }

                            @Override
                            public void onFailedUpdateUser(Exception e) {

                            }
                        });

                    }

                    @Override
                    public void onFailedFacebook(String error) {
                        mView.showMessage(error);
                    }
                });
            }

            @Override
            public void onCancel() {
                Log.e("LoginPresenter", "facebook:onCancel");
                mView.hideProgressBar();
                mView.enabledFacebookButton();
            }

            @Override
            public void onError(FacebookException error) {
                mView.showMessage(error.getMessage());
            }
        });
    }

    private void onEmptyEmail() {
        mView.hideProgressBar();
        mView.showMessage("Por favor, ingrese un mail");
    }

    private void onEmptyPassword() {
        mView.hideProgressBar();
        mView.showMessage("Por favor, ingrese una contraseña");
    }


}
