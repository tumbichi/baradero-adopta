package com.pity.appperros1.data.modelos;

import android.net.Uri;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Usuario {

    private String uid;
    private String nombre;
    private String apellido;
    private String email;
    private String descripcion;
    private String telefono;
    private String urlFotoPerfil;

    private boolean mailVerificado;

    private List<String> perrosPublicados;
    private List<String> perrosEncontrados;
    private List<String> perrosAdoptados;


    private long timestamp;

    public Usuario(){}

    public Usuario(String id, String email, String nombre, String apellido, String telefono){
        this.uid = id;
        this.email = email;
        this.mailVerificado = false;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = "";
        this.timestamp = Calendar.getInstance().getTime().getTime();
        this.telefono = telefono;
        this.urlFotoPerfil = null;

        this.perrosPublicados = new ArrayList<>();
        this.perrosEncontrados = new ArrayList<>();
        this.perrosAdoptados = new ArrayList<>();
    }

    public Usuario(String id, String email, String nombre, String apellido, String telefono, Uri fotoPerfil){
        this.uid = id;
        this.email = email;
        this.mailVerificado = false;
        this.nombre = nombre;
        this.apellido = apellido;
        this.descripcion = "";
        this.timestamp = Calendar.getInstance().getTime().getTime();
        this.telefono = telefono;
        this.urlFotoPerfil = fotoPerfil.toString();

        this.perrosPublicados = new ArrayList<>();
        this.perrosEncontrados = new ArrayList<>();
        this.perrosAdoptados = new ArrayList<>();
    }

    public String getUid() {
        return uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
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
