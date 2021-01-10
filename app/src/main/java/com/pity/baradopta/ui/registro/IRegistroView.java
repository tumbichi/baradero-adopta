package com.pity.baradopta.ui.registro;

import com.pity.baradopta.ui.base.IBaseView;

public interface IRegistroView extends IBaseView {
    void showProgressBar();
    void hideProgressBar();
    void toast(String msg);
    void backToLogin();
}
