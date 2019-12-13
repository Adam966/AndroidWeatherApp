package com.project.weatherapp.other;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPref {
    private static final String PREFERENCES = "SHARED_PREFERENCES";

    private static final String APP_THEME = "app_theme";
    private static final String PREFERRED_CITY = "pref_city";
    private static final String PREFERRED_LANG = "pref_lang";
    private static final String PREFERRED_UNITS = "pref_units";

    private static final int DEFAULT_THEME = 1; //classic theme
    private static final int DEFAULT_UNITS = 1; //imperial units
    private static final String DEFAULT_LANG = "en"; //english
    private static final String DEFAULT_CITY = null;

    private Context context;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;

    private static SharedPref instance = null;

    private SharedPref(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREFERENCES, context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public static SharedPref getSharedPrefInstance(Context context) {
        if (instance == null)
            instance = new SharedPref(context);

        return instance;
    }

    public void savePrefTheme(int theme) {
        editor.putInt(APP_THEME, theme);
        editor.apply();
    }

    public void savePrefUnits(int units) {
        editor.putInt(PREFERRED_UNITS, units);
        editor.apply();
    }

    public void savePrefCity(String city) {
        editor.putString(PREFERRED_CITY, city);
        editor.apply();
    }

    public void savePrefLang(String lang) {
        editor.putString(PREFERRED_LANG, lang);
        editor.apply();
    }

    public int loadPrefTheme() {
        return sharedPreferences.getInt(APP_THEME, DEFAULT_THEME);
    }

    public int loadPrefUnits() {
        return sharedPreferences.getInt(PREFERRED_UNITS, DEFAULT_UNITS);
    }

    public String loadPrefLang() {
        return sharedPreferences.getString(PREFERRED_LANG, DEFAULT_LANG);
    }

    public String loadPrefCity() {
        return sharedPreferences.getString(PREFERRED_CITY, DEFAULT_CITY);
    }

    public void savefavCity(boolean state) {
        editor.putBoolean("fav", state);
        editor.apply();
    }

    public boolean loadFavCity() {
        return sharedPreferences.getBoolean("fav", false);
    }
}
