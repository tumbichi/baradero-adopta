package com.pity.appperros1.ui.inicio;

import com.pity.appperros1.base.IBaseView;
import com.pity.appperros1.data.modelos.PerroModel;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;

import java.util.ArrayList;

public interface IInicioView extends IBaseView {

    void showProgressDialog();
    void hideProgressDialog();
    void showFragment();
    void hideFragment();
    void navigateToLogin();
    void navigateToInformacionOf(PerroModel onClickedDog);
    void showToast(String msg);
    void setListViewAdapter(InicioAdapter adapter, ArrayList<PerroModel> postList);

}
