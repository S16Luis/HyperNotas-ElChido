package com.example.hypernotas;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorTM extends BaseAdapter {
    private Context context;
    private ArrayList<EntidadTM> lista;

    public AdaptadorTM(Context context, ArrayList<EntidadTM> lista) {
        this.context = context;
        this.lista = lista;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Object getItem(int posicion) {
        return lista.get(posicion);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final EntidadTM item = (EntidadTM) getItem(i);

        view= LayoutInflater.from(context).inflate(R.layout.mostrartareas,null);
        TextView tvcla = view.findViewById(R.id.tvclaveTM);
        TextView tvtit = view.findViewById(R.id.tvtituloTM);
        TextView tvfec = view.findViewById(R.id.tvfechaTM);
        TextView tvcant= view.findViewById(R.id.tvcantidad);
        CheckBox cbcom= view.findViewById(R.id.cbcompletada);
        Button btnedit = view.findViewById(R.id.btneditarTM);
        Button btnelim = view.findViewById(R.id.btneliminarTM);
        tvcla.setText(item.getTvclave());
        tvtit.setText(item.getTvtitulo());
        tvfec.setText(item.getTvfecha());
        tvcant.setText(item.getTvcantidad());
        cbcom.setText("Completada");
        btnedit.setText("Editar");
        btnelim.setText("Eliminar");

        tvtit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,MostrarTareaM.class);
                intent.putExtra("clave",item.getTvclave());
                context.startActivity(intent);
            }
        });

        tvfec.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,MostrarTareaM.class);
                intent.putExtra("clave",item.getTvclave());
                context.startActivity(intent);
            }
        });

        tvcant.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,MostrarTareaM.class);
                intent.putExtra("clave",item.getTvclave());
                context.startActivity(intent);
            }
        });

        btnelim.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                BD admin =new BD(context,"hypernotas",null,1);
                SQLiteDatabase bd=admin.getWritableDatabase();
                int cantidad=bd.delete("multimedias","Tarea="+item.getTvclave(),null);
                int cantidad2=bd.delete("tareas","ClaveTarea="+item.getTvclave(),null);
                bd.close();
                Toast.makeText(context, "Tarea Multimedia Eliminada", Toast.LENGTH_SHORT).show();
            }
        });

        return view;


    }

}
