package com.example.tuseventos.models;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TipoArticulosDao {
    @Query("SELECT * FROM tipoarticulos")
    List<TipoArticulos> getAll();

    @Query("SELECT * FROM tipoarticulos WHERE id = :id")
    TipoArticulos getById(int id);

    @Delete
    void delete(TipoArticulos tipo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(TipoArticulos tipo);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(TipoArticulos... tipos);

    @Query("DELETE FROM TipoArticulos WHERE id = :id")
    void deleteById(int id);
}
