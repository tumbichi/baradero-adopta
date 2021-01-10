package com.pity.baradopta.data.modelos;

public class Solicitud {

    private String idSolicitud;
    private String idUser;
    private String idDog;
    private int type;
    private String adopterDispayName;
    private String adopterPhone;
    private String adopterEmail;
    private String dogName;
    private String dogUrlImage;

    public String getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(String idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getIdDog() {
        return idDog;
    }

    public void setIdDog(String idDog) {
        this.idDog = idDog;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getAdopterDispayName() {
        return adopterDispayName;
    }

    public void setAdopterDispayName(String adopterDispayName) {
        this.adopterDispayName = adopterDispayName;
    }

    public String getAdopterPhone() {
        return adopterPhone;
    }

    public void setAdopterPhone(String adopterPhone) {
        this.adopterPhone = adopterPhone;
    }

    public String getAdopterEmail() {
        return adopterEmail;
    }

    public void setAdopterEmail(String adopterEmail) {
        this.adopterEmail = adopterEmail;
    }

    public String getDogName() {
        return dogName;
    }

    public void setDogName(String dogName) {
        this.dogName = dogName;
    }

    public String getDogUrlImage() {
        return dogUrlImage;
    }

    public void setDogUrlImage(String dogUrlImage) {
        this.dogUrlImage = dogUrlImage;
    }
}
