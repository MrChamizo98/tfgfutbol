package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tfgfutbol.GlobalInfo;
import com.example.tfgfutbol.Layout.ImageLoadTask;
import com.example.tfgfutbol.Pojo.PlantillaPojo;
import com.example.tfgfutbol.R;
import com.example.tfgfutbol.Realm.Plantilla;
import com.example.tfgfutbol.Realm.ServicioAlineacion;
import com.example.tfgfutbol.Realm.ServicioClasificacion;
import com.example.tfgfutbol.Realm.ServicioPartidos;
import com.example.tfgfutbol.Realm.ServicioPlantilla;
import com.example.tfgfutbol.Tabla.Tabla;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PlantillaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    DatabaseReference mRootReference;
    ArrayList<PlantillaPojo> plantilla;
    private String NombreBD="";
    private String Equipo;
    private String Liga;
    private String Temporada;
    private String Escudo;
    TextView nombre_liga;
    ArrayList<String>elementos;

    Plantilla[] plantillas;

    /**
     * Método de creación de la vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantilla_layout);

        mRootReference= FirebaseDatabase.getInstance("https://tfg-futbol-plantilla.firebaseio.com/").getReference();
        //mRootReference.keepSynced(true);

        plantilla=new ArrayList<PlantillaPojo>();

        Intent i = getIntent();
        Equipo=i.getStringExtra("EQUIPO");
        Liga=i.getStringExtra("LIGA");
        Escudo=i.getStringExtra("FOTO");
        Temporada=i.getStringExtra("TEMPORADA");


        nombre_liga=(TextView)findViewById(R.id.nombre_liga_plantilla);
        nombre_liga.setText(Liga);

        ImageView imagen = (ImageView) findViewById(R.id.foto_equipo);
        new ImageLoadTask(Escudo, imagen).execute();

        TextView nombre_eq=(TextView)findViewById(R.id.nombre_equipo_plantilla);
        nombre_eq.setText(Equipo);

        if(Liga.equals("LaLiga Santander")) {
            if(Temporada.equals("Temporada2020")){
                NombreBD="plantilla_laliga/plantilla_laliga_2019_2020";
            }else if (Temporada.equals("Temporada2019")){
                NombreBD="plantilla_laliga/plantilla_laliga_2018_2019";
            }else if (Temporada.equals("Temporada2018")){
                NombreBD="plantilla_laliga/plantilla_laliga_2017_2018";
            }else {
                NombreBD="plantilla_laliga/plantilla_laliga_2016_2017";
            }

        }else if(Liga.equals("Premier League")) {
            if(Temporada.equals("Temporada2020")){
                NombreBD="plantilla_premier/plantilla_premier_2019_2020";
            }else if (Temporada.equals("Temporada2019")){
                NombreBD="plantilla_premier/plantilla_premier_2018_2019";
            }else if (Temporada.equals("Temporada2018")){
                NombreBD="plantilla_premier/plantilla_premier_2017_2018";
            }else {
                NombreBD="plantilla_premier/plantilla_premier_2016_2017";
            }
        }else if(Liga.equals("Bundesliga")) {
            if(Temporada.equals("Temporada2020")){
                NombreBD="plantilla_bundesliga/plantilla_bundesliga_2019_2020";
            }else if (Temporada.equals("Temporada2019")){
                NombreBD="plantilla_bundesliga/plantilla_bundesliga_2018_2019";
            }else if (Temporada.equals("Temporada2018")){
                NombreBD="plantilla_bundesliga/plantilla_bundesliga_2017_2018";
            }else {
                NombreBD="plantilla_bundesliga/plantilla_bundesliga_2016_2017";
            }
        }else if(Liga.equals("Serie A")){
            if(Temporada.equals("Temporada2020")){
                NombreBD="plantilla_seriea/plantilla_seriea_2019_2020";
            }else if (Temporada.equals("Temporada2019")){
                NombreBD="plantilla_seriea/plantilla_seriea_2018_2019";
            }else if (Temporada.equals("Temporada2018")){
                NombreBD="plantilla_seriea/plantilla_seriea_2017_2018";
            }else {
                NombreBD="plantilla_seriea/plantilla_seriea_2016_2017";
            }
        }

        final Tabla tabla=new Tabla(this, (TableLayout)findViewById(R.id.tabla));
        tabla.agregarCabecera(R.array.cabecera_plantilla);

        String nombre_equipo=Equipo;

        final ServicioPlantilla servicioPlantilla=iniciaRealm();

        if(!servicioPlantilla.tienedatos() || Temporada.equals("Temporada2020")){
            boolean entra=true;
            if (Liga.equals("LaLiga Santander") && GlobalInfo.get_plantlaliga()==1) {
                entra=false;
            } else if (Liga.equals("Premier League")&& GlobalInfo.get_plantpremier()==1) {
                entra=false;
            } else if (Liga.equals("Bundesliga") && GlobalInfo.get_plantbundesliga()==1) {
                entra=false;
            } else if (Liga.equals("Serie A") && GlobalInfo.get_plantseriea()==1) {
                entra=false;
            }
            if(entra && Temporada.equals("Temporada2020")) {
                BDCall(servicioPlantilla, tabla, nombre_equipo);
                if (Liga.equals("LaLiga Santander")) {
                    GlobalInfo.set_plantlaliga();
                } else if (Liga.equals("Premier League")) {
                    GlobalInfo.set_plantpremier();
                } else if (Liga.equals("Bundesliga")) {
                    GlobalInfo.set_plantbundesliga();
                } else if (Liga.equals("Serie A")) {
                    GlobalInfo.set_plantseriea();
                }
            }else if (!entra && Temporada.equals("Temporada2020")){
                plantillas=servicioPlantilla.ObetenerPlantilla();
                for (int j=0;j<plantillas.length;j++) {
                    elementos = new ArrayList<String>();
                    if (nombre_equipo.equals(plantillas[j].getPlantilla_equipo()) && plantillas[j].getPlantilla_temporada().equals(Temporada)) {
                        elementos = new ArrayList<String>();
                        elementos.add(plantillas[j].getPlantilla_foto());
                        elementos.add(plantillas[j].getPlantilla_dorsal());
                        elementos.add(plantillas[j].getPlantilla_name());
                        elementos.add(plantillas[j].getPlantilla_pais());
                        elementos.add(plantillas[j].getPlantilla_posicion());
                        elementos.add(plantillas[j].getPlantilla_edad());
                        elementos.add(plantillas[j].getPlantilla_altura());
                        elementos.add(plantillas[j].getPlantilla_peso());
                        elementos.add(plantillas[j].getPlantilla_goles());
                        elementos.add(plantillas[j].getPlantilla_rojas());
                        elementos.add(plantillas[j].getPlantilla_amarillas());
                        Log.e("NOMBRE JUGADOR REALM", plantillas[j].getPlantilla_name());
                        tabla.agregarFilaTabla(elementos);
                    }
                }
            }else{
                BDCall(servicioPlantilla, tabla, nombre_equipo);
            }
        }else {
            plantillas=servicioPlantilla.ObetenerPlantilla();
            for (int j=0;j<plantillas.length;j++) {
                elementos = new ArrayList<String>();
                if (nombre_equipo.equals(plantillas[j].getPlantilla_equipo()) && plantillas[j].getPlantilla_temporada().equals(Temporada)) {
                    elementos = new ArrayList<String>();
                    elementos.add(plantillas[j].getPlantilla_foto());
                    elementos.add(plantillas[j].getPlantilla_dorsal());
                    elementos.add(plantillas[j].getPlantilla_name());
                    elementos.add(plantillas[j].getPlantilla_pais());
                    elementos.add(plantillas[j].getPlantilla_posicion());
                    elementos.add(plantillas[j].getPlantilla_edad());
                    elementos.add(plantillas[j].getPlantilla_altura());
                    elementos.add(plantillas[j].getPlantilla_peso());
                    elementos.add(plantillas[j].getPlantilla_goles());
                    elementos.add(plantillas[j].getPlantilla_rojas());
                    elementos.add(plantillas[j].getPlantilla_amarillas());
                    Log.e("NOMBRE JUGADOR REALM", plantillas[j].getPlantilla_name());
                    tabla.agregarFilaTabla(elementos);
                }
            }

        }

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayoutplantilla);
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

    /**
     * Crear menú de selección
     * @param menu
     * @return true
     */
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menusuperior,menu);
        getMenuInflater().inflate(R.menu.menuinferior,menu);
        return true;
    }



    /**
     * Método para cambiar de actividad tras la selección de un elemento del menú
     * @param menuItem
     * @return true
     */
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
                i.putExtra("JORNADA","Jornada 1");
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

    /**
     * Método para pasar a la ventana de partidos de un equipo tras pulsar el botón
     * @param v
     */
    public void clickBotonPartidos(View v){
        //Creamos el intent
        Intent intent = new Intent(this, EquipoPartidoActivity.class);
        intent.putExtra("LIGA",Liga);
        intent.putExtra("TEMPORADA",Temporada);
        intent.putExtra("FOTO", Escudo);
        intent.putExtra("EQUIPO", Equipo);
        startActivity(intent);
    }

    /**
     * Método para acceder a la base de datos y descargar los datos de las plantillas de los
     * diferentes equipos
     * @param servicioPlantilla
     * @param tabla
     * @param nombre_equipo
     */
    public void BDCall( final ServicioPlantilla servicioPlantilla, final Tabla tabla, final String nombre_equipo){
        mRootReference.child(NombreBD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mRootReference.child(NombreBD).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            PlantillaPojo jugador = snapshot.getValue(PlantillaPojo.class);
                            try {
                                servicioPlantilla.ActualizaPlantilla(jugador,Liga);
                            }catch (Exception e){

                            }
                            elementos = new ArrayList<String>();
                            if(nombre_equipo.equals(jugador.getPlantilla_equipo()) && jugador.getPlantilla_temporada().equals(Temporada)) {
                                elementos = new ArrayList<String>();
                                elementos.add(jugador.getPlantilla_foto());
                                elementos.add(jugador.getPlantilla_dorsal());
                                elementos.add(jugador.getPlantilla_name());
                                elementos.add(jugador.getPlantilla_pais());
                                elementos.add(jugador.getPlantilla_posicion());
                                elementos.add(jugador.getPlantilla_edad());
                                elementos.add(jugador.getPlantilla_altura());
                                elementos.add(jugador.getPlantilla_peso());
                                elementos.add(jugador.getPlantilla_goles());
                                elementos.add(jugador.getPlantilla_rojas());
                                elementos.add(jugador.getPlantilla_amarillas());
                                Log.e("NOMBRE JUGADOR",jugador.getPlantilla_name());
                                tabla.agregarFilaTabla(elementos);
                            }
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {}
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    /**
     * Método para la inicializacaión de las instancias de la base de datos Realm
     * @return servicioplantilla
     */
    public ServicioPlantilla iniciaRealm(){
        Realm.init(this);

        final RealmConfiguration realmConfigurationLigaPlan= new RealmConfiguration.Builder()
                .name("PlantillaLiga")
                .schemaVersion(34)
                .build();

        final RealmConfiguration realmConfigurationPremierPlan= new RealmConfiguration.Builder()
                .name("PlantillaPremier")
                .schemaVersion(35)
                .build();

        final RealmConfiguration realmConfigurationBundesligaPlan= new RealmConfiguration.Builder()
                .name("PlantillaBundesliga")
                .schemaVersion(36)
                .build();

        final RealmConfiguration realmConfigurationSerieaPlan= new RealmConfiguration.Builder()
                .name("PlantillaSeriea")
                .schemaVersion(37)
                .build();

        final RealmConfiguration realmConfigurationLiga19Plan= new RealmConfiguration.Builder()
                .name("PlantillaLiga19")
                .schemaVersion(38)
                .build();

        final RealmConfiguration realmConfigurationPremier19Plan= new RealmConfiguration.Builder()
                .name("PlantillaPremier19")
                .schemaVersion(39)
                .build();

        final RealmConfiguration realmConfigurationBundesliga19Plan= new RealmConfiguration.Builder()
                .name("PlantillaBundesliga19")
                .schemaVersion(40)
                .build();

        final RealmConfiguration realmConfigurationSeriea19Plan= new RealmConfiguration.Builder()
                    .name("PlantillaSeriea19")
                .schemaVersion(41)
                .build();

        final RealmConfiguration realmConfigurationLiga18Plan= new RealmConfiguration.Builder()
                .name("PlantillaLiga18")
                .schemaVersion(42)
                .build();

        final RealmConfiguration realmConfigurationPremier18Plan= new RealmConfiguration.Builder()
                .name("PlantillaPremier18")
                .schemaVersion(43)
                .build();

        final RealmConfiguration realmConfigurationBundesliga18Plan= new RealmConfiguration.Builder()
                .name("PlantillaBundesliga18")
                .schemaVersion(44)
                .build();

        final RealmConfiguration realmConfigurationSeriea18Plan= new RealmConfiguration.Builder()
                .name("PlantillaSeriea18")
                .schemaVersion(45)
                .build();

        final RealmConfiguration realmConfigurationLiga17Plan= new RealmConfiguration.Builder()
                .name("PlantillaLiga17")
                .schemaVersion(46)
                .build();

        final RealmConfiguration realmConfigurationPremier17Plan= new RealmConfiguration.Builder()
                .name("PlantillaPremier17")
                .schemaVersion(47)
                .build();

        final RealmConfiguration realmConfigurationBundesliga17Plan= new RealmConfiguration.Builder()
                .name("PlantillaBundesliga17")
                .schemaVersion(48)
                .build();

        final RealmConfiguration realmConfigurationSeriea17Plan= new RealmConfiguration.Builder()
                .name("PlantillaSeriea17")
                .schemaVersion(49)
                .build();

        final Realm myRealmLigaPlan = Realm.getInstance(realmConfigurationLigaPlan);
        final Realm myRealmPremierPlan= Realm.getInstance(realmConfigurationPremierPlan);
        final Realm myRealmBundesligaPlan = Realm.getInstance(realmConfigurationBundesligaPlan);
        final Realm myRealmSerieAPlan = Realm.getInstance(realmConfigurationSerieaPlan);
        final Realm myRealmLiga19Plan = Realm.getInstance(realmConfigurationLiga19Plan);
        final Realm myRealmPremier19Plan = Realm.getInstance(realmConfigurationPremier19Plan);
        final Realm myRealmBundesliga19Plan = Realm.getInstance(realmConfigurationBundesliga19Plan);
        final Realm myRealmSerieA19Plan = Realm.getInstance(realmConfigurationSeriea19Plan);
        final Realm myRealmLiga18Plan = Realm.getInstance(realmConfigurationLiga18Plan);
        final Realm myRealmPremier18Plan = Realm.getInstance(realmConfigurationPremier18Plan);
        final Realm myRealmBundesliga18Plan = Realm.getInstance(realmConfigurationBundesliga18Plan);
        final Realm myRealmSerieA18Plan = Realm.getInstance(realmConfigurationSeriea18Plan);
        final Realm myRealmLiga17Plan = Realm.getInstance(realmConfigurationLiga17Plan);
        final Realm myRealmPremier17Plan= Realm.getInstance(realmConfigurationPremier17Plan);
        final Realm myRealmBundesliga17Plan = Realm.getInstance(realmConfigurationBundesliga17Plan);
        final Realm myRealmSerieA17Plan = Realm.getInstance(realmConfigurationSeriea17Plan);


        ServicioPlantilla servicioClasificacion=null;

        if(Liga.equals("LaLiga Santander")){
            if(Temporada.equals("Temporada2020")){
                servicioClasificacion= new ServicioPlantilla(myRealmLigaPlan);
            }else if (Temporada.equals("Temporada2019")){
                servicioClasificacion= new ServicioPlantilla(myRealmLiga19Plan);
            }else if (Temporada.equals("Temporada2018")){
                servicioClasificacion= new ServicioPlantilla(myRealmLiga18Plan);
            }else {
                servicioClasificacion= new ServicioPlantilla(myRealmLiga17Plan);
            }
        }else if(Liga.equals("Premier League")){
            if(Temporada.equals("Temporada2020")){
                servicioClasificacion= new ServicioPlantilla(myRealmPremierPlan);
            }else if (Temporada.equals("Temporada2019")){
                servicioClasificacion= new ServicioPlantilla(myRealmPremier19Plan);
            }else if (Temporada.equals("Temporada2018")){
                servicioClasificacion= new ServicioPlantilla(myRealmPremier18Plan);
            }else {
                servicioClasificacion= new ServicioPlantilla(myRealmPremier17Plan);
            }
        }else if(Liga.equals("Bundesliga")){
            if(Temporada.equals("Temporada2020")){
                servicioClasificacion= new ServicioPlantilla(myRealmBundesligaPlan);
            }else if (Temporada.equals("Temporada2019")){
                servicioClasificacion= new ServicioPlantilla(myRealmBundesliga19Plan);
            }else if (Temporada.equals("Temporada2018")){
                servicioClasificacion= new ServicioPlantilla(myRealmBundesliga18Plan);
            }else {
                servicioClasificacion= new ServicioPlantilla(myRealmBundesliga17Plan);
            }

        }else if(Liga.equals("Serie A")){
            if(Temporada.equals("Temporada2020")){
                servicioClasificacion= new ServicioPlantilla(myRealmSerieAPlan);
            }else if (Temporada.equals("Temporada2019")){
                servicioClasificacion= new ServicioPlantilla(myRealmSerieA19Plan);
            }else if (Temporada.equals("Temporada2018")){
                servicioClasificacion= new ServicioPlantilla(myRealmSerieA18Plan);
            }else {
                servicioClasificacion= new ServicioPlantilla(myRealmSerieA17Plan);
            }
        }

        return servicioClasificacion;
    }
}

