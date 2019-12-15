package com.pity.appperros1.utils;

import java.util.HashMap;
import java.util.Map;

public class AdopcionUtils {

    private final static String ADOPTION_ID_KEY = "adoptionID";
    private final static String DOG_ID_KEY = "dogID";
    private final static String UPLOADER_ID_KEY = "uploaderID";
    private final static String ADOPTER_ID_KEY = "adopterID";

    public static Map<String, Object> mapAdoption(String adoptionID, String dogID, String uploaderID, String adopterID){
        Map<String, Object> adoption = new HashMap<>();
        adoption.put(ADOPTION_ID_KEY, adoptionID);
        adoption.put(DOG_ID_KEY, dogID);
        adoption.put(UPLOADER_ID_KEY, uploaderID);
        adoption.put(ADOPTER_ID_KEY, adopterID);
        return adoption;
    }

    public static Map<String, Object> mapSolicitud(String adopterID){
        Map<String, Object> solicitud = new HashMap<>();
        solicitud.put("from_user_id", adopterID);
        return solicitud;
    }
}
