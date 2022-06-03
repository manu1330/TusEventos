package com.example.tuseventos.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

// Esta clase se utiliza para interactuar con los objetos ArticuloRemember en la base de datos
@Dao
public interface ArticuloRecordarDao {

    @Query("SELECT * FROM articulorecordar ORDER BY date DESC")
    List<ArticuloRecordar> getAll();

    @Query("SELECT * FROM articulorecordar WHERE id = :id")
    ArticuloRecordar getById(int id);

    @Query("SELECT * FROM articulorecordar WHERE tipo = :tipo")
    List<ArticuloRecordar> getByTipo(int tipo);

    @Query("SELECT * FROM articulorecordar WHERE isFavorite = 1")
    List<ArticuloRecordar> getFavorites();

    @Query("SELECT * FROM ArticuloRecordar WHERE isRemindme = 1")
    List<ArticuloRecordar> getRemindme();

    @Delete
    void delete(ArticuloRecordar articulo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(ArticuloRecordar articulo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(ArticuloRecordar... articulos);

    @Query("DELETE FROM ArticuloRecordar WHERE id = :id")
    void deleteById(int id);
}
