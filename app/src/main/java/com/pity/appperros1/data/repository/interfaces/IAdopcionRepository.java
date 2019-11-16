package com.pity.appperros1.data.repository.implementacion;

import androidx.annotation.NonNull;

import com.pity.appperros1.data.modelos.PerroModel;
import com.pity.appperros1.data.modelos.Usuario;

interface IAdopcionRepository {
    interface CallbackAdoption{
        void onSuccesAdoption();
        void onFailedAdoption();
    }

    void registerAdoptionOnDatabase(@NonNull PerroModel dog, @NonNull Usuario uploader, @NonNull Usuario adopter, CallbackAdoption callbackAdoption);

}
