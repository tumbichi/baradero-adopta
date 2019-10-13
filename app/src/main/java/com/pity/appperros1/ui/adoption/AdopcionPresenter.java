package com.pity.appperros1.ui.adoption;

import android.content.Context;

import com.pity.appperros1.base.BasePresenter;

public class AdopcionPresenter extends BasePresenter<IAdopcionView> implements IAdopcionPresenter {

    public AdopcionPresenter(Context context) {
        super(context);

    }

    @Override
    public void requestDataFromDatabase(String dogID, String uploaderID, String adopterID) {

    }
}
