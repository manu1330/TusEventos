package com.example.tuseventos;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.ortiz.touchview.TouchImageView;

public class AbrirImagenActivity extends AppCompatActivity {

    TouchImageView touchImg;
    Toolbar toolbarImagen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_abrir_imagen);

        touchImg = findViewById(R.id.touchImg);
        toolbarImagen = findViewById(R.id.toolbarImagen);

        String url = getIntent().getStringExtra("URL");
        Glide.with(this).load(Tags.SERVER + url.substring(1)).into(touchImg);
        toolbarImagen.setNavigationOnClickListener(view -> {
            finish();
        });
    }
}