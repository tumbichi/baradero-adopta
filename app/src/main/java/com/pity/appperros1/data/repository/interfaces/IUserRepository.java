package com.pity.appperros1.data.repository.interfaces;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.modelos.Usuario;

public interface IUserRepository {

    interface CallbackRepositoryNewUser{
        void onSuccefulSaveNewUser();
        void onFailedSaveNewUser(Exception e);

    }

    interface CallbackRepositorySendMail{
        void onSuccefulSendMail();
        void onFailedSendMail(Exception e);
    }

    void saveNewUser(Usuario newUser, CallbackRepositoryNewUser callback);
    void updateUser(Usuario currentUser);
    void sendMailVerication(FirebaseUser currentUser, CallbackRepositorySendMail callback);
    FirebaseUser currentUser();
    void logoutUser();


}
