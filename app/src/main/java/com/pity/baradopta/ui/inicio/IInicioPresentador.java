package com.pity.baradopta.ui.inicio;

import com.pity.baradopta.ui.base.IBasePresenter;
import com.pity.baradopta.data.interactor.interfaces.IInicioInteractor;

public interface IInicioPresentador extends IBasePresenter<IInicioView> {
    IInicioInteractor getInteractor();
    void logoutToFirebase();

}
