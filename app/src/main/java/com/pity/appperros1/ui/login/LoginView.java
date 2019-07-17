package com.pity.appperros1.ui.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseActivity;
import com.pity.appperros1.data.interactor.implementation.LoginIteractor;
import com.pity.appperros1.ui.inicio.InicioActivity;
import com.pity.appperros1.ui.olvidaste_contrasenia.OlvidasteContraseniaView;
import com.pity.appperros1.ui.registro.RegitroView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends BaseActivity<ILoginPresenter>
        implements ILoginView {

    @BindView(R.id.login_button_iniciar_sesion)  Button btnLogin;
    @BindView(R.id.login_button_registrate)  Button btnRegistrate;
    @BindView(R.id.text_view_login_olvidaste_password) TextView textViewOlvidasteContrasenia;
    @BindView(R.id.login_edit_text_usuario)  EditText editTextMail;
    @BindView(R.id.login_edit_text_password) EditText editTextContrasenia;
    @BindView(R.id.login_progress_bar)  ProgressBar progressBar;


    @Override
    public LoginPresenter createBasePresenter(Context context) {
        return new LoginPresenter(this, new LoginIteractor());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mPresenter.attachView(this);


    }

    @OnClick(R.id.login_button_iniciar_sesion)
    public void onClickLogin(View view){
        mPresenter.loginUserWith(editTextMail.getText().toString(),
                editTextContrasenia.getText().toString());
    }

    @OnClick(R.id.login_button_registrate)
    public void onClickRegistrate(View view){
        navigateTo(RegitroView.class);
    }

    @OnClick(R.id.text_view_login_olvidaste_password)
    public void onClickRegistrate(TextView textView){
        if (textView == this.textViewOlvidasteContrasenia) {
            navigateTo(OlvidasteContraseniaView.class);
        }

    }


    @Override
    public void navigateTo(Class activity){
        startActivity(new Intent(this,
                activity));
    }

    @Override
    public void navigateToInicio() {
        finish();
        startActivity(new Intent(this, InicioActivity.class));
    }



    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
