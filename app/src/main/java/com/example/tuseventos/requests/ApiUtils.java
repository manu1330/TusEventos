package com.example.tuseventos.requests;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.example.tuseventos.Preferences;
import com.example.tuseventos.Tags;
import com.example.tuseventos.TusEventos;

import org.json.JSONException;
import org.json.JSONObject;

public class ApiUtils {

    public static void errorResponse(Throwable t, Activity activity){
        String message = "Connection error";
        Toast.makeText(TusEventos.getApp(), message, Toast.LENGTH_LONG).show();
        Intent intent = new Intent(activity, ServerErrorActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    public static JSONObject getBasicAuth(){
        JSONObject data = new JSONObject();
        try {
            data.put(Tags.TOKEN, Preferences.getToken());
            data.put(Tags.USER_ID, Preferences.getID());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static JSONObject getBasicAuthWith(String key, String value){
        JSONObject data = new JSONObject();
        try {
            data.put(Tags.TOKEN, Preferences.getToken());
            data.put(Tags.USER_ID, Preferences.getID());
            data.put(key, value);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static JSONObject getLoginJSON(String username, String password) {
        JSONObject data = new JSONObject();
        try {
            data.put(Tags.USERNAME, username);
            data.put(Tags.PASSWORD, password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }

    public static JSONObject getRegisterJSON(String username, String email, String password) {
        JSONObject data = new JSONObject();
        try {
            data.put(Tags.USERNAME, username);
            data.put(Tags.EMAIL, email);
            data.put(Tags.PASSWORD, password);
        }  catch (JSONException e) {
            e.printStackTrace();
        }
        return data;
    }
}
