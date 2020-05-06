package com.example.tfgfutbol.Pojo;

public class LigaPojo {

    private int liga_equipos;
    private String liga_foto;
    private String liga_nombre;

    public LigaPojo(){

    }

    public LigaPojo(int liga_equipos, String liga_foto, String liga_nombre){
        this.liga_equipos=liga_equipos;
        this.liga_foto=liga_foto;
        this.liga_nombre=liga_nombre;
    }

    public int getLiga_equipos() {
        return liga_equipos;
    }

    public void setLiga_equipos(int liga_equipos) {
        this.liga_equipos = liga_equipos;
    }

    public String getLiga_foto() {
        return liga_foto;
    }

    public void setLiga_foto(String liga_foto) {
        this.liga_foto = liga_foto;
    }

    public String getLiga_nombre() {
        return liga_nombre;
    }

    public void setLiga_nombre(String liga_nombre) {
        this.liga_nombre = liga_nombre;
    }
}
