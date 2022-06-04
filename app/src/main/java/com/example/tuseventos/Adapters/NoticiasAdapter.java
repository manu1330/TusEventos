package com.example.tuseventos.Adapters;

import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tuseventos.AbrirNoticiaActivity;
import com.example.tuseventos.R;
import com.example.tuseventos.models.Articulos;

import java.util.ArrayList;
import java.util.List;

public class NoticiasAdapter extends RecyclerView.Adapter<NoticiasAdapter.NoticiasViewHolder> {

    Fragment fragment;
    List<Articulos>articulosList = new ArrayList<>();

    public NoticiasAdapter(Fragment fragment, List<Articulos> articulosList){
        this.fragment=fragment;
        this.articulosList=articulosList;
    }

    @NonNull
    @Override
    public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.noticias_adapter_layout, parent, false);
        return new NoticiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position) {
        Articulos articulo = articulosList.get(position);
        holder.txtTitulo.setText(articulo.getTitle());
        holder.txtSubtitulo.setText(articulo.getSubtitle());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(fragment.getContext(), AbrirNoticiaActivity.class);
            intent.putExtra("articulo", articulo);
            fragment.getActivity().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return articulosList.size();
    }

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder{

        ImageView imgNoticia;
        TextView txtTitulo, txtSubtitulo;

        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);
            imgNoticia=itemView.findViewById(R.id.imgNoticia);
            txtTitulo=itemView.findViewById(R.id.txtTitulo);
            txtSubtitulo=itemView.findViewById(R.id.txtSubtitulo);
        }
    }

}
