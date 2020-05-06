package com.example.tfgfutbol.Pojo;

public class UefaPojo {
    private String uefa_fecha;
    private String uefa_grupo;
    private String uefa_local;
    private String uefa_visitante;
    private String uefa_resultado;
    private String uefa_temporada;

    public UefaPojo(){

    }

    public UefaPojo(String uefa_fecha, String uefa_grupo, String uefa_local, String uefa_resultado, String uefa_temporada,
                    String uefa_visitante){
        this.uefa_fecha=uefa_fecha;
        this.uefa_grupo=uefa_grupo;
        this.uefa_local=uefa_local;
        this.uefa_resultado=uefa_resultado;
        this.uefa_temporada=uefa_temporada;
        this.uefa_visitante=uefa_visitante;
    }
    public String getUefa_fecha() {
        return uefa_fecha;
    }

    public void setUefa_fecha(String uefa_fecha) {
        this.uefa_fecha = uefa_fecha;
    }

    public String getUefa_grupo() {
        return uefa_grupo;
    }

    public void setUefa_grupo(String uefa_grupo) {
        this.uefa_grupo = uefa_grupo;
    }

    public String getUefa_local() {
        return uefa_local;
    }

    public void setUefa_local(String uefa_local) {
        this.uefa_local = uefa_local;
    }

    public String getUefa_visitante() {
        return uefa_visitante;
    }

    public void setUefa_visitante(String uefa_visitante) {
        this.uefa_visitante = uefa_visitante;
    }

    public String getUefa_resultado() {
        return uefa_resultado;
    }

    public void setUefa_resultado(String uefa_resultado) {
        this.uefa_resultado = uefa_resultado;
    }

    public String getUefa_temporada() {
        return uefa_temporada;
    }

    public void setUefa_temporada(String uefa_temporada) {
        this.uefa_temporada = uefa_temporada;
    }
}
