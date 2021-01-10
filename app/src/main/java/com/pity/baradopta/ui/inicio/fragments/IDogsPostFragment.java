package com.pity.baradopta.ui.inicio.fragments;

import com.pity.baradopta.ui.base.IBaseView;
import com.pity.baradopta.data.modelos.Perro;
import com.pity.baradopta.ui.inicio.adapters.InicioAdapter;

import java.util.ArrayList;

interface IDogsPostFragment extends IBaseView {
    void setListViewAdapter(ArrayList<Perro> postList);
    InicioAdapter getListViewAdapter();
    void notifyDataChangeForListView();
}
