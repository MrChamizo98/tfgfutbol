package com.example.tfgfutbol.Realm;

import com.example.tfgfutbol.Pojo.ClasificacionPojo;
import com.example.tfgfutbol.Pojo.PlantillaPojo;

import io.realm.Realm;
import io.realm.RealmResults;

public class ServicioPlantilla {

    private Realm realm;

    public ServicioPlantilla(){}

    public ServicioPlantilla(Realm realm){
        this.realm=realm;
    }

    public Plantilla[] ObetenerPlantilla(){

        RealmResults<Plantilla> results = realm.where(Plantilla.class).findAll();
        return results.toArray(new Plantilla[results.size()]);
    }

    public Plantilla[] PlantillaTemporadaEquipo(String temporada, String Equipo,String Liga){
        RealmResults<Plantilla> results=realm.where(Plantilla.class)
                .equalTo("plantilla_temporada",temporada)
                .equalTo("plantilla_equipo",Equipo)
                .equalTo("Liga",Liga)
                .findAll();
        return results.toArray(new Plantilla[results.size()]);
    }

    public boolean tienedatos(){
        if(realm.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    public void ActualizaPlantilla(PlantillaPojo plantilla, String Liga){
        String id=plantilla.getPlantilla_temporada()+""+plantilla.getPlantilla_equipo()+""+plantilla.getPlantilla_name();
        realm.beginTransaction();
        Plantilla u = realm.createObject(Plantilla.class,id);
        u.setLiga(Liga);
        u.setPlantilla_altura(plantilla.getPlantilla_altura());
        u.setPlantilla_amarillas(plantilla.getPlantilla_amarillas());
        u.setPlantilla_dorsal(plantilla.getPlantilla_dorsal());
        u.setPlantilla_edad(plantilla.getPlantilla_edad());
        u.setPlantilla_equipo(plantilla.getPlantilla_equipo());
        u.setPlantilla_foto(plantilla.getPlantilla_foto());
        u.setPlantilla_goles(plantilla.getPlantilla_goles());
        u.setPlantilla_name(plantilla.getPlantilla_name());
        u.setPlantilla_pais(plantilla.getPlantilla_pais());
        u.setPlantilla_peso(plantilla.getPlantilla_peso());
        u.setPlantilla_posicion(plantilla.getPlantilla_posicion());
        u.setPlantilla_rojas(plantilla.getPlantilla_rojas());
        u.setPlantilla_temporada(plantilla.getPlantilla_temporada());
        realm.commitTransaction();

    }
}
