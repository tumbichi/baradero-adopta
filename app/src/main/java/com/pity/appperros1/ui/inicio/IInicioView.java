package com.pity.appperros1.ui.inicio;

import com.pity.appperros1.base.IBaseView;
import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;

import java.util.ArrayList;

public interface IInicioView extends IBaseView {

    void showProgressDialog();
    void hideProgressDialog();
    void showListView();
    void hideListView();
    void showFragment();
    void hideFragment();
    void navigateToLogin();
    void navigateToInformacionOf(Perro onClickedDog);
    void showToast(String msg);
    void setListViewAdapter(ArrayList<Perro> postList);
    InicioAdapter getListViewAdapter();
    void notifyDataChangeForListView();

}
