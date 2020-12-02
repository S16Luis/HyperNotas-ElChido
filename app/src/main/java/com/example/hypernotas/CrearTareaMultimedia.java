package com.example.hypernotas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class CrearTareaMultimedia extends AppCompatActivity {

    Button btnfecha, btnhora, btncamara, btnvideo, btnaudio, btngaleria;
    EditText ettitulo, etfecha, ethora, etdescripcion;
    private int dia, mes, ano, hora, minutos;
    ListView lvmultimedias;
    ArrayList<EntidadM> lista;
    ArrayList<Uri> uris;
    ArrayList<String> tipos;
    AdaptadorMultimedias adaptador;
    String ruta="";
    Uri rutafoto;
    final int Photo=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea_multimedia);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        ettitulo=findViewById(R.id.txttituloCTM);
        etdescripcion=findViewById(R.id.txtdescripcionCTM);
        btnfecha=findViewById(R.id.btnfechaCTM);
        btnhora=findViewById(R.id.btnhoraCTM);
        etfecha=findViewById(R.id.txtfechaCTM);
        ethora=findViewById(R.id.txthoraCTM);
        btncamara=findViewById(R.id.btncamaraCTM);
        btnvideo=findViewById(R.id.btnvideoCTM);
        btnaudio=findViewById(R.id.btnaudioCTM);
        btngaleria=findViewById(R.id.btngaleriaCTM);
        lvmultimedias=findViewById(R.id.lvmultimediasCTM);
        lista= new ArrayList<>();
        uris= new ArrayList<>();
        tipos= new ArrayList<>();
    }

    public void SeleccionarFecha(View v)
    {
        final Calendar c = Calendar.getInstance();
        dia=c.get(Calendar.DAY_OF_MONTH);
        mes=c.get(Calendar.MONTH);
        ano=c.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                etfecha.setText(i2+"/"+(i1+1)+"/"+i);
            }
        },ano,mes,dia);
        datePickerDialog.show();
    }

    public void SeleccionarHora(View v)
    {
        Calendar c = Calendar.getInstance();
        hora=c.get(Calendar.HOUR_OF_DAY);
        minutos=c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hora, int minutos) {
                ethora.setText(hora+":"+minutos);
            }
        },hora,minutos,true);
        timePickerDialog.show();
    }

    //Metodos para las fotos
    public void TomarFoto(View v)
    {
        Intent tomarfoto= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(tomarfoto.resolveActivity(getPackageManager())!=null)
        {
            File photofile = null;
            try
            {
                photofile=CrearPhotoFile();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if(photofile!=null)
            {
                Uri photouri= FileProvider.getUriForFile(CrearTareaMultimedia.this,"com.example.hypernotas",photofile);
                tomarfoto.putExtra(MediaStore.EXTRA_OUTPUT,photouri);
                startActivityForResult(tomarfoto,Photo);
            }
        }
    }

    public File CrearPhotoFile() throws IOException {
        String tiempo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String nombre = "imagen"+tiempo;
        File storagefile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photofile = File.createTempFile(nombre,".jpg",storagefile);
        ruta=photofile.getAbsolutePath();
        return photofile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Photo && resultCode == RESULT_OK) {
            rutafoto = Uri.parse(ruta);
            lista.add(new EntidadM("Foto",R.drawable.camara,rutafoto,R.id.btnañadir,R.id.btnelim));
            tipos.add("Foto");
            uris.add(rutafoto);
            adaptador= new AdaptadorMultimedias(this,lista);
            lvmultimedias.setAdapter(adaptador);
        }
    }//ultimo metodo para tomar fotos

    public void Video(View v)
    {
        //lista.add(new EntidadM("Video",R.drawable.video,R.id.btnañadir,R.id.btnelim));
        adaptador= new AdaptadorMultimedias(this,lista);
        lvmultimedias.setAdapter(adaptador);
    }

    public void Audio(View v)
    {
        //lista.add(new EntidadM("Audio",R.drawable.audio,R.id.btnañadir,R.id.btnelim));
        adaptador= new AdaptadorMultimedias(this,lista);
        lvmultimedias.setAdapter(adaptador);
    }

    public void Galeria(View v)
    {
        //lista.add(new EntidadM("Galeria",R.drawable.galeria,R.id.btnañadir,R.id.btnelim));
        adaptador= new AdaptadorMultimedias(this,lista);
        lvmultimedias.setAdapter(adaptador);
    }

    public void Guardar(View view)
    {
        BD admin =new BD(this,"hypernotas",null,1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String titulo=ettitulo.getText().toString();
        String descripcion=etdescripcion.getText().toString();
        String fechar=etfecha.getText().toString();
        String hora=ethora.getText().toString();
        if(!titulo.isEmpty()&&!descripcion.isEmpty()&&!fechar.isEmpty()&&!hora.isEmpty())
        {
            if(lista.size()==0)
            {
                Toast.makeText(this, "Debes agregar minimo 1 multimedia", Toast.LENGTH_SHORT).show();
            }
            else
            {
                TareaM tarea=new TareaM();
                tarea.titulo=titulo;
                tarea.descripcion=descripcion;
                tarea.fechac=getDate();
                tarea.fechar=fechar;
                tarea.hora=hora;
                tarea.completado="No";
                ContentValues registro = new ContentValues();
                registro.put("titulo",tarea.titulo);
                registro.put("fechacreacion",tarea.fechac);
                registro.put("fecharealizacion",tarea.fechar);
                registro.put("hora",tarea.hora);
                registro.put("descripcion",tarea.descripcion);
                registro.put("completada",tarea.completado);
                bd.insert("tareas",null, registro);
                for(int i=0; i<uris.size();i++)
                {
                    Multimedia multimedia = new Multimedia();
                    multimedia.tipo=tipos.get(i);
                    multimedia.multimedia=uris.get(i);
                    multimedia.tarea=admin.ObtenerClaveTarea(tarea.titulo);
                    ContentValues regis = new ContentValues();
                    regis.put("tipo",multimedia.tipo);
                    regis.put("multimedia",String.valueOf(multimedia.multimedia));
                    regis.put("tarea",multimedia.tarea);
                    bd.insert("multimedias",null, regis);
                }
                bd.close();
                Toast.makeText(this, "Tarea Guardada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(),MainActivity.class);
                startActivity(intent);
                finish();
            }
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