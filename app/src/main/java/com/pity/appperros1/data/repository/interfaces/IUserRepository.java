package com.pity.appperros1.data.repository.interfaces;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.modelos.Usuario;

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

    interface CallbackIsUserRegistered {
        void saveUserOnDatabase(FirebaseUser mNoRegisteredUser);
    }


    Usuario getLoggedUser();
    void attachLoggedUser(String currentUserID, String token);
    void persistNewUserOnDatabase(Usuario newUser, CallbackUserUpdate callback);
    void updateUser(Usuario currentUser, CallbackUserUpdate callbackUserUpdate);
    void sendMailVerication(FirebaseUser currentUser, CallbackRepositorySendMail callback);
    FirebaseUser currentFirebaseUser();
    void logoutUser(Context context);
    void logoutWithoutToken();
    void isUserRegisteredOnDatabase(FirebaseUser facebookUser, CallbackIsUserRegistered callback);
    void getUserById(String id, CallbackQueryUser callbackUserById);

}
