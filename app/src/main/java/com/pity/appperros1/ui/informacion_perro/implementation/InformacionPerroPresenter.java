package com.pity.appperros1.ui.informacion_perro.implementation;

import android.content.Context;

import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.modelos.PerroModel;
import com.pity.appperros1.data.repository.implementacion.DogRepository;
import com.pity.appperros1.data.repository.interfaces.IDogRepository;
import com.pity.appperros1.ui.informacion_perro.interfaces.IInformacionPerroPresenter;
import com.pity.appperros1.ui.informacion_perro.interfaces.IInformacionPerroView;

import java.util.ArrayList;

public class InformacionPerroPresenter extends BasePresenter<IInformacionPerroView>
        implements IInformacionPerroPresenter, IDogRepository.CallbackQueryDog {

    public InformacionPerroPresenter(Context context) {
        super(context);
    }


    private void bindRowsOfDogInformation(PerroModel perroModel) {
        String nombre = perroModel.getNombre();
        mView.toast(nombre);
        String descripcion = perroModel.getDescripcion();
        String imageUrl = perroModel.getUrlFoto();
        String genero = perroModel.getGenero();
        String tamanio = perroModel.getTamanio();
        String edad = perroModel.getEdad();
        String vacunado = perroModel.getVacunado();
        String castrado = perroModel.getEsterilizado();
        ArrayList<Boolean> etiquetas = (ArrayList<Boolean>) perroModel.getEtiquetas();

        mView.setViewsOfRows(nombre,descripcion,imageUrl,genero,tamanio,edad,vacunado,castrado,etiquetas);
    }

    @Override
    public void attachCurrentDogId(String currentId) {
        DogRepository.getInstance().queryDogBy(currentId, this);
    }


    @Override
    public void onSucessQueryDog(PerroModel currentDog) {
        bindRowsOfDogInformation(currentDog);
    }

    @Override
    public void onFailureQueryDog(String msgError) {
        mView.toast(msgError);
    }
}
