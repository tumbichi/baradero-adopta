package com.pity.baradopta.data.interactor.implementation;

import android.util.Log;

import com.pity.baradopta.data.interactor.interfaces.IAdopcionInteractor;
import com.pity.baradopta.data.modelos.Adopcion;
import com.pity.baradopta.data.modelos.Perro;
import com.pity.baradopta.data.modelos.Usuario;
import com.pity.baradopta.data.repository.implementacion.AdopcionRepository;
import com.pity.baradopta.data.repository.implementacion.DogRepository;
import com.pity.baradopta.data.repository.implementacion.UserRepository;
import com.pity.baradopta.data.repository.interfaces.IAdopcionRepository;
import com.pity.baradopta.data.repository.interfaces.IDogRepository;
import com.pity.baradopta.data.repository.interfaces.IUserRepository;
import com.pity.baradopta.ui.adoption.IAdopcionPresenter;

public class AdopcionInteractor implements IAdopcionInteractor {

    public final Adopcion adopcion;

    private final static String TAG = "AdopcionInteractor";

    public AdopcionInteractor(){
       adopcion = new Adopcion();
    }

    @Override
    public void attachRequestedData(String dog, String uploader, String adopter, IAdopcionPresenter.CallbackResquest callbackResquest) {
        adopcion.setADOPTER(UserRepository.getInstance().getCurrentUser());
        DogRepository.getInstance().queryDogBy(dog, new IDogRepository.CallbackQueryDog() {
            @Override
            public void onSucessQueryDog(Perro currentDog) {
                Log.e(TAG, "onSucessQueryDog \n" + currentDog.toString());
                adopcion.setDOG(currentDog);
                callbackResquest.onSuccessRequest();
            }

            @Override
            public void onFailureQueryDog(String msgError) {
                Log.e(TAG, "Adoption Error: " + msgError);
                callbackResquest.onFailureRequest(msgError);
            }
        });
        UserRepository.getInstance().getUserById(uploader, new IUserRepository.CallbackQueryUser() {
            @Override
            public void onSuccessUserQueryById(Usuario user) {
                Log.i(TAG, "Uploader{ \n" + user.toString() + "}");
                adopcion.setUPLOADER(user);
                callbackResquest.onSuccessRequest();

            }

            @Override
            public void onFailureUserQueryById(String msgError) {
                Log.e(TAG, "Adoption Error: " + msgError);
                callbackResquest.onFailureRequest(msgError);
            }
        });

    }

    @Override
    public void updatePhoneNumber(String phone, IUserRepository.CallbackUserUpdate callbackUserUpdate) {
        adopcion.getADOPTER().setTelefono(phone);
        UserRepository.getInstance().updateUser(adopcion.getADOPTER(), callbackUserUpdate);
    }

    @Override
    public void adoptionEvent(IAdopcionRepository.CallbackAdoption callbackAdoption) {
        AdopcionRepository.getInstance().registerAdoptionOnDatabase(adopcion, callbackAdoption);
    }

    @Override
    public void sendNotification(IAdopcionRepository.CallbackAdoption callbackAdoption) {
        AdopcionRepository.getInstance().registerSolicitudOnDatabase(adopcion.getUPLOADER().getUid(), adopcion.getADOPTER().getUid(), adopcion.getDOG().getDid(), callbackAdoption);
    }

    @Override
    public Adopcion getCurrentAdopcion() {
        return adopcion;
    }
}
