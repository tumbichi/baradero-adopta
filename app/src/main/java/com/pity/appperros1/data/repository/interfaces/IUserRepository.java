package com.pity.appperros1.data.repository.interfaces;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.modelos.Usuario;

public interface IUserRepository {

    interface CallbackRepositoryNewUser{
        void onSuccefulSaveNewUser();
        void onFailedSaveNewUser(Exception e);
    }

    interface CallbackUserById{
        void onSuccessUserQueryById(Usuario uploader);
        void onFailureUserQueryById(String msgError);
    }

    interface CallbackRepositorySendMail{
        void onSuccefulSendMail();
        void onFailedSendMail(Exception e);
    }

    interface CallbackUserRegistered{
        void saveUserOnDatabase(FirebaseUser mNoRegisteredUser);
    }

    void saveNewUser(Usuario newUser, CallbackRepositoryNewUser callback);
    void updateUser(Usuario currentUser);
    void sendMailVerication(FirebaseUser currentUser, CallbackRepositorySendMail callback);
    FirebaseUser currentUser();
    void logoutUser();
    void isUserRegistered(FirebaseUser facebookUser, CallbackUserRegistered callback);
    void getUserById(String id, CallbackUserById callbackUserById);

}
