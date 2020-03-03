package com.pity.appperros1.ui.profile;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseActivity;
import com.pity.appperros1.ui.fragment_solcitudes.SolicitudesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileView extends BaseActivity<ProfilePresener> implements IProfileView {


    @BindView(R.id.profile_display_name)
    TextView displayNameTextView;
    @BindView(R.id.profile_cantidad_perros_adoptados)
    TextView cantidadPerrosAdoptadosTextView;
    @BindView(R.id.profile_cantidad_perros_publicados)
    TextView cantidadPerrosPublicadosTextView;
    @BindView(R.id.profile_cantidad_perros_encontrados)
    TextView cantidadPerrosEncontradosTextView;
    @BindView(R.id.profile_user_image)
    ImageView userImageView;
    @BindView(R.id.profile_back)
    ImageView backButton;
    @BindView(R.id.profile_progress_bar)
    ProgressBar progressBar;

    private FragmentManager fragmentManager;

    @Override
    public ProfilePresener createBasePresenter(Context context) {
        return new ProfilePresener(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        fragmentManager = getSupportFragmentManager();
        mPresenter.initView();
    }

    private void stackFragment(Fragment fragment) {
        fragmentManager.beginTransaction().add(R.id.content_solicitudes_fragment, fragment).commit();
    }

    @Override
    public void stackFragmentSolicitudes() {
        stackFragment(SolicitudesFragment.newInstance(this));
    }

    @Override
    public void populateView(String displayName, String cantAdoptados, String cantPublicados, String cantEncontrados, String userImage) {
        displayNameTextView.setText(displayName);
        cantidadPerrosAdoptadosTextView.setText(cantAdoptados);
        cantidadPerrosPublicadosTextView.setText(cantPublicados);
        cantidadPerrosEncontradosTextView.setText(cantEncontrados);
        Glide.with(this).load(userImage).fitCenter().centerCrop().into(userImageView);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileView.super.onBackPressed();
            }
        });

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    /*@OnClick({R.id.profile_cerrar_sesion})
    public void onClickLogout(View view){
        UserRepository.getInstance().logoutUser();
    }*/

}
