package com.pity.appperros1.data.repository.interfaces;

import androidx.annotation.NonNull;

import com.pity.appperros1.data.modelos.Adopcion;
import com.pity.appperros1.data.modelos.SolicitudReference;

import java.util.ArrayList;

public interface IAdopcionRepository {
    interface CallbackAdoption{
        void onSuccesAdoption();
        void onFailedAdoption(Exception e);
    }
    interface CallbackGetAdoptions{
        void onSuccessGetAdoptions(ArrayList<SolicitudReference> adoptions);
        void onFailureGetAdoptions(String msgError);
    }

    void registerAdoptionOnDatabase(@NonNull Adopcion adopcion, CallbackAdoption callbackAdoption);
    void registerSolicitudOnDatabase(String uploaderID, String adopterID, String dogID,  CallbackAdoption callbackAdoption);
    void getAdoptions(CallbackGetAdoptions callbackGetAdoptions);
    void getAdoptionsOfDog(String dogID, CallbackGetAdoptions callbackGetAdoptions);
    void deleteAdoption(String idAdoption, CallbackAdoption callbackAdoption);

}
