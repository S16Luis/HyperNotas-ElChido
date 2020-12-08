package com.example.hypernotas;

import android.net.Uri;

public class EntidadM {
    private String tipo;
    private int img;
    private Uri uri;
    private String nombre;

    public EntidadM(String tipo, int img, Uri uri, String nombre) {
        this.tipo=tipo;
        this.img = img;
        this.uri =uri;
        this.nombre = nombre;
    }

    public String getTipo(){return tipo;}
    public int getImg(){return img;}
    public Uri getUri(){return uri;}
    public String getNombre(){return nombre;}
}
