package com.pity.appperros1.ui.informacion_perro;

import com.pity.appperros1.ui.base.IBaseView;

import java.util.ArrayList;

public interface IInformacionPerroView extends IBaseView {

    void populateDogView(String nombre, String descripcion, String urlFoto, String genero,
                         String tamanio, String edad, String vacunado, String castrado,
                         ArrayList<Boolean> etiquetas);

    void populateUserView(String nombre, String urlFoto);


    void navigateToAdoption(String dogID, String uploaderID, String adopterID);

    void hideContactButton();

    void toast(String msg);
}
