package com.example.tuseventos.Adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseventos.AbrirNoticiaActivity;
import com.example.tuseventos.DialogTipos;
import com.example.tuseventos.R;
import com.example.tuseventos.models.Articulos;
import com.example.tuseventos.models.TipoArticulos;

import java.util.ArrayList;
import java.util.List;

public class TiposAdapter extends RecyclerView.Adapter<TiposAdapter.TiposArticulosViewHolder> {

    DialogTipos dialogTipos;
    List<TipoArticulos> tipoArticulosList = new ArrayList<>();

    public TiposAdapter(DialogTipos dialogTipos, List<TipoArticulos> tipoArticulosList){
        this.dialogTipos=dialogTipos;
        this.tipoArticulosList =tipoArticulosList;
    }

    @NonNull
    @Override
    public TiposAdapter.TiposArticulosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tipos_adapter_layout, parent, false);
        return new TiposAdapter.TiposArticulosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TiposAdapter.TiposArticulosViewHolder holder, int position) {
        TipoArticulos tipoArticulo = tipoArticulosList.get(position);
        holder.txtNombreTipo.setText(tipoArticulo.getName());
        holder.itemView.setOnClickListener(view -> {
            dialogTipos.tipoSeleccionado(tipoArticulo);
        });
    }

    @Override
    public int getItemCount() {
        return tipoArticulosList.size();
    }

    public static class TiposArticulosViewHolder extends RecyclerView.ViewHolder{

        TextView txtNombreTipo;

        public TiposArticulosViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNombreTipo=itemView.findViewById(R.id.txtNombreTipo);
        }
    }
}
