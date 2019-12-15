package com.pity.appperros1.data.interactor.implementation;

import android.net.Uri;

import com.google.firebase.auth.FirebaseUser;
import com.pity.appperros1.data.interactor.interfaces.IInicioInteractor;
import com.pity.appperros1.data.modelos.Perro;
import com.pity.appperros1.data.repository.implementacion.DogRepository;
import com.pity.appperros1.data.repository.implementacion.UserRepository;
import com.pity.appperros1.data.repository.interfaces.IDogRepository;
import com.pity.appperros1.ui.fragment_agregar_perro.interfaces.IAgregarPerroPresenter;

import java.util.ArrayList;

public class InicioInteractor implements IInicioInteractor,
        IDogRepository.CallbackUploadPhoto, IDogRepository.CallbackUploadDog, IDogRepository.CallbackDogList {

    private UserRepository userRepository;
    private DogRepository dogRepository;

    private IAgregarPerroPresenter.CallbackInteractor callbackAgregarPerroInteractor;
    private CallbackGetDogList callbackInicioInteractor;

    private Perro newDog;
    private ArrayList<Perro> postList;

    public InicioInteractor(){
        this.userRepository = UserRepository.getInstance();
        this.dogRepository = DogRepository.getInstance();

    }

    @Override
    public void logout() {
        userRepository.logoutUser();
    }

    @Override
    public void bringDogList(CallbackGetDogList callbackGetDogList) {
        this.callbackInicioInteractor = callbackGetDogList;
        dogRepository.getDogListPerdido(this);
    }

    @Override
    public ArrayList<Perro> getListPost() {
        return postList;
    }

    @Override
    public Perro getDogById(int position) {
        return getListPost().get(position);
    }


    @Override
    public void createCurrentNewDog() {
        newDog = new Perro(new ArrayList<>());
    }

    @Override
    public void deleteCurrentNewDog() {
        newDog = null;
    }


    @Override
    public void startUploadNewDog(String nombre, String descripcion, String genero, String edad,
                                  String tamanio, String castrado, String vacunado,
                                  ArrayList<Boolean> estados,
                                  IAgregarPerroPresenter.CallbackInteractor callbackInteractor) {
        this.callbackAgregarPerroInteractor = callbackInteractor;

        newDog.setNombre(nombre);
        newDog.setDescripcion(descripcion);
        newDog.setGenero(genero);
        newDog.setEdad(edad);
        newDog.setTamanio(tamanio);
        newDog.setEsterilizado(castrado);
        newDog.setVacunado(vacunado);
        newDog.setEtiquetas(estados);

        dogRepository.uploadPhoto(Uri.parse(newDog.getPathFoto()), this);
    }

    @Override
    public void setCurrentPathPhoto(Uri pathPhoto) {
        newDog.setPathFoto(pathPhoto.toString());
    }

    @Override
    public Uri getCurrentPathPhoto() throws NullPointerException {


        if (newDog.getPathFoto() == null) {
            return null;
        }
        return Uri.parse(newDog.getPathFoto());

    }

    @Override
    public FirebaseUser getUserLogged() {
        return userRepository.currentFirebaseUser();
    }


    @Override
    public void onSuccessUploadPhoto(String url, String fecha) {
        newDog.setUrlFoto(url);
        callbackAgregarPerroInteractor.onSuccessUploadPhoto();
        dogRepository.uploadPerro(newDog, userRepository.currentFirebaseUser(), this);

    }



    @Override
    public void onFailureUploadPhoto(String messageError) {
        callbackAgregarPerroInteractor.onFailureUploadPhoto(messageError);
    }

    @Override
    public void onSuccessUploadDog() {
        callbackAgregarPerroInteractor.onSuccesUploadDog();
    }

    @Override
    public void onFailureUploadDog(String messasgeError) {
        callbackAgregarPerroInteractor.onFailureUploadDog(messasgeError);
    }


    @Override
    public void onSuccesGetDogList(ArrayList<Perro> dogList) {
        postList = dogList;
        callbackInicioInteractor.onSuccesGetDogList();
    }

    @Override
    public void onFailureGetDogList(String error) {
        callbackInicioInteractor.onFailureDogList(error);
    }
}
