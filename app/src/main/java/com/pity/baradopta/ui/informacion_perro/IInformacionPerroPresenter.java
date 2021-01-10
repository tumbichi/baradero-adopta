package com.pity.baradopta.ui.informacion_perro;

import com.pity.baradopta.ui.base.IBasePresenter;

public interface IInformacionPerroPresenter extends IBasePresenter<IInformacionPerroView> {

    void attachCurrentDogId(String currentId);
    void startDogAdoption();
}
