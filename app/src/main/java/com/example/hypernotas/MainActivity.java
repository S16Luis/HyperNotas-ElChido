package com.example.hypernotas;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvnot, lvtareas;
    ArrayList<EntidadN> lista;
    ArrayList<EntidadTM> lista2;
    AdaptadorN adaptador;
    ArrayAdapter adapter;
    AdaptadorTM adap;
    SearchView buscarnota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        lvnot=findViewById(R.id.lvnotas);
        lvtareas=findViewById(R.id.lvtareas);
        buscarnota=findViewById(R.id.svbuscarN);
        actualizarlista();
        actualizarlista2();

        buscarnota.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(lista.size()==0)
                {
                    if(lista2.size()==0)
                    {

                    }
                    else
                    {
                        MainActivity.this.adap.getFilter().filter(s);
                    }

                }
                else
                {
                    if(lista2.size()==0)
                    {

                    }
                    else
                    {
                        MainActivity.this.adap.getFilter().filter(s);
                        MainActivity.this.adaptador.getFilter().filter(s);
                    }
                }
                if(lista2.size()==0)
                {
                    if(lista.size()==0)
                    {

                    }
                    else
                    {
                        MainActivity.this.adaptador.getFilter().filter(s);
                    }
                }
                else
                {
                    if(lista.size()==0)
                    {

                    }
                    else
                    {
                        MainActivity.this.adap.getFilter().filter(s);
                        MainActivity.this.adaptador.getFilter().filter(s);
                    }
                }
                if(lista.size()==0&&lista2.size()==0)
                {

                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        actualizarlista();
        actualizarlista2();
    }

    public void actualizarlista()
    {

        BD admin =new BD(this,"hypernotas",null,1);
        lista=admin.MostrarNotas();
        if(lista.size()==0)
        {
            ArrayList<String> listavacia=new ArrayList<>();
            listavacia.add("No existen Notas por el momento");
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listavacia);
            lvnot.setAdapter(adapter);
        }
        else
        {
            adaptador= new AdaptadorN(this,lista);
            lvnot.setAdapter(adaptador);
        }
    }

    public void actualizarlista2()
    {

        BD admin =new BD(this,"hypernotas",null,1);
        lista2=admin.MostrarTareas();
        if(lista2.size()==0)
        {
            ArrayList<String> listavacia=new ArrayList<>();
            listavacia.add("No existen Tareas Multimedia por el momento");
            adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,listavacia);
            lvtareas.setAdapter(adapter);
        }
        else
        {
            adap= new AdaptadorTM(this,lista2);
            lvtareas.setAdapter(adap);
        }
    }

    public void TipoNota(View v)
    {
            AlertDialog.Builder cuadroDialogo = new AlertDialog.Builder(this);
            cuadroDialogo.setTitle("Archivo a crear:");
            final String[] opciones = new String[]{"Nota", "Tarea Multimedia"};
            cuadroDialogo.setItems(opciones, new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 if(i==0)
                 {
                     Intent intent = new Intent(getBaseContext(),CrearNota.class);
                     startActivity(intent);
                 }
                 else
                 {
                     Intent intent = new Intent(getBaseContext(),CrearTareaMultimedia.class);
                     startActivity(intent);
                 }
             }
            });
            cuadroDialogo.setNegativeButton("Cancelar",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
            cuadroDialogo.create().show();
    }
}