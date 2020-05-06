package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tfgfutbol.Realm.Alineacion;
import com.example.tfgfutbol.Pojo.AlineacionPojo;
import com.example.tfgfutbol.Adapter.CustomAdapterAlineacion;
import com.example.tfgfutbol.Adapter.CustomAdapterAlineacionOtro;
import com.example.tfgfutbol.Layout.ImageLoadTask;
import com.example.tfgfutbol.Firebase.OnGetDataListener;
import com.example.tfgfutbol.R;
import com.example.tfgfutbol.Realm.ServicioAlineacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class AlineacionActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ListView titulareslocal;
    ListView suplenteslocal;
    ListView titularesvisitante;
    ListView suplentesvisitante;
    ListView alineacionlistotros;
    ListView listagoleadores;
    ListView listatarjetas;
    ListView listalesiones;
    ListView listaasistencias;
    DatabaseReference mRootReference;
    ArrayList<AlineacionPojo> alineacion_local_titulares;
    ArrayList<AlineacionPojo> alineacion_visitante_titulares;
    ArrayList<AlineacionPojo> alineacion_local_suplentes;
    ArrayList<AlineacionPojo> alineacion_visitante_suplentes;
    ArrayList<AlineacionPojo> alineacion;
    private String NombreBD="";
    private String Liga;
    private String Temporada;
    private String Local;
    private String Visitante;
    private String resultado;
    private String jornada;
    TextView nombre_liga;
    TextView equipo_local;
    TextView equipo_visitante;
    TextView text_resultado;


    ArrayList<Alineacion> alineacion_local;
    ArrayList<Alineacion> alineacion_visitante;
    Alineacion []alineaciones;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alineacion_layout);

        Realm.init(this);

        RealmConfiguration realmConfigurationLiga= new RealmConfiguration.Builder()
                .name("AlineacionLiga")
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationPremier= new RealmConfiguration.Builder()
                .name("AlineacionPremier")
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationBundesliga= new RealmConfiguration.Builder()
                .name("AlineacionBundesliga")
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationSeriea= new RealmConfiguration.Builder()
                .name("AlineacionSeriea")
                .schemaVersion(5)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationLiga19= new RealmConfiguration.Builder()
                .name("AlineacionLiga19")
                .schemaVersion(6)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationPremier19= new RealmConfiguration.Builder()
                .name("AlineacionPremier19")
                .schemaVersion(7)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationBundesliga19= new RealmConfiguration.Builder()
                .name("AlineacionBundesliga19")
                .schemaVersion(8)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationSeriea19= new RealmConfiguration.Builder()
                .name("AlineacionSeriea19")
                .schemaVersion(9)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationLiga18= new RealmConfiguration.Builder()
                .name("AlineacionLiga18")
                .schemaVersion(10)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationPremier18= new RealmConfiguration.Builder()
                .name("AlineacionPremier18")
                .schemaVersion(11)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationBundesliga18= new RealmConfiguration.Builder()
                .name("AlineacionBundesliga18")
                .schemaVersion(12)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationSeriea18= new RealmConfiguration.Builder()
                .name("AlineacionSeriea18")
                .schemaVersion(13)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationLiga17= new RealmConfiguration.Builder()
                .name("AlineacionLiga17")
                .schemaVersion(14)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationPremier17= new RealmConfiguration.Builder()
                .name("AlineacionPremier17")
                .schemaVersion(15)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationBundesliga17= new RealmConfiguration.Builder()
                .name("AlineacionBundesliga17")
                .schemaVersion(16)
                .deleteRealmIfMigrationNeeded()
                .build();

        RealmConfiguration realmConfigurationSeriea17= new RealmConfiguration.Builder()
                .name("AlineacionSeriea17")
                .schemaVersion(17)
                .deleteRealmIfMigrationNeeded()
                .build();

        //Realm.deleteRealm(realmConfiguration);
        ServicioAlineacion servicioAlineacion = null;

        mRootReference= FirebaseDatabase.getInstance("https://tfg-futbol-alineacion.firebaseio.com/").getReference();
        //mRootReference.keepSynced(true);

        alineacion_local_titulares=new ArrayList<AlineacionPojo>();
        alineacion_visitante_suplentes=new ArrayList<AlineacionPojo>();
        alineacion_local_suplentes=new ArrayList<AlineacionPojo>();
        alineacion_visitante_titulares=new ArrayList<AlineacionPojo>();
        //alineacion= new ArrayList<AlineacionPojo>();
        titulareslocal = (ListView) findViewById(R.id.AlineacionLocalTitulares);
        suplenteslocal = (ListView) findViewById(R.id.AlineacionLocalSuplentes);
        titularesvisitante = (ListView) findViewById(R.id.AlineacionVisitanteTitulares);
        suplentesvisitante = (ListView) findViewById(R.id.AlineacionVisitanteSuplentes);
        listagoleadores=(ListView) findViewById(R.id.lista_goleadores);
        listaasistencias=(ListView) findViewById(R.id.lista_asistencias);
        listalesiones=(ListView) findViewById(R.id.lista_lesiones);
        listatarjetas=(ListView) findViewById(R.id.lista_tarjetas);
        //alineacionlistotros=(ListView)findViewById(R.id.AlineacionListOtros);

        Intent i = getIntent();
        Liga=i.getStringExtra("LIGA");
        Temporada=i.getStringExtra("TEMPORADA");
        Local=i.getStringExtra("EQUIPO LOCAL");
        Visitante=i.getStringExtra("EQUIPO VISITANTE");
        String Escudo_local=i.getStringExtra("FOTO LOCAL");
        String Escudo_visitante=i.getStringExtra("FOTO VISITANTE");
        resultado=i.getStringExtra("RESULTADO");
        jornada=i.getStringExtra("JORNADA");
        //alineacion=(ArrayList<AlineacionPojo>)i.getSerializableExtra("ALINEACION");

        //FALTA PASAR EQUIPOS RESULTADO Y ESCUDOS

        nombre_liga=(TextView)findViewById(R.id.nombre_liga_plantilla);
        nombre_liga.setText(Liga);
        equipo_local=(TextView)findViewById(R.id.equipo_local_alineacion);
        equipo_local.setText(Local);
        equipo_visitante=(TextView)findViewById(R.id.equipo_visitante_alineacion);
        equipo_visitante.setText(Visitante);
        text_resultado=(TextView)findViewById(R.id.resultado_alineacion);
        text_resultado.setText(resultado);
        ImageView foto_local=(ImageView) findViewById(R.id.foto_equipo_local);
        ImageView foto_visitante=(ImageView)findViewById(R.id.foto_equipo_visitante);
        new ImageLoadTask(Escudo_local, foto_local).execute();
        new ImageLoadTask(Escudo_visitante, foto_visitante).execute();


        if(Liga.equals("LaLiga Santander")) {
            if(Temporada.equals("Temporada2020")){
                NombreBD="alineacion_laliga/alineacion_laliga_2019_2020";
            }else if (Temporada.equals("Temporada2019")){
                NombreBD="alineacion_laliga/alineacion_laliga_2018_2019";
            }else if (Temporada.equals("Temporada2018")){
                NombreBD="alineacion_laliga/alineacion_laliga_2017_2018";
            }else {
                NombreBD="alineacion_laliga/alineacion_laliga_2016_2017";
            }

        }else if(Liga.equals("Premier League")) {
            if(Temporada.equals("Temporada2020")){
                NombreBD="alineacion_premier/alineacion_premier_2019_2020";
            }else if (Temporada.equals("Temporada2019")){
                NombreBD="alineacion_premier/alineacion_premier_2018_2019";
            }else if (Temporada.equals("Temporada2018")){
                NombreBD="alineacion_premier/alineacion_premier_2017_2018";
            }else {
                NombreBD="alineacion_premier/alineacion_premier_2016_2017";
            }
        }else if(Liga.equals("Bundesliga")) {
            if(Temporada.equals("Temporada2020")){
                NombreBD="alineacion_bundesliga/alineacion_bundesliga_2019_2020";
            }else if (Temporada.equals("Temporada2019")){
                NombreBD="alineacion_bundesliga/alineacion_bundesliga_2018_2019";
            }else if (Temporada.equals("Temporada2018")){
                NombreBD="alineacion_bundesliga/alineacion_bundesliga_2017_2018";
            }else {
                NombreBD="alineacion_bundesliga/alineacion_bundesliga_2016_2017";
            }
        }else{
            if(Temporada.equals("Temporada2020")){
                NombreBD="alineacion_seriea/alienacion_seriea_2019_2020";
            }else if (Temporada.equals("Temporada2019")){
                NombreBD="alineacion_seriea/alineacion_seriea_2018_2019";
            }else if (Temporada.equals("Temporada2018")){
                NombreBD="alineacion_seriea/alineacion_seriea_2017_2018";
            }else {
                NombreBD="alineacion_seriea/alineacion_seriea_2016_2017";
            }
        }

        if(Liga.equals("LaLiga Santander")){
            if(Temporada.equals("Temporada2020")){
                Realm myRealmLiga = Realm.getInstance(realmConfigurationLiga);
                servicioAlineacion= new ServicioAlineacion(myRealmLiga);
            }else if (Temporada.equals("Temporada2019")){
                Realm myRealmLiga19 = Realm.getInstance(realmConfigurationLiga19);
                servicioAlineacion= new ServicioAlineacion(myRealmLiga19);
            }else if (Temporada.equals("Temporada2018")){
                Realm myRealmLiga18 = Realm.getInstance(realmConfigurationLiga18);
                servicioAlineacion= new ServicioAlineacion(myRealmLiga18);
            }else {
                Realm myRealmLiga17 = Realm.getInstance(realmConfigurationLiga17);
                servicioAlineacion= new ServicioAlineacion(myRealmLiga17);
            }
        }else if(Liga.equals("Premier League")){
            if(Temporada.equals("Temporada2020")){
                Realm myRealmPremier = Realm.getInstance(realmConfigurationPremier);
                servicioAlineacion= new ServicioAlineacion(myRealmPremier);
            }else if (Temporada.equals("Temporada2019")){
                Realm myRealmPremier19 = Realm.getInstance(realmConfigurationPremier19);
                servicioAlineacion= new ServicioAlineacion(myRealmPremier19);
            }else if (Temporada.equals("Temporada2018")){
                Realm myRealmPremier18 = Realm.getInstance(realmConfigurationPremier18);
                servicioAlineacion= new ServicioAlineacion(myRealmPremier18);
            }else {
                Realm myRealmPremier17 = Realm.getInstance(realmConfigurationPremier17);
                servicioAlineacion= new ServicioAlineacion(myRealmPremier17);
            }
        }else if(Liga.equals("Bundesliga")){
            if(Temporada.equals("Temporada2020")){
                Realm myRealmBundesliga = Realm.getInstance(realmConfigurationBundesliga);
                servicioAlineacion= new ServicioAlineacion(myRealmBundesliga);
            }else if (Temporada.equals("Temporada2019")){
                Realm myRealmBundesliga19 = Realm.getInstance(realmConfigurationBundesliga19);
                servicioAlineacion= new ServicioAlineacion(myRealmBundesliga19);
            }else if (Temporada.equals("Temporada2018")){
                Realm myRealmBundesliga18 = Realm.getInstance(realmConfigurationBundesliga18);
                servicioAlineacion= new ServicioAlineacion(myRealmBundesliga18);
            }else {
                Realm myRealmBundesliga17 = Realm.getInstance(realmConfigurationBundesliga17);
                servicioAlineacion= new ServicioAlineacion(myRealmBundesliga17);
            }

        }else{
            if(Temporada.equals("Temporada2020")){
                Realm myRealmSerieA = Realm.getInstance(realmConfigurationSeriea);
                servicioAlineacion= new ServicioAlineacion(myRealmSerieA);
            }else if (Temporada.equals("Temporada2019")){
                Realm myRealmSerieA19 = Realm.getInstance(realmConfigurationSeriea19);
                servicioAlineacion= new ServicioAlineacion(myRealmSerieA19);
            }else if (Temporada.equals("Temporada2018")){
                Realm myRealmSerieA18 = Realm.getInstance(realmConfigurationSeriea18);
                servicioAlineacion= new ServicioAlineacion(myRealmSerieA18);
            }else {
                Realm myRealmSerieA17 = Realm.getInstance(realmConfigurationSeriea17);
                servicioAlineacion= new ServicioAlineacion(myRealmSerieA17);
            }
        }

        alineacion_local=new ArrayList<Alineacion>();
        alineacion_visitante=new ArrayList<Alineacion>();

        synchronized (this){
            alineaciones=null;
            alineaciones = servicioAlineacion.ObetenerAlineacion();

            for (int l = 0; l < alineaciones.length; l++) {
                if (alineaciones[l].getAlineacion_equipo().equals(Local) && alineaciones[l].getAlineacion_jornada().equals(jornada)
                        && alineaciones[l].getAlineacion_temporada().equals(Temporada)
                        && alineaciones[l].getLiga().equals(Liga)) {
                    Log.e("ENTRA LOCAL", alineaciones[l].getAlineacion_jugador());
                    alineacion_local.add(alineaciones[l]);
                    //alineacion_local.notify();
                }
                if (alineaciones[l].getAlineacion_equipo().equals(Visitante) && alineaciones[l].getAlineacion_jornada().equals(jornada)
                        && alineaciones[l].getAlineacion_temporada().equals(Temporada)
                        && alineaciones[l].getLiga().equals(Liga)) {
                    Log.e("ENTRA VISITANTE", alineaciones[l].getAlineacion_jugador());
                    alineacion_visitante.add(alineaciones[l]);
                    //alineacion_visitante.notify();
                }
            }
            for (int j = 0; j < alineacion_local.size(); j++) {
                if (alineacion_local.get(j).getAlineacion_estado().equals("Titular")) {

                    AlineacionPojo jugador = new AlineacionPojo(alineacion_local.get(j).getAlineacion_amarilla(),
                            alineacion_local.get(j).getAlineacion_asistencia(), alineacion_local.get(j).getAlineacion_cambio(),
                            alineacion_local.get(j).getAlineacion_equipo(), alineacion_local.get(j).getAlineacion_estado(),
                            alineacion_local.get(j).getAlineacion_gol(), alineacion_local.get(j).getAlineacion_jornada(),
                            alineacion_local.get(j).getAlineacion_jugador(), alineacion_local.get(j).getAlineacion_lesion(),
                            alineacion_local.get(j).getAlineacion_roja(), alineacion_local.get(j).getAlineacion_temporada());
                    alineacion_local_titulares.add(jugador);
                } else {
                    AlineacionPojo jugador = new AlineacionPojo(alineacion_local.get(j).getAlineacion_amarilla(),
                            alineacion_local.get(j).getAlineacion_asistencia(), alineacion_local.get(j).getAlineacion_cambio(),
                            alineacion_local.get(j).getAlineacion_equipo(), alineacion_local.get(j).getAlineacion_estado(),
                            alineacion_local.get(j).getAlineacion_gol(), alineacion_local.get(j).getAlineacion_jornada(),
                            alineacion_local.get(j).getAlineacion_jugador(), alineacion_local.get(j).getAlineacion_lesion(),
                            alineacion_local.get(j).getAlineacion_roja(), alineacion_local.get(j).getAlineacion_temporada());
                    alineacion_local_suplentes.add(jugador);
                }
            }
            for (int x = 0; x < alineacion_visitante.size(); x++) {
                if (alineacion_visitante.get(x).getAlineacion_estado().equals("Titular")) {
                    AlineacionPojo jugador = new AlineacionPojo(alineacion_visitante.get(x).getAlineacion_amarilla(),
                            alineacion_visitante.get(x).getAlineacion_asistencia(), alineacion_visitante.get(x).getAlineacion_cambio(),
                            alineacion_visitante.get(x).getAlineacion_equipo(), alineacion_visitante.get(x).getAlineacion_estado(),
                            alineacion_visitante.get(x).getAlineacion_gol(), alineacion_visitante.get(x).getAlineacion_jornada(),
                            alineacion_visitante.get(x).getAlineacion_jugador(), alineacion_visitante.get(x).getAlineacion_lesion(),
                            alineacion_visitante.get(x).getAlineacion_roja(), alineacion_visitante.get(x).getAlineacion_temporada());
                    alineacion_visitante_titulares.add(jugador);
                } else {
                    AlineacionPojo jugador = new AlineacionPojo(alineacion_visitante.get(x).getAlineacion_amarilla(),
                            alineacion_visitante.get(x).getAlineacion_asistencia(), alineacion_visitante.get(x).getAlineacion_cambio(),
                            alineacion_visitante.get(x).getAlineacion_equipo(), alineacion_visitante.get(x).getAlineacion_estado(),
                            alineacion_visitante.get(x).getAlineacion_gol(), alineacion_visitante.get(x).getAlineacion_jornada(),
                            alineacion_visitante.get(x).getAlineacion_jugador(), alineacion_visitante.get(x).getAlineacion_lesion(),
                            alineacion_visitante.get(x).getAlineacion_roja(), alineacion_visitante.get(x).getAlineacion_temporada());
                    alineacion_visitante_suplentes.add(jugador);
                }
            }

            ArrayList<Alineacion> lista_goleadores=new ArrayList<Alineacion>();
            ArrayList<Alineacion> lista_asistencias=new ArrayList<Alineacion>();
            ArrayList<Alineacion> lista_tarjetas=new ArrayList<Alineacion>();
            ArrayList<Alineacion> lista_lesiones=new ArrayList<Alineacion>();

            for(int p=0;p<alineacion_local.size();p++){
                if(alineacion_local.get(p).getAlineacion_amarilla().equals("1")){
                    lista_tarjetas.add(alineacion_local.get(p));
                }
                if(alineacion_local.get(p).getAlineacion_roja().equals("1")){
                    lista_tarjetas.add(alineacion_local.get(p));
                }
                if(alineacion_local.get(p).getAlineacion_lesion().equals("1")){
                    lista_lesiones.add(alineacion_local.get(p));
                }
                if(!alineacion_local.get(p).getAlineacion_asistencia().equals("0")){
                    lista_asistencias.add(alineacion_local.get(p));
                }
                if(!alineacion_local.get(p).getAlineacion_gol().equals("0")){
                    lista_goleadores.add(alineacion_local.get(p));
                }
            }

            for(int p=0;p<alineacion_visitante.size();p++){
                if(alineacion_visitante.get(p).getAlineacion_amarilla().equals("1")){
                    lista_tarjetas.add(alineacion_visitante.get(p));
                }
                if(alineacion_visitante.get(p).getAlineacion_roja().equals("1")){
                    lista_tarjetas.add(alineacion_visitante.get(p));
                }
                if(alineacion_visitante.get(p).getAlineacion_lesion().equals("1")){
                    lista_lesiones.add(alineacion_visitante.get(p));
                }
                if(!alineacion_visitante.get(p).getAlineacion_asistencia().equals("0")){
                    lista_asistencias.add(alineacion_visitante.get(p));
                }
                if(!alineacion_visitante.get(p).getAlineacion_gol().equals("0")){
                    lista_goleadores.add(alineacion_visitante.get(p));
                }
            }
            CustomAdapterAlineacion customAdaptertitularlocal = new CustomAdapterAlineacion(getApplicationContext(), alineacion_local_titulares);
            titulareslocal.setAdapter(customAdaptertitularlocal);
            CustomAdapterAlineacion customAdaptertitularvisitante = new CustomAdapterAlineacion(getApplicationContext(), alineacion_visitante_titulares);
            titularesvisitante.setAdapter(customAdaptertitularvisitante);
            CustomAdapterAlineacion customAdaptersuplentelocal = new CustomAdapterAlineacion(getApplicationContext(), alineacion_local_suplentes);
            suplenteslocal.setAdapter(customAdaptersuplentelocal);
            CustomAdapterAlineacion customAdaptersuplentevisitante = new CustomAdapterAlineacion(getApplicationContext(), alineacion_visitante_suplentes);
            suplentesvisitante.setAdapter(customAdaptersuplentevisitante);

            CustomAdapterAlineacionOtro customAdapterGoles= new CustomAdapterAlineacionOtro("goles", getApplicationContext(), lista_goleadores);
            listagoleadores.setAdapter(customAdapterGoles);
            CustomAdapterAlineacionOtro customAdapterAsistencias=new CustomAdapterAlineacionOtro("asistencias", getApplicationContext(), lista_asistencias);
            listaasistencias.setAdapter(customAdapterAsistencias);
            CustomAdapterAlineacionOtro customAdapterLesiones= new CustomAdapterAlineacionOtro("lesiones", getApplicationContext(), lista_lesiones);
            listalesiones.setAdapter(customAdapterLesiones);
            CustomAdapterAlineacionOtro customAdapterTarjetas=new CustomAdapterAlineacionOtro("tarjetas", getApplicationContext(), lista_tarjetas);
            listatarjetas.setAdapter(customAdapterTarjetas);



            /*if (alineaciones.length == 0 || alineacion_local.size() == 0 || alineacion_visitante.size() == 0) {
                    BDCall(servicioAlineacion, new OnGetDataListener() {
                        @Override
                        public void onSuccess(DataSnapshot dataSnapshot) {
                            CustomAdapterAlineacion customAdaptertitularlocal = new CustomAdapterAlineacion(getApplicationContext(), alineacion_local_titulares);
                            titulareslocal.setAdapter(customAdaptertitularlocal);
                            CustomAdapterAlineacion customAdaptertitularvisitante = new CustomAdapterAlineacion(getApplicationContext(), alineacion_visitante_titulares);
                            titularesvisitante.setAdapter(customAdaptertitularvisitante);
                            CustomAdapterAlineacion customAdaptersuplentelocal = new CustomAdapterAlineacion(getApplicationContext(), alineacion_local_suplentes);
                            suplenteslocal.setAdapter(customAdaptersuplentelocal);
                            CustomAdapterAlineacion customAdaptersuplentevisitante = new CustomAdapterAlineacion(getApplicationContext(), alineacion_visitante_suplentes);
                            suplentesvisitante.setAdapter(customAdaptersuplentevisitante);

                            toolbar = findViewById(R.id.toolbar);
                            setSupportActionBar(toolbar);
                        }

                        @Override
                        public void onStart() {
                            alineacion = new ArrayList<AlineacionPojo>();
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                } else {
                    for (int j = 0; j < alineacion_local.size(); j++) {
                        if (alineacion_local.get(j).getAlineacion_estado().equals("Titular")) {

                            AlineacionPojo jugador = new AlineacionPojo(alineacion_local.get(j).getAlineacion_amarilla(),
                                    alineacion_local.get(j).getAlineacion_asistencia(), alineacion_local.get(j).getAlineacion_cambio(),
                                    alineacion_local.get(j).getAlineacion_equipo(), alineacion_local.get(j).getAlineacion_estado(),
                                    alineacion_local.get(j).getAlineacion_gol(), alineacion_local.get(j).getAlineacion_jornada(),
                                    alineacion_local.get(j).getAlineacion_jugador(), alineacion_local.get(j).getAlineacion_lesion(),
                                    alineacion_local.get(j).getAlineacion_roja(), alineacion_local.get(j).getAlineacion_temporada());
                            alineacion_local_titulares.add(jugador);
                        } else {
                            AlineacionPojo jugador = new AlineacionPojo(alineacion_local.get(j).getAlineacion_amarilla(),
                                    alineacion_local.get(j).getAlineacion_asistencia(), alineacion_local.get(j).getAlineacion_cambio(),
                                    alineacion_local.get(j).getAlineacion_equipo(), alineacion_local.get(j).getAlineacion_estado(),
                                    alineacion_local.get(j).getAlineacion_gol(), alineacion_local.get(j).getAlineacion_jornada(),
                                    alineacion_local.get(j).getAlineacion_jugador(), alineacion_local.get(j).getAlineacion_lesion(),
                                    alineacion_local.get(j).getAlineacion_roja(), alineacion_local.get(j).getAlineacion_temporada());
                            alineacion_local_suplentes.add(jugador);
                        }
                    }
                    for (int x = 0; x < alineacion_visitante.size(); x++) {
                        if (alineacion_visitante.get(x).getAlineacion_estado().equals("Titular")) {
                            AlineacionPojo jugador = new AlineacionPojo(alineacion_visitante.get(x).getAlineacion_amarilla(),
                                    alineacion_visitante.get(x).getAlineacion_asistencia(), alineacion_visitante.get(x).getAlineacion_cambio(),
                                    alineacion_visitante.get(x).getAlineacion_equipo(), alineacion_visitante.get(x).getAlineacion_estado(),
                                    alineacion_visitante.get(x).getAlineacion_gol(), alineacion_visitante.get(x).getAlineacion_jornada(),
                                    alineacion_visitante.get(x).getAlineacion_jugador(), alineacion_visitante.get(x).getAlineacion_lesion(),
                                    alineacion_visitante.get(x).getAlineacion_roja(), alineacion_visitante.get(x).getAlineacion_temporada());
                            alineacion_visitante_titulares.add(jugador);
                        } else {
                            AlineacionPojo jugador = new AlineacionPojo(alineacion_visitante.get(x).getAlineacion_amarilla(),
                                    alineacion_visitante.get(x).getAlineacion_asistencia(), alineacion_visitante.get(x).getAlineacion_cambio(),
                                    alineacion_visitante.get(x).getAlineacion_equipo(), alineacion_visitante.get(x).getAlineacion_estado(),
                                    alineacion_visitante.get(x).getAlineacion_gol(), alineacion_visitante.get(x).getAlineacion_jornada(),
                                    alineacion_visitante.get(x).getAlineacion_jugador(), alineacion_visitante.get(x).getAlineacion_lesion(),
                                    alineacion_visitante.get(x).getAlineacion_roja(), alineacion_visitante.get(x).getAlineacion_temporada());
                            alineacion_visitante_suplentes.add(jugador);
                        }
                    }
                    CustomAdapterAlineacion customAdaptertitularlocal = new CustomAdapterAlineacion(getApplicationContext(), alineacion_local_titulares);
                    titulareslocal.setAdapter(customAdaptertitularlocal);
                    CustomAdapterAlineacion customAdaptertitularvisitante = new CustomAdapterAlineacion(getApplicationContext(), alineacion_visitante_titulares);
                    titularesvisitante.setAdapter(customAdaptertitularvisitante);
                    CustomAdapterAlineacion customAdaptersuplentelocal = new CustomAdapterAlineacion(getApplicationContext(), alineacion_local_suplentes);
                    suplenteslocal.setAdapter(customAdaptersuplentelocal);
                    CustomAdapterAlineacion customAdaptersuplentevisitante = new CustomAdapterAlineacion(getApplicationContext(), alineacion_visitante_suplentes);
                    suplentesvisitante.setAdapter(customAdaptersuplentevisitante);

                }*/
        }

        toolbar=findViewById(R.id.toolbar);
        toolbar.bringToFront();
        setSupportActionBar(toolbar);

        SwipeRefreshLayout swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipelayoutalineacion);
        swipeRefresh.bringChildToFront(toolbar);

        //CustomAdapterAlineacionOtro customAdapter= new CustomAdapterAlineacionOtro(getApplicationContext(),alineacion);
        //alineacionlistotros.setAdapter(customAdapter);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayoutalineacion);
        swipeRefreshLayout.setColorSchemeResources(R.color.refresh,R.color.refresh1,R.color.refresh2);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                (new Handler()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        finish();
                        overridePendingTransition(0, 0);
                        startActivity(getIntent());
                        overridePendingTransition(0, 0);
                    }
                },3000);
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menusuperior,menu);
        getMenuInflater().inflate(R.menu.menuinferior,menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem){

        Intent i;

        switch (menuItem.getItemId()){
            case R.id.boton_volver_inicio:
                i=new Intent(this, LigaActivity.class);
                i.putExtra("LIGA",Liga);
                i.putExtra("TEMPORADA",Temporada);
                startActivity(i);
                break;
            case R.id.boton_dashboard:
                i = new Intent(this, DashboardActivity.class);
                Log.e("Has seleccionado","dashboard");
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA",Temporada);
                startActivity(i);
                break;
            case R.id.boton_clasificacion:
                i=new Intent(this, ClasificacionActivity.class);
                i.putExtra("LIGA",Liga);
                i.putExtra("TEMPORADA",Temporada);
                startActivity(i);
                break;
            case R.id.boton_equipos:
                i=new Intent(this, SelectEquiposActivity.class);
                i.putExtra("LIGA",Liga);
                i.putExtra("TEMPORADA",Temporada);
                startActivity(i);
                break;
            case R.id.boton_partidos:
                i=new Intent(this, PartidosActivity.class);
                i.putExtra("LIGA",Liga);
                i.putExtra("TEMPORADA",Temporada);
                startActivity(i);
                break;
            case R.id.boton_arbitros:
                i=new Intent(this, ArbitrosActivity.class);
                i.putExtra("LIGA",Liga);
                i.putExtra("TEMPORADA",Temporada);
                startActivity(i);
                break;
            case R.id.menu_usuario:
                i=new Intent(this, UsuarioActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }


    public void BDCall(final ServicioAlineacion servicioAlineacion, final OnGetDataListener listener){
        mRootReference.child(NombreBD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mRootReference.child(NombreBD).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            AlineacionPojo jugador = snapshot.getValue(AlineacionPojo.class);
                            try {
                                servicioAlineacion.ActualizaAlineacion(jugador,Liga);
                            }catch(Exception e){
                                Log.e("YA REGISTRADO",jugador.getAlineacion_jugador());
                            }
                            if(jornada.equals(jugador.getAlineacion_jornada())){
                                if(Local.equals(jugador.getAlineacion_equipo())){
                                    //alineacion.add(jugador);
                                    if("Titular".equals(jugador.getAlineacion_estado())) {
                                        Log.e("NOMBRE JUGADOR LOCAL", jugador.getAlineacion_jugador());
                                        alineacion_local_titulares.add(jugador);

                                    }else {
                                        Log.e("NOMBRE JUGADOR LOCAL", jugador.getAlineacion_jugador());
                                        alineacion_local_suplentes.add(jugador);
                                    }
                                }else if(Visitante.equals(jugador.getAlineacion_equipo())){
                                    //alineacion.add(jugador);
                                    if("Titular".equals(jugador.getAlineacion_estado())) {
                                        Log.e("NOMBRE JUGADOR VISIT", jugador.getAlineacion_jugador());
                                        alineacion_visitante_titulares.add(jugador);
                                    }else {
                                        Log.e("NOMBRE JUGADOR VISIT", jugador.getAlineacion_jugador());
                                        alineacion_visitante_suplentes.add(jugador);
                                    }
                                }else{

                                }
                            }
                            listener.onSuccess(dataSnapshot);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {listener.onFailure();}
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    /*public synchronized void putData(ArrayList<Alineacion>alineacion_local,ArrayList<Alineacion>alineacion_visitante){
        synchronized (alineaciones) {
            for (int l = 0; l < alineaciones.length; l++) {
                synchronized (alineacion_local) {
                    if (alineaciones[l].getAlineacion_equipo().equals(Local) && alineaciones[l].getAlineacion_jornada().equals(jornada)
                            && alineaciones[l].getAlineacion_temporada().equals(Temporada)
                            && alineaciones[l].getLiga().equals(Liga)) {
                        Log.e("ENTRA LOCAL", alineaciones[l].getAlineacion_jugador());
                        alineacion_local.add(alineaciones[l]);
                        alineacion_local.notify();
                    }
                }
                synchronized (alineacion_visitante) {
                    if (alineaciones[l].getAlineacion_equipo().equals(Visitante) && alineaciones[l].getAlineacion_jornada().equals(jornada)
                            && alineaciones[l].getAlineacion_temporada().equals(Temporada)
                            && alineaciones[l].getLiga().equals(Liga)) {
                        Log.e("ENTRA VISITANTE", alineaciones[l].getAlineacion_jugador());
                        alineacion_visitante.add(alineaciones[l]);
                        alineacion_visitante.notify();
                    }
                }
                if (l == alineaciones.length - 1) {

                    if(alineacion_local.size()==0){
                        throw  new Error();
                    }
                        //alineacion_local.notify();

                    if(alineacion_visitante.size()==0){
                        throw new Error();
                    }
                }
            }
        }
    }*/

    /*
    public synchronized void getData() throws InterruptedException {

            if (alineacion_local.size() == 0 || alineacion_visitante.size() == 0) {
                wait();
            }
            for (int j = 0; j < alineacion_local.size(); j++) {
                if (alineacion_local.get(j).getAlineacion_estado().equals("Titular")) {

                    AlineacionPojo jugador = new AlineacionPojo(alineacion_local.get(j).getAlineacion_amarilla(),
                            alineacion_local.get(j).getAlineacion_asistencia(), alineacion_local.get(j).getAlineacion_cambio(),
                            alineacion_local.get(j).getAlineacion_equipo(), alineacion_local.get(j).getAlineacion_estado(),
                            alineacion_local.get(j).getAlineacion_gol(), alineacion_local.get(j).getAlineacion_jornada(),
                            alineacion_local.get(j).getAlineacion_jugador(), alineacion_local.get(j).getAlineacion_lesion(),
                            alineacion_local.get(j).getAlineacion_roja(), alineacion_local.get(j).getAlineacion_temporada());
                    alineacion_local_titulares.add(jugador);
                } else {
                    AlineacionPojo jugador = new AlineacionPojo(alineacion_local.get(j).getAlineacion_amarilla(),
                            alineacion_local.get(j).getAlineacion_asistencia(), alineacion_local.get(j).getAlineacion_cambio(),
                            alineacion_local.get(j).getAlineacion_equipo(), alineacion_local.get(j).getAlineacion_estado(),
                            alineacion_local.get(j).getAlineacion_gol(), alineacion_local.get(j).getAlineacion_jornada(),
                            alineacion_local.get(j).getAlineacion_jugador(), alineacion_local.get(j).getAlineacion_lesion(),
                            alineacion_local.get(j).getAlineacion_roja(), alineacion_local.get(j).getAlineacion_temporada());
                    alineacion_local_suplentes.add(jugador);
                }
            }
            for (int x = 0; x < alineacion_visitante.size(); x++) {
                if (alineacion_visitante.get(x).getAlineacion_estado().equals("Titular")) {
                    AlineacionPojo jugador = new AlineacionPojo(alineacion_visitante.get(x).getAlineacion_amarilla(),
                            alineacion_visitante.get(x).getAlineacion_asistencia(), alineacion_visitante.get(x).getAlineacion_cambio(),
                            alineacion_visitante.get(x).getAlineacion_equipo(), alineacion_visitante.get(x).getAlineacion_estado(),
                            alineacion_visitante.get(x).getAlineacion_gol(), alineacion_visitante.get(x).getAlineacion_jornada(),
                            alineacion_visitante.get(x).getAlineacion_jugador(), alineacion_visitante.get(x).getAlineacion_lesion(),
                            alineacion_visitante.get(x).getAlineacion_roja(), alineacion_visitante.get(x).getAlineacion_temporada());
                    alineacion_visitante_titulares.add(jugador);
                } else {
                    AlineacionPojo jugador = new AlineacionPojo(alineacion_visitante.get(x).getAlineacion_amarilla(),
                            alineacion_visitante.get(x).getAlineacion_asistencia(), alineacion_visitante.get(x).getAlineacion_cambio(),
                            alineacion_visitante.get(x).getAlineacion_equipo(), alineacion_visitante.get(x).getAlineacion_estado(),
                            alineacion_visitante.get(x).getAlineacion_gol(), alineacion_visitante.get(x).getAlineacion_jornada(),
                            alineacion_visitante.get(x).getAlineacion_jugador(), alineacion_visitante.get(x).getAlineacion_lesion(),
                            alineacion_visitante.get(x).getAlineacion_roja(), alineacion_visitante.get(x).getAlineacion_temporada());
                    alineacion_visitante_suplentes.add(jugador);
                }
            }
            CustomAdapterAlineacion customAdaptertitularlocal = new CustomAdapterAlineacion(getApplicationContext(), alineacion_local_titulares);
            titulareslocal.setAdapter(customAdaptertitularlocal);
            CustomAdapterAlineacion customAdaptertitularvisitante = new CustomAdapterAlineacion(getApplicationContext(), alineacion_visitante_titulares);
            titularesvisitante.setAdapter(customAdaptertitularvisitante);
            CustomAdapterAlineacion customAdaptersuplentelocal = new CustomAdapterAlineacion(getApplicationContext(), alineacion_local_suplentes);
            suplenteslocal.setAdapter(customAdaptersuplentelocal);
            CustomAdapterAlineacion customAdaptersuplentevisitante = new CustomAdapterAlineacion(getApplicationContext(), alineacion_visitante_suplentes);
            suplentesvisitante.setAdapter(customAdaptersuplentevisitante);
        }*/
}
