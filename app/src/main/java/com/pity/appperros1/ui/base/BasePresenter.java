package com.pity.appperros1.ui.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;

public abstract class BasePresenter<TView extends IBaseView> implements IBasePresenter<TView> {

    protected Context mContext;
    protected TView view;


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
        this.view = view;
    }
    // desenlazar la vista
    @Override
    public void dettachView() {
        view = null;
    }
    // preguntar si la vista esta enlazada
    @Override
    public boolean isViewAttached() {
        return view != null;
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
