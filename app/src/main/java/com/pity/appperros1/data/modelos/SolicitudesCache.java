package com.pity.appperros1.data.modelos;

import java.util.ArrayList;
import java.util.HashMap;

public class SolicitudesCache {
    public static HashMap<String, ArrayList<Solicitud>> SOLICITUDES;

    public static void setSolicitudes(HashMap<String, ArrayList<Solicitud>> solicitudes) {
        SolicitudesCache.SOLICITUDES = solicitudes;
    }
}
