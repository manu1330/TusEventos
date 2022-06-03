package com.example.tuseventos.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.tuseventos.TusEventos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

@Entity
public class TipoArticulos implements Serializable {

    @PrimaryKey
    @NonNull
    String id = "0";

    @ColumnInfo(name = "title")
    String name;

    @ColumnInfo(name = "description")
    String description;

    public TipoArticulos() {
    }

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

    public static void saveTiposArticulos(List<TipoArticulos> tipoArticulosList) {
        new Thread(() -> {
            TusEventos.getDatabase().tipoArticulosDao().insertAll(tipoArticulosList.toArray(new TipoArticulos[0]));
        }).start();
    }

    public static void saveTipoArticulo(TipoArticulos tipoArticulos) {
        new Thread(() -> {
            TusEventos.getDatabase().tipoArticulosDao().insert(tipoArticulos);
        }).start();
    }
}
