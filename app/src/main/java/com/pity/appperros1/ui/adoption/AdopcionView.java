package com.pity.appperros1.ui.adoption;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.pity.appperros1.base.BaseActivity;

public class AdopcionView extends BaseActivity<AdopcionPresenter> implements IAdopcionView {

    @Override
    public AdopcionPresenter createBasePresenter(Context context) {
        return new AdopcionPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();

        requestData();
    }

    private void requestData() {
        Intent mIntent = getIntent();
        mPresenter.requestDataFromDatabase(mIntent.getStringExtra("dog"), mIntent.getStringExtra("uploader"), mIntent.getStringExtra("adopter"));
    }

}
