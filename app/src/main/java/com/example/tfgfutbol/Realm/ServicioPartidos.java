package com.example.tfgfutbol.Realm;

import com.example.tfgfutbol.Pojo.ClasificacionPojo;
import com.example.tfgfutbol.Pojo.PartidosPojo;

import io.realm.Realm;
import io.realm.RealmResults;

public class ServicioPartidos {
    private Realm realm;

    public ServicioPartidos(){}

    public ServicioPartidos(Realm realm){
        this.realm=realm;
    }

    public Partidos[] ObetenerPartidos(){

        RealmResults<Partidos> results = realm.where(Partidos.class).findAll();
        return results.toArray(new Partidos[results.size()]);
    }

    public Partidos[] PartidosJornada(String temporada, String jornada,String Liga){
        RealmResults<Alineacion> results=realm.where(Alineacion.class)
                .equalTo("partidos_temporada",temporada)
                .equalTo("partidos_jornada",jornada)
                .equalTo("Liga",Liga)
                .findAll();
        return results.toArray(new Partidos[results.size()]);
    }

    public boolean tienedatos(){
        if(realm.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void ActualizaPartidos(PartidosPojo partidos, String Liga){
        String id=partidos.getPartidos_temporada()+partidos.getPartidos_jornada()+
                partidos.getPartidos_equipo_local()+partidos.getPartidos_equipo_visitante();
        realm.beginTransaction();
        Partidos u = realm.createObject(Partidos.class,id);
        u.setLiga(Liga);
        u.setPartidos_equipo_local(partidos.getPartidos_equipo_local());
        u.setPartidos_equipo_visitante(partidos.getPartidos_equipo_visitante());
        u.setPartidos_fecha(partidos.getPartidos_fecha());
        u.setPartidos_foto_local(partidos.getPartidos_foto_local());
        u.setPartidos_foto_visitante(partidos.getPartidos_foto_visitante());
        u.setPartidos_jornada(partidos.getPartidos_jornada());
        u.setPartidos_resultado(partidos.getPartidos_resultado());
        u.setPartidos_temporada(partidos.getPartidos_temporada());
        realm.commitTransaction();

    }

}
