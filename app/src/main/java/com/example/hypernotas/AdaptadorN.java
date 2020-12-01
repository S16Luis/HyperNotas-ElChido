package com.example.hypernotas;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorN extends BaseAdapter {
    private Context context;
    private ArrayList<EntidadN>lista;

    public AdaptadorN(Context context, ArrayList<EntidadN> lista) {
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
        final EntidadN item = (EntidadN) getItem(i);

        view= LayoutInflater.from(context).inflate(R.layout.mostrarnotas,null);
        TextView tvcla = view.findViewById(R.id.tvclave);
        TextView tvtit = view.findViewById(R.id.tvtitulo);
        TextView tvfec = view.findViewById(R.id.tvfecha);
        Button btnedit = view.findViewById(R.id.btneditar);
        Button btnelim = view.findViewById(R.id.btneliminar);
        tvcla.setText(item.getTvclave());
        tvtit.setText(item.getTvtitulo());
        tvfec.setText(item.getTvfecha());
        btnedit.setText("Editar");
        btnelim.setText("Eliminar");

        tvtit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,MostrarNota.class);
                intent.putExtra("clave",item.getTvclave());
                context.startActivity(intent);
            }
        });
        tvfec.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,MostrarNota.class);
                intent.putExtra("clave",item.getTvclave());
                context.startActivity(intent);
            }
        });
        btnedit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,ModificarNota.class);
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
                int cantidad=bd.delete("notas","ClaveNota="+item.getTvclave(),null);
                bd.close();
                Toast.makeText(context, "Nota Eliminada", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
