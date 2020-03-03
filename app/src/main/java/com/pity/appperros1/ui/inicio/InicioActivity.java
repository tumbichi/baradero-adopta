package com.pity.appperros1.ui.inicio;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseActivity;
import com.pity.appperros1.data.interactor.implementation.InicioInteractor;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;
import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.ui.profile.ProfileView;
import com.pity.appperros1.ui.fragment_agregar_perro.implementation.AgregarPerroFragment;
import com.pity.appperros1.ui.informacion_perro.implementation.InformacionPerroView;
import com.pity.appperros1.ui.inicio.fragments.DogsPostFragment;
import com.pity.appperros1.ui.login.LoginView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InicioActivity extends BaseActivity<IInicioPresentador>
        implements IInicioView{

    @BindView(R.id.floatingActionButton)
    FloatingActionButton fab;
    @BindView(R.id.fragment_layout)
    FrameLayout fragmentLayout;
    @BindView(R.id.inicio_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.navigation_view)
    NavigationView navigationView;

    private FragmentManager fragmentManager;
    private DogsPostFragment postListFragment = null;
    private AgregarPerroFragment addDogFragment = null;

    @OnClick(R.id.floatingActionButton)
    public void onClickFab(FloatingActionButton clicked) {
        if (clicked == fab) {
            addDogFragment = new AgregarPerroFragment(this);
            fragmentManager
                    .beginTransaction().
                    setCustomAnimations(R.anim.slide_in_bottom, R.anim.slide_out_bottom)
                    .add(R.id.fragment_layout, addDogFragment)
                    .commit();
            fab.setVisibility(View.GONE);
        }
    }

    @Override
    public InicioPresentador createBasePresenter(Context context) {
        return new InicioPresentador(this, new InicioInteractor());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        ButterKnife.bind(this);
        setToolbar();

        /*if (UserRepository.getInstance().getLoggedUser() == null)
            UserRepository.getInstance().attachLoggedUser(UserRepository.getInstance().currentFirebaseUser().getUid(), );*/

        init();
        showPostsView(mPresenter.getInteractor());
    }

    @Override
    protected void onStart() {
        super.onStart();
        navigationView.getMenu().getItem(0).setChecked(true);
    }

    private void init(){
        fragmentManager = getSupportFragmentManager();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_perfil:
                        navigateToUserProfile();
                        return true;
                    case R.id.menu_logout:
                        mPresenter.logoutToFirebase();
                        return true;
                    default:
                        return false;
                }
            }
        });
        TextView navDrawerHeaderUsername = navigationView.getHeaderView(0).findViewById(R.id.navigation_drawer_header_username);
        navDrawerHeaderUsername.setText(UserRepository.getInstance().getLoggedUser().getDisplayName());
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void showPostsView(IInicioInteractor interactor) {
        postListFragment = DogsPostFragment.newInstance(interactor);
        fragmentManager.beginTransaction().add(R.id.fragment_layout, postListFragment).commit();
    }

    @Override
    public void hidePostsView() {
        fragmentManager.beginTransaction().remove(postListFragment).commit();
        postListFragment = null;
    }

    @Override
    public void hideAgregarPerroFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_top)
                .remove(addDogFragment)
                .commit();
        addDogFragment = null;
        fab.setVisibility(View.VISIBLE);
    }

    @Override
    public void navigateToLogin() {
        startActivity(new Intent(this, LoginView.class));
        finish();
    }

    @Override
    public void navigateToInformacionOf(Perro currentDog) {
        Intent intent = new Intent(this, InformacionPerroView.class);

        intent.setData(Uri.parse(currentDog.getDid()));
        startActivity(intent);
    }

    private void navigateToUserProfile() {
        startActivity(new Intent(this, ProfileView.class));
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPostListAdapter(ArrayList<Perro> postList) {
        if (postListFragment != null) {
            postListFragment.setListViewAdapter(postList);
        }
    }

    @Override
    public void notifyDataChangeForListView() {
        if (postListFragment != null) {
            postListFragment.notifyDataChangeForListView();
        }
    }

}
