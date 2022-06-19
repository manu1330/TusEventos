package com.example.tuseventos.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tuseventos.R;
import com.example.tuseventos.Tags;
import com.example.tuseventos.models.Comentarios;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ComentariosAdapter extends RecyclerView.Adapter<ComentariosAdapter.ComentariosViewHolder> {

    Activity activity;
    List<Comentarios>comentariosList = new ArrayList<>();

    public ComentariosAdapter(Activity activity, List<Comentarios> comentariosList) {
        this.activity = activity;
        this.comentariosList=comentariosList;
    }

    @NonNull
    @Override
    public ComentariosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.comentarios_layout, parent, false);
        return new ComentariosViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ComentariosViewHolder holder, int position) {
        String patternDia = "dd-MM-yy";
        SimpleDateFormat simpleDateFormatDia = new SimpleDateFormat(patternDia);

        Comentarios comentario = comentariosList.get(position);
        holder.imgUser.setVisibility(View.VISIBLE);
        holder.txtUsername.setText(comentario.getUser());
        holder.txtComentario.setText(comentario.getText());
        holder.txtFecha.setText(simpleDateFormatDia.format(comentario.getDate()));
        if (comentario.getImageUser() != null){
            Glide.with(activity).load(Tags.SERVER + comentario.getImageUser().substring(1)).centerCrop().into(holder.imgUser);
        }
    }

    @Override
    public int getItemCount() {
        return comentariosList.size();
    }

    public static class ComentariosViewHolder extends RecyclerView.ViewHolder {

        ImageView imgUser;
        TextView txtUsername, txtComentario, txtFecha;

        public ComentariosViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUser = itemView.findViewById(R.id.imgUser);
            txtUsername = itemView.findViewById(R.id.txtUsername);
            txtComentario = itemView.findViewById(R.id.txtComentario);
            txtFecha = itemView.findViewById(R.id.txtFecha);
        }
    }
}
