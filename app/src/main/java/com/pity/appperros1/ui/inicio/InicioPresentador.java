package com.pity.appperros1.ui.inicio;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.InicioInteractor;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;
import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;

import static android.content.Context.MODE_PRIVATE;

public class InicioPresentador extends BasePresenter<IInicioView>
            implements IInicioPresentador, IInicioInteractor.CallbackGetDogList {

    private InicioInteractor mInteractor;
    private SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefernces", MODE_PRIVATE);

     InicioPresentador(Context context, InicioInteractor interactor){
        super(context);
        this.mInteractor = interactor;
        mInteractor.bringDogList(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mInteractor.getUserLogged();
        Log.d("UID" , currentUser.getUid());

    }

    @Override
    public void logoutToFirebase() {
        mInteractor.logout(mContext);
        mView.navigateToLogin();
    }

    @Override
    public void onItemClickVerMas(int position, InicioAdapter adapter) {
        Perro perroModel = adapter.getItem(position);
        mView.navigateToInformacionOf(perroModel);
    }

    @Override
    public void onSuccesGetDogList() {
        mView.hideProgressDialog();
        mView.toast("Post cargados");
        mView.setPostListAdapter(mInteractor.getListPost());
    }

    @Override
    public void onFailureDogList(String error) {
        mView.toast(error);
    }

}
