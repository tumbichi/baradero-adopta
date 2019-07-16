package com.pity.appperros1.ui.inicio;

import android.content.Context;

import com.pity.appperros1.R;
import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;

public class InicioPresentador extends BasePresenter<IInicioView>
            implements IInicioPresentador, IInicioInteractor.CallbackGetDogList {


    private IInicioInteractor mInteractor;
    private InicioAdapter mAdapter;


    public InicioPresentador(Context context, IInicioInteractor interactor){
        super(context);
        this.mInteractor = interactor;
        mInteractor.bringDogList(this);
    }


    @Override
    public void logoutToFirebase() {
        mInteractor.logout();
        mView.navigateToLogin();
    }

    @Override
    public void onSuccesGetDogList() {
        mView.showToast("Post cargados");
        this.mAdapter = new InicioAdapter(mContext, mInteractor.getListPost(), R.layout.item_post_list);
        mView.setListViewAdapter(mAdapter);
    }

    @Override
    public void onFailureDogList(String error) {
        mView.showToast(error);
    }
}
