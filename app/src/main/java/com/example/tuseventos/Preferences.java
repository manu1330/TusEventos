package com.example.tuseventos;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {

    private static Application app = TusEventos.getApp();

    public static SharedPreferences getSharedPreferences() {
        return app.getSharedPreferences("preferences", Context.MODE_PRIVATE);
    }

    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(String key) {
        return getSharedPreferences().getString(key, "");
    }

    public static void setToken(String value) {
        setString(Tags.TOKEN, value);
    }

    public static String getToken() {
        return getString(Tags.TOKEN);
    }

    public static String getID() {
        String token = getToken();
        if (token != null && !token.equals("")) {
            return token.substring(0, token.indexOf("_"));
        } else {
            return -1 + "";
        }
    }

    public static boolean getBoolean(String key) {
        return getSharedPreferences().getBoolean(key, false);
    }

    public static void setBoolean(String key, boolean value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(key, value);
        editor.apply();
    }
}
