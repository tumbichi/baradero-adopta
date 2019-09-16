package com.pity.appperros1.ui.informacion_perro.implementation;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.pity.appperros1.R;
import com.pity.appperros1.base.BaseActivity;
import com.pity.appperros1.ui.informacion_perro.interfaces.IInformacionPerroView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InformacionPerroView extends BaseActivity<InformacionPerroPresenter>
        implements IInformacionPerroView {

    @BindView(R.id.informacion_perro_layout_adopcion)
    LinearLayout layoutAdopcion;
    @BindView(R.id.informacion_perro_layout_perdido)
    LinearLayout layoutPerdido;
    @BindView(R.id.informacion_perro_image_view_foto)
    ImageView imageViewFotoPerro;
    @BindView(R.id.informacion_perro_text_view_nombre)
    TextView textViewNombrePerro;
    @BindView(R.id.informacion_perro_text_view_descripcion)
    TextView textViewDescripcionPerro;
    @BindView(R.id.informacion_perro_image_view_genero)
    ImageView imageViewGenero;
    @BindView(R.id.informacion_perro_text_view_genero)
    TextView textViewGenero;
    @BindView(R.id.informacion_perro_image_view_tamanio)
    ImageView imageViewTamanio;
    @BindView(R.id.informacion_perro_text_view_tamanio)
    TextView textViewTamanio;
    @BindView(R.id.informacion_perro_image_view_edad)
    ImageView imageViewEdad;
    @BindView(R.id.informacion_perro_text_view_edad)
    TextView textViewEdad;
    @BindView(R.id.informacion_perro_image_view_vacuna)
    ImageView imageViewVacuna;
    @BindView(R.id.informacion_perro_text_view_vacuna)
    TextView textViewVacunado;
    @BindView(R.id.informacion_perro_image_view_castrado)
    ImageView imageViewCastrado;
    @BindView(R.id.informacion_perro_text_view_castrado)
    TextView textViewCastrado;
    @BindView(R.id.informacion_perro_button_contactar)
    Button buttonContactar;

    @BindView(R.id.informacion_perro_text_view_user_name)
    TextView textViewUserName;
    @BindView(R.id.informacion_perro_image_view_user)
    ImageView imageViewUserPhoto;

    @BindView(R.id.informacion_perro_view)
    LinearLayout mainView;

    @BindView(R.id.informacion_perro_progress_bar)
    ProgressBar progressBar;

    @Override
    public InformacionPerroPresenter createBasePresenter(Context context) {
        return new InformacionPerroPresenter(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_informacion_perro);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        String currentIDDog = getIntent().getData().toString();
        mPresenter.attachCurrentDogId(currentIDDog);


    }


    @Override
    public void setViewOfInformationDog(String nombre, String descripcion, String urlFoto, String genero,
                                        String tamanio, String edad, String vacunado, String castrado,
                                        ArrayList<Boolean> etiquetas) {


        textViewNombrePerro.setText(nombre);
        textViewDescripcionPerro.setText(descripcion);


        textViewGenero.setText(genero);
        attachIconGeneroOnView(genero);

        textViewTamanio.setText(tamanio);
        attachIconTamanioOnView(tamanio);

        textViewEdad.setText(edad);
        attachIconEdadOnView(edad);

        textViewVacunado.setText(vacunado);
        attachIconVacunadoOnView(vacunado);

        textViewCastrado.setText(castrado);
        attachIconCastradoOnView(castrado);

        showEtiqueta(etiquetas);

        Glide.with(this)
                .load(urlFoto)
                .fitCenter()
                .centerCrop()
                .into(imageViewFotoPerro);

        progressBar.setVisibility(View.GONE);
        mainView.setVisibility(View.VISIBLE);

    }

    @Override
    public void setViewOfInformationUser(String nombre, String urlFoto) {
        textViewUserName.setText(nombre);
        if (!TextUtils.isEmpty(urlFoto))
        Glide.with(this).load(urlFoto).fitCenter().centerCrop().into(imageViewUserPhoto);
    }


    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void showEtiqueta(ArrayList<Boolean> etiquetas){
        if (etiquetas.get(1)){
            layoutPerdido.setVisibility(View.GONE);
            layoutAdopcion.setVisibility(View.VISIBLE);
        }else if (etiquetas.get(2)){
            layoutAdopcion.setVisibility(View.GONE);
            layoutPerdido.setVisibility(View.VISIBLE);
            }

    }

    private void attachIconGeneroOnView(String genero){
        if (TextUtils.equals ("Macho" , genero)){
            Glide.with(this).load(R.drawable.macho).into(imageViewGenero);
        }else Glide.with(this).load(R.drawable.hembra).into(imageViewGenero);

    }

    private void attachIconTamanioOnView(String tamanio){
        if (TextUtils.equals ("Grande" ,tamanio)){
            Glide.with(this).load(R.drawable.grande).into(imageViewTamanio);
        }else{
            if (TextUtils.equals("Mediano", tamanio)) {
                Glide.with(this).load(R.drawable.mediano).into(imageViewTamanio);
            }   else Glide.with(this).load(R.drawable.pequnio).into(imageViewTamanio);
        }
    }

    private void attachIconEdadOnView(String edad){
        if (TextUtils.equals ("Adulto" , edad)){
            Glide.with(this).load(R.drawable.adulto).into(imageViewEdad);
        }else{
            if (TextUtils.equals("Cachorro", edad)) {
                Glide.with(this).load(R.drawable.cachorro).into(imageViewEdad);
            }else Glide.with(this).load(R.drawable.anciano).into(imageViewEdad);
        }
    }

    private void attachIconVacunadoOnView(String vacunado){
        if (TextUtils.equals ("Si" , vacunado)){
            Glide.with(this).load(R.drawable.vacuna).into(imageViewVacuna);
        }else{
            Glide.with(this).load(R.drawable.no_vacunado).into(imageViewVacuna);
        }
    }

    private void attachIconCastradoOnView(String castrado){
        if (TextUtils.equals ("Si" , castrado)){
            Glide.with(this).load(R.drawable.castrado).into(imageViewCastrado);
        }else{
            if (TextUtils.equals("No", castrado)) {
                Glide.with(this).load(R.drawable.no_castrado).into(imageViewCastrado);
            }else Glide.with(this).load(R.drawable.no_se_castrado).into(imageViewCastrado);
        }
    }



}
