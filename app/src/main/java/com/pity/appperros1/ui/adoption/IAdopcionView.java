package com.pity.appperros1.ui.adoption;

import com.pity.appperros1.ui.base.IBaseView;

public interface IAdopcionView extends IBaseView {

    void populateUI(String email, String telefono);
    void showProgressBar();
    void hideProgressBar();
    void toast(String text);
    void killActivity();
}
