package com.example.hypernotas;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MostrarTareaM extends AppCompatActivity {

    EditText titulo,descripcion;
    ListView lvmultimedia;
    TextView tvfechahora;
    ArrayList <EntidadM> lista;
    AdaptadorMultimedias Adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_tarea_m);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        titulo=findViewById(R.id.txtTituloMTM);
        descripcion=findViewById(R.id.txtdescripcionMTM);
        lvmultimedia=findViewById(R.id.lvmultimediasMTM);
        tvfechahora=findViewById(R.id.tvfechadatosMTM);
        int clave=Integer.parseInt(getIntent().getStringExtra("clave"));
        BD admin =new BD(this,"hypernotas",null,1);
        TareaM tar=admin.ObtenerTareaM(clave);
        lista = admin.ObtenerMultimedia(clave);
        titulo.setText(tar.titulo);
        tvfechahora.setText(tar.fechar + " " + tar.hora);
        descripcion.setText(tar.descripcion);
        Adaptador = new AdaptadorMultimedias(this, lista);
        lvmultimedia.setAdapter(Adaptador);
}
}