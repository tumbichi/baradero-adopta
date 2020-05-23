package com.pity.appperros1.data.repository.interfaces;

import android.content.Context;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.modelos.Usuario;
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

    interface CallbackIsUserRegistered {
        void onNotRegisteredUser(FirebaseUser mNoRegisteredUser);
        void onRegisteredUser();
    }

    interface CallbackAttachUser {
        void onUserAttached(Usuario user);
    }


    Usuario getLoggedUser();
    void getServerToken(Context context, IInicioView view);
    void attachLoggedUser(String currentUserID, String token, CallbackAttachUser callbackAttachUser);
    void persistNewUserOnDatabase(Usuario newUser, CallbackUserUpdate callback);
    void updateUser(Usuario currentUser, CallbackUserUpdate callbackUserUpdate);
    void sendMailVerification(FirebaseUser currentUser, CallbackRepositorySendMail callback);
    FirebaseUser currentFirebaseUser();
    void logoutUser(Context context);
    void logoutWithoutToken();
    void isUserRegisteredOnDatabase(FirebaseUser facebookUser, CallbackIsUserRegistered callback);
    void getUserById(String id, CallbackQueryUser callbackUserById);

}
