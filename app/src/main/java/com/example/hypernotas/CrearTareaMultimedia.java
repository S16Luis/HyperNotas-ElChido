package com.example.hypernotas;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaRecorder;
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

    Button  btnaudio;
    EditText ettitulo, etfecha, ethora, etdescripcion;
    private int dia, mes, ano, hora, minutos;
    ListView lvmultimedias;
    ArrayList<EntidadM> lista;
    ArrayList<Uri> uris;
    ArrayList<String> tipos;
    ArrayList<String> nombres;
    AdaptadorMultimedias adaptador;
    String ruta="";
    String nombre="";
    Uri rutaarchivo;
    final int Photo=1;
    final int Video=2;
    final int Galeria=3;
    final int notificar=4;
    MediaRecorder grabacion;
    Calendar choras = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_tarea_multimedia);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);
        ettitulo=findViewById(R.id.txttituloCTM);
        etdescripcion=findViewById(R.id.txtdescripcionCTM);
        etfecha=findViewById(R.id.txtfechaCTM);
        ethora=findViewById(R.id.txthoraCTM);
        btnaudio=findViewById(R.id.btnaudioCTM);
        lvmultimedias=findViewById(R.id.lvmultimediasCTM);
        lista= new ArrayList<>();
        uris= new ArrayList<>();
        tipos= new ArrayList<>();
        nombres= new ArrayList<>();
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
        hora=choras.get(Calendar.HOUR_OF_DAY);
        minutos=choras.get(Calendar.MINUTE);

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
        nombre = "imagen"+tiempo;
        File storagefile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File photofile = File.createTempFile(nombre,".jpg",storagefile);
        ruta=photofile.getAbsolutePath();
        return photofile;
    }

    //Metodos para los videos
    public void TomarVideo(View v)
    {
        Intent tomarvideo= new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        if(tomarvideo.resolveActivity(getPackageManager())!=null)
        {
            File videofile = null;
            try
            {
                videofile=CrearVideoFile();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if(videofile!=null)
            {
                Uri videouri= FileProvider.getUriForFile(CrearTareaMultimedia.this,"com.example.hypernotas",videofile);
                tomarvideo.putExtra(MediaStore.EXTRA_OUTPUT,videouri);
                startActivityForResult(tomarvideo,Video);
            }
        }
    }

    public File CrearVideoFile() throws IOException {
        String tiempo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        nombre = "video"+tiempo;
        File storagefile = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File videofile = File.createTempFile(nombre,".mp4",storagefile);
        ruta=videofile.getAbsolutePath();
        return videofile;
    }

    //Metodos para los audios
    public void GrabarAudio(View v)
    {
            try
            {
                CrearAudioFile();
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            if(ruta!="")
            {
                if(grabacion==null)
                {
                    grabacion=new MediaRecorder();
                    grabacion.setAudioSource(MediaRecorder.AudioSource.MIC);
                    grabacion.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                    grabacion.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
                    grabacion.setOutputFile(ruta);
                    try
                    {
                        grabacion.prepare();
                        grabacion.start();

                    }
                    catch (IOException e) {

                    }
                    btnaudio.setText("⬜");
                    Toast.makeText(this, "Grabando...", Toast.LENGTH_SHORT).show();
                }
                else if(grabacion!=null)
                {
                    grabacion.stop();
                    grabacion.release();
                    grabacion=null;
                    btnaudio.setText("AUDIO");
                    Toast.makeText(this, "Grabación Terminada", Toast.LENGTH_SHORT).show();
                    rutaarchivo = Uri.parse(ruta);
                    lista.add(new EntidadM("Audio",R.drawable.audio,rutaarchivo,nombre));
                    tipos.add("Audio");
                    uris.add(rutaarchivo);
                    nombres.add(nombre);
                    adaptador= new AdaptadorMultimedias(this,lista);
                    lvmultimedias.setAdapter(adaptador);
                }
            }
    }

    public void CrearAudioFile() throws IOException {
        String tiempo = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        nombre = "audio"+tiempo;
        ruta=getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath()+"/"+nombre+".mp3";
    }

    //Metodo para la galeria
    public void CargarImagen(View v)
    {
        Intent galeria= new Intent(Intent.ACTION_PICK,MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        galeria.setType("image/");
        startActivityForResult(galeria,Galeria);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Photo && resultCode == RESULT_OK) {
            rutaarchivo = Uri.parse(ruta);
            lista.add(new EntidadM("Foto",R.drawable.camara,rutaarchivo,nombre));
            tipos.add("Foto");
            uris.add(rutaarchivo);
            nombres.add(nombre);
            adaptador= new AdaptadorMultimedias(this,lista);
            lvmultimedias.setAdapter(adaptador);
        }
        if (requestCode == Video && resultCode == RESULT_OK) {
            rutaarchivo = Uri.parse(ruta);
            lista.add(new EntidadM("Video",R.drawable.video,rutaarchivo,nombre));
            tipos.add("Video");
            uris.add(rutaarchivo);
            nombres.add(nombre);
            adaptador= new AdaptadorMultimedias(this,lista);
            lvmultimedias.setAdapter(adaptador);
        }
        if (requestCode == Galeria && resultCode == RESULT_OK) {
            rutaarchivo = data.getData();
            nombre="galeria"+rutaarchivo.toString();
            lista.add(new EntidadM("Galeria",R.drawable.galeria,rutaarchivo,nombre));
            tipos.add("Galeria");
            uris.add(rutaarchivo);
            nombres.add(nombre);
            adaptador= new AdaptadorMultimedias(this,lista);
            lvmultimedias.setAdapter(adaptador);
        }
    }//ultimo metodo para tomar fotos, videos, audios y galeria

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
                    multimedia.nombre=nombres.get(i);
                    multimedia.tarea=admin.ObtenerClaveTarea(tarea.titulo);
                    ContentValues regis = new ContentValues();
                    regis.put("tipo",multimedia.tipo);
                    regis.put("multimedia",String.valueOf(multimedia.multimedia));
                    regis.put("nombre",String.valueOf(multimedia.nombre));
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

    public void Recordatorio(View v)
    {
        String titulo=ettitulo.getText().toString();
        String descripcion=etdescripcion.getText().toString();
        String fechar=etfecha.getText().toString();
        String horas=ethora.getText().toString();
        if(!titulo.isEmpty()&&!descripcion.isEmpty()&&!fechar.isEmpty()&&!horas.isEmpty())
        {
            Intent intent = new Intent(this, AlarmReceiver.class);
            intent.putExtra("notificacion",notificar);
            intent.putExtra("titulo",titulo);
            intent.putExtra("descripcion",descripcion);
            intent.putExtra("hora",horas);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
            final Calendar horaalarma= Calendar.getInstance();
            horaalarma.set(Calendar.HOUR_OF_DAY,hora);
            horaalarma.set(Calendar.MINUTE,minutos);
            horaalarma.set(Calendar.SECOND,0);
            long alarma = horaalarma.getTimeInMillis()-System.currentTimeMillis();
            alarmManager.set(AlarmManager.RTC_WAKEUP,alarma,pendingIntent);
            Toast.makeText(this, "Recordatorio Creado", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }
}