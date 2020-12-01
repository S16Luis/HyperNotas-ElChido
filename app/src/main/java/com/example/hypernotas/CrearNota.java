package com.example.hypernotas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CrearNota extends AppCompatActivity {

    EditText titulo,descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_nota);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        titulo=findViewById(R.id.txttituloCN);
        descripcion=findViewById(R.id.txtdescripcionCN);

    }

    public void Guardar(View view)
    {
        BD admin =new BD(this,"hypernotas",null,1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String tit=titulo.getText().toString();
        String desc=descripcion.getText().toString();
        if(!tit.isEmpty()&&!desc.isEmpty())
        {
            Nota nota=new Nota();
            nota.titulo=tit;
            nota.descripcion=desc;
            nota.fecha=getDate();
            ContentValues registro = new ContentValues();
            registro.put("titulo",nota.titulo);
            registro.put("descripcion",nota.descripcion);
            registro.put("fecha",nota.fecha);
            bd.insert("notas",null, registro);
            bd.close();
            Toast.makeText(this, "Nota Guardada", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getBaseContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }


}