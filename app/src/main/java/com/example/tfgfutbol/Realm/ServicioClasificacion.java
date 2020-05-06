package com.example.tfgfutbol.Realm;

import com.example.tfgfutbol.Pojo.ClasificacionPojo;

import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;

public class ServicioClasificacion {

    private Realm realm;

    public ServicioClasificacion(){}

    public ServicioClasificacion(Realm realm){
        this.realm=realm;
    }

    public Clasificacion[] ObetenerClasificacion(){

        RealmResults<Clasificacion> results = realm.where(Clasificacion.class).findAll();
        return results.toArray(new Clasificacion[results.size()]);
    }

    public Clasificacion[] ClasificacionTemporadaJornada(String temporada, String jornada,String Liga){
        RealmResults<Alineacion> results=realm.where(Alineacion.class)
                .equalTo("clasificacion_temporada",temporada)
                .equalTo("clasificacion_jornada",jornada)
                .equalTo("Liga",Liga)
                .findAll();
        return results.toArray(new Clasificacion[results.size()]);
    }

    public boolean tienedatos(){
        if(realm.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void ActualizaClasificacion(ClasificacionPojo clasificacion, String Liga){
        String id=clasificacion.getClasificacion_temporada()+""+clasificacion.getClasificacion_jornada()+
                ""+clasificacion.getClasificacion_equipo();
        realm.beginTransaction();
        Clasificacion u = realm.createObject(Clasificacion.class,id);
        u.setLiga(Liga);
        u.setClasificacion_empatados(clasificacion.getClasificacion_empatados());
        u.setClasificacion_equipo(clasificacion.getClasificacion_equipo());
        u.setClasificacion_ganados(clasificacion.getClasificacion_ganados());
        u.setClasificacion_goles_contra(clasificacion.getClasificacion_goles_contra());
        u.setClasificacion_goles_favor(clasificacion.getClasificacion_goles_favor());
        u.setClasificacion_jornada(clasificacion.getClasificacion_jornada());
        u.setClasificacion_partidos_jugados(clasificacion.getClasificacion_partidos_jugados());
        u.setClasificacion_perdidos(clasificacion.getClasificacion_perdidos());
        u.setClasificacion_posicion(clasificacion.getClasificacion_posicion());
        u.setClasificacion_puntos(clasificacion.getClasificacion_puntos());
        u.setClasificacion_temporada(clasificacion.getClasificacion_temporada());
        realm.commitTransaction();

    }
}
