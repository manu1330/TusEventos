package com.example.tuseventos;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseventos.Adapters.TiposAdapter;
import com.example.tuseventos.models.TipoArticulos;
import com.example.tuseventos.requests.NoticiasRequests;
import com.example.tuseventos.requests.UserRequests;

import java.util.ArrayList;
import java.util.List;

public class DialogTipos {

    AlertDialog alertDialog;
    RecyclerView rvTipos;
    ProgressBar progressBar;
    Fragment fragment;
    TiposAdapter tiposAdapter;
    List<TipoArticulos> tipoArticulosList = new ArrayList<>();

    public DialogTipos(Fragment fragment) {
        this.fragment = fragment;
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(fragment.getContext());
        dialogBuilder.setView(R.layout.dialog_tipo);
        dialogBuilder.setTitle("Tipos");
        dialogBuilder.setNegativeButton("Cancelar", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });
        dialogBuilder.setCancelable(true);
        alertDialog = dialogBuilder.create();
        alertDialog.show();
        rvTipos = alertDialog.findViewById(R.id.rvTipos);
        progressBar = alertDialog.findViewById(R.id.progressBar);

        rvTipos.setLayoutManager(new LinearLayoutManager(fragment.getContext()));
        tiposAdapter = new TiposAdapter(this, tipoArticulosList);
        rvTipos.setAdapter(tiposAdapter);

        NoticiasRequests.get_article_types(this);
    }

    public void tipoSeleccionado(TipoArticulos tipoArticulos){
        UserRequests.invokeMethodWithObject("seleccionarTipo", fragment, tipoArticulos);
        alertDialog.dismiss();
    }

    public void show(){
        alertDialog.show();
    }

    public Activity getActivity(){
        return  fragment.getActivity();
    }

    public void onGetTypeArticlesSuccess(List<TipoArticulos> tipoArticulosList){
        this.tipoArticulosList.clear();
        this.tipoArticulosList.addAll(tipoArticulosList);
        tiposAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.GONE);
    }

}
