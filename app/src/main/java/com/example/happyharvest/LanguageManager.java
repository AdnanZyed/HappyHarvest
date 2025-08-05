package com.example.happyharvest;

import android.content.Context;
import android.content.SharedPreferences;

public class LanguageManager {
    private SharedPreferences prefs;
    private static final String PREF_NAME = "AppPrefs";
    private static final String KEY_LANG = "app_language";

    public LanguageManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void setLanguage(String languageCode) {
        prefs.edit().putString(KEY_LANG, languageCode).apply();
    }

    public String getLanguage() {
        return prefs.getString(KEY_LANG, "en");
    }

    public String getUrlWithLanguage(String baseUrl) {
        if (baseUrl.contains("/ar/") || baseUrl.contains("/en/")) {
            return baseUrl.replace("/ar/", "/" + getLanguage() + "/")
                    .replace("/en/", "/" + getLanguage() + "/");
        }

//        String base = "https://fursaty.kicklance.com/";
//        if (baseUrl.startsWith(base)) {
//            return base + getLanguage() + baseUrl.substring(base.length());
//        }

        return baseUrl;
    }
}