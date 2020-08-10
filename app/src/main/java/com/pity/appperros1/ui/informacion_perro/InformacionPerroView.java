package com.pity.appperros1.ui.informacion_perro;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.pity.appperros1.R;
import com.pity.appperros1.ui.base.BaseActivity;
import com.pity.appperros1.ui.adoption.AdopcionView;
import com.pity.appperros1.utils.DogUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InformacionPerroView extends BaseActivity<InformacionPerroPresenter>
        implements IInformacionPerroView {

    /*@BindView(R.id.informacion_perro_layout_adopcion)
    LinearLayout layoutAdopcion;
    @BindView(R.id.informacion_perro_layout_perdido)
    LinearLayout layoutPerdido;*/
    @BindView(R.id.informacion_perro_image_view_foto)
    ImageView imageViewFotoPerro;
    @BindView(R.id.informacion_perro_text_view_nombre)
    TextView textViewNombrePerro;
    @BindView(R.id.informacion_perro_text_view_descripcion)
    TextView textViewDescripcionPerro;
    @BindView(R.id.informacion_perro_image_view_genero)
    ImageView imageViewGenero;
    /*@BindView(R.id.informacion_perro_text_view_genero)
    TextView textViewGenero;*/
    @BindView(R.id.informacion_perro_image_view_tamanio)
    ImageView imageViewTamanio;
    /*@BindView(R.id.informacion_perro_text_view_tamanio)
    TextView textViewTamanio;*/
    @BindView(R.id.informacion_perro_image_view_edad)
    ImageView imageViewEdad;
    /*@BindView(R.id.informacion_perro_text_view_edad)
    TextView textViewEdad;*/
    @BindView(R.id.informacion_perro_image_view_vacuna)
    ImageView imageViewVacuna;
    /*@BindView(R.id.informacion_perro_text_view_vacuna)
    TextView textViewVacunado;*/
    @BindView(R.id.informacion_perro_image_view_castrado)
    ImageView imageViewCastrado;
    /*@BindView(R.id.informacion_perro_text_view_castrado)
    TextView textViewCastrado;*/
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
        setContentView(R.layout.activity_informacion_perro);
        ButterKnife.bind(this);
        mPresenter.attachView(this);
        String currentIDDog = getIntent().getData().toString();
        mPresenter.attachCurrentDogId(currentIDDog);
        setToolbar();
    }

    private void setToolbar(){
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_info_dog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_share:
                checkPermissionForShareDog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private int CODIGO_GALERIA = 100;

    public void checkPermissionForShareDog() {
        if (Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(this , Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                shareDog();
            }else {
                ActivityCompat.requestPermissions(this , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODIGO_GALERIA);
                
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                    shareDog();
            }
        }else{
            // Older 23
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                shareDog();
            }else{
                toast("Se necesitan permisos de la storage para seguir");
            }
        }
    }

    private boolean checkPermission(String permission){ // comprueba si existe el permiso
        int resultado = ContextCompat.checkSelfPermission(this, permission);
        return resultado == PackageManager.PERMISSION_GRANTED;
    }



    private void shareDog() {
        Drawable drawable = imageViewFotoPerro.getDrawable();
        Bitmap bitmap = ((BitmapDrawable)drawable).getBitmap();

        String path = MediaStore.Images.Media.insertImage(this.getContentResolver(),
                bitmap, textViewNombrePerro.getText().toString(), null);

        Uri uri = Uri.parse(path);

        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/*");
        share.putExtra(Intent.EXTRA_STREAM, uri);
        share.putExtra(Intent.EXTRA_TEXT, textViewNombrePerro.getText().toString() + " Descripcion: " + textViewDescripcionPerro.getText().toString());
        startActivity(Intent.createChooser(share, "Eliga el medio donde quiera compartir a este perro"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return super.onSupportNavigateUp();
    }

    @Override
    public void populateDogView(String nombre, String descripcion, String urlFoto, String genero,
                                String tamanio, String edad, String vacunado, String castrado,
                                ArrayList<Boolean> etiquetas) {

        getSupportActionBar().setTitle(nombre);
        getSupportActionBar().setSubtitle(etiquetas.get(DogUtils.ETIQUETA_ADOPCION_ID) ? DogUtils.ETIQUETA_ADOPCION : DogUtils.ETIQUETA_PERDIDO);
        textViewNombrePerro.setText(nombre);
        textViewDescripcionPerro.setText(descripcion);

       /* textViewGenero.setText(genero);*/
        attachIconGeneroOnView(genero);

        /*textViewTamanio.setText(tamanio);*/
        attachIconTamanioOnView(tamanio);

        /*textViewEdad.setText(edad);*/
        attachIconEdadOnView(edad);

        /*textViewVacunado.setText(vacunado);*/
        attachIconVacunadoOnView(vacunado);

        /*textViewCastrado.setText(castrado);*/
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
    public void populateUserView(String nombre, String urlFoto) {
        textViewUserName.setText(nombre);
        if (!TextUtils.isEmpty(urlFoto))
        Glide.with(this).load(urlFoto).fitCenter().centerCrop().into(imageViewUserPhoto);
    }

    @Override
    public void navigateToAdoption(String dogID, String uploaderID, String adopterID) {
        Intent intentAdoption = new Intent(InformacionPerroView.this, AdopcionView.class);
        intentAdoption.putExtra("dog", dogID);
        intentAdoption.putExtra("uploader", uploaderID);
        intentAdoption.putExtra("adopter", adopterID);
        startActivity(intentAdoption);
    }

    @Override
    public void hideContactButton() {
        buttonContactar.setVisibility(View.GONE);
    }

    @Override
    public void toast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }

    private void showEtiqueta(ArrayList<Boolean> etiquetas){
        /*if (etiquetas.get(1)){
            layoutPerdido.setVisibility(View.GONE);
            layoutAdopcion.setVisibility(View.VISIBLE);
        }else
            if (etiquetas.get(2)){
                layoutAdopcion.setVisibility(View.GONE);
                layoutPerdido.setVisibility(View.VISIBLE);
        }*/

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


    @OnClick(R.id.informacion_perro_button_contactar)
    public void onClickIniciarAdopcion(View clicked){
        AlertDialog.Builder mDialog = new AlertDialog.Builder(this);
        mDialog.setTitle("¿Esta seguro que quiere adoptar este perro? ");
        mDialog.setMessage("Esto implica que debera proporcionar un numero y email de contacto");
        mDialog.setCancelable(false);
        mDialog.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mPresenter.initDogAdoption();
            }
        });
        mDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                toast("shameeee");
            }
        });
        mDialog.show();
    }
}
