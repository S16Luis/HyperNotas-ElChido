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
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class AdaptadorTM extends BaseAdapter implements Filterable {
    private Context context;
    private ArrayList<EntidadTM> lista;
    CustomFilter filtro;
    ArrayList<EntidadTM> filtroList;

    public AdaptadorTM(Context context, ArrayList<EntidadTM> lista) {
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

                ArrayList<EntidadTM> filtro = new ArrayList<EntidadTM>();

                for(Integer i=0;i<filtroList.size();i++){
                    if(filtroList.get(i).getTvtitulo().toUpperCase().contains(constraint)){
                        filtro.add(new EntidadTM(filtroList.get(i).getTvclave(),filtroList.get(i).getTvtitulo(),
                                filtroList.get(i).getTvfecha(),filtroList.get(i).getTvcantidad(),
                                R.id.cbcompletada,R.id.btneditar,R.id.btneliminar));
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
            lista = (ArrayList<EntidadTM>) results.values;
            notifyDataSetChanged();

        }
    }

}
