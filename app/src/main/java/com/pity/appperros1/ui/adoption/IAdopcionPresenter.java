package com.pity.appperros1.ui.adoption;

import com.pity.appperros1.base.IBasePresenter;

public interface IAdopcionPresenter extends IBasePresenter<IAdopcionView> {


    void requestDataFromDatabase(String dogID, String uploaderID, String adopterID);


}
