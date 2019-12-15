package com.pity.appperros1.data.repository.implementacion;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.pity.appperros1.data.modelos.Adopcion;
import com.pity.appperros1.data.repository.interfaces.IAdopcionRepository;
import com.pity.appperros1.utils.AdopcionUtils;

public class AdopcionRepository implements IAdopcionRepository {

    private static AdopcionRepository mInstance;
    private FirebaseDatabase database;

    private final static String TAG = "AdopcionRepository";
    private AdopcionRepository(){
        database = FirebaseDatabase.getInstance();
    }

    public static AdopcionRepository getInstance(){
        if (mInstance == null)  mInstance = new AdopcionRepository();
        return mInstance;
    }

    @Override
    public void registerAdoptionOnDatabase(@NonNull Adopcion adopcion, CallbackAdoption callbackAdoption) {
        DatabaseReference mRef = database.getReference().child("Adopciones").push();
        adopcion.setId( mRef.getKey());
        Log.i(TAG, adopcion.toString());
        mRef.updateChildren(AdopcionUtils.mapAdoption(adopcion.getId(), adopcion.getDOG().getDid(), adopcion.getUPLOADER().getUid(), adopcion.getADOPTER().getUid()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            callbackAdoption.onSuccesAdoption();
                        }else{
                            callbackAdoption.onFailedAdoption(task.getException());
                        }
                    }
                });
    }

    @Override
    public void registerSolicitudOnDatabase(String uploaderID, String adopterID, CallbackAdoption callbackAdoption) {
        DatabaseReference mRef = database.getReference().child("Usuarios").child(uploaderID).child("Solicitudes").push();
        //String solicitudID = mRef.getKey();

        mRef.updateChildren(AdopcionUtils.mapSolicitud(adopterID))
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            callbackAdoption.onSuccesAdoption();
                        }else callbackAdoption.onFailedAdoption(task.getException());
                    }
                });
    }


}
