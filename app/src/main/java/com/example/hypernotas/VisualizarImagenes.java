package com.example.hypernotas;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

public class VisualizarImagenes extends AppCompatActivity {

    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_imagenes);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        img=findViewById(R.id.imgfotos);
        Uri ruta=Uri.parse(getIntent().getStringExtra("Uri"));
        img.setImageURI(ruta);
    }
}