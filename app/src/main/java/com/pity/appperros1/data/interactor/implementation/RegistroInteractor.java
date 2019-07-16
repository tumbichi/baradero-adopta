package com.pity.appperros1.data.interactor.implementation;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pity.appperros1.data.interactor.interfaces.IRegistroInteractor;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;
import com.pity.appperros1.data.repository.implementacion.UserRepository;



public class RegistroInteractor implements IRegistroInteractor,
        IUserRepository.CallbackRepositoryNewUser, IUserRepository.CallbackRepositorySendMail {

    private FirebaseAuth mAuth;
    private UserRepository repository;
    private IUserRepository.CallbackRepositoryNewUser callbackRepositoryNewUser = this;
    private IRegistroInteractor.CallbackRegistro callbackPresenter;

    public RegistroInteractor(){
        mAuth = FirebaseAuth.getInstance();
        repository = new UserRepository();
    }

    @Override
    public void agregarUsuarioToFirebase(final String email, String contrasenia1, final String nombre, final String apellido, final String telefono ,final IRegistroInteractor.CallbackRegistro callbackRegistro) {

        this.callbackPresenter = callbackRegistro;

        mAuth.createUserWithEmailAndPassword(email, contrasenia1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Usuario newUser = new Usuario(mAuth.getCurrentUser().getUid(), email, nombre, apellido, telefono);
                            repository.saveNewUser(newUser, callbackRepositoryNewUser);

                        }
                        else
                        {
                            callbackRegistro.onFailedRegistro(task.getException());

                        }
                    }
                });
    }

    @Override
    public void onSuccefulSaveNewUser() {
        // Usuario creado en la base de datos
        repository.sendMailVerication(mAuth.getCurrentUser(), this);
    }

    @Override
    public void onFailedSaveNewUser(Exception e) {
        // Usuario no se creo en la base de datos
        callbackPresenter.onFailedRegistro(e);

    }

    @Override
    public void onSuccefulSendMail() {
        // Se envio mail
        repository.logoutUser();
        callbackPresenter.onSuccessfulRegistro();
    }

    @Override
    public void onFailedSendMail(Exception e) {
        // No se envio mail
        callbackPresenter.onFailedRegistro(e);
    }
}
