package com.pity.baradopta.utils;

import com.pity.baradopta.data.modelos.Perro;

import java.util.HashMap;

public class DogUtils {

    public final static String DOG_DB_REFERENCE = "Perros";

    public final static String DOG_ID_KEY = "did";
    public final static String NOMBRE_KEY = "nombre";
    public final static String DESCRIPCION_KEY = "descripcion";
    public final static String URL_FOTO_KEY = "urlFoto";
    public final static String PATH_FOTO_KEY = "pathFoto";
    public final static String GENERO_KEY = "genero";
    public final static String EDAD_KEY = "edad";
    public final static String TAMANIO_KEY = "tamanio";
    public final static String ESTERILIZADO_KEY = "esterilizado";
    public final static String VACUNADO_KEY = "vacunado";
    public final static String TIMESTAMP_KEY = "timestamp";
    public final static String USER_ID_KEY = "uid";
    public final static String ETIQUETAS_KEY = "etiquetas";
    public final static String IS_AVAILABLE_KEY = "available";

    public final static String ETIQUETA_URGENTE = "URGENTE";
    public final static String ETIQUETA_ADOPCION  = "ADOPCION";
    public final static String ETIQUETA_PERDIDO  = "PERDIDO";
    public final static String ETIQUETA_ENCONTRADO  = "ENCONTRADO";
    public final static String ETIQUETA_CALLEJERO  = "CALLEJERO";
    public final static String ETIQUETA_VETERINARIA  = "VETERINARIA";

    public final static int ETIQUETA_URGENTE_ID = 0;
    public final static int ETIQUETA_ADOPCION_ID = 1;
    public final static int ETIQUETA_PERDIDO_ID = 2;
    public final static int ETIQUETA_ENCONTRADO_ID = 3;
    public final static int ETIQUETA_CALLEJERO_ID = 4;
    public final static int ETIQUETA_VETERINARIA_ID = 5;


    public static HashMap<String, Object> dogToMap(Perro perro){
        HashMap<String, Object> dogMap = new HashMap<>();

        dogMap.put(DOG_ID_KEY, perro.getDid());
        dogMap.put(NOMBRE_KEY, perro.getNombre());
        dogMap.put(DESCRIPCION_KEY, perro.getDescripcion());
        dogMap.put(URL_FOTO_KEY, perro.getUrlFoto());
        dogMap.put(PATH_FOTO_KEY, perro.getPathFoto());
        dogMap.put(GENERO_KEY, perro.getGenero());
        dogMap.put(EDAD_KEY, perro.getEdad());
        dogMap.put(TAMANIO_KEY, perro.getTamanio());
        dogMap.put(ESTERILIZADO_KEY, perro.getEsterilizado());
        dogMap.put(VACUNADO_KEY, perro.getVacunado());
        dogMap.put(TIMESTAMP_KEY, perro.getTimestamp());
        dogMap.put(USER_ID_KEY, perro.getUid());
        dogMap.put(ETIQUETAS_KEY, perro.getEtiquetas());
        dogMap.put(IS_AVAILABLE_KEY, perro.isAvailable());

        return dogMap;
    }

}
