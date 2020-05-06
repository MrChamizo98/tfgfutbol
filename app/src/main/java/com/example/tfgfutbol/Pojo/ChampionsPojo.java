package com.example.tfgfutbol.Pojo;

public class ChampionsPojo {
    private String champions_fecha;
    private String champions_grupo;
    private String champions_local;
    private String champions_visitante;
    private String champions_resultado;
    private String champions_temporada;

    public ChampionsPojo(){

    }

    public ChampionsPojo(String champions_fecha, String champions_grupo, String champions_local, String champions_resultado,
                         String champions_temporada, String champions_visitante){
        this.champions_fecha=champions_fecha;
        this.champions_grupo=champions_grupo;
        this.champions_local=champions_local;
        this.champions_resultado=champions_resultado;
        this.champions_temporada=champions_temporada;
        this.champions_visitante=champions_visitante;
    }

    public String getChampions_fecha() {
        return champions_fecha;
    }

    public void setChampions_fecha(String champions_fecha) {
        this.champions_fecha = champions_fecha;
    }

    public String getChampions_grupo() {
        return champions_grupo;
    }

    public void setChampions_grupo(String champions_grupo) {
        this.champions_grupo = champions_grupo;
    }

    public String getChampions_local() {
        return champions_local;
    }

    public void setChampions_local(String champions_local) {
        this.champions_local = champions_local;
    }

    public String getChampions_visitante() {
        return champions_visitante;
    }

    public void setChampions_visitante(String champions_visitante) {
        this.champions_visitante = champions_visitante;
    }

    public String getChampions_resultado() {
        return champions_resultado;
    }

    public void setChampions_resultado(String champions_resultado) {
        this.champions_resultado = champions_resultado;
    }

    public String getChampions_temporada() {
        return champions_temporada;
    }

    public void setChampions_temporada(String champions_temporada) {
        this.champions_temporada = champions_temporada;
    }
}
