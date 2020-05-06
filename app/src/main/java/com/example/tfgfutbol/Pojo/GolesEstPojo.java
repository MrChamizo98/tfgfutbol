package com.example.tfgfutbol.Pojo;

public class GolesEstPojo {

    private String Equipo;
    private String Jornada;
    private String Temporada;
    private String Cambios_defensa_partido;
    private String Cambio_lesion_partido;
    private String Titulares_cambio_jornadapasada;
    private String exp_lambda;
    private String y_lambda;

    public GolesEstPojo(){

    }

    public GolesEstPojo(String Equipo,
             String Jornada,
             String Temporada,
             String Cambios_defensa_partido,
             String Cambio_lesion_partido,
             String Titulares_cambio_jornadapasada,
             String exp_lambda,
             String y_lambda){
        this.Equipo=Equipo;
        this.Jornada=Jornada;
        this.Temporada=Temporada;
        this.Cambios_defensa_partido=Cambios_defensa_partido;
        this.Cambio_lesion_partido=Cambio_lesion_partido;
        this.Titulares_cambio_jornadapasada=Titulares_cambio_jornadapasada;
        this.exp_lambda=exp_lambda;
        this.y_lambda=y_lambda;
    }


    public String getEquipo() {
        return Equipo;
    }

    public void setEquipo(String equipo) {
        Equipo = equipo;
    }

    public String getJornada() {
        return Jornada;
    }

    public void setJornada(String jornada) {
        Jornada = jornada;
    }

    public String getTemporada() {
        return Temporada;
    }

    public void setTemporada(String temporada) {
        Temporada = temporada;
    }

    public String getCambios_defensa_partido() {
        return Cambios_defensa_partido;
    }

    public void setCambios_defensa_partido(String cambios_defensa_partido) {
        Cambios_defensa_partido = cambios_defensa_partido;
    }

    public String getCambio_lesion_partido() {
        return Cambio_lesion_partido;
    }

    public void setCambio_lesion_partido(String cambio_lesion_partido) {
        Cambio_lesion_partido = cambio_lesion_partido;
    }

    public String getTitulares_cambio_jornadapasada() {
        return Titulares_cambio_jornadapasada;
    }

    public void setTitulares_cambio_jornadapasada(String titulares_cambio_jornadapasada) {
        Titulares_cambio_jornadapasada = titulares_cambio_jornadapasada;
    }

    public String getExp_lambda() {
        return exp_lambda;
    }

    public void setExp_lambda(String exp_lambda) {
        this.exp_lambda = exp_lambda;
    }

    public String getY_lambda() {
        return y_lambda;
    }

    public void setY_lambda(String y_lambda) {
        this.y_lambda = y_lambda;
    }
}
