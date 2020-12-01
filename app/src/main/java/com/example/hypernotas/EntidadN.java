package com.example.hypernotas;

public class EntidadN {
    private String tvclave;
    private String tvtitulo;
    private String tvfecha;
    private int btneditar;
    private int btneliminar;

    public EntidadN(String tvclave,String tvtitulo,String tvfecha, int btneditar, int btneliminar) {
        this.tvclave = tvclave;
        this.tvtitulo = tvtitulo;
        this.tvfecha = tvfecha;
        this.btneditar = btneditar;
        this.btneliminar = btneliminar;
    }

    public String getTvclave(){return tvclave;}
    public String getTvtitulo(){return tvtitulo;}
    public String getTvfecha(){return tvfecha;}

}
