package com.example.hypernotas;

public class EntidadTM {
    private String tvclave;
    private String tvtitulo;
    private String tvfecha;
    private String tvcantidad;
    private String completada;
    private int cbcompletada;
    private int btneditar;
    private int btneliminar;

    public EntidadTM(String tvclave, String tvtitulo, String tvfecha, String tvcantidad,String completada, int cbcompletada, int btneditar, int btneliminar) {
        this.tvclave = tvclave;
        this.tvtitulo = tvtitulo;
        this.tvfecha = tvfecha;
        this.tvcantidad = tvcantidad;
        this.completada = completada;
        this.cbcompletada = cbcompletada;
        this.btneditar = btneditar;
        this.btneliminar = btneliminar;
    }

    public String getTvclave(){return tvclave;}
    public String getTvtitulo(){return tvtitulo;}
    public String getTvfecha(){return tvfecha;}
    public String getTvcantidad(){return tvcantidad;}
    public String getTvcompletada(){return completada;}
}
