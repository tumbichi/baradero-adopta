package com.pity.baradopta.ui.profile;

import com.pity.baradopta.ui.base.IBaseView;

public interface IProfileView extends IBaseView {

    void stackFragmentSolicitudes();
    void populateView(String displayName, String cantAdoptados, String cantPublicados, String cantEncontrados, String userImage);
    void showProgressBar();
    void hideProgressBar();
}
