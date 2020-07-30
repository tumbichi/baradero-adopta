package com.pity.appperros1.ui.fragment_agregar_perro.interfaces;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;

import com.pity.appperros1.ui.base.IBaseView;

public interface IAgregarPerroFragment extends IBaseView {

    void showProgressDialog();
    void hideProgressDialog();

    void showToastError(String msgError);
    void setImageViewFotoPerroTo(Bitmap bmFoto);
    void setImageViewFotoPerroTo(Uri uriFoto);
    void cleanFields();
    void navigateForResult(Intent intent, final int CODIGO_GALERIA);

}
