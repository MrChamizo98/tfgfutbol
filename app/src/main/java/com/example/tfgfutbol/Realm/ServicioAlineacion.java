package com.example.tfgfutbol.Realm;

import com.example.tfgfutbol.Pojo.AlineacionPojo;

import io.realm.Realm;
import io.realm.RealmResults;

public class ServicioAlineacion {

    private Realm realm;

    public ServicioAlineacion(){

    }

    public ServicioAlineacion(Realm realm){
        this.realm=realm;
    }

    public Alineacion[] ObetenerAlineacion(){

        RealmResults<Alineacion> results = realm.where(Alineacion.class).findAll();
        return results.toArray(new Alineacion[results.size()]);
    }

    public Alineacion[] AlineacionPartido(String temporada, String jornada, String equipo, String Liga){
        RealmResults<Alineacion> results=realm.where(Alineacion.class)
                .equalTo("alineacion_temporada",temporada)
                .equalTo("alineacion_jornada",jornada)
                .equalTo("alineacion_equipo",equipo)
                .equalTo("liga",Liga)
                .findAll();
        return results.toArray(new Alineacion[results.size()]);
    }

    public boolean tienedatos(){
        if(realm.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void ActualizaAlineacion(AlineacionPojo alineacion, String Liga){
        String id=alineacion.getAlineacion_jugador()+""+alineacion.getAlineacion_jornada()
                +""+alineacion.getAlineacion_temporada()+""+alineacion.getAlineacion_equipo();
        realm.beginTransaction();
        Alineacion u = realm.createObject(Alineacion.class,id);
        u.setLiga(Liga);
        u.setAlineacion_amarilla(alineacion.getAlineacion_amarilla());
        u.setAlineacion_asistencia(alineacion.getAlineacion_asistencia());
        u.setAlineacion_cambio(alineacion.getAlineacion_cambio());
        u.setAlineacion_equipo(alineacion.getAlineacion_equipo());
        u.setAlineacion_estado(alineacion.getAlineacion_estado());
        u.setAlineacion_gol(alineacion.getAlineacion_gol());
        u.setAlineacion_jornada(alineacion.getAlineacion_jornada());
        u.setAlineacion_lesion(alineacion.getAlineacion_lesion());
        u.setAlineacion_roja(alineacion.getAlineacion_roja());
        u.setAlineacion_temporada(alineacion.getAlineacion_temporada());
        u.setAlineacion_jugador(alineacion.getAlineacion_jugador());
        realm.commitTransaction();

    }

}
