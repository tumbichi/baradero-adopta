package com.pity.baradopta.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class CommonUtils {

    private final static String DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
    private final static String[] ETIQUETAS =
            {DogUtils.ETIQUETA_URGENTE, DogUtils.ETIQUETA_ADOPCION, DogUtils.ETIQUETA_PERDIDO, DogUtils.ETIQUETA_ENCONTRADO, DogUtils.ETIQUETA_CALLEJERO, DogUtils.ETIQUETA_VETERINARIA};


    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity =(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivity == null) {
            return false;
        } else {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            for (NetworkInfo networkInfo : info) {
                if (networkInfo.getState() == NetworkInfo.State.CONNECTED) {
                    return true;
                }
            }
        }
        return false;
    }

    public static String timestampToString(long timestamp){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        return dateFormat.format(new Date(timestamp));
    }

    public static String listToString(List<String> list) {
        String result = "";
        for (int i = 0; i < list.size(); i++) result += (i + ": " + list.get(i) + "\n");
        return result;
    }

    public static String etiquetaListToString(List<Boolean> list) {
        String result = "";
        /* URGENTE    = 0 | ADOPCION  = 1 | PERDIDO     = 2
         * ENCONTRADO = 3 | CALLEJERO = 4 | VETERINARIA = 5
         * */
        for (int i = 0; i < list.size(); i++) result += (i + ": " + ETIQUETAS[i] + ": " + list.get(i).toString() + "\n");
        return result;
    }

}
