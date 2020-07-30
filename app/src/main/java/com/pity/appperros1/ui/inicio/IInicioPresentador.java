package com.pity.appperros1.ui.inicio;

import com.pity.appperros1.ui.base.IBasePresenter;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;

public interface IInicioPresentador extends IBasePresenter<IInicioView> {
    IInicioInteractor getInteractor();
    void logoutToFirebase();

}
