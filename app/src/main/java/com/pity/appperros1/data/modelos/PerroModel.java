package com.pity.appperros1.data.modelos;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PerroModel {
    private String did;

    private String nombre;
    private String descripcion;

    private String urlFoto;
    private String pathFoto;

    private String genero;
    private String edad;
    private String tamanio;
    private String esterilizado;
    private String vacunado;

    private List<Boolean> etiquetas;

    private long timestamp;

    private String uid;

    private List <SolicitudModel> solicitudes;


    public PerroModel(){

    }

    public PerroModel(ArrayList<SolicitudModel> solicitudes){
        this.solicitudes = solicitudes;
        timestamp = Calendar.getInstance().getTime().getTime();

        /*Date date = new Date(timestamp);
        Calendar dat = Calendar.getInstance();
        dat.setTime(date);

        Log.e("Fecha", "timestamp " + date.getDate() + "/" + date.getMonth() + "/" + date.getYear()
                + " " +  date.getHours() + ":" + date.getMinutes() + ":" +  date.getSeconds());*/

    }


    public String getDid() {
        return did;
    }

    public void setDid(String did) {
        this.did = did;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getTamanio() {
        return tamanio;
    }

    public void setTamanio(String tamanio) {
        this.tamanio = tamanio;
    }

    public String getEsterilizado() {
        return esterilizado;
    }

    public void setEsterilizado(String esterilizado) {
        this.esterilizado = esterilizado;
    }

    public String getVacunado() {
        return vacunado;
    }

    public void setVacunado(String vacunado) {
        this.vacunado = vacunado;
    }

    public List<Boolean> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(List<Boolean> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<SolicitudModel> getSolicitudes() {
        return solicitudes;
    }

    public void setSolicitudes(List<SolicitudModel> solicitudes) {
        this.solicitudes = solicitudes;
    }
}
