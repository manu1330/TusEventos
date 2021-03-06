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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuseventos.Adapters.ComentariosAdapter;
import com.example.tuseventos.Adapters.GaleriaImgAdapter;
import com.example.tuseventos.Adapters.RecommendedArticulosAdapter;
import com.example.tuseventos.models.ArticuloRecordar;
import com.example.tuseventos.models.ArticuloRecordarDao;
import com.example.tuseventos.models.Articulos;
import com.example.tuseventos.models.Comentarios;
import com.example.tuseventos.models.TipoArticulos;
import com.example.tuseventos.requests.NoticiasRequests;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AbrirNoticiaActivity extends Activity {

    Articulos articuloMostrar;
    TextView txtTituloNoticia, txtSubtituloNoticia, txtDia, txtHora, txtContenido;
    EditText etEscribirComentario;
    Button btFavoritos, btRecordados, btMapa, btEnviarComentario;
    ImageView imgNoticiaSeleccionada;
    float lat, lng;
    Toolbar toolbar2;
    RecyclerView rvArticulosRecomendados, rvGaleriaImg, rvComentarios;
    ProgressBar progressBar;
    TextView tvNoRecommended;
    ComentariosAdapter comentariosAdapter;

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
        etEscribirComentario = findViewById(R.id.etEscribirComentario);
        btFavoritos = findViewById(R.id.btFavoritos);
        btRecordados = findViewById(R.id.btRecordados);
        btMapa = findViewById(R.id.btMapa);
        btEnviarComentario = findViewById(R.id.btEnviarComentario);
        imgNoticiaSeleccionada = findViewById(R.id.imgNoticiaSeleccionada);
        toolbar2 = findViewById(R.id.toolbar2);
        rvArticulosRecomendados = findViewById(R.id.rv_recommended_articles);
        rvGaleriaImg = findViewById(R.id.rvGaleriaImg);
        rvComentarios = findViewById(R.id.rvComentarios);
        progressBar = findViewById(R.id.progress_bar);
        tvNoRecommended = findViewById(R.id.tv_no_recommended);

        if (getIntent().getSerializableExtra("articulo") != null) {
            articuloMostrar = (Articulos) getIntent().getSerializableExtra("articulo");
            TipoArticulos.saveTipoArticulo(articuloMostrar.getTipo());
            inicializarVistas();
            NoticiasRequests.read_article(this, articuloMostrar.getId());
        } else {
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

        btEnviarComentario.setOnClickListener(view -> {
            String textoComentario;
            textoComentario = etEscribirComentario.getText().toString();
            NoticiasRequests.send_article_comment(this, articuloMostrar.getId(), textoComentario);
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvArticulosRecomendados.setLayoutManager(layoutManager);
        rvArticulosRecomendados.setAdapter(new RecommendedArticulosAdapter(this, recommendedArticulos));

        LinearLayoutManager layoutManagerImg = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvGaleriaImg.setLayoutManager(layoutManagerImg);
        rvGaleriaImg.setAdapter(new GaleriaImgAdapter(this, articuloMostrar.getImagenes()));

        LinearLayoutManager layoutManagerComment = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvComentarios.setLayoutManager(layoutManagerComment);
        comentariosAdapter = new ComentariosAdapter(this, articuloMostrar.getComentarios());
        rvComentarios.setAdapter(comentariosAdapter);

    }

    public void onAddFavoriteArticleSuccess() {
        btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.red));
        articuloMostrar.setFavorite(true);
        btFavoritos.setText("Quitar Favoritos");
        Snackbar.make(btRecordados, "El art??culo se ha a??adido a favoritos", Snackbar.LENGTH_LONG).show();
    }

    public void onRemoveFavoriteArticleSuccess() {
        btFavoritos.setBackgroundTintList(getResources().getColorStateList(R.color.purple_200));
        articuloMostrar.setFavorite(false);
        btFavoritos.setText("Favoritos");
        Snackbar.make(btRecordados, "El art??culo se ha eliminado de favoritos", Snackbar.LENGTH_LONG).show();
    }

    public void onGetArticleSuccess(Articulos articulo) {
        articuloMostrar = articulo;
        inicializarVistas();
    }

    public void onGetArticleFailed(String message) {
        Snackbar.make(btRecordados, "Ha ocurrido un error al cargar el art??culo.", Snackbar.LENGTH_LONG).show();
    }

    public void onGetRecommendedArticlesSuccess(List<Articulos> articulos) {
        recommendedArticulos.clear();
        for (Articulos articulo : articulos) {
            if (articulo.getId().equals(articuloMostrar.getId())) {
                articulos.remove(articulo);
                break;
            }
        }
        recommendedArticulos.addAll(articulos);
        rvArticulosRecomendados.getAdapter().notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
        if (recommendedArticulos.size() == 0) {
            tvNoRecommended.setVisibility(View.VISIBLE);
        } else {
            tvNoRecommended.setVisibility(View.GONE);
        }
    }

    public void onGetRecommendedArticlesFailed(String message) {
        progressBar.setVisibility(View.GONE);
        Snackbar.make(btRecordados, "Ha ocurrido un error al cargar los art??culos recomendados.", Snackbar.LENGTH_LONG).show();
    }

    public void onGetCommentsSuccess(List<Comentarios> comentariosList) {
        articuloMostrar.getComentarios().clear();
        articuloMostrar.getComentarios().addAll(comentariosList);
        comentariosAdapter.notifyDataSetChanged();
    }

    public void onSentCommentSuccess() {
        NoticiasRequests.get_comments_article(this, articuloMostrar.getId());
        Snackbar.make(btRecordados, "El comentario se ha enviado correctamente", Snackbar.LENGTH_LONG).show();
    }

    private void insertarArticulo(Articulos articulo) {
        if (articulo.getDate().getTime() > System.currentTimeMillis()) {
            new Thread(() -> {
                ArticuloRecordarDao dao = TusEventos.getDatabase().articuloRememberDao();
                dao.insert(new ArticuloRecordar(articulo));
                runOnUiThread(() -> {
                    Snackbar.make(btRecordados, "El art??culo se ha a??adido a recordar", Snackbar.LENGTH_LONG).show();
                    btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.purple_500));
                    btRecordados.setText("Quitar Recordar");
                    setReminderButton();
                });
            }).start();

            AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("titulo", articulo.getTitle());
            intent.putExtra("id", articulo.getId());
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
                Snackbar.make(btRecordados, "El art??culo se ha eliminado de recordar", Snackbar.LENGTH_LONG).show();
                btRecordados.setBackgroundTintList(getResources().getColorStateList(R.color.verde));
                btRecordados.setText("Recordar");
                setReminderButton();
            });
        }).start();

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
