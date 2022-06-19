package com.example.tuseventos.ui;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.tuseventos.R;
import com.example.tuseventos.Tags;
import com.example.tuseventos.requests.FileUtils;
import com.example.tuseventos.requests.UserRequests;

import java.util.Arrays;

public class AjustesFragment extends Fragment {

    EditText etEmail, etContrasena, etRepetirContrasena;
    Button btRealizarCambios;
    ImageView iv_imagen;

    private static final int PICK_IMAGE = 106;
    private static final int REQUEST_PERMISSIONS_FOR_IMAGE = 107;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);
        etEmail = root.findViewById(R.id.etEscribirComentario);
        etContrasena = root.findViewById(R.id.etContraseña);
        etRepetirContrasena = root.findViewById(R.id.etRepetirContraseña);
        btRealizarCambios = root.findViewById(R.id.btRealizarCambios);
        iv_imagen = root.findViewById(R.id.iv_imagen);

        btRealizarCambios.setOnClickListener(view -> {
            String email;
            String contrasena;
            String repetirContrasena;
            email = etEmail.getText().toString();
            contrasena = etContrasena.getText().toString();
            repetirContrasena = etRepetirContrasena.getText().toString();

            comprobar(email, contrasena, repetirContrasena);

        });

        iv_imagen.setOnClickListener(view -> {
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Para conseguir una imagen de tu dispositivo necesitamos que nos des permiso para acceder a tus archivos.")
                        .setCancelable(false)
                        .setTitle("Dar permisos");
                builder.setPositiveButton("Confirmar", (DialogInterface.OnClickListener) (dialog, id) -> {
                    dialog.dismiss();

                    ActivityCompat.requestPermissions(getActivity(),
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            REQUEST_PERMISSIONS_FOR_IMAGE);
                }).setNegativeButton("Cancelar", (DialogInterface.OnClickListener) (dialog, id) -> {
                    dialog.dismiss();
                }).show();
            } else {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        });

        UserRequests.get_self_profile_picture(this);

        return root;
    }

    public void onChangedCredentialsSuccess() {
        Toast.makeText(getContext(), "Datos cambiados correctamente", Toast.LENGTH_LONG).show();
    }

    private void comprobar(String email, String contrasena, String repetirContrasena) {
        if (contrasena.isEmpty() && email.isEmpty() && repetirContrasena.isEmpty()) {
            Toast.makeText(getContext(), "Ningun dato se ha cambiado", Toast.LENGTH_LONG).show();
            return;
        }
        if (!email.isEmpty() && !email.matches(Tags.EMAIL_REGEX)) {
            Toast.makeText(getContext(), "Email incorrecto", Toast.LENGTH_LONG).show();
            return;
        }

        if (!contrasena.equals(repetirContrasena)) {
            Toast.makeText(getContext(), "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
            return;
        }

        if (email.isEmpty()) {
            email = null;
        }
        if (contrasena.isEmpty()) {
            contrasena = null;
        }

        UserRequests.change_credentials(this, email, contrasena);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSIONS_FOR_IMAGE) {
            for (int i : grantResults) {
                if (i == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(getContext(), "No se puede cambiar la imagen", Toast.LENGTH_LONG).show();
                    return;
                }

                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, PICK_IMAGE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        if (requestCode == PICK_IMAGE) {
            UserRequests.change_profile_picture(this, FileUtils.getFile(getContext(), data.getData()));
        }
    }

    public void onChangeProfilePictureSuccess(String url) {
        if (url != null) {
            Glide.with(this).load(Tags.SERVER + url.substring(1)).circleCrop().into(iv_imagen);
        }
    }

    public void onGetSelfProfilePictureSuccess(String url) {
        if (url != null) {
            Glide.with(this).load(Tags.SERVER + url.substring(1)).circleCrop().into(iv_imagen);
        }
    }
}
