package com.pity.appperros1.ui.informacion_perro.implementation;

import android.content.Context;
import android.util.Log;

import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.modelos.PerroModel;
import com.pity.appperros1.data.modelos.Usuario;
import com.pity.appperros1.data.repository.implementacion.DogRepository;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.data.repository.interfaces.IDogRepository;
import com.pity.appperros1.data.repository.interfaces.IUserRepository;
import com.pity.appperros1.ui.informacion_perro.interfaces.IInformacionPerroPresenter;
import com.pity.appperros1.ui.informacion_perro.interfaces.IInformacionPerroView;

import java.util.ArrayList;

public class InformacionPerroPresenter extends BasePresenter<IInformacionPerroView>
        implements IInformacionPerroPresenter {

    private DogRepository mDogRepository;
    private UserRepository mUserRepository;

    private Usuario mCurrentUploader;
    private PerroModel mCurrentDog;

    InformacionPerroPresenter(Context context) {
        super(context);
        this.mDogRepository = DogRepository.getInstance();
        this.mUserRepository = UserRepository.getInstance();
    }


    private void bindRowsOfDogInformation(PerroModel perroModel) {
        String nombre = perroModel.getNombre();
        String descripcion = perroModel.getDescripcion();
        String imageUrl = perroModel.getUrlFoto();
        String genero = perroModel.getGenero();
        String tamanio = perroModel.getTamanio();
        String edad = perroModel.getEdad();
        String vacunado = perroModel.getVacunado();
        String castrado = perroModel.getEsterilizado();
        ArrayList<Boolean> etiquetas = (ArrayList<Boolean>) perroModel.getEtiquetas();

        mView.setViewOfInformationDog(nombre, descripcion, imageUrl, genero, tamanio, edad, vacunado, castrado, etiquetas);
        Log.d("InfoDogPresenter", "DogAttached!");
    }

    private void bindRowsOfUserInformation(Usuario uploader) {
        String nombre = uploader.getDisplayName();
        String urlFoto;
        if (uploader.getUrlFotoPerfil() == null) {
            urlFoto = "";
        } else urlFoto = uploader.getUrlFotoPerfil();
        mView.setViewOfInformationUser(nombre, urlFoto);
    }


    @Override
    public void attachCurrentDogId(String currentId) {
        mDogRepository.queryDogBy(currentId, new IDogRepository.CallbackQueryDog() {
            @Override
            public void onSucessQueryDog(PerroModel currentDog) {
                mCurrentDog = currentDog;
                bindRowsOfDogInformation(currentDog);
                mUserRepository.getUserById(currentDog.getUid(), new IUserRepository.CallbackUserById() {
                    @Override
                    public void onSuccessUserQueryById(Usuario user) {
                        mCurrentUploader = user;
                        bindRowsOfUserInformation(user);
                    }

                    @Override
                    public void onFailureUserQueryById(String msgError) {
                        mView.toast(msgError);
                    }
                });
            }

            @Override
            public void onFailureQueryDog(String msgError) {
                mView.toast(msgError);
            }
        });
    }

    @Override
    public void initDogAdoption() {
        String dogID = mCurrentDog.getDid();
        String uploaderID = mCurrentUploader.getUid();
        String adopterID = mUserRepository.currentFirebaseUser().getUid();

        mView.navigateToAdoption(dogID, uploaderID, adopterID);
    }


}
