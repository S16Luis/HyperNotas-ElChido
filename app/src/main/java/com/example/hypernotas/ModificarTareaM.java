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

public class ModificarTareaM extends AppCompatActivity {
    EditText titulo,descripcion, fecha,ethora;
    ListView lvmultimedia;
    ArrayList<EntidadM> lista;
    ArrayList<Uri> uris;
    ArrayList<String> tipos;
    AdaptadorMultimedias Adaptador;
    private int dia, mes, ano,hora, minutos,clave;
    String ruta= "";
    Uri rutafoto;
    final  int Photo=1;
    Button btnfecha, btnhora, btncamara, btnvideo, btnaudio, btngaleria;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificar_tarea_m);



        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        titulo=findViewById(R.id.txttituloMODTM);
        descripcion=findViewById(R.id.txtdescripcionMODTM);
        btnfecha= findViewById(R.id.btnfechaMODTM);
        btnhora= findViewById(R.id.btnhoraMODTM);
        fecha= findViewById(R.id.txtfechaMODTM);
        ethora= findViewById(R.id.txthoraMODTM);
        btncamara= findViewById(R.id.btncamaraMODTM);
        btnvideo= findViewById(R.id.btnvideoMODTM);
        btnaudio= findViewById(R.id.btnaudioMODTM);
        btngaleria= findViewById(R.id.btngaleriaMODTM);
        lvmultimedia=findViewById(R.id.lvmultimediasMODTM);
        clave=Integer.parseInt(getIntent().getStringExtra("clave"));
        BD admin =new BD(this,"hypernotas",null,1);
        TareaM tar=admin.ObtenerTareaM(clave);
        lista = admin.ObtenerMultimedia(clave);
        titulo.setText(tar.titulo);
        descripcion.setText(tar.descripcion);
        fecha.setText(tar.fechar);
        ethora.setText(tar.hora);
        Adaptador = new AdaptadorMultimedias(this, lista);
        lvmultimedia.setAdapter(Adaptador);
        uris = new ArrayList<>();
        tipos = new ArrayList<>();
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
                fecha.setText(i2+"/"+(i1+1)+"/"+i);
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
                Uri photouri= FileProvider.getUriForFile(ModificarTareaM.this,"com.example.hypernotas",photofile);
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
            Adaptador= new AdaptadorMultimedias(this,lista);
            lvmultimedia.setAdapter(Adaptador);
        }
    }//ultimo metodo para tomar fotos

    public void Guardar(View view)
    {
        BD admin =new BD(this,"hypernotas",null,1);
        SQLiteDatabase bd= admin.getWritableDatabase();
        String tit=titulo.getText().toString();
        String descrip=descripcion.getText().toString();
        String fechar=fecha.getText().toString();
        String hora=ethora.getText().toString();
        if(!tit.isEmpty()&&!descrip.isEmpty()&&!fechar.isEmpty()&&!hora.isEmpty()) {
            if (lista.size() == 0) {
                Toast.makeText(this, "Debes agregar minimo 1 multimedia", Toast.LENGTH_SHORT).show();
            } else {
                TareaM tarea = new TareaM();
                tarea.titulo = tit;
                tarea.descripcion = descrip;
                tarea.fechac = getDate();
                tarea.fechar = fechar;
                tarea.hora = hora;
                ContentValues registro = new ContentValues();
                registro.put("titulo", tarea.titulo);
                registro.put("fechacreacion", tarea.fechac);
                registro.put("fecharealizacion", tarea.fechar);
                registro.put("hora", tarea.hora);
                registro.put("descripcion", tarea.descripcion);
                bd.update("tareas", registro, "ClaveTarea = " + clave, null);
                for (int i = 0; i < uris.size(); i++) {
                    Multimedia multimedia = new Multimedia();
                    multimedia.tipo = tipos.get(i);
                    multimedia.multimedia = uris.get(i);
                    multimedia.tarea = clave;
                    ContentValues regis = new ContentValues();
                    regis.put("tipo", multimedia.tipo);
                    regis.put("multimedia", String.valueOf(multimedia.multimedia));
                    regis.put("tarea", multimedia.tarea);
                    bd.insert("multimedias", null, regis);
                }
                bd.close();
                Toast.makeText(this, "Tarea Editada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
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