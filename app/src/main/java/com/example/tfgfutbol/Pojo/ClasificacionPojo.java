package com.example.tfgfutbol.Pojo;

public class ClasificacionPojo {

    private int clasificacion_goles_favor;
    private int clasificacion_goles_contra;
    private String clasificacion_jornada;
    private int clasificacion_empatados;
    private String clasificacion_equipo;
    private int clasificacion_ganados;
    private int clasificacion_partidos_jugados;
    private int clasificacion_perdidos;
    private int clasificacion_posicion;
    private int clasificacion_puntos;
    private String clasificacion_temporada;

    public ClasificacionPojo(){}

    public ClasificacionPojo(int clasificacion_goles_favor, int clasificacion_goles_contra, int clasificacion_empatados,
                             String clasificacion_equipo, int clasificacion_ganados, int clasificacion_partidos_jugados,
                             int clasificacion_perdidos, int clasificacion_posicion, int clasificacion_puntos,
                             String clasificacion_temporada, String clasificacion_jornada){
        this. clasificacion_goles_favor=clasificacion_goles_favor;
        this. clasificacion_goles_contra=clasificacion_goles_contra;
        this.clasificacion_empatados=clasificacion_empatados;
        this.clasificacion_equipo=clasificacion_equipo;
        this.clasificacion_ganados=clasificacion_ganados;
        this.clasificacion_perdidos=clasificacion_perdidos;
        this.clasificacion_partidos_jugados=clasificacion_partidos_jugados;
        this.clasificacion_posicion=clasificacion_posicion;
        this.clasificacion_puntos=clasificacion_puntos;
        this.clasificacion_jornada=clasificacion_jornada;
        this.clasificacion_temporada=clasificacion_temporada;
    }

    public int getClasificacion_empatados() {
        return clasificacion_empatados;
    }

    public void setClasificacion_empatados(int clasificacion_empatados) {
        this.clasificacion_empatados = clasificacion_empatados;
    }

    public String getClasificacion_equipo() {
        return clasificacion_equipo;
    }

    public void setClasificacion_equipo(String clasificacion_equipo) {
        this.clasificacion_equipo = clasificacion_equipo;
    }

    public int getClasificacion_ganados() {
        return clasificacion_ganados;
    }

    public void setClasificacion_ganados(int clasificacion_ganados) {
        this.clasificacion_ganados = clasificacion_ganados;
    }

    public int getClasificacion_partidos_jugados() {
        return clasificacion_partidos_jugados;
    }

    public void setClasificacion_partidos_jugados(int clasificacion_partidos_jugados) {
        this.clasificacion_partidos_jugados = clasificacion_partidos_jugados;
    }

    public int getClasificacion_perdidos() {
        return clasificacion_perdidos;
    }

    public void setClasificacion_perdidos(int clasificacion_perdidos) {
        this.clasificacion_perdidos = clasificacion_perdidos;
    }

    public int getClasificacion_puntos() {
        return clasificacion_puntos;
    }

    public void setClasificacion_puntos(int clasificacion_puntos) {
        this.clasificacion_puntos = clasificacion_puntos;
    }

    public int getClasificacion_goles_favor() {
        return clasificacion_goles_favor;
    }

    public void setClasificacion_goles_favor(int clasificacion_goles_favor) {
        this.clasificacion_goles_favor = clasificacion_goles_favor;
    }

    public int getClasificacion_goles_contra() {
        return clasificacion_goles_contra;
    }

    public void setClasificacion_goles_contra(int clasificacion_goles_contra) {
        this.clasificacion_goles_contra = clasificacion_goles_contra;
    }

    public String getClasificacion_jornada() {
        return clasificacion_jornada;
    }

    public void setClasificacion_jornada(String clasificacion_jornada) {
        this.clasificacion_jornada = clasificacion_jornada;
    }

    public int getClasificacion_posicion() {
        return clasificacion_posicion;
    }

    public void setClasificacion_posicion(int clasificacion_posicion) {
        this.clasificacion_posicion = clasificacion_posicion;
    }

    public String getClasificacion_temporada() {
        return clasificacion_temporada;
    }

    public void setClasificacion_temporada(String clasificacion_temporada) {
        this.clasificacion_temporada = clasificacion_temporada;
    }
}
