package com.pity.appperros1.ui.inicio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseActivity;
import com.pity.appperros1.data.interactor.implementation.InicioInteractor;
import com.pity.appperros1.data.modelos.PerroModel;
import com.pity.appperros1.ui.fragment_agregar_perro.implementation.AgregarPerroFragment;
import com.pity.appperros1.ui.informacion_perro.implementation.InformacionPerroView;
import com.pity.appperros1.ui.inicio.adapters.InicioAdapter;
import com.pity.appperros1.ui.login.LoginView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class InicioActivity extends BaseActivity<IInicioPresentador>
        implements IInicioView, View.OnClickListener {

    private AgregarPerroFragment fragment;
    private InicioAdapter mAdapter;

    @BindView(R.id.inicio_icon_button_logout)
    ImageView imgViewLogout;
    @BindView(R.id.floatingActionButton)
    FloatingActionButton fab;
    @BindView(R.id.fragment_layout)
    FrameLayout fragmentLayout;
    @BindView(R.id.inicio_list_view)
    ListView postListView;
    @BindView(R.id.inicio_progress_bar)
    ProgressBar progressBar;


    @OnClick(R.id.inicio_icon_button_logout)
    public void onClickLogout(ImageView imgView) {
        if (imgView == imgViewLogout) {
            mPresenter.logoutToFirebase();

        }
    }

    @OnClick(R.id.floatingActionButton)
    public void onClickFab(FloatingActionButton clicked) {
        if (clicked == fab) {
            showFragment();
        }
    }


    @Override
    public InicioPresentador createBasePresenter(Context context) {
        return new InicioPresentador(this, new InicioInteractor());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_inicio);
        ButterKnife.bind(this);
        fragment = new AgregarPerroFragment(this);
        Log.e("InicioView", "onCreate");


    }


    @Override
    public void showProgressDialog() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressDialog() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showListView() {
        postListView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideListView() {
        postListView.setVisibility(View.GONE);
    }

    @Override
    public void showFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
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
        startActivity(new Intent(this, LoginView.class));
        finish();
    }

    @Override
    public void navigateToInformacionOf(PerroModel currentDog) {
        Intent intent = new Intent(this, InformacionPerroView.class);

        intent.setData(Uri.parse(currentDog.getDid()));
        startActivity(intent);
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setListViewAdapter(ArrayList<PerroModel> postList) {
        mAdapter = new InicioAdapter(this, postList, R.layout.item_post_list, this);
        postListView.setAdapter(mAdapter);
    }

    @Override
    public void notifyDataChangeForListView() {
        mAdapter.notifyDataSetChanged();
    }

    @OnItemClick(R.id.inicio_list_view)
    public void onItemClickListener(AdapterView<?> parent, int position) {
        Log.e("InicioView" , "onItemClickListener(" + parent.getAdapter().getItem(position) + ")");
    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.post_button_ver_mas) {
            final int position = postListView.getPositionForView(v);
            mPresenter.onItemClickVerMas(position);
        }
    }
}
