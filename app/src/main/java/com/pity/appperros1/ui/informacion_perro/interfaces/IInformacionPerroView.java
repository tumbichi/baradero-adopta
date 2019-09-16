package com.pity.appperros1.ui.informacion_perro.interfaces;

import com.pity.appperros1.base.IBaseView;

import java.util.ArrayList;

public interface IInformacionPerroView extends IBaseView {

    void setViewOfInformationDog(String nombre, String descripcion, String urlFoto, String genero,
                                 String tamanio, String edad, String vacunado, String castrado,
                                 ArrayList<Boolean> etiquetas);

    void setViewOfInformationUser(String nombre, String urlFoto);

    void toast(String msg);
}
