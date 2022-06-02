package com.example.tuseventos;

import android.app.Application;

import androidx.room.Room;

public class TusEventos extends Application {

    private static Application app;
    private static AppDatabase database;


    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database-tuseventos").build();
    }

    public static Application getApp() {
        return app;
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
