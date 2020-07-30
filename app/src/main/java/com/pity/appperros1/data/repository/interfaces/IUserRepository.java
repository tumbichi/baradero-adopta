package com.pity.appperros1.data.repository.interfaces;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.DataCallback;
import com.pity.appperros1.data.repository.SimpleCallback;
import com.pity.appperros1.ui.inicio.IInicioView;

public interface IUserRepository {

    interface CallbackUserUpdate {
        void onSuccessUpdateUser();
        void onFailedUpdateUser(Exception e);
    }

    interface CallbackQueryUser {
        void onSuccessUserQueryById(Usuario user);
        void onFailureUserQueryById(String msgError);
    }

    interface CallbackRepositorySendMail{
        void onSuccefulSendMail();
        void onFailedSendMail(Exception e);
    }



    void attachUser(Usuario user);
    Usuario getCurrentUser();
    void getCurrentUser(CallbackQueryUser callbackQueryUser);
    void getServerToken(SimpleCallback callback);
    void attachLoggedUser(String token, DataCallback<Usuario> callbackAttachUser);
    void saveUser(Usuario newUser, CallbackUserUpdate callback);
    void updateUser(Usuario currentUser, CallbackUserUpdate callbackUserUpdate);
    void sendMailVerification(FirebaseUser currentUser, CallbackRepositorySendMail callback);
    FirebaseUser currentFirebaseUser();
    void logout();
    void doesUserExists(FirebaseUser facebookUser, DataCallback<FirebaseUser> callback);
    void getUserById(String id, CallbackQueryUser callbackUserById);

}
