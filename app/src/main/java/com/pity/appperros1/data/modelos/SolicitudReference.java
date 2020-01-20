package com.pity.appperros1.data.modelos;

public class SolicitudReference {

    private String adoptionID;
    private String adopterID;
    private String dogID;
    private String uploaderID;


    public SolicitudReference(){}

    public SolicitudReference(String id, String idAdopter, String idDog, String idUploader){
        this.adoptionID = id;
        this.adopterID = idAdopter;
        this.dogID = idDog;
        this.uploaderID = idUploader;
    }

    public String getAdoptionID() {
        return adoptionID;
    }

    public String getAdopterID() {
        return adopterID;
    }

    public String getDogID() {
        return dogID;
    }

    public String getUploaderID() {
        return uploaderID;
    }
}
