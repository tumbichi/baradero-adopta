package com.pity.appperros1.data.repository.implementacion;

import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.pity.appperros1.data.modelos.Adopcion;
import com.pity.appperros1.data.modelos.SolicitudReference;
import com.pity.appperros1.data.repository.interfaces.IAdopcionRepository;
import com.pity.appperros1.utils.AdopcionUtils;
import com.pity.appperros1.utils.UserUtils;

import java.util.ArrayList;

public class AdopcionRepository implements IAdopcionRepository {

    private static AdopcionRepository mInstance;
    private FirebaseDatabase database;

    private final static String TAG = "AdopcionRepository";

    private AdopcionRepository() {
        database = FirebaseDatabase.getInstance();
    }

    public static AdopcionRepository getInstance() {
        if (mInstance == null) mInstance = new AdopcionRepository();
        return mInstance;
    }

    @Override
    public void registerAdoptionOnDatabase(@NonNull Adopcion adopcion, CallbackAdoption callbackAdoption) {
        DatabaseReference mRef = database.getReference().child("Usuarios").child(adopcion.getUPLOADER().getUid()).child("Adopciones").push();
        adopcion.setId(mRef.getKey());
        Log.i(TAG, adopcion.toString());
        mRef.updateChildren(AdopcionUtils.mapAdoption(adopcion.getId(), adopcion.getDOG().getDid(), adopcion.getUPLOADER().getUid(), adopcion.getADOPTER().getUid()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callbackAdoption.onSuccesAdoption();
                        } else {
                            callbackAdoption.onFailedAdoption(task.getException());
                        }
                    }
                });
    }

    @Override
    public void registerSolicitudOnDatabase(String uploaderID, String adopterID, CallbackAdoption callbackAdoption) {
        DatabaseReference mRef = database.getReference().child(UserUtils.USER_DB_REF).child(uploaderID).child("Notifications").push();
        //String solicitudID = mRef.getKey();

        mRef.updateChildren(AdopcionUtils.mapSolicitud(adopterID))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            callbackAdoption.onSuccesAdoption();
                        } else callbackAdoption.onFailedAdoption(task.getException());
                    }
                });
    }

    public void getNotificationsCount(TextView badgeValue){
        DatabaseReference ref = database.getReference().child(UserUtils.USER_DB_REF).child(UserRepository.getInstance().getCurrentUser().getUid());

        ref.child(AdopcionUtils.ADOPTION_DB_REF).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ArrayList<String> notifications = new ArrayList<>();
                for (DataSnapshot data : dataSnapshot.getChildren()){
                    //HashMap<String, Object> notification = (HashMap<String, Object>) data.getValue();
                    notifications.add(data.getKey());
                }

                if (notifications.isEmpty()){
                    badgeValue.setText("");
                }else{
                    String value = Integer.toString(notifications.size());
                    badgeValue.setText(value);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void getAdoptions(CallbackGetAdoptions callbackGetAdoptions) {
        DatabaseReference mRef = database.getReference()
                .child("Usuarios")
                .child(UserRepository.getInstance().getCurrentUser().getUid())
                .child("Adopciones");
        ArrayList<SolicitudReference> solicitudes = new ArrayList<>();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SolicitudReference currentSolicitud = snapshot.getValue(SolicitudReference.class);

                    solicitudes.add(currentSolicitud);
                    Log.i(TAG, currentSolicitud.toString());
                }
                callbackGetAdoptions.onSuccessGetAdoptions(solicitudes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e(TAG, databaseError.getMessage());
                callbackGetAdoptions.onFailureGetAdoptions(databaseError.getMessage());
            }

        });
    }

    @Override
    public void getAdoptionsOfDog(String dogID, CallbackGetAdoptions callbackGetAdoptions) {
        ArrayList<SolicitudReference> solicitudes = new ArrayList<>();
        DatabaseReference mRef = database.getReference()
                .child(UserUtils.USER_DB_REF)
                .child(UserRepository.getInstance().getCurrentUser().getUid())
                .child(AdopcionUtils.ADOPTION_DB_REF);

        mRef.orderByChild(AdopcionUtils.DOG_ID_KEY).equalTo(dogID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    SolicitudReference currentSolicitud = snapshot.getValue(SolicitudReference.class);
                    solicitudes.add(currentSolicitud);
                    Log.i(TAG, currentSolicitud.toString());
                }
                callbackGetAdoptions.onSuccessGetAdoptions(solicitudes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    @Override
    public void deleteAdoption(String idAdoption, CallbackAdoption callbackAdoption) {
        DatabaseReference mRef = database.getReference()
                .child("Usuarios")
                .child(UserRepository.getInstance().getCurrentUser().getUid())
                .child("Adopciones")
                .child(idAdoption);

        mRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null){
                    Log.i(TAG,"Solicitud eliminada con exito! \n" + databaseReference.toString());
                    callbackAdoption.onSuccesAdoption();
                }else{
                    Log.e(TAG, databaseError.getMessage());
                    callbackAdoption.onFailedAdoption(new Exception(databaseError.getMessage()));
                }
            }
        });

    }


}
