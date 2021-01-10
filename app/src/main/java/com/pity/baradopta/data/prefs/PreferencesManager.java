package com.pity.baradopta.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import static com.pity.baradopta.utils.AppConstants.EMPTY_STRING;
import static com.pity.baradopta.utils.AppConstants.PREF_NAME;
import static com.pity.baradopta.utils.AppConstants.PREF_TOKEN_KEY;

public class PreferencesManager {

    private static PreferencesManager instance;
    private final SharedPreferences sharedPreferences;

    private PreferencesManager(Context context) {
        sharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static synchronized void initializeInstance(Context context) {
        if (instance == null) {
            instance = new PreferencesManager(context);
        }
    }

    public static synchronized PreferencesManager getInstance() {
        if (instance == null) {
            throw new IllegalStateException(PreferencesManager.class.getSimpleName() +
                    " is not initialized, call initializeInstance(..) method first.");
        }
        return instance;
    }

    public void setToken(String token) {
        Log.d("PreferencesManager", "setToken(" + token + ")");
        sharedPreferences.edit()
                .putString(PREF_TOKEN_KEY, token)
                .apply();
    }

    public String getToken() {
        return sharedPreferences.getString(PREF_TOKEN_KEY, EMPTY_STRING);
    }

    public void remove(String key) {
        sharedPreferences.edit().remove(key).apply();
    }

    public boolean clear() {
        return sharedPreferences.edit()
                .clear()
                .commit();
    }
}
