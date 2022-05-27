package com.example.tuseventos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseventos.Adapters.NoticiasAdapter;
import com.example.tuseventos.R;
import com.example.tuseventos.models.Articulos;

import java.util.ArrayList;
import java.util.List;

public class NoticiasFragment extends Fragment {

    RecyclerView recyclerNoticias;
    NoticiasAdapter noticiasAdapter;
    List<Articulos> articulosList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        articulosList.add(new Articulos("Baile ceremonial", "baile ceremonial guapardo"));
        articulosList.add(new Articulos("Canto Folclorico", "canto folclorico guapardo"));
        articulosList.add(new Articulos("Torneo lolsito", "torneo lolsito guapardo"));

        View root = inflater.inflate(R.layout.fragment_noticias, container, false);
        recyclerNoticias = root.findViewById(R.id.recycler_noticias);
        recyclerNoticias.setLayoutManager(new LinearLayoutManager(getContext()));
        noticiasAdapter = new NoticiasAdapter(this, articulosList);
        recyclerNoticias.setAdapter(noticiasAdapter);
        return root;

    }

}
