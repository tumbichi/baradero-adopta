package com.pity.baradopta.ui.fragment_agregar_perro.adapters.models_item_spinners;

import com.pity.baradopta.ui.base.BaseItem;

public class EstadoItem extends BaseItem {

    private final int NO_ICON = -1;

    public EstadoItem(){
        super();
        getItems().add(new EstadoItem("¿En que estado esta el animal?", NO_ICON));
        getItems().add(new EstadoItem("Adopcion", NO_ICON));
        getItems().add(new EstadoItem("Perdido", NO_ICON));

    }

    private EstadoItem(String estado, int icono){
        super(estado, icono);
    }


    @Override
    public int getIconoAtPosition(int position) {
        return NO_ICON;
    }
}
