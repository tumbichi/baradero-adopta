package com.pity.appperros1.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BasePresenter<TView extends IBaseView> implements IBasePresenter<TView> {

    protected Context mContext;
    protected TView mView;


    public BasePresenter(Context context){
        mContext = context;
    }


    // onCreate para activity
    @Override
    public void onCreate(final Bundle savedInstanceState){
    }
    // onCreate para Fragment


    // enlazar una vista al presenter
    @Override
    public void attachView(TView view) {
        mView = view;
    }
    // desenlazar la vista
    @Override
    public void dettachView() {
        mView = null;
    }
    // preguntar si la vista esta enlazada
    @Override
    public boolean isViewAttached() {
        return mView != null;
    }

    @Override
    public void onStart() { }

    @Override
    public void onStop() { }


    @Override
    public void onResume(){}

    @Override
    public void onPause(){}

    @Override
    public void onSave(final Bundle outState){}

    @Override
    public void onDestroy(){}

    @Override
    public void onActivityResult(final int requestCode, final int resultCode, @Nullable final Intent data){}

    @Override
    public void onRequestPermissionResult(int requestCode,   String[] permission,  int[] grantResults){}


}
