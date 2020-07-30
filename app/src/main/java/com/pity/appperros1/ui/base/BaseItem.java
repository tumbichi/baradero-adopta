package com.pity.appperros1.ui.base;

import java.util.ArrayList;

public abstract class BaseItem {
    private String valor;
    private int icono;
    private ArrayList<BaseItem> items;

    public BaseItem(){
        this.items = new ArrayList<>();
    }

    public BaseItem(String valor, int icono){
        this.valor = valor;
        this.icono = icono;
    }

    public  String getValor(){
        return valor;
    }

    public int getIcono(){
        return icono;
    }

    public ArrayList<BaseItem> getItems(){
        return items;
    }

    public abstract int getIconoAtPosition(int position);

}
