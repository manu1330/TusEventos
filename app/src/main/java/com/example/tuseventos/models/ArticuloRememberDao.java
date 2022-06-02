package com.example.tuseventos.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

// Esta clase se utiliza para interactuar con los objetos ArticuloRemember en la base de datos
@Dao
public class ArticuloRememberDao {

    @Query("SELECT * FROM articuloremember ORDER BY date DESC")
    public ArticuloRemember[] getAll() {
        return null;
    }

    @Query("SELECT * FROM articuloremember WHERE id = :id")
    public ArticuloRemember getById(int id) {
        return null;
    }

    @Query("SELECT * FROM articuloremember WHERE isFavorite = 1")
    public ArticuloRemember[] getFavorites() {
        return null;
    }

    @Query("SELECT * FROM articuloremember WHERE isRemindme = 1")
    public ArticuloRemember[] getRemindme() {
        return null;
    }

    @Delete
    public void delete(ArticuloRemember articulo) {
    }

    @Insert
    public void insert(ArticuloRemember articulo) {
    }

    @Insert
    public void insertAll(ArticuloRemember... articulos) {
    }

    @Query("DELETE FROM articuloremember WHERE id = :id")
    public void deleteById(int id) {
    }
}
