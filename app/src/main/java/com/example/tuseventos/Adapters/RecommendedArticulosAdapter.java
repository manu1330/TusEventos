package com.example.tuseventos.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuseventos.AbrirNoticiaActivity;
import com.example.tuseventos.R;
import com.example.tuseventos.Tags;
import com.example.tuseventos.models.Articulos;

import java.util.List;

public class RecommendedArticulosAdapter extends RecyclerView.Adapter<RecommendedArticulosAdapter.NoticiasViewHolder> {

    Activity activity;
    List<Articulos> articulosList;

    public RecommendedArticulosAdapter(Activity activity, List<Articulos> articulosList) {
        this.activity = activity;
        this.articulosList = articulosList;
    }

    @NonNull
    @Override
    public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articulo_recomendado_layout, parent, false);
        return new NoticiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position) {
        Articulos articulo = articulosList.get(position);
        holder.tvTitulo.setText(articulo.getTitle());
        holder.ivImagen.setVisibility(View.VISIBLE);
        Glide.with(activity).load(Tags.SERVER + articulo.getImage().substring(1)).centerCrop().into(holder.ivImagen);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, AbrirNoticiaActivity.class);
            intent.putExtra("articulo", articulo);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articulosList.size();
    }

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImagen;
        TextView tvTitulo;

        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.iv_imagen);
            tvTitulo = itemView.findViewById(R.id.tv_titulo);
        }
    }
}
