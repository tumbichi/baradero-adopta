package com.pity.appperros1.ui.inicio;

import com.pity.appperros1.base.IBasePresenter;

public interface IInicioPresentador extends IBasePresenter<IInicioView> {

    void logoutToFirebase();
    void onItemClickVerMas(int position);



}
