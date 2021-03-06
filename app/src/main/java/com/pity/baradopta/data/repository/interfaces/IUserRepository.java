package com.pity.baradopta.data.repository.interfaces;

import com.google.firebase.auth.FirebaseUser;
import com.pity.baradopta.data.ExistingCallback;
import com.pity.baradopta.data.modelos.Usuario;
import com.pity.baradopta.data.DataCallback;
import com.pity.baradopta.data.SimpleCallback;

public interface IUserRepository {

    interface CallbackUserUpdate {
        void onSuccessUpdateUser();
        void onFailedUpdateUser(Exception e);
    }

    interface CallbackQueryUser {
        void onSuccessUserQueryById(Usuario user);
        void onFailureUserQueryById(String msgError);
    }

    void attachUser(Usuario user);
    Usuario getCurrentUser();
    void getCurrentUser(CallbackQueryUser callbackQueryUser);
    void getServerToken(SimpleCallback callback);
    void attachLoggedUser(String token, DataCallback<Usuario> callbackAttachUser);
    void saveUser(Usuario newUser, SimpleCallback callback);
    void updateUser(Usuario currentUser, CallbackUserUpdate callbackUserUpdate);
    void sendMailVerification(FirebaseUser currentUser);
    FirebaseUser currentFirebaseUser();
    void logout();
    void doesUserExists(FirebaseUser facebookUser, ExistingCallback<FirebaseUser> callback);
    void getUserById(String id, CallbackQueryUser callbackUserById);

}
