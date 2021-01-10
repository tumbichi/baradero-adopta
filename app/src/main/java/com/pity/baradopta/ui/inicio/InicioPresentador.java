package com.pity.baradopta.ui.inicio;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.pity.baradopta.ui.base.BasePresenter;
import com.pity.baradopta.data.interactor.implementation.InicioInteractor;
import com.pity.baradopta.data.interactor.interfaces.IInicioInteractor;

public class InicioPresentador extends BasePresenter<IInicioView> implements IInicioPresentador {

    private InicioInteractor mInteractor;
    //private SharedPreferences sharedPreferences = mContext.getSharedPreferences("prefernces", MODE_PRIVATE);

     InicioPresentador(Context context, InicioInteractor interactor){
        super(context);
        this.mInteractor = interactor;

         FirebaseUser currentUser = mInteractor.getUserLogged();
         Log.d("UID" , currentUser.getUid());
    }

    @Override
    public IInicioInteractor getInteractor(){
         return mInteractor;
    }

    @Override
    public void logoutToFirebase() {
        mInteractor.logoutUser();
        view.navigateToLogin();
    }

}
