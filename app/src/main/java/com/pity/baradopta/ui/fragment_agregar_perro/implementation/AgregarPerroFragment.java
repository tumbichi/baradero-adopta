package com.pity.baradopta.ui.fragment_agregar_perro.implementation;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.pity.baradopta.R;
import com.pity.baradopta.ui.base.BaseFragment;
import com.pity.baradopta.ui.base.BaseItem;
import com.pity.baradopta.ui.fragment_agregar_perro.adapters.AdapterItemSpinner;
import com.pity.baradopta.ui.fragment_agregar_perro.adapters.models_item_spinners.EdadItem;
import com.pity.baradopta.ui.fragment_agregar_perro.adapters.models_item_spinners.EstadoItem;
import com.pity.baradopta.ui.fragment_agregar_perro.adapters.models_item_spinners.EsterilizadoItem;
import com.pity.baradopta.ui.fragment_agregar_perro.adapters.models_item_spinners.GeneroItem;
import com.pity.baradopta.ui.fragment_agregar_perro.adapters.models_item_spinners.TamanioItem;
import com.pity.baradopta.ui.fragment_agregar_perro.adapters.models_item_spinners.VacunadoItem;
import com.pity.baradopta.ui.fragment_agregar_perro.interfaces.IAgregarPerroFragment;
import com.pity.baradopta.ui.inicio.IInicioView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AgregarPerroFragment extends BaseFragment<AgregarPerroPresenter>
        implements IAgregarPerroFragment {

    @BindView(R.id.button_image_view_esconder_fragment)
    ImageView imgViewBtnCerrar;
    @BindView(R.id.edit_text_nombre_nuevo_perro)
    EditText editTextNombrePerro;
    @BindView(R.id.edit_text_descripcion_nuevo_perro)
    EditText editTextDescripcionPerro;

    @BindView(R.id.spinner_seleccionar_estado)
    Spinner spinnerEstado;
    @BindView(R.id.spinner_seleccionar_genero)
    Spinner spinnerGenero;
    @BindView(R.id.spinner_seleccionar_edad)
    Spinner spinnerEdad;
    @BindView(R.id.spinner_seleccionar_tamanio)
    Spinner spinnerTamanio;
    @BindView(R.id.spinner_seleccionar_castracion)
    Spinner spinnerCastrado;
    @BindView(R.id.spinner_seleccionar_vacunacion)
    Spinner spinnerVacunado;

    @BindView(R.id.image_view_nuevo_perro)
    ImageView imageViewPerro;

    @BindView(R.id.button_abrir_camara)
    ImageButton buttonCamara;
    @BindView(R.id.button_abrir_galeria)
    ImageButton buttonGaleria;
    @BindView(R.id.button_agregar_nuevo_perro)
    Button buttonAgregar;

    @BindView(R.id.fragment_progress_dialog)
    ProgressBar progressBar;

    private AdapterItemSpinner adapterItemGenero;
    private AdapterItemSpinner adapterItemEdad;
    private AdapterItemSpinner adapterItemTamanio;
    private AdapterItemSpinner adapterItemEsterilizado;
    private AdapterItemSpinner adapterItemVacunado;
    private AdapterItemSpinner adapterItemEstado;

    private IInicioView mActivityView;

    public AgregarPerroFragment(IInicioView view){
        this.mActivityView = view;
    }

    @Override
    public AgregarPerroPresenter initializePresenter(Context context) {
        return new AgregarPerroPresenter(context, getActivity(), mActivityView);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter.onAttach(context);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_agregar_post, container, false);
        ButterKnife.bind(this, view);
        mPresenter.attachView(this);
        bindAdapterSpinner();
        return view;
    }

    private void bindAdapterSpinner(){
        BaseItem item;

        item = new GeneroItem();
        adapterItemGenero = new AdapterItemSpinner(getContext(), item.getItems(), R.layout.item_spinner_agregar_perro);
        spinnerGenero.setAdapter(adapterItemGenero);

        item = new EdadItem();
        adapterItemEdad = new AdapterItemSpinner(getContext(), item.getItems(), R.layout.item_spinner_agregar_perro);
        spinnerEdad.setAdapter(adapterItemEdad);

        item = new TamanioItem();
        adapterItemTamanio = new AdapterItemSpinner(getContext(), item.getItems(), R.layout.item_spinner_agregar_perro);
        spinnerTamanio.setAdapter(adapterItemTamanio);

        item = new EsterilizadoItem();
        adapterItemEsterilizado = new AdapterItemSpinner(getContext(), item.getItems(), R.layout.item_spinner_agregar_perro);
        spinnerCastrado.setAdapter(adapterItemEsterilizado);

        item = new VacunadoItem();
        adapterItemVacunado = new AdapterItemSpinner(getContext(), item.getItems(), R.layout.item_spinner_agregar_perro);
        spinnerVacunado.setAdapter(adapterItemVacunado);

        item = new EstadoItem();
        adapterItemEstado = new AdapterItemSpinner(getContext(), item.getItems(), R.layout.item_spinner_agregar_perro);
        spinnerEstado.setAdapter(adapterItemEstado);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mPresenter.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        mPresenter.onRequestPermissionResult(requestCode, permissions, grantResults);
    }


    @OnClick(R.id.button_agregar_nuevo_perro)
    public void onClickAgregar(Button clicked) {
        //mPresenter.getIteamAtSpinner();
        //spinnerGenero.getSelectedItem();

        if (clicked == buttonAgregar){
            mPresenter.saveDog(
                    editTextNombrePerro.getText().toString().trim(),
                    editTextDescripcionPerro.getText().toString().trim(),
                    adapterItemGenero.getItem(spinnerGenero.getSelectedItemPosition()).getValor(),
                    adapterItemEdad.getItem(spinnerEdad.getSelectedItemPosition()).getValor(),
                    adapterItemTamanio.getItem(spinnerTamanio.getSelectedItemPosition()).getValor(),
                    adapterItemEsterilizado.getItem(spinnerCastrado.getSelectedItemPosition()).getValor(),
                    adapterItemVacunado.getItem(spinnerVacunado.getSelectedItemPosition()).getValor(),
                    spinnerEstado.getSelectedItemPosition()
            );
        }
    }

    @OnClick(R.id.button_abrir_camara)
    public void onClickCamara(ImageButton clicked){
        if (clicked == buttonCamara) mPresenter.checkPermissionCamera();
    }

    @OnClick(R.id.button_abrir_galeria)
    public void onClickGaleria(ImageButton clicked){
        if (clicked == buttonGaleria) mPresenter.checkPermissionGallery();
    }

    @OnClick(R.id.button_image_view_esconder_fragment)
    public void onClickCerrar(ImageView clicked){
        if (clicked == imgViewBtnCerrar) {
            mActivityView.hideAgregarPerroFragment();
            mPresenter.onHideFragment();
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
    public void showToastError(String msgError) {
        Toast.makeText(getContext(), msgError, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setImageViewFotoPerroTo(Bitmap bmFoto) {
        imageViewPerro.setImageBitmap(bmFoto);
    }

    @Override
    public void setImageViewFotoPerroTo(Uri uriFoto) {
        imageViewPerro.setImageURI(uriFoto);
    }

    @Override
    public void cleanFields() {
        editTextNombrePerro.setText("");
        editTextNombrePerro.setHint("Nombre del perro");
        editTextDescripcionPerro.setText("");
        editTextDescripcionPerro.setHint("Descripcion");

        spinnerGenero.setSelection(0);
        spinnerEdad.setSelection(0);
        spinnerTamanio.setSelection(0);
        spinnerCastrado.setSelection(0);
        spinnerVacunado.setSelection(0);
    }

    @Override
    public void navigateForResult(Intent intent, int CODIGO_GALERIA) {
        startActivityForResult(intent, CODIGO_GALERIA);
    }
}
