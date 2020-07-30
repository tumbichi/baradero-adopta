package com.pity.appperros1.ui.fragment_agregar_perro.adapters.models_item_spinners;

import com.pity.appperros1.R;
import com.pity.appperros1.ui.base.BaseItem;

public class TamanioItem extends BaseItem {



    private final static int ICONO_GRANDE = R.drawable.grande;
    private final static int ICONO_MEDIANO = R.drawable.mediano;
    private final static int ICONO_PEQUEÑO = R.drawable.pequnio;
    private final static int NO_ICON = -1;


    public TamanioItem(){
        super();
        getItems().add(new TamanioItem("Tamaño", -1));
        getItems().add(new TamanioItem("Grande", ICONO_GRANDE));
        getItems().add(new TamanioItem("Mediano", ICONO_MEDIANO));
        getItems().add(new TamanioItem("Pequeño" , ICONO_PEQUEÑO));
    }

    private TamanioItem (String tamanio, int icono){
        super(tamanio, icono);
    }

    @Override
    public int getIconoAtPosition(int position) {
        switch (position){
            case 1:
                return ICONO_GRANDE;
            case 2:
                return ICONO_MEDIANO;
            case 3:
                return ICONO_PEQUEÑO;
            default:
                return NO_ICON;
        }
    }

}