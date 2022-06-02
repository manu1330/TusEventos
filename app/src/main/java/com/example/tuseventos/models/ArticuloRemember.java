package com.example.tuseventos.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "articuloremember", foreignKeys = {
        @ForeignKey(entity = TipoArticulos.class,
                parentColumns = "id",
                childColumns = "tipo",
                onDelete = ForeignKey.CASCADE)}
)
public class ArticuloRemember {
    @PrimaryKey
    public int id;

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

    @ColumnInfo(name = "tipo")
    public TipoArticulos tipo;

    public ArticuloRemember(Articulos articulos) {
        this.id = Integer.parseInt(articulos.id);
        this.title = articulos.title;
        this.subtitle = articulos.subtitle;
        this.text = articulos.text;
        this.image = articulos.image;
        this.date = articulos.date;
        this.lat = articulos.lat;
        this.lng = articulos.lng;
        this.isFavorite = articulos.isFavorite;
        this.isRemindme = articulos.isRemindme;
        this.tipo = articulos.tipo;
    }
}
