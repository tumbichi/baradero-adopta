package com.pity.appperros1.data.repository.implementacion;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;

import java.util.HashMap;
import java.util.Map;

public class UserRepository implements IUserRepository {


    private static UserRepository userRepository;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    // For

    //private AccessToken accessToken;

    private UserRepository(){
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
    }

    public static UserRepository getInstance(){
        if (userRepository == null){
            userRepository = new UserRepository();
        }
        return userRepository;
    }


    @Override
    public void saveNewUser(Usuario newUser, final CallbackRepositoryNewUser callbackNewUser) {
        mDatabase.child("Usuarios")
                .child(newUser.getUid())
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
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) LoginManager.getInstance().logOut();
        mAuth.signOut();
    }

    @Override
    public void isUserRegistered(FirebaseUser currentUser, CallbackUserRegistered callbackUserRegistered) {
        DatabaseReference mRef = mDatabase.child("Usuarios");

        Query mQuery = mRef.getRef().orderByKey().equalTo(currentUser.getUid()).limitToFirst(1);

        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()) callbackUserRegistered.saveUserOnDatabase(currentUser);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }


    private Map<String, Object> createUserMap(Usuario user){
        Map<String, Object> crearUsuario = new HashMap<>();

        crearUsuario.put("id", user.getUid());

        crearUsuario.put("displayName", user.getDisplayName());
        crearUsuario.put("email", user.getEmail());
        crearUsuario.put("telefono", user.getTelefono());

        crearUsuario.put("mailVerification", user.isMailVerificated());
        crearUsuario.put("timestamp", user.getTimestamp());

        crearUsuario.put("descripcion", user.getDescripcion());
        crearUsuario.put("fotoperfil", user.getUrlFotoPerfil());

        crearUsuario.put("perrosPublicados", user.getPerrosPublicados());
        crearUsuario.put("perrosAdoptados", user.getPerrosAdoptados());
        crearUsuario.put("perrosEncontrados", user.getPerrosEncontrados());

        return crearUsuario;

    }

}
