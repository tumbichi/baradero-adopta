package com.pity.appperros1.ui.inicio;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseActivity;
import com.pity.appperros1.data.interactor.implementation.InicioInteractor;
import com.pity.appperros1.ui.fragment_agregar_perro.implementation.AgregarPerroFragment;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;
import com.pity.appperros1.ui.login.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InicioActivity extends BaseActivity<IInicioPresentador> implements IInicioView {

    private AgregarPerroFragment fragment;

    @BindView(R.id.inicio_icon_button_logout)
    ImageView imgViewLogout;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton fab;
    @BindView(R.id.fragment_layout)
    FrameLayout fragmentLayout;
    @BindView(R.id.inicio_list_view)
    ListView postListView;

    @OnClick (R.id.inicio_icon_button_logout)
    public void onClickLogout(ImageView imgView){
        if (imgView == imgViewLogout){
            mPresenter.logoutToFirebase();

        }
    }

    @OnClick(R.id.floatingActionButton)
    public void onClickFab(FloatingActionButton clicked){
        if(clicked == fab){
            showFragment();
        }
    }



    @Override
    public InicioPresentador createBasePresenter(Context context) {
        return new InicioPresentador(this,  new InicioInteractor());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_inicio);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        fragment = new AgregarPerroFragment(this);


    }


    @Override
    public void showProgressDialog() {

    }

    @Override
    public void hideProgressDialog() {

    }

    @Override
    public void showFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom,R.anim.slide_out_bottom)
                .add(R.id.fragment_layout, fragment)
                .commit();
        fab.setVisibility(View.GONE);
    }

    @Override
    public void hideFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top)
                .remove(fragment)
                .commit();

        fab.setVisibility(View.VISIBLE);

    }

    @Override
    public void navigateToLogin() {
        finish();
        startActivity(new Intent(this, LoginView.class));

    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setListViewAdapter(InicioAdapter adapter) {
        postListView.setAdapter(adapter);
    }
}
