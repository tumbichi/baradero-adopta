package com.pity.appperros1.base;

import android.content.Intent;
import android.os.Bundle;

public interface IBasePresenter<TView extends IBaseView> {

    void attachView(TView view);

    void dettachView();

    boolean isViewAttached();

    void onCreate(Bundle savedState);

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    void onSave(Bundle outState);

    void onActivityResult(int requestCode, int resultCode, Intent data);

    void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults);


}
