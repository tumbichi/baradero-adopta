package com.pity.appperros1.data.repository.interfaces;

import androidx.annotation.NonNull;

import com.pity.appperros1.data.modelos.Adopcion;

public interface IAdopcionRepository {
    interface CallbackAdoption{
        void onSuccesAdoption();
        void onFailedAdoption(Exception e);
    }

    void registerAdoptionOnDatabase(@NonNull Adopcion adopcion, CallbackAdoption callbackAdoption);
    void registerSolicitudOnDatabase(String uploaderID, String adopterID, CallbackAdoption callbackAdoption);

}
