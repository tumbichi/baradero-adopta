package com.pity.appperros1.ui.registro;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.pity.appperros1.R;
import com.pity.appperros1.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegitroView extends BaseActivity<IRegistroPresenter>
            implements IRegistroView  {

    @BindView(R.id.edit_text_email_registro)
    EditText editTextMail;
    @BindView(R.id.edit_text_password_registro)
    EditText editTextPassword1;
    @BindView(R.id.edit_text_confirmar_password_registro)
    EditText editTextPassword2;
    @BindView(R.id.edit_text_nombre_registro)
    EditText editTextNombre;
    @BindView(R.id.edit_text_apellido_registro)
    EditText editTextApellido;
    @BindView(R.id.edit_text_telefono_registro)
    EditText editTextPhone;
    @BindView(R.id.registro_progress_bar)
    ProgressBar progressBar;
    @BindView(R.id.registro_button_registrate)
    Button btnRegistrate;


    @OnClick (R.id.registro_button_registrate)
    public void onClickRegistrate(Button btnRegistrate){
        if (btnRegistrate == this.btnRegistrate){
            mPresenter.sendRegistro(
                    editTextMail.getText().toString(),
                    editTextPassword1.getText().toString(),
                    editTextPassword2.getText().toString(),
                    editTextNombre.getText().toString(),
                    editTextApellido.getText().toString(),
                    editTextPhone.getText().toString()
            );
        }
    }

    @Override
    public RegistroPresenter createBasePresenter(Context context) {
        return new RegistroPresenter(context);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
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
    public void showError(String messageError) {
        Toast.makeText(this, messageError, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToastMessage(String msj) {
        Toast.makeText(this, msj, Toast.LENGTH_LONG).show();
    }

    @Override
    public void finishRegistro() {
        finish();
    }


}
