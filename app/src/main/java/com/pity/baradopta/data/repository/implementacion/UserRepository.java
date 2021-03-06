package com.pity.baradopta.data.repository.implementacion;

import android.util.Log;

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
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.pity.baradopta.data.ExistingCallback;
import com.pity.baradopta.data.modelos.Usuario;
import com.pity.baradopta.data.prefs.PreferencesManager;
import com.pity.baradopta.data.DataCallback;
import com.pity.baradopta.data.SimpleCallback;
import com.pity.baradopta.data.repository.interfaces.IUserRepository;
import com.pity.baradopta.utils.UserUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.pity.baradopta.utils.UserUtils.USER_DB_REF;
import static com.pity.baradopta.utils.UserUtils.compareToken;

public class UserRepository implements IUserRepository {

    private static UserRepository userRepository;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private static Usuario CURRENT_USER;
    private final static String TAG = "UserRepository";

    private UserRepository() {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    @Override
    public void attachUser(Usuario user) {
        CURRENT_USER = user;
    }

    @Override
    public Usuario getCurrentUser() {
        return CURRENT_USER;
    }

    @Override
    public void getCurrentUser(CallbackQueryUser callbackQueryUser) {
        this.getUserById(CURRENT_USER.getUid(), callbackQueryUser);
    }

    @Override
    public void getServerToken(SimpleCallback callback) {
        DatabaseReference ref = database.getReference();

        ref.child(USER_DB_REF)
                .child(CURRENT_USER.getUid())
                .child(UserUtils.TOKEN_KEY)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String serverToken = (String) dataSnapshot.getValue();
                        String lastToken = PreferencesManager.getInstance().getToken();

                        Log.e(TAG, "\n ServerToken: " + serverToken + "\n DeviceToken: " + lastToken);

                        if (compareToken(lastToken, serverToken)) return;

                        ref.removeEventListener(this);
                        logout();
                        callback.onSuccess();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        callback.onFailure(databaseError.getDetails());
                    }
                });
    }

    public void getServerToken(OnCompleteListener<InstanceIdResult> onCompleteListener){
        FirebaseInstanceId.getInstance().getInstanceId().addOnCompleteListener(onCompleteListener);
    }

    @Override
    public void attachLoggedUser(String token, DataCallback<Usuario> callbackAttachUser) {
        getUserById(auth.getCurrentUser().getUid(), new CallbackQueryUser() {
            @Override
            public void onSuccessUserQueryById(Usuario user) {
                Log.i(TAG, "logged user id: " + auth.getCurrentUser().getUid() + " with token: " + token);

                Map<String, Object> tokenMap = new HashMap<>();

                CURRENT_USER = user;
                tokenMap.put(UserUtils.TOKEN_KEY, token);

                database.getReference().child("Usuarios").child(CURRENT_USER.getUid())
                    .updateChildren(tokenMap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                callbackAttachUser.onSuccess(user);
                            }else{
                                String errorMsg = "User with id " + auth.getCurrentUser().getUid() + " could not be attached with token: " + token;
                                Log.e(TAG, errorMsg);
                                callbackAttachUser.onFailure(errorMsg);
                            }
                        }
                    });
            }

            @Override
            public void onFailureUserQueryById(String msgError) {
                Log.e(TAG, msgError);
                callbackAttachUser.onFailure(msgError);
            }
        });
    }

    @Override
    public void saveUser(Usuario newUser, SimpleCallback callbackNewUser) {
        DatabaseReference mRef = database.getReference();
        mRef.child(USER_DB_REF)
                .child(newUser.getUid())
                .updateChildren(UserUtils.userToMap(newUser))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callbackNewUser.onSuccess();

                        } else {
                            callbackNewUser.onFailure(task.getException().getMessage());
                        }
                    }
                });
    }

    @Override
    public void updateUser(Usuario currentUser, final CallbackUserUpdate callbackUserUpdate) {
        DatabaseReference mRef = database.getReference().child(USER_DB_REF);
        mRef.child(currentUser.getUid())
                .updateChildren(UserUtils.userToMap(currentUser))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            callbackUserUpdate.onSuccessUpdateUser();
                        }else{
                            callbackUserUpdate.onFailedUpdateUser(task.getException());
                        }
                    }
                });

    }

    @Override
    public void sendMailVerification(FirebaseUser currentUser) {
        currentUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "Email sended");
                        } else {
                            Log.e(TAG, "Email not sended");
                        }
                    }
                });

    }

    @Override
    public FirebaseUser currentFirebaseUser() throws NullPointerException {
        return FirebaseAuth.getInstance().getCurrentUser() != null ? auth.getCurrentUser() : null;
    }

    @Override
    public void logout() {
        if (auth.getCurrentUser() != null){
            database.getReference()
                    .child(USER_DB_REF)
                    .child(auth.getCurrentUser().getUid())
                    .child(UserUtils.TOKEN_KEY)
                    .removeValue();
        }

        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) LoginManager.getInstance().logOut();
        CURRENT_USER = null;
        auth.signOut();
    }

    @Override
    public void doesUserExists(FirebaseUser currentUser, ExistingCallback<FirebaseUser> callback) {
        DatabaseReference mRef = database.getReference().child(USER_DB_REF);

        Query mQuery = mRef.orderByKey().equalTo(currentUser.getUid()).limitToFirst(1);

        mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    callback.isInExistence(currentUser);
                }else{
                    callback.notInExistence();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getUserById(String id, CallbackQueryUser callbackUserById) {
        DatabaseReference mRef = database.getReference().child(USER_DB_REF);
        Query query = mRef.orderByKey().equalTo(id).limitToFirst(1);


        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<Usuario> userResult = new ArrayList<>();

                for (DataSnapshot user : dataSnapshot.getChildren()) {
                    userResult.add(UserUtils.unmapperUser(user));
                }
                if (userResult.size() > 0 && userResult.get(0) != null) {
                    callbackUserById.onSuccessUserQueryById(userResult.get(0));
                } else {
                    callbackUserById.onFailureUserQueryById("Not found user with id: " + id);
                }

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callbackUserById.onFailureUserQueryById("Se cancelo");
            }
        });

    }

}
