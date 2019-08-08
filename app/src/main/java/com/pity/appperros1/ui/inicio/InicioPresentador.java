package com.pity.appperros1.ui.inicio;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;
import com.pity.appperros1.data.modelos.PerroModel;

public class InicioPresentador extends BasePresenter<IInicioView>
            implements IInicioPresentador, IInicioInteractor.CallbackGetDogList {

    private IInicioInteractor mInteractor;


     InicioPresentador(Context context, IInicioInteractor interactor){
        super(context);
        this.mInteractor = interactor;
        mInteractor.bringDogList(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mInteractor.getUserLogged();
        Log.e("UID" , currentUser.getUid());

    }

    @Override
    public void logoutToFirebase() {
        mInteractor.logout();
        mView.navigateToLogin();
    }

    @Override
    public void onItemClickVerMas(int position) {
        PerroModel perroModel = mInteractor.getListPost().get(position);
        mView.navigateToInformacionOf(perroModel);
    }

    @Override
    public void onSuccesGetDogList() {
        mView.hideProgressDialog();
        mView.showListView();
        mView.showToast("Post cargados");
        mView.setListViewAdapter(mInteractor.getListPost());
    }

    @Override
    public void onFailureDogList(String error) {
        mView.showToast(error);
    }


}
