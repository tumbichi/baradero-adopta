package com.pity.appperros1.ui.fragment_agregar_perro.implementation;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.pity.appperros1.base.BasePresenter;
import com.pity.appperros1.data.interactor.implementation.InicioInteractor;
import com.pity.appperros1.ui.fragment_agregar_perro.interfaces.IAgregarPerroFragment;
import com.pity.appperros1.ui.fragment_agregar_perro.interfaces.IAgregarPerroPresenter;
import com.pity.appperros1.ui.inicio.IInicioView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import static java.lang.System.out;

public class AgregarPerroPresenter extends BasePresenter<IAgregarPerroFragment>
        implements IAgregarPerroPresenter, IAgregarPerroPresenter.CallbackInteractor {

    private final static int CODIGO_CAMARA = 10;
    private final static int CODIGO_GALERIA = 20;
    private InicioInteractor mInteractor;
    private Activity mActivity;
    private IInicioView mViewParent;

    //private Uri currentPathFoto;

    public AgregarPerroPresenter(Context context, Activity activity, IInicioView parent) {
        super(context);
        this.mInteractor = new InicioInteractor();
        this.mActivity = activity;
        this.mViewParent = parent;
    }


    @Override
    public void onAttach(Context context) {
        mInteractor.createCurrentNewDog();
    }

    @Override
    public void onHideFragment() {
        mView.cleanFields();
        mInteractor.deleteCurrentNewDog();
    }



    private void openCamera() {
        final Intent intentFoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intentFoto.resolveActivity(mActivity.getPackageManager()) != null) {

            File fotoFile = null;

            try{
                fotoFile = createImageFile();

            }
            catch (IOException e){
                e.printStackTrace();
            }

            if (fotoFile != null) {
                Uri currentPathFoto;
                
                currentPathFoto = FileProvider.getUriForFile(mContext,
                        "com.pity.appperros1.android.FileProvider",
                        fotoFile);


                mInteractor.setCurrentPathPhoto(currentPathFoto);
                intentFoto.putExtra(MediaStore.EXTRA_OUTPUT, currentPathFoto);
                mView.navigateForResult(intentFoto, CODIGO_CAMARA);

            }

        }
    }

    private File createImageFile() throws IOException {
        String timeCreate = new SimpleDateFormat("ddMMyyyy_HHmmss", Locale.getDefault()).format(new Date());
        out.println("Fecha " + timeCreate);
        String imageFileName = "IMG_" + timeCreate + "_";
        File storageDir = mActivity.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        //String pathFoto = image.getAbsolutePath();
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }



    private void openGallery() {
        Intent intentGaleria = new Intent(Intent.ACTION_PICK);
        intentGaleria.setType("image/*");
        mView.navigateForResult(Intent.createChooser(intentGaleria, "Seleccione galeria preferida"), CODIGO_GALERIA);

    }

  
    @Override
    public void checkCurrentFields(String nombre, String descripcion, String genero, String edad,
                                   String tamanio, String castrado, String vacunado, int estado) {
        mView.showProgressDialog();

        if (!validateFields(nombre, descripcion, genero, edad, tamanio, castrado, vacunado, mInteractor.getCurrentPathPhoto(), estado)){
            mView.hideProgressDialog();
            return;
        }

        mInteractor.startUploadNewDog(nombre, descripcion, genero, edad, tamanio, castrado, vacunado, getEstados(estado),this);
    }

    private boolean validateFields(String nombre, String descripcion, String genero, String edad, String tamanio, String castrado, String vacunado, Uri pathFotoPerro, int estado){
        if (TextUtils.isEmpty(nombre)){
            mView.showToastError("Por favor, ingrese un email");
            return false;
        }

        if (TextUtils.isEmpty(descripcion)){
            mView.showToastError("Debe ingresar una breve descripcion, de su situacion con el animal");
            return false;
        }

        if (TextUtils.equals(genero, "Genero")){
            mView.showToastError("Debe ingresar el genero del perro");
            return false;
        }

        if (TextUtils.equals(edad, "Edad")){
            mView.showToastError("Debe ingresar la edad del perro");
            return false;
        }

        if (TextUtils.equals(tamanio, "Tamaño")){
            mView.showToastError("Debe ingresar de que tamaño es el perro");
            return false;
        }

        if (TextUtils.equals(castrado, "Esterilizado")){
            mView.showToastError("Debe ingresar, si el perro esta castrado, o no");
            return false;
        }

        if (TextUtils.equals(vacunado, "Vacunado")){
            mView.showToastError("Debe ingresar, si el perro esta vacunado, o no");
            return false;
        }

        if (pathFotoPerro == null){
            mView.showToastError("Debe subir al menos una foto del perro");
            return false;
        }

        if (estado == 0){
            mView.showToastError
                    ("El nuevo perro tiene que tener un estado, para darlo en adopcion o ser reportado como un perro perdido");
            return false;
        }
        return true;
    }

    private ArrayList<Boolean> getEstados(int estado){
        ArrayList<Boolean> estados = new ArrayList<>();
        estados.add(0, false);

        if (estado == 1){ estados.add(1, true) ;
        }else estados.add(1, false);

        if (estado == 2 ) {
            estados.add(2, true);
        } else estados.add(2, false);

        estados.add(3, false);
        estados.add(4, false);
        estados.add(5, false);

        return estados;
    }

    @Override
    public void checkPermissionCamera(){
        if (Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.CAMERA}, CODIGO_CAMARA);
                if (ContextCompat.checkSelfPermission(mActivity,
                        Manifest.permission.CAMERA) ==
                        PackageManager.PERMISSION_GRANTED) openCamera();
            }
         }else{
                // Older 23
                if (checkPermission(Manifest.permission.CAMERA)){
                    openCamera();
                }else{
                    mView.showToastError("Se necesitan permisos de la camara para seguir");
                }
            }
        }

    @Override
    public void checkPermissionGallery() {
        if (Build.VERSION.SDK_INT >= 23){
            if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                openGallery();
            }else {
                ActivityCompat.requestPermissions(mActivity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, CODIGO_GALERIA);
                if (ContextCompat.checkSelfPermission(mActivity,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) ==
                        PackageManager.PERMISSION_GRANTED) openGallery();
            }
        }else{
            // Older 23
            if (checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
                openGallery();
            }else{
                mView.showToastError("Se necesitan permisos de la storage para seguir");
            }
        }
    }

    private boolean checkPermission(String permission){ // comprueba si existe el permiso
        int resultado = ContextCompat.checkSelfPermission(mContext, permission);
        return resultado == PackageManager.PERMISSION_GRANTED;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            if (requestCode == CODIGO_CAMARA){
                Bitmap fotoBitmap = null;
                try {
                    fotoBitmap = MediaStore.Images.Media.getBitmap(mActivity.getContentResolver(), mInteractor.getCurrentPathPhoto());
                    mView.setImageViewFotoPerroTo(fotoBitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                //dataListener.sendData(bitmap);
                //imgViewFotoPerro.setImageURI(Uri.parse(rutaImageFile));
            }


            if (requestCode == CODIGO_GALERIA){
                Uri currentPathFoto = null;
                if (data != null) {
                    currentPathFoto = data.getData();
                    mInteractor.setCurrentPathPhoto(currentPathFoto);
                    mView.setImageViewFotoPerroTo(currentPathFoto);
                }



            }

        }
    }

    @Override
    public void onRequestPermissionResult(int requestCode, String[] permission, int[] grantResults) {
        super.onRequestPermissionResult(requestCode, permission, grantResults);

        if (requestCode == CODIGO_CAMARA) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openCamera();
            } else {
                mView.showToastError("Se necesitan permisos de la camara para seguir");
            }
        }


    }

    @Override
    public void onSuccessUploadPhoto() {
        mView.showToastError("Foto subida con éxito");
    }

    @Override
    public void onFailureUploadPhoto(String msg) {
        mView.showToastError(msg);
    }

    @Override
    public void onSuccesUploadDog() {
        mView.showToastError("Perro subido con exito!");
        mView.hideProgressDialog();
    }

    @Override
    public void onFailureUploadDog(String msg) {
        mView.showToastError(msg);
        mView.hideProgressDialog();
    }
}
