package com.pity.appperros1.ui.informacion_perro;

import com.pity.appperros1.ui.base.IBasePresenter;

public interface IInformacionPerroPresenter extends IBasePresenter<IInformacionPerroView> {

    void attachCurrentDogId(String currentId);
    void startDogAdoption();
}
