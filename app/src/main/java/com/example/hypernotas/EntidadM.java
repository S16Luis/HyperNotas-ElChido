package com.example.hypernotas;

import android.net.Uri;

public class EntidadM {
    private String tipo;
    private int img;
    private Uri uri;
    private int btneditar;
    private int btneliminar;

    public EntidadM(String tipo, int img, Uri uri, int btneditar, int btneliminar) {
        this.tipo=tipo;
        this.img = img;
        this.uri =uri;
        this.btneditar = btneditar;
        this.btneliminar = btneliminar;
    }

    public String getTipo(){return tipo;}
    public int getImg(){return img;}
    public Uri getUri(){return uri;}

}
