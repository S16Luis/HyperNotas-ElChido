package com.example.hypernotas;

import android.net.Uri;

public class Multimedia {
    int clave;
    String tipo;
    Uri multimedia;
    String nombre;
    int tarea;

    public int getClave() {
        return clave;
    }

    public void setClave(int clave) {
        this.clave = clave;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Uri getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(Uri multimedia) {
        this.multimedia = multimedia;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTarea() {
        return tarea;
    }

    public void setTarea(int tarea) {
        this.tarea = tarea;
    }

}
