package com.example.tfgfutbol.Pojo;

public class ArbitroPojo {

    private String arbitros_name;
    private int arbitros_partido;

    public ArbitroPojo(){}

    public ArbitroPojo(String arbitros_name, int arbitros_partido){
        this.arbitros_name=arbitros_name;
        this.arbitros_partido=arbitros_partido;
    }

    public String getArbitros_name() {
        return arbitros_name;
    }

    public void setArbitros_name(String arbitros_name) {
        this.arbitros_name = arbitros_name;
    }

    public int getArbitros_partido() {
        return arbitros_partido;
    }

    public void setArbitros_partido(int arbitros_partido) {
        this.arbitros_partido = arbitros_partido;
    }
}
