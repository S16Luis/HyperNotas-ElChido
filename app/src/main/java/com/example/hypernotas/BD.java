package com.example.hypernotas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class BD extends SQLiteOpenHelper{

    SQLiteDatabase bd;

    public BD(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDeDatos) {
        BaseDeDatos.execSQL("create table notas(ClaveNota integer primary key autoincrement, Titulo varchar(25) not null, Descripcion text not null,Fecha varchar(25) not null)");
        BaseDeDatos.execSQL("create table tareas(ClaveTarea integer primary key autoincrement, Titulo varchar(25) not null, FechaCreacion varchar(25) not null," +
                            "FechaRealizacion varchar(25) not null, Hora varchar(25) not null, Descripcion text not null, Completada varchar(2) not null)");
        BaseDeDatos.execSQL("create table multimedias(ClaveMultimedia integer primary key autoincrement, Tipo varchar(25) not null,"+
                            "Multimedia Text not null, Nombre Text not null, Tarea integer not null, foreign key(Tarea) references tareas(ClaveTarea))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public ArrayList MostrarNotas()
    {
        ArrayList <EntidadN> listanotas= new ArrayList<>();
        bd= this.getWritableDatabase();
        String consulta="Select * from notas order by Fecha desc, ClaveNota desc";
        Cursor registros = bd.rawQuery(consulta,null);
        if(registros.moveToFirst())
        {
            do {
                listanotas.add(new EntidadN(registros.getString(0),registros.getString(1),registros.getString(3),R.id.btneditar,R.id.btneliminar));
            }while(registros.moveToNext());
        }
        return listanotas;
    }


    public ArrayList MostrarTareas()
    {
        ArrayList <EntidadTM> listatareas= new ArrayList<>();
        bd= this.getWritableDatabase();
        String consulta="Select t.ClaveTarea, t.titulo, t.FechaRealizacion, t.hora," +
                " (select count(*) from multimedias m where m.tarea=t.clavetarea) as cantidad" +
                " from tareas t";
        Cursor registros = bd.rawQuery(consulta,null);
        if(registros.moveToFirst())
        {
            do {
                listatareas.add(new EntidadTM(registros.getString(0), registros.getString(1),
                                    "Fecha de realización: "+registros.getString(2)+" "+registros.getString(3),
                                "Cantidad Multimedias: "+registros.getString(4),R.id.cbcompletada,R.id.btneditar,R.id.btneliminar));
            }while(registros.moveToNext());
        }
        return listatareas;
    }

    public Nota ObtenerNota(int clave)
    {
        Nota n= new Nota();
        bd= this.getWritableDatabase();
        String consulta="Select * from notas where ClaveNota="+clave;
        Cursor registros = bd.rawQuery(consulta,null);
        if(registros.moveToFirst())
        {
            do {
                n.clave=registros.getInt(0);
                n.titulo=registros.getString(1);
                n.descripcion=registros.getString(2);
                n.fecha=registros.getString(3);
            }while(registros.moveToNext());
        }
        return n;
    }

    public int ObtenerClaveTarea(String titulo)
    {
        int clave=0;
        bd= this.getWritableDatabase();
        String consulta="Select ClaveTarea from tareas where titulo='"+titulo+"'";
        Cursor registros = bd.rawQuery(consulta,null);
        if(registros.moveToFirst())
        {
            clave = registros.getInt(0);
        }
        return clave;
    }

    public TareaM ObtenerTareaM(int clave)
    {
        TareaM tm= new TareaM();
        bd= this.getWritableDatabase();
        String consulta = "Select * from tareas where ClaveTarea ="+clave;
        Cursor registros = bd.rawQuery(consulta,null);
        if(registros.moveToFirst())
        {
            do {
                tm.clave=registros.getInt(0);
                tm.titulo=registros.getString(1);
                tm.fechac=registros.getString(2);
                tm.fechar=registros.getString(3);
                tm.hora=registros.getString(4);
                tm.descripcion=registros.getString(5);
                tm.completado=registros.getString(6);
            }while(registros.moveToNext());
        }
        return tm;
    }

    public ArrayList ObtenerMultimedia( int clave)
    {
        ArrayList <EntidadM> listamultimedia= new ArrayList<>();
        bd= this.getWritableDatabase();
        String consulta="Select * from multimedias where Tarea = " + clave;
        Cursor registros = bd.rawQuery(consulta,null);
        if(registros.moveToFirst())
        {
            do {
                if(registros.getString(1).equals("Foto"))
                {
                    listamultimedia.add(new EntidadM(registros.getString(1), R.drawable.camara,
                            Uri.parse(registros.getString(2)), registros.getString(3)));
                }
                if(registros.getString(1).equals("Video"))
                {
                    listamultimedia.add(new EntidadM(registros.getString(1), R.drawable.video,
                            Uri.parse(registros.getString(2)),registros.getString(3) ));
                }
                if(registros.getString(1).equals("Audio"))
                {
                    listamultimedia.add(new EntidadM(registros.getString(1), R.drawable.audio,
                            Uri.parse(registros.getString(2)), registros.getString(3)));
                }
                if(registros.getString(1).equals("Galeria"))
                {
                    listamultimedia.add(new EntidadM(registros.getString(1), R.drawable.galeria,
                            Uri.parse(registros.getString(2)), registros.getString(3)));
                }

            }while(registros.moveToNext());
        }
        return listamultimedia;
    }

}
