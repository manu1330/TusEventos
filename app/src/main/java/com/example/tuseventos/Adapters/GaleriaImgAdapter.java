package com.example.tuseventos.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuseventos.AbrirImagenActivity;
import com.example.tuseventos.R;
import com.example.tuseventos.Tags;

import java.util.List;

public class GaleriaImgAdapter extends RecyclerView.Adapter<GaleriaImgAdapter.NoticiasViewHolder> {

    Activity activity;
    List<String> imgList;

    public GaleriaImgAdapter(Activity activity, List<String> imgList) {
        this.activity = activity;
        this.imgList = imgList;
    }

    @NonNull
    @Override
    public NoticiasViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.articulo_galeria_imagenes_layout, parent, false);
        return new NoticiasViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticiasViewHolder holder, int position) {
        String img = imgList.get(position);
        holder.ivImagen.setVisibility(View.VISIBLE);
        Glide.with(activity).load(Tags.SERVER + img.substring(1)).centerCrop().into(holder.ivImagen);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(activity, AbrirImagenActivity.class);
            intent.putExtra("URL", img);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return imgList.size();
    }

    public static class NoticiasViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImagen;

        public NoticiasViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImagen = itemView.findViewById(R.id.iv_imagen);
        }
    }
}

