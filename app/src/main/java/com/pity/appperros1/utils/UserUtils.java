package com.pity.appperros1.utils;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.pity.appperros1.data.modelos.Usuario;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class UserUtils {

    public final static String USER_CHILD = "Usuarios";

    private final static String UID_KEY = "id";
    private final static String DISPLAY_NAME_KEY = "displayName";
    private final static String EMAIL_KEY = "email";
    private final static String PHONE_KEY = "telefono";
    private final static String EMAIL_VERIFICATION_KEY = "mailVerification";
    private final static String TIMESTAMP_KEY = "timestamp";
    private final static String DESCRIPTION_NKEY = "descripcion";
    private final static String PROFILE_PHOTO_KEY = "fotoperfil";
    private final static String DOGS_UPLOADS_KEY = "perrosPublicados";
    private final static String DOGS_ADOPTED_KEY = "perrosAdoptados";
    private final static String DOGS_FOUND_KEY = "perrosEncontrados";

    public final static String TOKEN_KEY = "device_token";

    public static boolean isEmailValid(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    }

    public static boolean isPhoneNumberValid(String phone) {
        return PhoneNumberUtils.isGlobalPhoneNumber(phone);
    }

    public static Map<String, Object> userToMap(Usuario user) {
        Map<String, Object> userMap = new HashMap<>();

        userMap.put(UID_KEY, user.getUid());
        userMap.put(DISPLAY_NAME_KEY, user.getDisplayName());
        userMap.put(EMAIL_KEY, user.getEmail());
        userMap.put(PHONE_KEY, user.getTelefono());
        userMap.put(EMAIL_VERIFICATION_KEY, user.isMailVerificated());
        userMap.put(TIMESTAMP_KEY, user.getTimestamp());
        userMap.put(DESCRIPTION_NKEY, user.getDescripcion());
        userMap.put(PROFILE_PHOTO_KEY, user.getUrlFotoPerfil());
        userMap.put(DOGS_UPLOADS_KEY, user.getPerrosPublicados());
        userMap.put(DOGS_ADOPTED_KEY, user.getPerrosAdoptados());
        userMap.put(DOGS_FOUND_KEY, user.getPerrosEncontrados());
        return userMap;
    }

    public static Usuario unmapperUser(@NonNull DataSnapshot userMap) {
        Usuario user = new Usuario();
        int countUploads;

        user.setId( (String) userMap.child(UID_KEY).getValue() );
        user.setDisplayName( (String) userMap.child(DISPLAY_NAME_KEY).getValue() );
        user.setEmail( (String) userMap.child(EMAIL_KEY).getValue() );
        user.setTelefono( (String) userMap.child(PHONE_KEY).getValue() );
        user.setMailVerification( (boolean) userMap.child(EMAIL_VERIFICATION_KEY).getValue() );
        user.setTimestamp( (long) userMap.child(TIMESTAMP_KEY).getValue() );
        user.setDescripcion( (String) userMap.child(DESCRIPTION_NKEY).getValue() );
        user.setUrlFotoPerfil( (String) userMap.child(PROFILE_PHOTO_KEY).getValue() );

        countUploads = (int) userMap.child(DOGS_UPLOADS_KEY).getChildrenCount();
        if (countUploads != 0) user.setPerrosPublicados(unmapperStringArray(userMap.child(DOGS_UPLOADS_KEY), countUploads));

        countUploads = (int) userMap.child(DOGS_ADOPTED_KEY).getChildrenCount();
        if (countUploads != 0) user.setPerrosAdoptados(unmapperStringArray(userMap.child(DOGS_ADOPTED_KEY), countUploads));

        countUploads = (int) userMap.child(DOGS_FOUND_KEY).getChildrenCount();
        if (countUploads != 0) user.setPerrosEncontrados(unmapperStringArray(userMap.child(DOGS_FOUND_KEY), countUploads));

        return user;
    }

    private static ArrayList<String> unmapperStringArray(DataSnapshot childList, int countUploads){
        ArrayList<String> resultList = new ArrayList<>();
        for (int i = 0 ; i < countUploads ; i++){
            String pos = Integer.toString(i);
            resultList.add((String) childList.child(pos).getValue());
        }
        return resultList;
    }

}
