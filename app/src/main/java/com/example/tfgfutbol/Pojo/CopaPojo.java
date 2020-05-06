package com.example.tfgfutbol.Pojo;

public class CopaPojo {

    private String copa_fecha;
    private String copa_local;
    private String copa_resultado;
    private String copa_visitante;
    private String copa_temporada;

    public CopaPojo(){

    }

    public CopaPojo(String copa_fecha, String copa_local, String copa_resultado, String copa_temporada, String copa_visitante){
        this.copa_fecha=copa_fecha;
        this.copa_local=copa_local;
        this.copa_resultado=copa_resultado;
        this.copa_temporada=copa_temporada;
        this.copa_visitante=copa_visitante;
    }


    public String getCopa_fecha() {
        return copa_fecha;
    }

    public void setCopa_fecha(String copa_fecha) {
        this.copa_fecha = copa_fecha;
    }

    public String getCopa_local() {
        return copa_local;
    }

    public void setCopa_local(String copa_local) {
        this.copa_local = copa_local;
    }

    public String getCopa_temporada() {
        return copa_temporada;
    }

    public void setCopa_temporada(String copa_temporada) {
        this.copa_temporada = copa_temporada;
    }

    public String getCopa_visitante() {
        return copa_visitante;
    }

    public void setCopa_visitante(String copa_visitante) {
        this.copa_visitante = copa_visitante;
    }

    public String getCopa_resultado() {
        return copa_resultado;
    }

    public void setCopa_resultado(String copa_resultado) {
        this.copa_resultado = copa_resultado;
    }
}
