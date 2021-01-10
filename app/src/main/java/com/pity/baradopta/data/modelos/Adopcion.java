package com.pity.baradopta.data.modelos;

import androidx.annotation.NonNull;

public class Adopcion {
    private String id;
    private Usuario UPLOADER;
    private Usuario ADOPTER;
    private Perro DOG;

    public Adopcion(){
        UPLOADER = null;
        ADOPTER = null;
        DOG = null;
    }

    @NonNull
    @Override
    public String toString() {
        return "Adopcion { \n"
                + "id: " + id + "\n"
                + "DOG: " + DOG + "\n"
                + "UPLOADER: " + UPLOADER + "\n"
                + "ADOPTER: " + ADOPTER + "\n";
    }

    public void setId(String id) {
        this.id = id;
    }

    public  void setUPLOADER(Usuario UPLOADER) {
        this.UPLOADER = UPLOADER;
    }

    public  void setADOPTER(Usuario ADOPTER) {
        this.ADOPTER = ADOPTER;
    }

    public void setDOG(Perro DOG) {
        this.DOG = DOG;
    }

    public String getId() {
        return id;
    }

    public Usuario getUPLOADER() {
        return UPLOADER;
    }

    public Usuario getADOPTER() {
        return ADOPTER;
    }

    public Perro getDOG() {
        return DOG;
    }
}
