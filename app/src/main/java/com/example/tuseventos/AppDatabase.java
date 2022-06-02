package com.example.tuseventos;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.tuseventos.models.ArticuloRemember;
import com.example.tuseventos.models.ArticuloRememberDao;

@Database(entities = {ArticuloRemember.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ArticuloRememberDao articuloRememberDao();
}
