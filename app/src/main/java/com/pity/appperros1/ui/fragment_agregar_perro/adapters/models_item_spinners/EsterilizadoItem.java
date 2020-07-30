package com.pity.appperros1.ui.fragment_agregar_perro.adapters.models_item_spinners;

import com.pity.appperros1.R;
import com.pity.appperros1.ui.base.BaseItem;

public class EsterilizadoItem extends BaseItem {

    private final static int ICONO_CASTRADO = R.drawable.castrado;
    private final static int ICONO_NO_CASTRADO = R.drawable.no_castrado;
    private final static int ICONO_NOSE_CASTRADO = R.drawable.no_se_castrado;
    private final static int NO_ICON = -1;


    public EsterilizadoItem(){
        super();
        getItems().add(new EsterilizadoItem("Esterilizado", ICONO_NOSE_CASTRADO));
        getItems().add(new EsterilizadoItem("No se", ICONO_NOSE_CASTRADO));
        getItems().add(new EsterilizadoItem("Si", ICONO_CASTRADO));
        getItems().add(new EsterilizadoItem("No", ICONO_NO_CASTRADO));

    }

    private EsterilizadoItem(String estado, int icono){
        super(estado, icono);
    }

    @Override
    public int getIconoAtPosition(int position){
        switch (position){
            case 1:
                return ICONO_NOSE_CASTRADO;
            case 2:
                return ICONO_CASTRADO;
            case 3:
                return ICONO_NO_CASTRADO;
            default:
                return NO_ICON;
        }
    }





}