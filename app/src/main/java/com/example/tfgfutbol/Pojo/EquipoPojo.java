package com.example.tfgfutbol.Pojo;

public class EquipoPojo {

    private String equipos_entrenador;
    private String equipos_escudo;
    private String equipos_estadio;
    private String equipos_name;
    private String equipos_ubicacion;
    private String equipos_temporada;

    public EquipoPojo(){}

    public EquipoPojo(String equipos_entrenador, String equipos_escudo, String equipos_estadio,
                      String equipos_name, String equipos_ubicacion, String equipos_temporada){
        this.equipos_entrenador=equipos_entrenador;
        this.equipos_escudo=equipos_escudo;
        this.equipos_estadio=equipos_estadio;
        this.equipos_name=equipos_name;
        this.equipos_ubicacion=equipos_ubicacion;
        this.equipos_temporada=equipos_temporada;
    }


    public String getEquipos_entrenador() {
        return equipos_entrenador;
    }

    public void setEquipos_entrenador(String equipos_entrenador) {
        this.equipos_entrenador = equipos_entrenador;
    }

    public String getEquipos_escudo() {
        return equipos_escudo;
    }

    public void setEquipos_escudo(String equipos_escudo) {
        this.equipos_escudo = equipos_escudo;
    }

    public String getEquipos_estadio() {
        return equipos_estadio;
    }

    public void setEquipos_estadio(String equipos_estadio) {
        this.equipos_estadio = equipos_estadio;
    }

    public String getEquipos_name() {
        return equipos_name;
    }

    public void setEquipos_name(String equipos_name) {
        this.equipos_name = equipos_name;
    }

    public String getEquipos_ubicacion() {
        return equipos_ubicacion;
    }

    public void setEquipos_ubicacion(String equipos_ubicacion) {
        this.equipos_ubicacion = equipos_ubicacion;
    }

    public String getEquipos_temporada() {
        return equipos_temporada;
    }

    public void setEquipos_temporada(String equipos_temporada) {
        this.equipos_temporada = equipos_temporada;
    }
}
