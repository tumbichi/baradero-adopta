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
import com.pity.appperros1.data.interactor.interfaces.ILoginInteractor;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;


public class LoginIteractor implements ILoginInteractor, IUserRepository.CallbackUserRegistered, IUserRepository.CallbackRepositoryNewUser {

    private UserRepository mRepository;
    private FirebaseAuth mAuth;

    public LoginIteractor(){
        this.mAuth = FirebaseAuth.getInstance();
        this.mRepository = UserRepository.getInstance();
    }


    @Override
    public void login(String email, String pass, final OnLoginCallback listener) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            listener.onSuccess();
                        }else{
                            listener.onFailed(task.getException().getMessage());
                        }

                    }
                });

    }



    @Override
    public void handleFacebookAccessToken(AccessToken token, OnLoginCallback listener) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            listener.onSuccessFacebook(mAuth.getCurrentUser());

                        } else {
                            // If sign in fails, display a message to the user.
                            listener.onFailedFacebook("Error en la autentificacion");
                        }

                        // ...
                    }
                });
    }

    @Override
    public void checkIfIsRegistedOnDatabase(FirebaseUser currentUser) {
        mRepository.isUserRegistered(currentUser, this);
    }


    @Override
    public boolean isUserLogged() {
        return (mRepository.currentUser() != null);
    }

    @Override
    public void saveUserOnDatabase(FirebaseUser mNoRegisteredUser) {
        Usuario mUser;
        if (mNoRegisteredUser.getPhotoUrl() != null) {
            mUser = new Usuario(mNoRegisteredUser.getUid(), mNoRegisteredUser.getEmail(),
                    mNoRegisteredUser.getDisplayName(), "", mNoRegisteredUser.getPhoneNumber(), mNoRegisteredUser.getPhotoUrl());
        }else mUser = new Usuario(mNoRegisteredUser.getUid(), mNoRegisteredUser.getEmail(),
                mNoRegisteredUser.getDisplayName(), "", mNoRegisteredUser.getPhoneNumber());

        mRepository.saveNewUser(mUser, this);

    }


    @Override
    public void onSuccefulSaveNewUser() {
        Log.e("Login", "facebook:onSuccefulSaveNewUser()");
    }

    @Override
    public void onFailedSaveNewUser(Exception e) {
        Log.e("Login","facebook:onFailedSaveNewUser");
    }
}