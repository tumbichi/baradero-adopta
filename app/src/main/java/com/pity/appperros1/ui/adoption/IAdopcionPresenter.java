package com.pity.appperros1.ui.adoption;

import android.view.View;

import com.pity.appperros1.ui.base.IBasePresenter;

public interface IAdopcionPresenter extends IBasePresenter<IAdopcionView> {


    void requestDataFromDatabase(String dogID, String uploaderID, String adopterID);
    void onClickConfirmar(View view, String email, String telefono);
    interface CallbackResquest{
        void onSuccessRequest();
        void onFailureRequest(String msgError);
    }

}
