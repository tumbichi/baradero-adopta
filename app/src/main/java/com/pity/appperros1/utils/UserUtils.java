package com.pity.appperros1.utils;

import android.telephony.PhoneNumberUtils;
import android.util.Patterns;

import com.pity.appperros1.data.modelos.Usuario;

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

    public static Usuario unmapUser(Map<String, Object> userMap) {
        Usuario user = new Usuario();

        String uid = userMap.get(UID_KEY).toString();
        return user;
    }

}
