package com.example.hypernotas;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;

public class AdaptadorMultimedias extends BaseAdapter {
    private Context context;
    private ArrayList<EntidadM> lista;

    public AdaptadorMultimedias(Context context, ArrayList<EntidadM> lista) {
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
        final EntidadM item = (EntidadM) getItem(i);
        view= LayoutInflater.from(context).inflate(R.layout.multimedias,null);
        ImageView img = view.findViewById(R.id.imgiconos);
        Button btnaña = view.findViewById(R.id.btnañadir);
        Button btnelim = view.findViewById(R.id.btnelim);
        img.setImageResource(item.getImg());
        img.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(context,VisualizarImagenes.class);
                intent.putExtra("Uri",String.valueOf(item.getUri()));
                context.startActivity(intent);
            }
        });
        return view;
    }
}
