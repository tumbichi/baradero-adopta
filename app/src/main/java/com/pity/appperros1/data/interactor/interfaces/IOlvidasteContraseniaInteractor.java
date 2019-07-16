package com.pity.appperros1.data.interactor.interfaces;

public interface IOlvidasteContraseniaInteractor {

    interface OnFinishListener {
        void onSuccess();
        void onFailed(Exception e);

    }

    void enviarEmailFromFirebaseTo(String email, OnFinishListener callbackListener);
}
