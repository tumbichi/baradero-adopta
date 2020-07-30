package com.pity.appperros1.data.interactor.implementation;

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
import com.pity.appperros1.data.repository.DataCallback;
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
                        if (!task.isSuccessful()) {
                            listener.onFailed(task.getException().getMessage());
                            return;
                        }

                        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(@NonNull Task<InstanceIdResult> task) {
                                listener.onSuccess(task.getResult().getToken());
                            }
                        });
                    }
                });

    }

    @Override
    public void logoutUser() {
        UserRepository.getInstance().logout();
    }


    @Override
    public void handleFacebookAccessToken(AccessToken token, LoginFacebookCallback listener) {
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
    public void handleDataOfLogin(FirebaseUser currentUser, IUserRepository.CallbackUserUpdate callbackUserUpdate) {
        mRepository.doesUserExists(currentUser, new DataCallback<FirebaseUser>() {
            @Override
            public void onSuccess(FirebaseUser notRegisteredUser) {
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

                mRepository.saveUser(user, callbackUserUpdate);
            }

            @Override
            public void onFailure(String error) {
                callbackUserUpdate.onSuccessUpdateUser();
            }
        });

    }

    @Override
    public void attachLoggedUser(String token, DataCallback<Usuario> callbackAttachUser) {
        UserRepository.getInstance().attachLoggedUser(token, callbackAttachUser);
    }

    @Override
    public boolean isUserLogged() {
        return mRepository.currentFirebaseUser() != null;
    }



}