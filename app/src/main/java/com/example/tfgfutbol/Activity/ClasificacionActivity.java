package com.example.tfgfutbol.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.example.tfgfutbol.GlobalInfo;
import com.example.tfgfutbol.Pojo.AlineacionPojo;
import com.example.tfgfutbol.Pojo.ClasificacionPojo;
import com.example.tfgfutbol.R;
import com.example.tfgfutbol.Realm.Clasificacion;
import com.example.tfgfutbol.Realm.ServicioAlineacion;
import com.example.tfgfutbol.Realm.ServicioClasificacion;
import com.example.tfgfutbol.Tabla.TablaClasificacion;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;


public class ClasificacionActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar inferior;
    private String Liga;
    private String Temporada;
    private String NombreBD = "";
    private String NombreBD2 = "";
    TextView nombre_liga;
    DatabaseReference mRootReference;
    //DatabaseReference mRootReference1;
    ArrayList<String> elementos;
    ArrayList<AlineacionPojo> alineacion;
    ProgressBar barra;
    private String Jornada;
    TableLayout tabla_clasificacion;
    ArrayList<ClasificacionPojo> clasificacion_jornada;
    private ProgressDialog progressDialog;
    Spinner spinner;
    private TablaClasificacion tabla;
    private Clasificacion[] clasificaciones;
    private int id_liga;

    public ClasificacionActivity() {
    }

        @Override
        protected void onCreate (Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.clasificacion_layout);

            //barra= (ProgressBar) findViewById(R.id.progressBar);

            mRootReference = FirebaseDatabase.getInstance("https://tfg-futbol-clasificacion.firebaseio.com/").getReference();
            //mRootReference.keepSynced(true);
            //mRootReference1 = FirebaseDatabase.getInstance("https://tfg-futbol-alineacion.firebaseio.com/").getReference();

            Intent i = getIntent();
            Liga = i.getStringExtra("LIGA");
            Temporada = i.getStringExtra("TEMPORADA");
            Log.e("LIGA EN CLASIFICACION", Liga);


            alineacion = new ArrayList<AlineacionPojo>();
            clasificacion_jornada = new ArrayList<ClasificacionPojo>();

            nombre_liga = (TextView) findViewById(R.id.nombre_liga_clasificacion);
            nombre_liga.setText(Liga);
            tabla_clasificacion = (TableLayout) findViewById(R.id.tabla);

            spinner = (Spinner) findViewById(R.id.jornada_spinner);
            ArrayAdapter<CharSequence> adapter;
            if (Liga.equals("Bundesliga")) {
                adapter = ArrayAdapter.createFromResource(this, R.array.lista_clasificacion_bundesliga, android.R.layout.simple_spinner_item);
                Jornada = "Jornada 34";
            } else {
                adapter = ArrayAdapter.createFromResource(this, R.array.lista_clasificacion, android.R.layout.simple_spinner_item);
                Jornada = "Jornada 38";
            }
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(this);

            if (Liga.equals("LaLiga Santander")) {
                id_liga=1;
                NombreBD2 = "clasificacion_laliga";
            } else if (Liga.equals("Premier League")) {
                id_liga=2;
                NombreBD2 = "clasificacion_premier";
            } else if (Liga.equals("Bundesliga")) {
                id_liga=3;
                NombreBD2 = "clasificacion_bundesliga";
            } else if (Liga.equals("Serie A")) {
                id_liga=4;
                NombreBD2 = "clasificacion_seriea";
            }

            tabla = new TablaClasificacion(this, (TableLayout) findViewById(R.id.tabla));
            //final TablaClasificacion tabla=new TablaClasificacion(this, (TableLayout)findViewById(R.id.tabla));
            //tabla.agregarCabecera(R.array.cabecera_tabla);

            //BDCall2();

            inferior = findViewById(R.id.inferior);
            setSupportActionBar(inferior);

        }

        public boolean onCreateOptionsMenu (Menu menu){
            getMenuInflater().inflate(R.menu.menusuperior, menu);
            getMenuInflater().inflate(R.menu.menuinferior, menu);
            return true;
        }

        public boolean onOptionsItemSelected (MenuItem menuItem){
            Intent i;
            switch (menuItem.getItemId()) {
                case R.id.boton_volver_inicio:
                    i = new Intent(ClasificacionActivity.this, LigaActivity.class);
                    i.putExtra("LIGA", Liga);
                    startActivity(i);
                    break;
                case R.id.boton_dashboard:
                    i = new Intent(ClasificacionActivity.this, DashboardActivity.class);
                    Log.e("Has seleccionado","dashboard");
                    i.putExtra("LIGA", Liga);
                    i.putExtra("TEMPORADA",Temporada);
                    startActivity(i);
                    break;
                case R.id.boton_clasificacion:
                    i = new Intent(ClasificacionActivity.this, ClasificacionActivity.class);
                    i.putExtra("LIGA", Liga);
                    i.putExtra("TEMPORADA", Temporada);
                    startActivity(i);
                    break;
                case R.id.boton_equipos:
                    i = new Intent(ClasificacionActivity.this, SelectEquiposActivity.class);
                    i.putExtra("LIGA", Liga);
                    i.putExtra("TEMPORADA", Temporada);
                    startActivity(i);
                    break;
                case R.id.boton_partidos:
                    i = new Intent(ClasificacionActivity.this, PartidosActivity.class);
                    i.putExtra("LIGA", Liga);
                    i.putExtra("TEMPORADA", Temporada);
                    i.putExtra("JORNADA", "Jornada 1");
                    //i.putExtra("ALINEACION",alineacion);
                    startActivity(i);
                    break;
                case R.id.boton_arbitros:
                    i = new Intent(ClasificacionActivity.this, ArbitrosActivity.class);
                    i.putExtra("LIGA", Liga);
                    i.putExtra("TEMPORADA", Temporada);
                    startActivity(i);
                    break;
                case R.id.menu_usuario:
                    i = new Intent(this, UsuarioActivity.class);
                    startActivity(i);
                    break;
            }
            return true;
        }


        public void BDCall (final ServicioClasificacion servicioClasificacion, final TablaClasificacion tabla, final String Jornada){

            mRootReference.child(NombreBD2).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        mRootReference.child(NombreBD2).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                ClasificacionPojo equipo = snapshot.getValue(ClasificacionPojo.class);
                                elementos = new ArrayList<String>();
                                //Log.e("TEMPORADA", equipo.getClasificacion_temporada());
                                //Log.e("TEMPORADA 1", Temporada);
                                if (equipo.getClasificacion_temporada().equals(Temporada)) {
                                    clasificacion_jornada.add(equipo);
                                    try {
                                        servicioClasificacion.ActualizaClasificacion(equipo, Liga);
                                    }catch (Exception e){

                                    }
                                }
                                if (equipo.getClasificacion_temporada().equals(Temporada) &&
                                        equipo.getClasificacion_jornada().equals(Jornada) &&
                                        !NombreBD.equals("clasificacion_bundesliga")) {
                                    elementos.add(Integer.toString(equipo.getClasificacion_posicion()));
                                    elementos.add(equipo.getClasificacion_equipo());
                                    elementos.add(Integer.toString(equipo.getClasificacion_partidos_jugados()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_ganados()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_empatados()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_perdidos()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_goles_favor()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_goles_contra()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_puntos()));
                                    tabla.agregarFilaTabla(id_liga,elementos);
                                    //Log.e("NOMBRE EQUIPO", equipo.getClasificacion_equipo());
                                }
                                if (equipo.getClasificacion_temporada().equals(Temporada) && equipo.getClasificacion_jornada().equals(Jornada)
                                        && NombreBD.equals("clasificacion_bundesliga")) {
                                    elementos.add(Integer.toString(equipo.getClasificacion_posicion()));
                                    elementos.add(equipo.getClasificacion_equipo());
                                    elementos.add(Integer.toString(equipo.getClasificacion_partidos_jugados()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_ganados()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_empatados()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_perdidos()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_goles_favor()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_goles_contra()));
                                    elementos.add(Integer.toString(equipo.getClasificacion_puntos()));
                                    tabla.agregarFilaTabla(id_liga,elementos);
                                    //Log.e("NOMBRE EQUIPO", equipo.getClasificacion_equipo());
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }

        @Override
        public void onItemSelected (AdapterView < ? > parent, View view,int position, long id){
            String text = parent.getItemAtPosition(position).toString();
            Toast.makeText(parent.getContext(), text, Toast.LENGTH_SHORT);
            tabla.limpiatabla();
            Jornada = text;
            tabla.agregarCabecera(R.array.cabecera_tabla);
            final ServicioClasificacion servicioClasificacion = iniciaRealm();
            //BDCall(tabla, Jornada);
            if (!servicioClasificacion.tienedatos() || Temporada.equals("Temporada2020")){
                boolean entra=true;
                if (Liga.equals("LaLiga Santander") && GlobalInfo.get_clasiflaliga()==1) {
                    entra=false;
                } else if (Liga.equals("Premier League")&& GlobalInfo.get_clasifpremier()==1) {
                    entra=false;
                } else if (Liga.equals("Bundesliga") && GlobalInfo.get_clasifbundesliga()==1) {
                    entra=false;
                } else if (Liga.equals("Serie A") && GlobalInfo.get_clasifseriea()==1) {
                    entra=false;
                }

                if (clasificacion_jornada.isEmpty() && !Temporada.equals("Temporada2020")) {
                    BDCall(servicioClasificacion,tabla, Jornada);
                }else if(entra && Temporada.equals("Temporada2020")){
                    BDCall(servicioClasificacion,tabla, Jornada);
                    if (Liga.equals("LaLiga Santander")) {
                        GlobalInfo.set_clasiflaliga();
                    } else if (Liga.equals("Premier League")) {
                        GlobalInfo.set_clasifpremier();
                    } else if (Liga.equals("Bundesliga")) {
                        GlobalInfo.set_clasifbundesliga();
                    } else if (Liga.equals("Serie A")) {
                        GlobalInfo.set_clasifseriea();
                    }
                }else if(!entra && Temporada.equals("Temporada2020") && servicioClasificacion.tienedatos()){
                    clasificaciones=servicioClasificacion.ObetenerClasificacion();
                    for (int j=0;j<clasificaciones.length;j++){
                        if(clasificaciones[j].getClasificacion_jornada().equals(Jornada)){
                            Log.e("EQUIPO", "REALM "+clasificaciones[j].getClasificacion_equipo());
                            elementos = new ArrayList<String>();
                            elementos.add(Integer.toString(clasificaciones[j].getClasificacion_posicion()));
                            elementos.add(clasificaciones[j].getClasificacion_equipo());
                            elementos.add(Integer.toString(clasificaciones[j].getClasificacion_partidos_jugados()));
                            elementos.add(Integer.toString(clasificaciones[j].getClasificacion_ganados()));
                            elementos.add(Integer.toString(clasificaciones[j].getClasificacion_empatados()));
                            elementos.add(Integer.toString(clasificaciones[j].getClasificacion_perdidos()));
                            elementos.add(Integer.toString(clasificaciones[j].getClasificacion_goles_favor()));
                            elementos.add(Integer.toString(clasificaciones[j].getClasificacion_goles_contra()));
                            elementos.add(Integer.toString(clasificaciones[j].getClasificacion_puntos()));
                            tabla.agregarFilaTabla(id_liga,elementos);
                        }
                    }
                } else{
                    for (int j = 0; j < clasificacion_jornada.size(); j++) {
                        if (clasificacion_jornada.get(j).getClasificacion_jornada().equals(Jornada)) {
                            Log.e("EQUIPO", clasificacion_jornada.get(j).getClasificacion_equipo());
                            elementos = new ArrayList<String>();
                            elementos.add(Integer.toString(clasificacion_jornada.get(j).getClasificacion_posicion()));
                            elementos.add(clasificacion_jornada.get(j).getClasificacion_equipo());
                            elementos.add(Integer.toString(clasificacion_jornada.get(j).getClasificacion_partidos_jugados()));
                            elementos.add(Integer.toString(clasificacion_jornada.get(j).getClasificacion_ganados()));
                            elementos.add(Integer.toString(clasificacion_jornada.get(j).getClasificacion_empatados()));
                            elementos.add(Integer.toString(clasificacion_jornada.get(j).getClasificacion_perdidos()));
                            elementos.add(Integer.toString(clasificacion_jornada.get(j).getClasificacion_goles_favor()));
                            elementos.add(Integer.toString(clasificacion_jornada.get(j).getClasificacion_goles_contra()));
                            elementos.add(Integer.toString(clasificacion_jornada.get(j).getClasificacion_puntos()));
                            tabla.agregarFilaTabla(id_liga,elementos);
                        }
                    }
                }
            }else {
                clasificaciones=servicioClasificacion.ObetenerClasificacion();
                for (int j=0;j<clasificaciones.length;j++){
                    if(clasificaciones[j].getClasificacion_jornada().equals(Jornada)){
                        Log.e("EQUIPO", "REALM "+clasificaciones[j].getClasificacion_equipo());
                        elementos = new ArrayList<String>();
                        elementos.add(Integer.toString(clasificaciones[j].getClasificacion_posicion()));
                        elementos.add(clasificaciones[j].getClasificacion_equipo());
                        elementos.add(Integer.toString(clasificaciones[j].getClasificacion_partidos_jugados()));
                        elementos.add(Integer.toString(clasificaciones[j].getClasificacion_ganados()));
                        elementos.add(Integer.toString(clasificaciones[j].getClasificacion_empatados()));
                        elementos.add(Integer.toString(clasificaciones[j].getClasificacion_perdidos()));
                        elementos.add(Integer.toString(clasificaciones[j].getClasificacion_goles_favor()));
                        elementos.add(Integer.toString(clasificaciones[j].getClasificacion_goles_contra()));
                        elementos.add(Integer.toString(clasificaciones[j].getClasificacion_puntos()));
                        tabla.agregarFilaTabla(id_liga,elementos);
                    }
                }
            }
        }

        @Override
        public void onNothingSelected (AdapterView < ? > parent){

        }

        @Override
        public void onPointerCaptureChanged ( boolean hasCapture){

        }

        public ServicioClasificacion iniciaRealm(){
            Realm.init(this);

            final RealmConfiguration realmConfigurationLigaClasif= new RealmConfiguration.Builder()
                    .name("ClasificacionLiga")
                    .schemaVersion(18)
                    .build();

            final RealmConfiguration realmConfigurationPremierClasif= new RealmConfiguration.Builder()
                    .name("ClasificacionPremier")
                    .schemaVersion(19)
                    .build();

            final RealmConfiguration realmConfigurationBundesligaClasif= new RealmConfiguration.Builder()
                    .name("ClasificacionBundesliga")
                    .schemaVersion(20)
                    .build();

            final RealmConfiguration realmConfigurationSerieaClasif= new RealmConfiguration.Builder()
                    .name("ClasificacionSeriea")
                    .schemaVersion(21)
                    .build();

            final RealmConfiguration realmConfigurationLiga19Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionLiga19")
                    .schemaVersion(22)
                    .build();

            final RealmConfiguration realmConfigurationPremier19Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionPremier19")
                    .schemaVersion(23)
                    .build();

            final RealmConfiguration realmConfigurationBundesliga19Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionBundesliga19")
                    .schemaVersion(24)
                    .build();

            final RealmConfiguration realmConfigurationSeriea19Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionSeriea19")
                    .schemaVersion(25)
                    .build();

            final RealmConfiguration realmConfigurationLiga18Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionLiga18")
                    .schemaVersion(26)
                    .build();

            final RealmConfiguration realmConfigurationPremier18Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionPremier18")
                    .schemaVersion(27)
                    .build();

            final RealmConfiguration realmConfigurationBundesliga18Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionBundesliga18")
                    .schemaVersion(28)
                    .build();

            final RealmConfiguration realmConfigurationSeriea18Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionSeriea18")
                    .schemaVersion(29)
                    .build();

            final RealmConfiguration realmConfigurationLiga17Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionLiga17")
                    .schemaVersion(30)
                    .build();

            final RealmConfiguration realmConfigurationPremier17Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionPremier17")
                    .schemaVersion(31)
                    .build();

            final RealmConfiguration realmConfigurationBundesliga17Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionBundesliga17")
                    .schemaVersion(32)
                    .build();

            final RealmConfiguration realmConfigurationSeriea17Clasif= new RealmConfiguration.Builder()
                    .name("ClasificacionSeriea17")
                    .schemaVersion(33)
                    .build();

            final Realm myRealmLigaClasif = Realm.getInstance(realmConfigurationLigaClasif);
            final Realm myRealmPremierClasif = Realm.getInstance(realmConfigurationPremierClasif);
            final Realm myRealmBundesligaClasif = Realm.getInstance(realmConfigurationBundesligaClasif);
            final Realm myRealmSerieAClasif = Realm.getInstance(realmConfigurationSerieaClasif);
            final Realm myRealmLiga19Clasif = Realm.getInstance(realmConfigurationLiga19Clasif);
            final Realm myRealmPremier19Clasif = Realm.getInstance(realmConfigurationPremier19Clasif);
            final Realm myRealmBundesliga19Clasif = Realm.getInstance(realmConfigurationBundesliga19Clasif);
            final Realm myRealmSerieA19Clasif = Realm.getInstance(realmConfigurationSeriea19Clasif);
            final Realm myRealmLiga18Clasif = Realm.getInstance(realmConfigurationLiga18Clasif);
            final Realm myRealmPremier18Clasif = Realm.getInstance(realmConfigurationPremier18Clasif);
            final Realm myRealmBundesliga18Clasif = Realm.getInstance(realmConfigurationBundesliga18Clasif);
            final Realm myRealmSerieA18Clasif = Realm.getInstance(realmConfigurationSeriea18Clasif);
            final Realm myRealmLiga17Clasif = Realm.getInstance(realmConfigurationLiga17Clasif);
            final Realm myRealmPremier17Clasif = Realm.getInstance(realmConfigurationPremier17Clasif);
            final Realm myRealmBundesliga17Clasif = Realm.getInstance(realmConfigurationBundesliga17Clasif);
            final Realm myRealmSerieA17Clasif = Realm.getInstance(realmConfigurationSeriea17Clasif);


            ServicioClasificacion servicioClasificacion=null;

            if(Liga.equals("LaLiga Santander")){
                if(Temporada.equals("Temporada2020")){
                    servicioClasificacion= new ServicioClasificacion(myRealmLigaClasif);
                }else if (Temporada.equals("Temporada2019")){
                    servicioClasificacion= new ServicioClasificacion(myRealmLiga19Clasif);
                }else if (Temporada.equals("Temporada2018")){
                    servicioClasificacion= new ServicioClasificacion(myRealmLiga18Clasif);
                }else {
                    servicioClasificacion= new ServicioClasificacion(myRealmLiga17Clasif);
                }
            }else if(Liga.equals("Premier League")){
                if(Temporada.equals("Temporada2020")){
                    servicioClasificacion= new ServicioClasificacion(myRealmPremierClasif);
                }else if (Temporada.equals("Temporada2019")){
                    servicioClasificacion= new ServicioClasificacion(myRealmPremier19Clasif);
                }else if (Temporada.equals("Temporada2018")){
                    servicioClasificacion= new ServicioClasificacion(myRealmPremier18Clasif);
                }else {
                    servicioClasificacion= new ServicioClasificacion(myRealmPremier17Clasif);
                }
            }else if(Liga.equals("Bundesliga")){
                if(Temporada.equals("Temporada2020")){
                    servicioClasificacion= new ServicioClasificacion(myRealmBundesligaClasif);
                }else if (Temporada.equals("Temporada2019")){
                    servicioClasificacion= new ServicioClasificacion(myRealmBundesliga19Clasif);
                }else if (Temporada.equals("Temporada2018")){
                    servicioClasificacion= new ServicioClasificacion(myRealmBundesliga18Clasif);
                }else {
                    servicioClasificacion= new ServicioClasificacion(myRealmBundesliga17Clasif);
                }

            }else if(Liga.equals("Serie A")){
                if(Temporada.equals("Temporada2020")){
                    servicioClasificacion= new ServicioClasificacion(myRealmSerieAClasif);
                }else if (Temporada.equals("Temporada2019")){
                    servicioClasificacion= new ServicioClasificacion(myRealmSerieA19Clasif);
                }else if (Temporada.equals("Temporada2018")){
                    servicioClasificacion= new ServicioClasificacion(myRealmSerieA18Clasif);
                }else {
                    servicioClasificacion= new ServicioClasificacion(myRealmSerieA17Clasif);
                }
            }

            return servicioClasificacion;
        }


    }
