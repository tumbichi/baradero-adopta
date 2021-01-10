package com.pity.baradopta.data.interactor.interfaces;

import com.pity.baradopta.data.modelos.Adopcion;
import com.pity.baradopta.data.repository.interfaces.IAdopcionRepository;
import com.pity.baradopta.data.repository.interfaces.IUserRepository;
import com.pity.baradopta.ui.adoption.IAdopcionPresenter;

public interface IAdopcionInteractor {

    void attachRequestedData(String dog, String uploader, String adopter, IAdopcionPresenter.CallbackResquest callbackResquest);
    void updatePhoneNumber(String phone, IUserRepository.CallbackUserUpdate callbackUserUpdate);
    void adoptionEvent(IAdopcionRepository.CallbackAdoption callbackAdoption);
    void sendNotification(IAdopcionRepository.CallbackAdoption callbackAdoption);
    Adopcion getCurrentAdopcion();

}
