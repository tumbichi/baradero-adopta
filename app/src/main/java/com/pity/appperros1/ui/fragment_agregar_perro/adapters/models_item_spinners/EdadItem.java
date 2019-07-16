package com.pity.appperros1.ui.fragment_agregar_perro.adapters.models_item_spinners;

import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseItem;

public class EdadItem extends BaseItem {

    private final static int ICONO_EDAD = R.drawable.edad;
    private final static int ICONO_ADULTO = R.drawable.adulto;
    private final static int ICONO_CACHORRO = R.drawable.cachorro;
    private final static int ICONO_ANCIANO = R.drawable.anciano;
    private final static int NO_ICON = -1;

    public EdadItem() {
        getItems().add(new EdadItem("Edad", ICONO_EDAD));
        getItems().add(new EdadItem("Cachorro", ICONO_CACHORRO));
        getItems().add(new EdadItem("Adulto", ICONO_ADULTO));
        getItems().add(new EdadItem("Anciano", ICONO_ANCIANO));
    }


    private EdadItem(String edad, int iconoEdad){
        super(edad, iconoEdad);
    }



    @Override
    public int getIconoAtPosition(int position){
        switch (position){
            case 0:
                return ICONO_EDAD;
            case 1:
                return ICONO_CACHORRO;
            case 2:
                return ICONO_ADULTO;
            case 3:
                return ICONO_ANCIANO;
            default:
                return NO_ICON;
        }
    }



}
