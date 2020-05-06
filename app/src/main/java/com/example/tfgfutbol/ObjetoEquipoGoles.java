package com.example.tfgfutbol;

public class ObjetoEquipoGoles {

    private String equipo;
    private int goles;

    public ObjetoEquipoGoles(){
        this.goles=0;
        this.equipo="";
    }

    public ObjetoEquipoGoles(String equipo, int goles){
        this.equipo=equipo;
        this.goles=goles;
    }

    public ObjetoEquipoGoles(String equipo){
        this.equipo=equipo;
        this.goles=0;
    }

    public String getEquipo() {
        return equipo;
    }

    public void setEquipo(String equipo) {
        this.equipo = equipo;
    }

    public int getGoles() {
        return goles;
    }

    public void setGoles(int goles) {
        this.goles = goles;
    }

    public void sumaGoles(int goles){
        this.goles=this.goles+goles;
    }
}
