package com.pity.appperros1.data.interactor.implementation;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pity.appperros1.data.SimpleCallback;
import com.pity.appperros1.data.interactor.interfaces.IRegistroInteractor;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.implementacion.UserRepository;

public class RegistroInteractor implements IRegistroInteractor{

    private FirebaseAuth auth;
    private UserRepository repository;

    public RegistroInteractor(){
        auth = FirebaseAuth.getInstance();
        repository = UserRepository.getInstance();
    }

    @Override
    public void registerUser(final String email, String contrasenia1, final String nombre, final String apellido, final String telefono , SimpleCallback callback) {
        auth.createUserWithEmailAndPassword(email, contrasenia1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            callback.onFailure(task.getException().getMessage());
                            return;
                        }
                        repository.sendMailVerification(auth.getCurrentUser());

                        Usuario newUser = new Usuario(
                                auth.getCurrentUser().getUid(),
                                email,
                                nombre,
                                apellido,
                                telefono
                        );

                        repository.saveUser(newUser, new SimpleCallback() {
                            @Override
                            public void onSuccess() {
                                repository.logout();
                                callback.onSuccess();
                            }

                            @Override
                            public void onFailure(String error) {
                                callback.onFailure(error);
                            }
                        });

                    }
                });
    }

}
