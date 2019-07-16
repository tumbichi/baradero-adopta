package com.pity.appperros1.data.interactor.implementation;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pity.appperros1.data.interactor.interfaces.ILoginInteractor;
import com.pity.appperros1.data.repository.implementacion.UserRepository;


public class LoginIteractor implements ILoginInteractor {

    private UserRepository repository;
    private FirebaseAuth mAuth;

    public LoginIteractor(){
        this.mAuth = FirebaseAuth.getInstance();
        this.repository = new UserRepository();
    }


    @Override
    public void login(String email, String pass, final OnLoginFinishedListener listener) {

        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            listener.onSuccess();
                        }else{

                            listener.onFailed(task.getException());
                        }

                    }
                });

    }

    @Override
    public boolean isUserLogged() {
        return (repository.currentUser() != null);
    }
}