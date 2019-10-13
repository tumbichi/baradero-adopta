package com.pity.appperros1.data.interactor.implementation;

import com.pity.appperros1.data.interactor.interfaces.IAdopcionInteractor;
import com.pity.appperros1.data.modelos.PerroModel;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.implementacion.DogRepository;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.data.repository.interfaces.IDogRepository;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;

public class AdopcionInteractor implements IAdopcionInteractor {

    private Usuario mUploader;
    private Usuario mAdopter;
    private PerroModel mDog;
    private UserRepository mUserRepository;

    public AdopcionInteractor(){
        mUserRepository = UserRepository.getInstance();
    }

    @Override
    public void attachRequestedData(String dog, String uploader, String adopter) {
        DogRepository.getInstance().queryDogBy(dog, new IDogRepository.CallbackQueryDog() {
            @Override
            public void onSucessQueryDog(PerroModel currentDog) {
                mDog = currentDog;
            }

            @Override
            public void onFailureQueryDog(String msgError) {

            }
        });
        UserRepository.getInstance().getUserById(uploader, new IUserRepository.CallbackUserById() {
            @Override
            public void onSuccessUserQueryById(Usuario user) {
                mUploader = user;
            }

            @Override
            public void onFailureUserQueryById(String msgError) {

            }
        });
        mAdopter = mUserRepository

    }
}
