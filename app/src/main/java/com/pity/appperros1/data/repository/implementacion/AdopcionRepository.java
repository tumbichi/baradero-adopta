package com.pity.appperros1.data.repository.implementacion;

import android.util.Log;

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
        DatabaseReference mRef = database.getReference().child("Usuarios").child(uploaderID).child("Notifications").push();
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

    @Override
    public void getAdoptions(CallbackGetAdoptions callbackGetAdoptions) {
        DatabaseReference mRef = database.getReference().child("Usuarios").child(UserRepository.getInstance().getLoggedUser().getUid()).child("Adopciones");
        ArrayList<SolicitudReference> solicitudes = new ArrayList<>();
        mRef.addValueEventListener(new ValueEventListener() {
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
    public void deleteAdoption(String idAdoption) {
        DatabaseReference mRef = database.getReference()
                .child("Usuarios")
                .child(UserRepository.getInstance().getLoggedUser().getUid())
                .child("Adopciones")
                .child(idAdoption);

        mRef.removeValue(new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                if (databaseError == null){
                    Log.i(TAG,"Solicitud eliminada con exito! \n" + databaseReference.toString());
                }else{
                    Log.e(TAG, databaseError.getMessage());
                }
            }
        });

    }


}
