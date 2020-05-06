package com.example.tfgfutbol.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Partidos extends RealmObject {

    @PrimaryKey
    private String id;
    private String Liga;
    private String partidos_fecha;
    private String partidos_equipo_local;
    private String partidos_equipo_visitante;
    private String partidos_resultado;
    private String partidos_jornada;
    private String partidos_foto_local;
    private String partidos_foto_visitante;
    private String partidos_temporada;


    public Partidos(){}
    public Partidos(String partidos_fecha, String partidos_jornada, String partidos_local, String partidos_resultado, String partidos_visitante,
                        String partidos_foto_local,String partidos_foto_visitante, String partidos_temporada ){
        this.partidos_fecha=partidos_fecha;
        this.partidos_jornada=partidos_jornada;
        this.partidos_equipo_local=partidos_local;
        this.partidos_resultado=partidos_resultado;
        this.partidos_equipo_visitante=partidos_visitante;
        this.partidos_foto_local=partidos_foto_local;
        this.partidos_foto_visitante=partidos_foto_visitante;
        this.partidos_temporada=partidos_temporada;
    }
    public Partidos(String partidos_fecha, String partidos_local, String partidos_resultado, String partidos_visitante){
        this.partidos_fecha=partidos_fecha;
        this.partidos_equipo_local=partidos_local;
        this.partidos_resultado=partidos_resultado;
        this.partidos_equipo_visitante=partidos_visitante;
    }

    public String getPartidos_fecha() {
        return partidos_fecha;
    }

    public void setPartidos_fecha(String partidos_fecha) {
        this.partidos_fecha = partidos_fecha;
    }



    public String getPartidos_resultado() {
        return partidos_resultado;
    }

    public void setPartidos_resultado(String partidos_resultado) {
        this.partidos_resultado = partidos_resultado;
    }

    public String getPartidos_jornada() {
        return partidos_jornada;
    }

    public void setPartidos_jornada(String partidos_jornada) {
        this.partidos_jornada = partidos_jornada;
    }

    public String getPartidos_equipo_local() {
        return partidos_equipo_local;
    }

    public void setPartidos_equipo_local(String partidos_equipo_local) {
        this.partidos_equipo_local = partidos_equipo_local;
    }

    public String getPartidos_equipo_visitante() {
        return partidos_equipo_visitante;
    }

    public void setPartidos_equipo_visitante(String partidos_equipo_visitante) {
        this.partidos_equipo_visitante = partidos_equipo_visitante;
    }

    public String getPartidos_foto_local() {
        return partidos_foto_local;
    }

    public void setPartidos_foto_local(String partidos_foto_local) {
        this.partidos_foto_local = partidos_foto_local;
    }

    public String getPartidos_foto_visitante() {
        return partidos_foto_visitante;
    }

    public void setPartidos_foto_visitante(String partidos_foto_visitante) {
        this.partidos_foto_visitante = partidos_foto_visitante;
    }

    public String getPartidos_temporada() {
        return partidos_temporada;
    }

    public void setPartidos_temporada(String partidos_temporada) {
        this.partidos_temporada = partidos_temporada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLiga() {
        return Liga;
    }

    public void setLiga(String liga) {
        Liga = liga;
    }
}
