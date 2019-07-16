package com.pity.appperros1.ui.fragment_agregar_perro.adapters.models_item_spinners;

import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseItem;

public class VacunadoItem extends BaseItem {



    private final static int ICONO_VACUNADO = R.drawable.vacuna;
    private final static int ICONO_NO_VACUNADO = R.drawable.no_vacunado;
    private final static int NO_ICON = -1;



    public VacunadoItem(){
        super();
        getItems().add(new VacunadoItem("Vacunado", -1));
        getItems().add(new VacunadoItem("Si", ICONO_VACUNADO));
        getItems().add(new VacunadoItem("No", ICONO_NO_VACUNADO));

    }

    private VacunadoItem(String estado, int icono){
        super(estado, icono);
    }

    @Override
    public int getIconoAtPosition(int position){
        switch (position){
            case 1:
                return ICONO_VACUNADO;
            case 2:
                return ICONO_NO_VACUNADO;
            default:
                return NO_ICON;
        }
    }

}