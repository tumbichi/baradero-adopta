package com.pity.appperros1.data.interactor.implementation;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pity.appperros1.data.interactor.interfaces.ILoginInteractor;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;


public class LoginIteractor implements ILoginInteractor {

    private UserRepository mRepository;
    private FirebaseAuth mAuth;

    private final static String TAG = "LoginInteractor";

    public LoginIteractor(){
        this.mAuth = FirebaseAuth.getInstance();
        this.mRepository = UserRepository.getInstance();
    }


    @Override
    public void login(String email, String pass, final LoginCallback listener) {
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    listener.onSuccess(task.getResult().getToken());
                                }
                            });
                        }else{
                            listener.onFailed(task.getException().getMessage());
                        }

                    }
                });

    }



    @Override
    public void handleFacebookAccessToken(AccessToken token, LoginFacebookCallback listener) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                @Override
                                public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                    if (task.isSuccessful()){
                                        listener.onSuccessFacebook(mAuth.getCurrentUser(), task.getResult().getToken());
                                    }
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            listener.onFailedFacebook("Error en la autentificacion");
                        }

                        // ...
                    }
                });
    }

    @Override
    public void checkIfIsRegistedOnDatabase(FirebaseUser currentUser, IUserRepository.CallbackUserUpdate callbackUserUpdate) {
        mRepository.isUserRegisteredOnDatabase(currentUser, new IUserRepository.CallbackIsUserRegistered() {
            @Override
            public void onNotRegisteredUser(FirebaseUser mNoRegisteredUser) {
                Usuario mUser;
                if (mNoRegisteredUser.getPhotoUrl() != null) {
                    mUser = new Usuario(mNoRegisteredUser.getUid(), mNoRegisteredUser.getEmail(),
                            mNoRegisteredUser.getDisplayName(), mNoRegisteredUser.getPhoneNumber(), mNoRegisteredUser.getPhotoUrl());
                }else mUser = new Usuario(mNoRegisteredUser.getUid(), mNoRegisteredUser.getEmail(),
                        mNoRegisteredUser.getDisplayName(), mNoRegisteredUser.getPhoneNumber());

                mRepository.persistNewUserOnDatabase(mUser, callbackUserUpdate);
            }

            @Override
            public void onRegisteredUser() {
                callbackUserUpdate.onSuccessUpdateUser();
            }
        });
    }

    @Override
    public void attachLoggedUser(String userID, String token, IUserRepository.CallbackAttachUser callbackAttachUser) {
        UserRepository.getInstance()
                .attachLoggedUser(userID, token, callbackAttachUser);
    }


    @Override
    public boolean isUserLogged() {
        return mRepository.currentFirebaseUser() != null;
    }

}