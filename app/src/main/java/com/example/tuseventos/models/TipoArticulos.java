package com.example.tuseventos.models;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class TipoArticulos implements Serializable {

    String id;
    String name;
    String description;

    public TipoArticulos(JSONObject json) {
        try {
            id = json.getString("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            name = json.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            description = json.getString("description");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
