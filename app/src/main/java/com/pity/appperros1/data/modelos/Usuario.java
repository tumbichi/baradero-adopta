package com.pity.appperros1.data.modelos;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Usuario {

    private String uid;
    private String displayName;
    private String email;
    private String descripcion;
    private String telefono;
    private String urlFotoPerfil;

    private boolean mailVerification;

    private List<String> perrosPublicados;
    private List<String> perrosEncontrados;
    private List<String> perrosAdoptados;


    private long timestamp;

    public Usuario(){}

    public Usuario(String id, String email, String nombre, String apellido, String telefono){
        this.uid = id;
        this.email = email;
        this.displayName = nombre + " " + apellido;
        this.telefono = telefono;

        this.timestamp = Calendar.getInstance().getTime().getTime();
        this.mailVerification = false;

        this.descripcion = "";
        this.urlFotoPerfil = null;

        this.perrosPublicados = new ArrayList<>();
        this.perrosEncontrados = new ArrayList<>();
        this.perrosAdoptados = new ArrayList<>();
    }

    public Usuario(String id, String email, String displayName, String telefono){
        this.uid = id;
        this.email = email;
        this.displayName = displayName;
        this.telefono = telefono;

        this.timestamp = Calendar.getInstance().getTime().getTime();
        this.mailVerification = true;

        this.descripcion = "";
        this.urlFotoPerfil = null;

        this.perrosPublicados = new ArrayList<>();
        this.perrosEncontrados = new ArrayList<>();
        this.perrosAdoptados = new ArrayList<>();
    }


    public Usuario(String id, String email, String displayName, String telefono, Uri fotoPerfil){
        this.uid = id;
        this.email = email;
        this.displayName = displayName;
        this.telefono = telefono;
        this.mailVerification = true;

        this.timestamp = Calendar.getInstance().getTime().getTime();
        this.urlFotoPerfil = fotoPerfil.toString();

        this.descripcion = "";

        this.perrosPublicados = new ArrayList<>();
        this.perrosEncontrados = new ArrayList<>();
        this.perrosAdoptados = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public boolean isMailVerificated() {
        return mailVerification;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getUrlFotoPerfil() {
        return urlFotoPerfil;
    }

    public List<String> getPerrosPublicados() {
        return perrosPublicados;
    }

    public List<String> getPerrosEncontrados() {
        return perrosEncontrados;
    }

    public List<String> getPerrosAdoptados() {
        return perrosAdoptados;
    }

    public long getTimestamp() {
        return timestamp;
    }


}
