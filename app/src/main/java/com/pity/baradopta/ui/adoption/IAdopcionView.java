package com.pity.baradopta.ui.adoption;

import com.pity.baradopta.ui.base.IBaseView;

public interface IAdopcionView extends IBaseView {

    void populateUI(String email, String telefono);
    void showProgressBar();
    void hideProgressBar();
    void toast(String text);
    void finishAdoption(boolean result);
}
