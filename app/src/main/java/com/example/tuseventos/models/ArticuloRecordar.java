package com.example.tuseventos.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.tuseventos.AppDatabase;
import com.example.tuseventos.TusEventos;

import java.util.Date;

@Entity(tableName = "articulorecordar", foreignKeys = {
        @ForeignKey(
                entity = TipoArticulos.class,
                parentColumns = "id",
                childColumns = "tipo",
                onDelete = ForeignKey.CASCADE
        )}
)
@TypeConverters(AppDatabase.DateConverter.class)
public class ArticuloRecordar {
    @PrimaryKey
    @NonNull
    public String id = "0";

    @ColumnInfo(name = "title")
    public String title;

    @ColumnInfo(name = "subtitle")
    public String subtitle;

    @ColumnInfo(name = "text")
    public String text;

    @ColumnInfo(name = "image")
    public String image;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "lat")
    public float lat;

    @ColumnInfo(name = "lng")
    public float lng;

    @ColumnInfo(name = "isFavorite")
    public boolean isFavorite;

    @ColumnInfo(name = "isRemindme")
    public boolean isRemindme;

    @ColumnInfo(name = "tipo", index = true)
    public int tipoId;

    public ArticuloRecordar() {

    }

    public ArticuloRecordar(Articulos articulos) {
        this.id = articulos.id;
        this.title = articulos.title;
        this.subtitle = articulos.subtitle;
        this.text = articulos.text;
        this.image = articulos.image;
        this.date = articulos.date;
        this.lat = articulos.lat;
        this.lng = articulos.lng;
        this.isFavorite = articulos.isFavorite;
        this.isRemindme = false;
        if (articulos.tipo != null) {
            this.tipoId = Integer.parseInt(articulos.tipo.id);
        }
    }

    public Articulos toArticulos() {
        TipoArticulosDao tiposDao = TusEventos.getDatabase().tipoArticulosDao();
        TipoArticulos tipo = tiposDao.getById(this.tipoId);
        return new Articulos(
                id,
                title,
                subtitle,
                text,
                image,
                date,
                lat,
                lng,
                isFavorite,
                isRemindme,
                tipo
        );
    }
}
