package com.example.tuseventos;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.example.tuseventos.models.ArticuloRecordar;
import com.example.tuseventos.models.ArticuloRecordarDao;
import com.example.tuseventos.models.TipoArticulos;
import com.example.tuseventos.models.TipoArticulosDao;

import java.util.Date;


@Database(entities = {ArticuloRecordar.class, TipoArticulos.class}, version = 1, exportSchema = false)
@TypeConverters({AppDatabase.DateConverter.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract TipoArticulosDao tipoArticulosDao();
    public abstract ArticuloRecordarDao articuloRememberDao();

    public static class DateConverter {
        @TypeConverter
        public static Date toDate(Long timestamp) {
            return timestamp == null ? null : new Date(timestamp);
        }

        @TypeConverter
        public static Long fromDate(Date date) {
            return date == null ? null : date.getTime();
        }
    }
}
