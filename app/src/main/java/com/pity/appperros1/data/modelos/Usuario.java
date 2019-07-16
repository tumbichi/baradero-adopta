package com.pity.appperros1.data.modelos;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Usuario {

    private String id;
    private String nombre;
    private String apellido;
    private String email;
    private String descripcion;
    private String telefono;
    private String urlFotoPerfil;

    private boolean mailVerificado;

    private List<PublicacionModel> perrosPublicados;
    private List<PerroModel> perrosEncontrados;
    private List<PerroModel> perrosAdoptados;
    private String fechaCreacion;

    public Usuario(){}

    public Usuario(String id, String email, String nombre, String apellido, String telefono){
        this.id = id;
        this.email = email;
        this.mailVerificado = false;
        this.nombre = nombre;
        this.apellido = apellido;
        this.fechaCreacion = new SimpleDateFormat("dd/MM/yyyy_HH:mm:ss", Locale.getDefault()).format(new Date());
        this.descripcion = "";
        this.telefono = telefono;
        this.urlFotoPerfil = null;
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getEmail() {
        return email;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public boolean isMailVerificado() {
        return mailVerificado;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }
}
