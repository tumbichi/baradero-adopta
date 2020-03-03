package com.pity.appperros1.ui.inicio;

import com.pity.appperros1.base.IBasePresenter;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;

public interface IInicioPresentador extends IBasePresenter<IInicioView> {
    IInicioInteractor getInteractor();
    void logoutToFirebase();

}
