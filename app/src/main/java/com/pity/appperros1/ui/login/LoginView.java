package com.pity.appperros1.ui.login;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import com.pity.appperros1.data.prefs.PreferencesManager;
import com.pity.appperros1.ui.base.BaseActivity;
import com.pity.appperros1.data.interactor.implementation.LoginIteractor;
import com.pity.appperros1.ui.inicio.InicioActivity;
import com.pity.appperros1.ui.olvidaste_contrasenia.OlvidasteContraseniaView;
import com.pity.appperros1.ui.registro.RegitroView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginView extends BaseActivity<ILoginPresenter> implements ILoginView {

    @BindView(R.id.login_button_iniciar_sesion) Button btnLogin;
    @BindView(R.id.login_button_registrate) Button btnRegistrate;
    @BindView(R.id.login_button_registrate_facebook) Button btnFacebook;
    @BindView(R.id.text_view_login_olvidaste_password) TextView textViewOlvidasteContrasenia;
    @BindView(R.id.login_edit_text_usuario)  EditText editTextMail;
    @BindView(R.id.login_edit_text_password) EditText editTextContrasenia;

    private AlertDialog progressDialog;

    @Override
    public LoginPresenter createBasePresenter(Context context) {
        LoginPresenter loginPresenter = new LoginPresenter(this, new LoginIteractor());
        loginPresenter.attachView(this);
        return loginPresenter;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        PreferencesManager.initializeInstance(getApplicationContext());
        createProgressDialog();
    }

    private void createProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle(getString(R.string.iniciando_sesion));
        progressDialog.setMessage(getString(R.string.aguarde));
    }

    @OnClick(R.id.login_button_iniciar_sesion)
    public void onClickLogin(View view){
        String email = editTextMail.getText().toString();
        String pass = editTextContrasenia.getText().toString();
        mPresenter.loginUserWith(email, pass);
    }

    @OnClick(R.id.login_button_registrate)
    public void onClickRegistrate(View view){
        navigateTo(RegitroView.class);
    }

    @OnClick(R.id.login_button_registrate_facebook)
    public void onClickFacebook(View view){
        mPresenter.manageLoginWithFacebook();
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
        startActivity(new Intent(LoginView.this, InicioActivity.class));
        finish();
    }

    @Override
    public Activity provideActivity() {
        return LoginView.this;
    }

    @Override
    public void showProgressBar() {
        progressDialog.show();
    }

    @Override
    public void hideProgressBar() {
        progressDialog.dismiss();
    }

    @Override
    public void enabledFacebookButton() {
        btnFacebook.setEnabled(true);
    }

    @Override
    public void disableFacebookButton() {
        btnFacebook.setEnabled(false);
    }

    @Override
    public void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }
}
