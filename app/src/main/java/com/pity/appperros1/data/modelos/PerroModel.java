package com.pity.appperros1.data.modelos;

import java.util.ArrayList;
import java.util.List;

public class PerroModel {
    private String id;
    private String nombre;
    private String descripcion;
    private String urlFoto;
    private String pathFoto;
    private String genero;
    private String edad;
    private String tamanio;
    private String esterilizado;
    private String vacunado;
    private String fechaPublicacion;
    private List<Boolean> etiquetas;
    private String idUser;

    public PerroModel(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (pathFoto != null) {
            return pathFoto;
        }
        return null;
    }

    public void setPathFoto(String pathFoto) throws NullPointerException {

        this.pathFoto = pathFoto;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
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

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }


    public String getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }


    public List<Boolean> getEtiquetas() {
        return etiquetas;
    }

    public void setEtiquetas(ArrayList<Boolean> etiquetas) {
        this.etiquetas = etiquetas;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }
}
