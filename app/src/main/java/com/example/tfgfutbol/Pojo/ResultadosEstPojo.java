package com.example.tfgfutbol.Pojo;

public class ResultadosEstPojo {

    private String Cambio_jugador_lesion;
    private String Cambios_defensa_partido;
    private String Equipo;
    private String Jornada;
    private String Puntos_y;
    private String Temporada;
    private String Titulares_cambiados_jornada_anterior;
    private String cut1_y;
    private String cut2_y;

    public ResultadosEstPojo(){}

    public ResultadosEstPojo(String Cambio_jugador_lesion, String Cambios_defensa_partido,
                             String Equipo, String Jornada, String Puntos_y, String Temporada,
                             String Titulares_cambiados_jornada_anterior, String cut1_y,
                             String cut2_y){
        this.Cambio_jugador_lesion=Cambio_jugador_lesion;
        this.Cambios_defensa_partido=Cambios_defensa_partido;
        this.Equipo=Equipo;
        this.Jornada=Jornada;
        this.Puntos_y=Puntos_y;
        this.Temporada=Temporada;
        this.Titulares_cambiados_jornada_anterior=Titulares_cambiados_jornada_anterior;
        this.cut1_y=cut1_y;
        this.cut2_y=cut2_y;
    }


    public String getCambio_jugador_lesion() {
        return Cambio_jugador_lesion;
    }

    public void setCambio_jugador_lesion(String cambio_jugador_lesion) {
        Cambio_jugador_lesion = cambio_jugador_lesion;
    }

    public String getCambios_defensa_partido() {
        return Cambios_defensa_partido;
    }

    public void setCambios_defensa_partido(String cambios_defensa_partido) {
        Cambios_defensa_partido = cambios_defensa_partido;
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

    public String getPuntos_y() {
        return Puntos_y;
    }

    public void setPuntos_y(String puntos_y) {
        Puntos_y = puntos_y;
    }

    public String getTemporada() {
        return Temporada;
    }

    public void setTemporada(String temporada) {
        Temporada = temporada;
    }

    public String getTitulares_cambiados_jornada_anterior() {
        return Titulares_cambiados_jornada_anterior;
    }

    public void setTitulares_cambiados_jornada_anterior(String titulares_cambiados_jornada_anterior) {
        Titulares_cambiados_jornada_anterior = titulares_cambiados_jornada_anterior;
    }

    public String getCut1_y() {
        return cut1_y;
    }

    public void setCut1_y(String cut1_y) {
        this.cut1_y = cut1_y;
    }

    public String getCut2_y() {
        return cut2_y;
    }

    public void setCut2_y(String cut2_y) {
        this.cut2_y = cut2_y;
    }
}
