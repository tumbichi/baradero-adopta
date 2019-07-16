package com.pity.appperros1.ui.fragment_agregar_perro.adapters.models_item_spinners;

import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseItem;

public class GeneroItem extends BaseItem {


    private final static int ICONO_MACHO = R.drawable.macho;
    private final static int ICONO_HEMBRA = R.drawable.hembra;
    private final static int ICONO_GENERO = R.drawable.generos;
    private final static int NO_ICON = -1;


    public GeneroItem(){
        super();
        getItems().add(new GeneroItem("Genero", ICONO_GENERO));
        getItems().add(new GeneroItem("Macho", ICONO_MACHO));
        getItems().add(new GeneroItem("Hembra" , ICONO_HEMBRA));

    }

    private GeneroItem (String genero, int iconoGenero){
        super(genero, iconoGenero);
    }

    @Override
    public int getIconoAtPosition(int position) {

        switch (position){
            case 0:
                return ICONO_GENERO;
            case 1:
                return ICONO_MACHO;
            case 2:
                return ICONO_HEMBRA;
            default:
                return NO_ICON;
        }
    }


}