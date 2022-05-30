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
import com.example.tuseventos.requests.NoticiasRequests;

import java.util.ArrayList;
import java.util.List;

public class NoticiasFragment extends Fragment {

    RecyclerView recyclerNoticias;
    NoticiasAdapter noticiasAdapter;
    List<Articulos> articulosList = new ArrayList<>();
    int page=1;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_noticias, container, false);
        recyclerNoticias = root.findViewById(R.id.recycler_noticias);
        recyclerNoticias.setLayoutManager(new LinearLayoutManager(getContext()));
        noticiasAdapter = new NoticiasAdapter(this, articulosList);
        recyclerNoticias.setAdapter(noticiasAdapter);
        return root;

    }

    @Override
    public void onResume() {
        super.onResume();

        NoticiasRequests.get_articles(this, 1);
        articulosList.clear();

    }

    public void onGetArticlesSuccess(List<Articulos> articulosList){
        this.articulosList.addAll(articulosList);
        noticiasAdapter.notifyDataSetChanged();
        page++;
    }
}
