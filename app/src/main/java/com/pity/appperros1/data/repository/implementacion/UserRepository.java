package com.pity.appperros1.data.repository.implementacion;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;

import java.util.HashMap;
import java.util.Map;

public class UserRepository implements IUserRepository {

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public UserRepository(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }


    @Override
    public void saveNewUser(Usuario newUser, final IUserRepository.CallbackRepositoryNewUser callbackNewUser) {

        mDatabase.child("Usuarios")
                .child(newUser.getId())
                .updateChildren(createUserMap(newUser))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            callbackNewUser.onSuccefulSaveNewUser();

                        }else{
                            callbackNewUser.onFailedSaveNewUser(task.getException());
                        }
                    }
                });

    }

    @Override
    public void updateUser(Usuario currentUser) {

    }

    @Override
    public void sendMailVerication(FirebaseUser currentUser,final CallbackRepositorySendMail callback) {
        currentUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccefulSendMail();
                        }else{
                            callback.onFailedSendMail(task.getException());
                        }
                    }
                });

    }

    @Override
    public FirebaseUser currentUser() throws NullPointerException {
       if (mAuth.getCurrentUser() != null) {
           return mAuth.getCurrentUser();
       }else{

           return null;

       }
    }

    @Override
    public void logoutUser() {
        mAuth.signOut();
    }


    private Map<String, Object> createUserMap(Usuario user){
        Map<String, Object> crearUsuario = new HashMap<>();

        crearUsuario.put("id", user.getId());
        crearUsuario.put("nombre", user.getNombre());
        crearUsuario.put("apellido", user.getApellido());
        crearUsuario.put("email", user.getEmail());
        crearUsuario.put("fechaCreacion", user.getFechaCreacion());
        crearUsuario.put("descripcion", user.getDescripcion());
        crearUsuario.put("telefono", user.getTelefono());


        return crearUsuario;

    }
}
