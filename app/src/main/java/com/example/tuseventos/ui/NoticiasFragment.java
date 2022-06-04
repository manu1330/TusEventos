package com.example.tuseventos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseventos.Adapters.NoticiasAdapter;
import com.example.tuseventos.DialogTipos;
import com.example.tuseventos.R;
import com.example.tuseventos.models.Articulos;
import com.example.tuseventos.models.TipoArticulos;
import com.example.tuseventos.requests.NoticiasRequests;

import java.util.ArrayList;
import java.util.List;

public class NoticiasFragment extends Fragment {

    RecyclerView recyclerNoticias;
    NoticiasAdapter noticiasAdapter;
    List<Articulos> articulosList = new ArrayList<>();
    int page = 1;
    TipoArticulos tipoSeleccionado;
    Button bt_filtro, bt_quitar_filtro;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_noticias, container, false);
        recyclerNoticias = root.findViewById(R.id.recycler_noticias);
        bt_filtro = root.findViewById(R.id.bt_filtro);
        bt_quitar_filtro = root.findViewById(R.id.bt_quitar_filtro);
        recyclerNoticias.setLayoutManager(new LinearLayoutManager(getContext()));
        noticiasAdapter = new NoticiasAdapter(this, articulosList);
        recyclerNoticias.setAdapter(noticiasAdapter);

        bt_filtro.setOnClickListener(view -> {
            DialogTipos dialogTipos = new DialogTipos(this);
            dialogTipos.show();
        });
        bt_quitar_filtro.setOnClickListener(view -> {
            articulosList.clear();
            NoticiasRequests.get_articles(this, 1, null);
        });
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

        String idTipo = null;
        if (tipoSeleccionado != null) {
            idTipo = tipoSeleccionado.getId();
        }
        articulosList.clear();
        NoticiasRequests.get_articles(this, 1, idTipo);

    }

    public void onGetArticlesSuccess(List<Articulos> articulosList) {
        this.articulosList.addAll(articulosList);
        noticiasAdapter.notifyDataSetChanged();
        page++;
    }

    public void seleccionarTipo(TipoArticulos tipoArticulos) {
        tipoSeleccionado = tipoArticulos;

        String idTipo = null;
        if (tipoSeleccionado != null) {
            idTipo = tipoSeleccionado.getId();
        }
        articulosList.clear();
        NoticiasRequests.get_articles(this, 1, idTipo);
    }
}
