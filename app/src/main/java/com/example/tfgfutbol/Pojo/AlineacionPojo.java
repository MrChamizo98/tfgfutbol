package com.example.tfgfutbol.Pojo;

import android.os.Parcel;
import android.os.Parcelable;

import io.realm.RealmObject;

public class AlineacionPojo{


    private String alineacion_temporada;
    private String alineacion_jornada;
    private String alineacion_equipo;
    private String alineacion_jugador;
    private String alineacion_gol;
    private String alineacion_amarilla;
    private String alineacion_roja;
    private String alineacion_estado;
    private String alineacion_cambio;
    private String alineacion_asistencia;
    private String alineacion_lesion;


    public AlineacionPojo(){}

    public AlineacionPojo(String alineacion_amarilla, String alineacion_asistencia,
                          String alineacion_cambio, String alineacion_equipo,
                          String alineacion_estado, String alineacion_gol,
                          String alineacion_jornada, String alineacion_jugador,
                          String alineacion_lesion, String alineacion_roja, String alineacion_temporada){
        this.alineacion_amarilla=alineacion_amarilla;
        this.alineacion_asistencia=alineacion_asistencia;
        this.alineacion_cambio=alineacion_cambio;
        this.alineacion_equipo=alineacion_equipo;
        this.alineacion_estado=alineacion_estado;
        this.alineacion_gol=alineacion_gol;
        this.alineacion_jornada=alineacion_jornada;
        this.alineacion_jugador=alineacion_jugador;
        this.alineacion_lesion=alineacion_lesion;
        this.alineacion_roja=alineacion_roja;
        this.alineacion_temporada=alineacion_temporada;
    }

    public String getAlineacion_temporada() {
        return alineacion_temporada;
    }

    public void setAlineacion_temporada(String alineacion_temporada) {
        this.alineacion_temporada = alineacion_temporada;
    }

    public String getAlineacion_jornada() {
        return alineacion_jornada;
    }

    public void setAlineacion_jornada(String alineacion_jornada) {
        this.alineacion_jornada = alineacion_jornada;
    }

    public String getAlineacion_equipo() {
        return alineacion_equipo;
    }

    public void setAlineacion_equipo(String alineacion_equipo) {
        this.alineacion_equipo = alineacion_equipo;
    }

    public String getAlineacion_jugador() {
        return alineacion_jugador;
    }

    public void setAlineacion_jugador(String alineacion_jugador) {
        this.alineacion_jugador = alineacion_jugador;
    }

    public String getAlineacion_gol() {
        return alineacion_gol;
    }

    public void setAlineacion_gol(String alineacion_gol) {
        this.alineacion_gol = alineacion_gol;
    }

    public String getAlineacion_amarilla() {
        return alineacion_amarilla;
    }

    public void setAlineacion_amarilla(String alineacion_amarilla) {
        this.alineacion_amarilla = alineacion_amarilla;
    }

    public String getAlineacion_roja() {
        return alineacion_roja;
    }

    public void setAlineacion_roja(String alineacion_roja) {
        this.alineacion_roja = alineacion_roja;
    }

    public String getAlineacion_estado() {
        return alineacion_estado;
    }

    public void setAlineacion_estado(String alineacion_estado) {
        this.alineacion_estado = alineacion_estado;
    }

    public String getAlineacion_cambio() {
        return alineacion_cambio;
    }

    public void setAlineacion_cambio(String alineacion_cambio) {
        this.alineacion_cambio = alineacion_cambio;
    }

    public String getAlineacion_lesion() {
        return alineacion_lesion;
    }

    public void setAlineacion_lesion(String alineacion_lesion) {
        this.alineacion_lesion = alineacion_lesion;
    }

    public String getAlineacion_asistencia() {
        return alineacion_asistencia;
    }

    public void setAlineacion_asistencia(String alineacion_asistencia) {
        this.alineacion_asistencia = alineacion_asistencia;
    }
}
