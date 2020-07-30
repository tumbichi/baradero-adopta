package com.pity.appperros1.ui.adoption;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.textfield.TextInputEditText;
import com.hbb20.CountryCodePicker;
import com.pity.appperros1.R;
import com.pity.appperros1.ui.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdopcionView extends BaseActivity<AdopcionPresenter> implements IAdopcionView {


    @BindView(R.id.content_view_adoption)
    LinearLayout contentView;
    @BindView(R.id.adopcion_text_email)
    TextInputEditText editTextEmail;
    @BindView(R.id.adopcion_text_telefono)
    TextInputEditText editTextTelefono;
    /*@BindView(R.id.adopcion_country_code_picker)*/

    @BindView(R.id.adopcion_button_confirmar)
    Button buttonConfirmar;
    @BindView(R.id.progress_bar_adopcion)
    ProgressBar progressBar;
    CountryCodePicker countryCodePicker;






    @Override
    public AdopcionPresenter createBasePresenter(Context context) {
        return new AdopcionPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adopcion);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        showProgressBar();
        requestData();
        countryCodePicker = findViewById(R.id.adopcion_country_code_picker);
        countryCodePicker.setArrowColor(getResources().getColor(R.color.accent));
        onChangedCountryCodeListener(countryCodePicker);

    }

    private void requestData() {
        Intent mIntent = getIntent();
        mPresenter.requestDataFromDatabase(mIntent.getStringExtra("dog"), mIntent.getStringExtra("uploader"), mIntent.getStringExtra("adopter"));
    }

    @Override
    public void populateUI(String email, String telefono) {
        editTextEmail.setText(email);
        editTextTelefono.setText(telefono);
    }


    @Override
    public void showProgressBar() {
        contentView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
        contentView.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.adopcion_button_confirmar)
    public void onClickConfirmar(View view){
        mPresenter.onClickConfirmar(view, editTextEmail.getText().toString().trim(), countryCodePicker.getSelectedCountryCodeWithPlus() + editTextTelefono.getText().toString().trim());
    }

    public void onChangedCountryCodeListener(View view){
        countryCodePicker.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                toast("CCP = " + countryCodePicker.getSelectedCountryCodeWithPlus());
            }
        });
    }

    @Override
    public void toast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void killActivity() {
        finish();
    }
}
