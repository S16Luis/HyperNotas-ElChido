package com.example.hypernotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

public class MostrarNota extends AppCompatActivity {

    EditText titulo,descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_nota);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        titulo=findViewById(R.id.txttituloMN);
        descripcion=findViewById(R.id.txtdescripcionMN);
        int clave=Integer.parseInt(getIntent().getStringExtra("clave"));
        BD admin =new BD(this,"hypernotas",null,1);
        Nota not=admin.ObtenerNota(clave);
        titulo.setText(not.titulo);
        descripcion.setText(not.descripcion);
    }
}