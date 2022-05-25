package com.example.tuseventos;

import android.app.Application;

public class TusEventos extends Application {

    private static Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
    }

    public static Application getApp() {
        return app;
    }

}
