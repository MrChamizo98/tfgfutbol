package com.example.tfgfutbol.Realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Plantilla extends RealmObject {

    @PrimaryKey
    private String id;
    private String Liga;
    private String plantilla_equipo;
    private String plantilla_name;
    private String plantilla_edad;
    private String plantilla_foto;
    private String plantilla_pais;
    private String plantilla_goles;
    private String plantilla_rojas;
    private String plantilla_amarillas;
    private String plantilla_dorsal;
    private String plantilla_altura;
    private String plantilla_peso;
    private String plantilla_posicion;
    private String plantilla_temporada;

    public Plantilla(){
    }

    public Plantilla(String plantilla_equipo,String plantilla_name, String plantilla_edad,
                         String plantilla_foto, String plantilla_pais, String plantilla_goles,
                         String plantilla_rojas, String plantilla_amarillas, String plantilla_dorsal,
                         String plantilla_altura, String plantilla_peso, String plantilla_posicion,
                         String plantilla_temporada){
        this.plantilla_equipo=plantilla_equipo;
        this.plantilla_name=plantilla_name;
        this.plantilla_edad=plantilla_edad;
        this.plantilla_foto=plantilla_foto;
        this.plantilla_pais=plantilla_pais;
        this.plantilla_goles=plantilla_goles;
        this.plantilla_rojas=plantilla_rojas;
        this.plantilla_amarillas=plantilla_amarillas;
        this.plantilla_dorsal=plantilla_dorsal;
        this.plantilla_altura=plantilla_altura;
        this.plantilla_peso=plantilla_peso;
        this.plantilla_posicion=plantilla_posicion;
        this.plantilla_temporada=plantilla_temporada;
    }

    public String getPlantilla_equipo() {
        return plantilla_equipo;
    }

    public void setPlantilla_equipo(String plantilla_equipo) {
        this.plantilla_equipo = plantilla_equipo;
    }

    public String getPlantilla_name() {
        return plantilla_name;
    }

    public void setPlantilla_name(String plantilla_name) {
        this.plantilla_name = plantilla_name;
    }



    public String getPlantilla_foto() {
        return plantilla_foto;
    }

    public void setPlantilla_foto(String plantilla_foto) {
        this.plantilla_foto = plantilla_foto;
    }

    public String getPlantilla_pais() {
        return plantilla_pais;
    }

    public void setPlantilla_pais(String plantilla_pais) {
        this.plantilla_pais = plantilla_pais;
    }


    public String getPlantilla_posicion() {
        return plantilla_posicion;
    }

    public void setPlantilla_posicion(String plantilla_posicion) {
        this.plantilla_posicion = plantilla_posicion;
    }

    public String getPlantilla_temporada() {
        return plantilla_temporada;
    }

    public void setPlantilla_temporada(String plantilla_temporada) {
        this.plantilla_temporada = plantilla_temporada;
    }


    public String getPlantilla_altura() {
        return plantilla_altura;
    }

    public void setPlantilla_altura(String plantilla_altura) {
        this.plantilla_altura = plantilla_altura;
    }

    public String getPlantilla_peso() {
        return plantilla_peso;
    }

    public void setPlantilla_peso(String plantilla_peso) {
        this.plantilla_peso = plantilla_peso;
    }


    public String getPlantilla_edad() {
        return plantilla_edad;
    }

    public void setPlantilla_edad(String plantilla_edad) {
        this.plantilla_edad = plantilla_edad;
    }

    public String getPlantilla_goles() {
        return plantilla_goles;
    }

    public void setPlantilla_goles(String plantilla_goles) {
        this.plantilla_goles = plantilla_goles;
    }

    public String getPlantilla_rojas() {
        return plantilla_rojas;
    }

    public void setPlantilla_rojas(String plantilla_rojas) {
        this.plantilla_rojas = plantilla_rojas;
    }

    public String getPlantilla_amarillas() {
        return plantilla_amarillas;
    }

    public void setPlantilla_amarillas(String plantilla_amarillas) {
        this.plantilla_amarillas = plantilla_amarillas;
    }

    public String getPlantilla_dorsal() {
        return plantilla_dorsal;
    }

    public void setPlantilla_dorsal(String plantilla_dorsal) {
        this.plantilla_dorsal = plantilla_dorsal;
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
