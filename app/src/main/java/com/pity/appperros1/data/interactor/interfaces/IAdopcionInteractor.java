package com.pity.appperros1.data.interactor.interfaces;

import com.pity.appperros1.data.modelos.Adopcion;
import com.pity.appperros1.data.repository.interfaces.IAdopcionRepository;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;
import com.pity.appperros1.ui.adoption.IAdopcionPresenter;

public interface IAdopcionInteractor {

    void attachRequestedData(String dog, String uploader, String adopter, IAdopcionPresenter.CallbackResquest callbackResquest);
    void updatePhoneNumber(String phone, IUserRepository.CallbackUserUpdate callbackUserUpdate);
    void adoptionEvent(IAdopcionRepository.CallbackAdoption callbackAdoption);
    void sendNotification(IAdopcionRepository.CallbackAdoption callbackAdoption);
    Adopcion getCurrentAdopcion();

}
