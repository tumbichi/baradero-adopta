package com.pity.appperros1.data.modelos;

import android.net.Uri;

import androidx.annotation.NonNull;

import com.pity.appperros1.utils.CommonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Usuario {

    private String id;
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

    public Usuario(){
        perrosPublicados = new ArrayList<>();
        perrosEncontrados = new ArrayList<>();
        perrosAdoptados = new ArrayList<>();
    }

    public Usuario(String id, String email, String nombre, String apellido, String telefono){
        this.id = id;
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
        this.id = id;
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
        this.id = id;
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

    @NonNull
    @Override
    public String toString() {
        return "id: " + getUid() + "\n" +
                "displayName: " + getDisplayName() + "\n" +
                "descripcion: " + getDescripcion() + "\n" +
                "urlFoto: " + getUrlFotoPerfil() + "\n" +
                "email: " + getEmail() + "\n" +
                "telefono: " + getTelefono() + "\n" +
                "email_verificado: " + isMailVerificated() + "\n" +
                "timestamp: " + getTimestamp() + "\n" +
                "fecha: " + CommonUtils.timestampToString(getTimestamp()) + "\n" +
                "perros_publicados: " + CommonUtils.listToString(getPerrosPublicados()) + "\n" +
                "perros_adoptados: " + CommonUtils.listToString(getPerrosAdoptados()) + "\n" +
                "perros_encontrados: " + CommonUtils.listToString(getPerrosEncontrados()) + "\n";
    }

    public String getUid() {
        return id;
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

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrlFotoPerfil(String urlFotoPerfil) {
        this.urlFotoPerfil = urlFotoPerfil;
    }

    public void setMailVerification(boolean mailVerification) {
        this.mailVerification = mailVerification;
    }

    public void setPerrosPublicados(List<String> perrosPublicados) {
        this.perrosPublicados = perrosPublicados;
    }

    public void setPerrosEncontrados(List<String> perrosEncontrados) {
        this.perrosEncontrados = perrosEncontrados;
    }

    public void setPerrosAdoptados(List<String> perrosAdoptados) {
        this.perrosAdoptados = perrosAdoptados;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
