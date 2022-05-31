package com.example.tuseventos.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.tuseventos.R;
import com.example.tuseventos.Tags;
import com.example.tuseventos.requests.UserRequests;

public class AjustesFragment extends Fragment {

    EditText etEmail, etContrasena, etRepetirContrasena;
    Button btRealizarCambios;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_ajustes, container, false);
        etEmail = root.findViewById(R.id.etEmail);
        etContrasena = root.findViewById(R.id.etContraseña);
        etRepetirContrasena = root.findViewById(R.id.etRepetirContraseña);
        btRealizarCambios = root.findViewById(R.id.btRealizarCambios);

        btRealizarCambios.setOnClickListener(view -> {
            String email;
            String contrasena;
            String repetirContrasena;
            email = etEmail.getText().toString();
            contrasena = etContrasena.getText().toString();
            repetirContrasena = etRepetirContrasena.getText().toString();

            if (email.equals("")){
                email = null;
            }
            if (!email.matches(Tags.EMAIL_REGEX)) {
                Toast.makeText(getContext(), "El email no es correcto", Toast.LENGTH_SHORT).show();
            } else {
                if (!contrasena.equals(repetirContrasena)){
                    contrasena = null;
                    Toast.makeText(getContext(), "La contrasena debe ser igual en ambas partes", Toast.LENGTH_SHORT).show();
                } else{
                    if (contrasena.equals("")){
                        contrasena = null;
                    }
                    if (email.equals("")){
                        email = null;
                    }

                    UserRequests.change_credentials(this, email, contrasena);
                }
            }

        });

        return root;

    }

    public void onChangedCredentialsSuccess(){
        Toast.makeText(getContext(), "Datos cambiados correctamente", Toast.LENGTH_LONG).show();
    }

}
