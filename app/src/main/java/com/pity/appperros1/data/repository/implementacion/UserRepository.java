package com.pity.appperros1.data.repository.implementacion;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskCompletionSource;
import com.google.android.gms.tasks.Tasks;
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
import com.pity.appperros1.utils.UserUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UserRepository implements IUserRepository {


    private static UserRepository userRepository;
    private FirebaseDatabase mDatabase;
    private FirebaseAuth mAuth;
    private static Usuario CURRENT_USER;
    //private AccessToken accessToken;
    private final static String TAG = "UserRepository";

    private UserRepository() {
        mDatabase = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    public static UserRepository getInstance() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }


    @Override
    public Usuario getLoggedUser() {
        return CURRENT_USER;
    }

    @Override
    public void attachLoggedUser(String currentUserID) {
        getUserById(currentUserID, new CallbackQueryUser() {
            @Override
            public void onSuccessUserQueryById(Usuario user) {
                CURRENT_USER = user;
            }

            @Override
            public void onFailureUserQueryById(String msgError) {
                Log.e(TAG, "onFailQueryByID " + msgError);
            }
        });
    }

    @Override
    public void persistNewUserOnDatabase(Usuario newUser, final CallbackUserUpdate callbackNewUser) {
        DatabaseReference mRef = mDatabase.getReference();
        mRef.child(UserUtils.USER_CHILD)
                .child(newUser.getUid())
                .updateChildren(UserUtils.userToMap(newUser))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callbackNewUser.onSuccessUpdateUser();

                        } else {
                            callbackNewUser.onFailedUpdateUser(task.getException());
                        }
                    }
                });
    }

    @Override
    public void updateUser(Usuario currentUser, final CallbackUserUpdate callbackUserUpdate) {
        DatabaseReference mRef = mDatabase.getReference().child(UserUtils.USER_CHILD);
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
    public void sendMailVerication(FirebaseUser currentUser, final CallbackRepositorySendMail callback) {
        currentUser.sendEmailVerification()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callback.onSuccefulSendMail();
                        } else {
                            callback.onFailedSendMail(task.getException());
                        }
                    }
                });

    }

    @Override
    public FirebaseUser currentFirebaseUser() throws NullPointerException {
        if (mAuth.getCurrentUser() != null) {
            return mAuth.getCurrentUser();
        } else {
            return null;
        }
    }

    @Override
    public void logoutUser() {
        CURRENT_USER = null;
        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        if (accessToken != null && !accessToken.isExpired()) LoginManager.getInstance().logOut();
        mAuth.signOut();
    }

    @Override
    public void isUserRegisteredOnDatabase(FirebaseUser currentUser, CallbackIsUserRegistered callbackUserRegistered) {
        DatabaseReference mRef = mDatabase.getReference().child(UserUtils.USER_CHILD);

        Query mQuery = mRef.orderByKey().equalTo(currentUser.getUid()).limitToFirst(1);

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

    @Override
    public void getUserById(String id, CallbackQueryUser callbackUserById) {
        DatabaseReference mRef = mDatabase.getReference().child(UserUtils.USER_CHILD);
        Query query = mRef.orderByKey().equalTo(id).limitToFirst(1);

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario mCurrentUser = null;

                mCurrentUser = dataSnapshot.getChildren().iterator().next().getValue(Usuario.class);

                if (mCurrentUser != null) {
                    callbackUserById.onSuccessUserQueryById(mCurrentUser);
                } else callbackUserById.onFailureUserQueryById("Error de ID");

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callbackUserById.onFailureUserQueryById("Se cancelo");
            }
        });

    }

    // Syncronic but is not asdasd
    public Usuario getUserById(String id) {
        final TaskCompletionSource<List<Usuario>> tcs = new TaskCompletionSource<>();
        DatabaseReference mRef = mDatabase.getReference().child(UserUtils.USER_CHILD);
        Query query = mRef.orderByKey().equalTo(id).limitToFirst(1);
        final List<Usuario> currentUser = new ArrayList<>();

        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentUser.add(dataSnapshot.getChildren().iterator().next().getValue(Usuario.class));
                tcs.setResult(currentUser);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                tcs.setException(databaseError.toException());
            }
        });

        Task<List<Usuario>> resultTask = tcs.getTask();

        try {
            Tasks.await(resultTask);
        }catch (ExecutionException | InterruptedException e){
            resultTask = Tasks.forException(e);
        }

        if (resultTask.isSuccessful()){
            return resultTask.getResult().get(0);

        }else return null;
    }



}
