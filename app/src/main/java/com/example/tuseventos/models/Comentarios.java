package com.example.tuseventos.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Comentarios implements Serializable {
    String id;
    String user;
    String user_image;
    String text;
    Date date;

    public Comentarios(String id, String user, String user_image, String text, Date date) {
        this.id = id;
        this.user = user;
        this.user_image = user_image;
        this.text = text;
        this.date = date;
    }

    public Comentarios(JSONObject json) {
        try {
            id = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            user = json.getString("user");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            user_image = json.getString("user_image");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            text = json.getString("text");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(json.getString("date"));
        } catch (JSONException | ParseException e) {
            e.printStackTrace();
        }
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getUser() {return user;}

    public void setUser(String user) {this.user = user;}

    public String getUser_image() {return user_image;}

    public void setUser_image(String user_image) {this.user_image = user_image;}

    public String getText() {return text;}

    public void setText(String text) {this.text = text;}

    public Date getDate() {return date;}

    public void setDate(Date date) {this.date = date;}
}
