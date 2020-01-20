package com.pity.appperros1.ui.profile;

import com.pity.appperros1.base.IBaseView;

public interface IProfileView extends IBaseView {

    void stackFragmentSolicitudes();
    void populateView(String displayName, String cantAdoptados, String cantPublicados, String cantEncontrados, String userImage);
    void showProgressBar();
    void hideProgressBar();
}
