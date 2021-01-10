package com.pity.baradopta.ui.olvidaste_contrasenia;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pity.baradopta.R;
import com.pity.baradopta.ui.base.BaseActivity;
import com.pity.baradopta.data.interactor.implementation.OlvidasteContraseniaInteractor;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OlvidasteContraseniaView extends BaseActivity<IOlvidasteContraseniaPresenter>
            implements IOlvidasteContraseniaView{

    @BindView(R.id.edit_text_email_olvidate_contrasenia)
    EditText editTextOlvidasteContrasenia;
    @BindView(R.id.button_enviar_olvidaste_contrasenia)
    Button buttonOlvidasteContrasenia;
    @BindView(R.id.olvidaste_pass_progress_bar)
    ProgressBar progressBar;

    @OnClick (R.id.button_enviar_olvidaste_contrasenia)
    public void onClickEnviar(View view){
        mPresenter.enviarMailDeRecuperacionA(editTextOlvidasteContrasenia.getText().toString());
    }

    @Override
    public OlvidasteContraseniaPresenter createBasePresenter(Context context) {
        return new OlvidasteContraseniaPresenter(context, new OlvidasteContraseniaInteractor());
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olvidaste_contrasenia);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
    }


    @Override
    public void navagateToLogin() {
        finish();
    }

    @Override
    public void showError(String messageError) {
        Toast.makeText(this, messageError, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


}
