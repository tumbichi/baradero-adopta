package com.pity.appperros1.ui.inicio;

import com.pity.appperros1.ui.base.IBaseView;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;
import com.pity.appperros1.data.modelos.Perro;

import java.util.ArrayList;

public interface IInicioView extends IBaseView {

    void showProgressDialog();
    void hideProgressDialog();
    void showPostsView(IInicioInteractor interactor);
    void hidePostsView();
    void hideAgregarPerroFragment();
    void navigateToLogin();
    void navigateToInformacionOf(Perro onClickedDog);
    void toast(String msg);
    void setPostListAdapter(ArrayList<Perro> postList);
    void notifyDataChangeForListView();


}
