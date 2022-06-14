package com.example.tuseventos;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuseventos.Adapters.RecommendedArticulosAdapter;
import com.example.tuseventos.models.ArticuloRecordar;
import com.example.tuseventos.models.ArticuloRecordarDao;
import com.example.tuseventos.models.Articulos;
import com.example.tuseventos.models.TipoArticulos;
import com.example.tuseventos.requests.NoticiasRequests;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AbrirNoticiaActivity extends Activity {

    Articulos articuloMostrar;
    TextView txtTituloNoticia, txtSubtituloNoticia, txtDia, txtHora, txtContenido;
    Button btFavoritos, btRecordados, btMapa;
    ImageView imgNoticiaSeleccionada;
    float lat, lng;
    Toolbar toolbar2;
    RecyclerView rvArticulosRecomendados;
    ProgressBar progressBar;

    List<Articulos> recommendedArticulos = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticia_seleccionada);

        txtTituloNoticia = findViewById(R.id.txtTituloNoticia);
        txtSubtituloNoticia = findViewById(R.id.txtSubtituloNoticia);
        txtDia = findViewById(R.id.txtDia);
        txtHora = findViewById(R.id.txtHora);
        txtContenido = findViewById(R.id.txtContenido);
        btFavoritos = findViewById(R.id.btFavoritos);
        btRecordados = findViewById(R.id.btRecordados);
        btMapa = findViewById(R.id.btMapa);
        imgNoticiaSeleccionada = findViewById(R.id.imgNoticiaSeleccionada);
        toolbar2 = findViewById(R.id.toolbar2);
        rvArticulosRecomendados = findViewById(R.id.rv_recommended_articles);
        progressBar = findViewById(R.id.progress_bar);

        if (getIntent().getSerializableExtra("articulo") != null) {
            articuloMostrar = (Articulos) getIntent().getSerializableExtra("articulo");
            // guardamos el tipo en la base de datos por si no estuviera guardado
            TipoArticulos.saveTipoArticulo(articuloMostrar.getTipo());
            inicializarVistas();
            NoticiasRequests.read_article(this, articuloMostrar.getId());
        } else {
            // aqui se entra desde una notificacion, no hay articulo
            String id = getIntent().getStringExtra("id");
            NoticiasRequests.get_article(this, id);
        }

        NoticiasRequests.get_recommended_articles(this);
    }

    public void inicializarVistas() {

        String patternDia = "dd-MM-yy";
        SimpleDateFormat simpleDateFormatDia = new SimpleDateFormat(patternDia);

        String patternHora = "HH:mm";
        SimpleDateFormat simpleDateFormatHora = new SimpleDateFormat(patternHora);

        txtTituloNoticia.setText(articuloMostrar.getTitle());
        txtSubtituloNoticia.setText(articuloMostrar.getSubtitle());
        txtDia.setText(simpleDateFormatDia.format(articuloMostrar.getDate()));
        txtHora.setText(simpleDateFormatHora.format(articuloMostrar.getDate()));
        txtContenido.setText(articuloMostrar.getText());
        Glide.with(this).load(Tags.SERVER + articuloMostrar.getImage().substring(1)).centerCrop().into(imgNoticiaSeleccionada);
        lat = articuloMostrar.getLat();
        lng = articuloMostrar.getLng();
        toolbar2.setNavigationOnClickListener(view -> {
            finish();
        });

        if (articuloMostrar.getFavorite()) {
            btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.red));
            btFavoritos.setText("Quitar Favoritos");
        } else {
            btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
            btFavoritos.setText("Favoritos");
        }

        setReminderButton();

        btFavoritos.setOnClickListener(view -> {
            if (articuloMostrar.getFavorite()) {
                NoticiasRequests.remove_favorite_article(this, articuloMostrar.getId());
            } else {
                NoticiasRequests.add_favorite_article(this, articuloMostrar.getId());
            }
        });

        btRecordados.setOnClickListener(view -> {
            if (articuloMostrar.getRemindme()) {
                borrarArticulo(articuloMostrar);
            } else {
                insertarArticulo(articuloMostrar);
            }
        });

        btMapa.setOnClickListener(view -> {
            String uri = "geo:" + lat + "," + lng + "?q=" + lat + "," + lng;
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            startActivity(intent);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvArticulosRecomendados.setLayoutManager(layoutManager);
        rvArticulosRecomendados.setAdapter(new RecommendedArticulosAdapter(this, recommendedArticulos));
    }

    public void onAddFavoriteArticleSuccess() {
        btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.red));
        articuloMostrar.setFavorite(true);
        btFavoritos.setText("Quitar Favoritos");
        Snackbar.make(btRecordados, "El artículo se ha añadido a favoritos", Snackbar.LENGTH_LONG).show();
    }

    public void onRemoveFavoriteArticleSuccess() {
        btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
        articuloMostrar.setFavorite(false);
        btFavoritos.setText("Favoritos");
        Snackbar.make(btRecordados, "El artículo se ha eliminado de favoritos", Snackbar.LENGTH_LONG).show();
    }

    public void onGetArticleSuccess(Articulos articulo) {
        articuloMostrar = articulo;
        inicializarVistas();
    }

    public void onGetArticleFailed(String message) {
        Snackbar.make(btRecordados, "Ha ocurrido un error al cargar el artículo.", Snackbar.LENGTH_LONG).show();
    }

    public void onGetRecommendedArticlesSuccess(List<Articulos> articulos) {
        recommendedArticulos.clear();
        recommendedArticulos.addAll(articulos);
        rvArticulosRecomendados.getAdapter().notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

    public void onGetRecommendedArticlesFailed(String message) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(btRecordados, "Ha ocurrido un error al cargar los artículos recomendados.", Snackbar.LENGTH_LONG).show();
    }

    private void insertarArticulo(Articulos articulo) {
        if (articulo.getDate().getTime() > System.currentTimeMillis()) {
            // Insertar en base de datos para recordar
            new Thread(() -> {
                ArticuloRecordarDao dao = TusEventos.getDatabase().articuloRememberDao();
                dao.insert(new ArticuloRecordar(articulo));
                runOnUiThread(() -> {
                    Snackbar.make(btRecordados, "El artículo se ha añadido a recordar", Snackbar.LENGTH_LONG).show();
                    btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
                    btRecordados.setText("Quitar Recordar");
                    setReminderButton();
                });
            }).start();

            // Establecer alarma en la fecha y hora del evento
            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("titulo", articulo.getTitle());
            intent.putExtra("id", articulo.getId());
            intent.putExtra("imagen", articulo.getImage());
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                    100,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

            System.out.println("poner alarma " + articulo.getDate());
            manager.setExact(AlarmManager.RTC_WAKEUP, articulo.getDate().getTime(), pendingIntent);
        } else {
            Snackbar.make(btRecordados, "El evento ya ha pasado", Snackbar.LENGTH_LONG).show();
        }
    }

    private void borrarArticulo(Articulos articulo) {
        new Thread(() -> {
            ArticuloRecordarDao dao = TusEventos.getDatabase().articuloRememberDao();
            dao.deleteById(Integer.parseInt(articulo.getId()));
            runOnUiThread(() -> {
                Snackbar.make(btRecordados, "El artículo se ha eliminado de recordar", Snackbar.LENGTH_LONG).show();
                btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.verde));
                btRecordados.setText("Recordar");
                setReminderButton();
            });
        }).start();

        // Eliminar alarma
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,
                100,
                intent,
                PendingIntent.FLAG_IMMUTABLE | PendingIntent.FLAG_UPDATE_CURRENT);

        System.out.println("cancelar alarma");
        manager.cancel(pendingIntent);
    }

    private void setReminderButton() {
        new Thread(() -> {
            ArticuloRecordarDao dao = TusEventos.getDatabase().articuloRememberDao();
            ArticuloRecordar articuloRecordar = dao.getById(Integer.parseInt(articuloMostrar.getId()));
            runOnUiThread(() -> {
                if (articuloRecordar != null) {
                    btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
                    btRecordados.setText("Quitar Recordar");
                    articuloMostrar.setRemindme(true);
                } else {
                    btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.verde));
                    btRecordados.setText("Recordar");
                    articuloMostrar.setRemindme(false);
                }
            });
        }).start();
    }
}
