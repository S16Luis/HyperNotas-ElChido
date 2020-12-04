package com.example.hypernotas;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorN extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<EntidadN>lista;
    CustomFilter filtro;
    ArrayList<EntidadN> filtroList;

    public AdaptadorN(Context context, ArrayList<EntidadN> lista) {
        this.context = context;
        this.lista = lista;
        this.filtroList=lista;
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
        return lista.indexOf(getItem(i));
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

    @Override
    public Filter getFilter() {
        if(filtro == null){
            filtro = new CustomFilter();
        }

        return filtro;
    }

    class CustomFilter extends Filter{

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults resulst = new FilterResults();
            if(constraint != null && constraint.length()>0){
                //pasamos a mayusculas
                constraint = constraint.toString().toUpperCase();

                ArrayList<EntidadN> filtro = new ArrayList<EntidadN>();

                for(Integer i=0;i<filtroList.size();i++){
                    if(filtroList.get(i).getTvtitulo().toUpperCase().contains(constraint)){
                        filtro.add(new EntidadN(filtroList.get(i).getTvclave(),filtroList.get(i).getTvtitulo(),
                                filtroList.get(i).getTvfecha(),R.id.btneditar,R.id.btneliminar));
                    }
                }
                resulst.count= filtro.size();
                resulst.values = filtro;
            }else{
                resulst.count= filtroList.size();
                resulst.values = filtroList;
            }

            return resulst;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            lista = (ArrayList<EntidadN>) results.values;
            notifyDataSetChanged();

        }
    }
}
