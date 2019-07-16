package com.pity.appperros1.ui.inicio;

import com.pity.appperros1.base.IBaseView;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;

public interface IInicioView extends IBaseView {

    void showProgressDialog();
    void hideProgressDialog();
    void showFragment();
    void hideFragment();
    void navigateToLogin();
    void showToast(String msg);
    void setListViewAdapter(InicioAdapter adapter);
}
