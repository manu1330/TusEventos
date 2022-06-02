package com.example.tuseventos;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.tuseventos.models.ArticuloRemember;
import com.example.tuseventos.models.ArticuloRememberDao;
import com.example.tuseventos.models.Articulos;
import com.example.tuseventos.requests.NoticiasRequests;

import java.text.SimpleDateFormat;
import java.util.List;

public class AbrirNoticiaActivity extends Activity {

    Articulos articuloMostrar;
    TextView txtTituloNoticia,txtSubtituloNoticia, txtDia, txtHora, txtContenido;
    Button btFavoritos, btRecordados, btMapa;
    ImageView imgNoticiaSeleccionada;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticia_seleccionada);

        String patternDia = "dd-MM-yy";
        SimpleDateFormat simpleDateFormatDia = new SimpleDateFormat(patternDia);

        String patternHora = "HH:mm";
        SimpleDateFormat simpleDateFormatHora = new SimpleDateFormat(patternHora);

        articuloMostrar = (Articulos) getIntent().getSerializableExtra("articulo");

        txtTituloNoticia = findViewById(R.id.txtTituloNoticia);
        txtSubtituloNoticia = findViewById(R.id.txtSubtituloNoticia);
        txtDia = findViewById(R.id.txtDia);
        txtHora = findViewById(R.id.txtHora);
        txtContenido = findViewById(R.id.txtContenido);
        btFavoritos = findViewById(R.id.btFavoritos);
        btRecordados = findViewById(R.id.btRecordados);
        btMapa = findViewById(R.id.btMapa);
        imgNoticiaSeleccionada = findViewById(R.id.imgNoticiaSeleccionada);

        txtTituloNoticia.setText(articuloMostrar.getTitle());
        txtSubtituloNoticia.setText(articuloMostrar.getSubtitle());
        txtDia.setText(simpleDateFormatDia.format(articuloMostrar.getDate()));
        txtHora.setText(simpleDateFormatHora.format(articuloMostrar.getDate()));
        txtContenido.setText(articuloMostrar.getText());
        Glide.with(this).load(articuloMostrar.getImage()).into(imgNoticiaSeleccionada);

        if (articuloMostrar.getFavorite()){
            btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.red));
            btFavoritos.setText("Quitar Favoritos");
        }else{
            btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
            btFavoritos.setText("Favoritos");
        }
        btFavoritos.setOnClickListener(view -> {
            if (articuloMostrar.getFavorite()){
                NoticiasRequests.remove_favorite_article(this, articuloMostrar.getId());
            }else{
                NoticiasRequests.add_favorite_article(this, articuloMostrar.getId());
            }
        });

        if (articuloMostrar.getRemindme()){
            btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
            btRecordados.setText("Quitar Recordar");
            ArticuloRememberDao articuloRemDAO = TusEventos.getDatabase().articuloRememberDao();
            articuloRemDAO.deleteById(Integer.parseInt(articuloMostrar.getId()));
        }else{
            btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.verde));
            btRecordados.setText("Recordar");
            ArticuloRememberDao articuloRemDAO = TusEventos.getDatabase().articuloRememberDao();
            ArticuloRemember articuloRem = new ArticuloRemember(articuloMostrar);
        }
        btRecordados.setOnClickListener(view -> {
            if (articuloMostrar.getRemindme()){
                NoticiasRequests.remove_remindme_article(this, articuloMostrar.getId());
            }else{
                NoticiasRequests.add_remindme_article(this, articuloMostrar.getId());
            }
        });

    }

    public void onAddFavoriteArticleSuccess(){
        btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.red));
        articuloMostrar.setFavorite(true);
        btFavoritos.setText("Quitar Favoritos");
    }

    public void onRemoveFavoriteArticleSuccess(){
        btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
        articuloMostrar.setFavorite(false);
        btFavoritos.setText("Favoritos");
    }

    public void onAddRemindmeArticleSuccess(){
        btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
        articuloMostrar.setRemindme(true);
        btRecordados.setText("Quitar Recordar");
    }

    public void onRemoveRemindmeArticleSuccess(){
        btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.verde));
        articuloMostrar.setRemindme(false);
        btRecordados.setText("Recordar");
    }
}
