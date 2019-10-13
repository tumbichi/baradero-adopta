package com.pity.appperros1.ui.informacion_perro.interfaces;

import com.pity.appperros1.base.IBasePresenter;

public interface IInformacionPerroPresenter extends IBasePresenter<IInformacionPerroView> {

    void attachCurrentDogId(String currentId);
    void initDogAdoption();
}
