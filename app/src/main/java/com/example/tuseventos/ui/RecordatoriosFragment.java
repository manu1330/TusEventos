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
import com.example.tuseventos.TusEventos;
import com.example.tuseventos.models.ArticuloRecordar;
import com.example.tuseventos.models.ArticuloRecordarDao;
import com.example.tuseventos.models.Articulos;
import com.example.tuseventos.models.TipoArticulos;
import com.example.tuseventos.requests.NoticiasRequests;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

public class RecordatoriosFragment extends Fragment {

    RecyclerView recyclerNoticias;
    NoticiasAdapter noticiasAdapter;
    List<Articulos> articulosList = new ArrayList<>();
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
            tipoSeleccionado = null;
            articulosList.clear();
            getArticulosRecordatorios(null);
        });
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

        String idTipo;
        if (tipoSeleccionado == null) {
            idTipo = null;
        } else {
            idTipo = tipoSeleccionado.getId();
        }
        getArticulosRecordatorios(idTipo);
    }

    public void seleccionarTipo(TipoArticulos tipoArticulos) {
        tipoSeleccionado = tipoArticulos;
        getArticulosRecordatorios(tipoSeleccionado.getId());
    }

    private void getArticulosRecordatorios(String idTipo) {
        new Thread(() -> {
            ArticuloRecordarDao articulosDAO = TusEventos.getDatabase().articuloRememberDao();
            List<ArticuloRecordar> articulosRoom;
            if (idTipo != null) {
                articulosRoom = articulosDAO.getByTipo(Integer.parseInt(idTipo));
            } else {
                articulosRoom = articulosDAO.getAll();
            }

            articulosList.clear();
            for (ArticuloRecordar a : articulosRoom) {
                articulosList.add(a.toArticulos());
            }
            getActivity().runOnUiThread(() -> noticiasAdapter.notifyDataSetChanged());
            if (articulosList.size() == 0) {
                getActivity().runOnUiThread(() -> Snackbar.make(getView(), "No hay noticias.", Snackbar.LENGTH_SHORT).show());
            }
        }).start();
    }
}
