package com.pity.baradopta.data.interactor.implementation;

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
import com.pity.baradopta.data.ExistingCallback;
import com.pity.baradopta.data.SimpleCallback;
import com.pity.baradopta.data.interactor.interfaces.ILoginInteractor;
import com.pity.baradopta.data.modelos.Usuario;
import com.pity.baradopta.data.DataCallback;
import com.pity.baradopta.data.repository.implementacion.UserRepository;

public class LoginIteractor implements ILoginInteractor {

    private UserRepository repository;
    private FirebaseAuth mAuth;

    public LoginIteractor(){
        this.mAuth = FirebaseAuth.getInstance();
        this.repository = UserRepository.getInstance();
    }

    @Override
    public void sendLogin(String email, String pass, OnCompleteListener<AuthResult> onComplete) {
        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(onComplete);
    }

    @Override
    public void logoutUser() {
        UserRepository.getInstance().logout();
    }

    public void requestServerToken(OnCompleteListener<InstanceIdResult> onCompleteListener){
        repository.getServerToken(onCompleteListener);
    }

    @Override
    public void requestFacebookAccessToken(AccessToken token, LoginFacebookCallback listener) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            listener.onFailedFacebook("Error en la autentificacion");
                            return;
                        }
                        // Sign in success, update UI with the signed-in user's information
                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                if (task.isSuccessful()){
                                    listener.onSuccessFacebook(mAuth.getCurrentUser(), task.getResult().getToken());
                                }
                            }
                        });
                    }
                });
    }

    @Override
    public void saveDataOfLoginWithFacebook(FirebaseUser currentUser, SimpleCallback callback) {
        repository.doesUserExists(currentUser, new ExistingCallback<FirebaseUser>() {
            @Override
            public void isInExistence(FirebaseUser notRegisteredUser) {
                Usuario user = notRegisteredUser.getPhotoUrl() != null ?
                        new Usuario(
                            notRegisteredUser.getUid(),
                            notRegisteredUser.getEmail(),
                            notRegisteredUser.getDisplayName(),
                            notRegisteredUser.getPhoneNumber(),
                            notRegisteredUser.getPhotoUrl()
                        )
                        : new Usuario(
                            notRegisteredUser.getUid(),
                            notRegisteredUser.getEmail(),
                            notRegisteredUser.getDisplayName(),
                            notRegisteredUser.getPhoneNumber()
                        );

                repository.saveUser(user, callback);
            }

            @Override
            public void notInExistence() {
                callback.onSuccess();
            }
        });

    }

    @Override
    public void attachLoggedUser(String token, DataCallback<Usuario> callbackAttachUser) {
        UserRepository.getInstance().attachLoggedUser(token, callbackAttachUser);
    }

    @Override
    public boolean isUserLogged() {
        return repository.currentFirebaseUser() != null;
    }

}