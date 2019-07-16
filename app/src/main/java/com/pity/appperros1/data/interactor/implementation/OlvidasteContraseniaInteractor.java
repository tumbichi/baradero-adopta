package com.pity.appperros1.data.interactor.implementation;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.pity.appperros1.data.interactor.interfaces.IOlvidasteContraseniaInteractor;

public class OlvidasteContraseniaInteractor implements IOlvidasteContraseniaInteractor {

    private FirebaseAuth mAuth;

    public OlvidasteContraseniaInteractor(){
        this.mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void enviarEmailFromFirebaseTo(String email, final IOlvidasteContraseniaInteractor.OnFinishListener listener) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    listener.onSuccess();
                }else{
                    listener.onFailed(task.getException());
                }
            }
        });
    }
}
