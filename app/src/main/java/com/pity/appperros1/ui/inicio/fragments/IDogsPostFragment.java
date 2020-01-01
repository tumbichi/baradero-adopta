package com.pity.appperros1.ui.inicio.fragments;

import com.pity.appperros1.base.IBaseView;
import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;

import java.util.ArrayList;

interface IDogsPostFragment extends IBaseView {
    void setListViewAdapter(ArrayList<Perro> postList);
    InicioAdapter getListViewAdapter();
    void notifyDataChangeForListView();
}
