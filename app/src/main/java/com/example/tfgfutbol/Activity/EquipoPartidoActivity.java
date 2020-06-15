package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tfgfutbol.Pojo.ChampionsPojo;
import com.example.tfgfutbol.Pojo.CopaPojo;
import com.example.tfgfutbol.Adapter.CustomAdapterPartido;
import com.example.tfgfutbol.Adapter.CustomAdapterPartidoChampions;
import com.example.tfgfutbol.Adapter.CustomAdapterPartidoCopa;
import com.example.tfgfutbol.Adapter.CustomAdapterPartidoUefa;
import com.example.tfgfutbol.Pojo.EquipoPojo;
import com.example.tfgfutbol.Layout.ImageLoadTask;
import com.example.tfgfutbol.Firebase.OnGetDataListener;
import com.example.tfgfutbol.Pojo.PartidosPojo;
import com.example.tfgfutbol.R;
import com.example.tfgfutbol.Pojo.UefaPojo;
import com.example.tfgfutbol.Realm.Partidos;
import com.example.tfgfutbol.Realm.ServicioPartidos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EquipoPartidoActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private Toolbar toolbar;
    private String Liga;
    private String Temporada;
    //private String Jornada;
    private String Equipo;

    ListView simpleList;
    ArrayList<PartidosPojo> partidos;
    ArrayList<CopaPojo> partidos_copa;
    ArrayList<CopaPojo> partidos_copa1;
    ArrayList<ChampionsPojo> partidos_champions;
    ArrayList<UefaPojo> partidos_uefa;
    ArrayList<EquipoPojo> equipos;
    DatabaseReference mRootReference;
    private String NombreBD="";
    private String NombreBDCopa="";
    private String NombreBDCopa1="";
    private String NombreBDChampions="";
    private String NombreBDUefa="";
    private String Escudo;
    TextView nombre_liga;
    TextView nombre_equipo;
    Spinner spinner;

    /**
     * Método de creación de la vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.equipopartido_layout);
        simpleList = (ListView) findViewById(R.id.listaPartidosEquipo);

        mRootReference= FirebaseDatabase.getInstance("https://tfg-futbol-calendario.firebaseio.com/").getReference();
        mRootReference.keepSynced(true);

        Intent i = getIntent();
        Liga=i.getStringExtra("LIGA");
        Temporada=i.getStringExtra("TEMPORADA");
        //Jornada=i.getStringExtra("JORNADA");
        Equipo=i.getStringExtra("EQUIPO");
        Escudo=i.getStringExtra("FOTO");


        spinner= (Spinner)findViewById(R.id.jornada_spinner);
        ArrayAdapter<CharSequence> adapter;

        if (Liga.equals("Bundesliga")){
            adapter=ArrayAdapter.createFromResource(this,R.array.partidos_bundesliga, android.R.layout.simple_spinner_item);
        }else if(Liga.equals("LaLiga Santander")){
            adapter=ArrayAdapter.createFromResource(this,R.array.partidos_laliga, android.R.layout.simple_spinner_item);
        }else if(Liga.equals("Premier League")){
            adapter=ArrayAdapter.createFromResource(this,R.array.partidos_premier, android.R.layout.simple_spinner_item);
        }else{
            adapter=ArrayAdapter.createFromResource(this,R.array.partidos_seriea, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        nombre_liga=(TextView)findViewById(R.id.nombre_liga_partidosequipo);
        nombre_liga.setText(Liga);

        nombre_equipo=(TextView) findViewById(R.id.nombre_equipo_partido);
        nombre_equipo.setText(Equipo);

        ImageView imagen = (ImageView) findViewById(R.id.foto_equipo);
        new ImageLoadTask(Escudo, imagen).execute();

        if(Liga.equals("LaLiga Santander")) {
            NombreBD="calendario_laliga";
            NombreBDCopa="calendario_copa_del_rey";
        }else if(Liga.equals("Premier League")) {
            NombreBD="calendario_premier";
            NombreBDCopa="calendario_fa_cup";
            NombreBDCopa1="calendario_carabao";
        }else if(Liga.equals("Bundesliga")) {
            NombreBD="calendario_bundesliga";
            NombreBDCopa="calendario_dfb_pokal";
        }else if(Liga.equals("Serie A")){
            NombreBD="calendario_seriea";
            NombreBDCopa="calendario_copa_italia";
        }
        NombreBDChampions="calendario_champions";
        NombreBDUefa="calendario_uefa";

        partidos= new ArrayList<PartidosPojo>();
        partidos_copa=new ArrayList<CopaPojo>();
        partidos_copa1=new ArrayList<CopaPojo>();
        equipos=new ArrayList<EquipoPojo>();
        partidos_uefa=new ArrayList<UefaPojo>();
        partidos_champions=new ArrayList<ChampionsPojo>();

        /*
        ArrayList<PartidosPojo> partido_equipo= new ArrayList<PartidosPojo>();
        for(int j=0;j<partidos.size();j++){
            if(partidos.get(j).getPartidos_equipo_local().equals(Equipo)){
                partido_equipo.add(partidos.get(j));
            }
            if(partidos.get(j).getPartidos_equipo_visitante().equals(Equipo)){
                partido_equipo.add(partidos.get(j));
            }
        }
        CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_equipo);
        customAdapter.notifyDataSetChanged();
        simpleList.setAdapter(customAdapter);


         */
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayoutselectpartidos);
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
     * Método para la llamada a la vista de plantillas tras pulsar el botón de plantillas
     * @param v
     */
    public void clickBotonPlantillas(View v){
        //Creamos el intent
        Intent intent = new Intent(this, PlantillaActivity.class);
        intent.putExtra("LIGA",Liga);
        intent.putExtra("TEMPORADA",Temporada);
        intent.putExtra("FOTO", Escudo);
        intent.putExtra("EQUIPO", Equipo);
        startActivity(intent);
    }

    /**
     * Método para acceder a los datos de la base de datos y en caso de no estar registrados
     * almacenarlos en la base de datos local Realm
     * @param servicioPartidos
     * @param BaseDatos
     * @param numero
     * @param listener
     */
    public void BDCall(final ServicioPartidos servicioPartidos, final String BaseDatos, final int numero, final OnGetDataListener listener){
        mRootReference.child(BaseDatos).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mRootReference.child(BaseDatos).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (numero==0) {
                                PartidosPojo partido = snapshot.getValue(PartidosPojo.class);
                                if (partido.getPartidos_temporada().equals(Temporada)) {
                                    partidos.add(partido);
                                    servicioPartidos.ActualizaPartidos(partido,Liga);
                                    listener.onFinish(numero);
                                }
                            }else if(numero==1){
                                CopaPojo partido= snapshot.getValue(CopaPojo.class);
                                if (partido.getCopa_temporada().equals(Temporada)) {
                                    partidos_copa.add(partido);
                                    listener.onFinish(numero);
                                }
                            }else if(numero==2){
                                ChampionsPojo partido=snapshot.getValue(ChampionsPojo.class);
                                if(partido.getChampions_temporada().equals(Temporada)){
                                    partidos_champions.add(partido);
                                    listener.onFinish(numero);
                                }
                            }else if(numero==3) {
                                UefaPojo partido= snapshot.getValue(UefaPojo.class);
                                if(partido.getUefa_temporada().equals(Temporada)){
                                    partidos_uefa.add(partido);
                                    listener.onFinish(numero);
                                }
                            }else {
                                CopaPojo partido= snapshot.getValue(CopaPojo.class);
                                if (partido.getCopa_temporada().equals(Temporada)) {
                                    partidos_copa1.add(partido);
                                    listener.onFinish(numero);
                                }
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
     * Método utilizado para filtrar la competición seleccionada
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT);

        final ServicioPartidos servicioPartidos=iniciaRealm();

        String BaseDatos="";
        int numero=0;
        if(text.equals("BUDESLIGA") || text.equals("LA LIGA SANTANDER") || text.equals("PREMIER LEAGUE") ||
        text.equals("SERIE A")){
            BaseDatos=NombreBD;
            numero=0;
        }else if(text.equals("DFB POKAL") || text.equals("COPA DEL REY") || text.equals("COPPA ITALIA") ||
        text.equals("FA CUP")){
            BaseDatos=NombreBDCopa;
            numero=1;
        }else if(text.equals("CARABAO CUP")){
            BaseDatos=NombreBDCopa1;
            numero=4;
        }else if(text.equals("CHAMPIONS LEAGUE")){
            BaseDatos=NombreBDChampions;
            numero=2;
        }else if(text.equals("EUROPA LEAGUE")){
            BaseDatos=NombreBDUefa;
            numero=3;
        }
        if (numero==0 && partidos.isEmpty() && servicioPartidos.tienedatos()){
            Partidos[] part=servicioPartidos.ObetenerPartidos();
            for (int i=0;i<part.length;i++){
                PartidosPojo nuevo=new PartidosPojo(part[i].getPartidos_fecha(),
                        part[i].getPartidos_jornada(),
                        part[i].getPartidos_equipo_local(),
                        part[i].getPartidos_resultado(),
                        part[i].getPartidos_equipo_visitante(),
                        part[i].getPartidos_foto_local(),
                        part[i].getPartidos_foto_visitante(),
                        part[i].getPartidos_temporada());
                partidos.add(nuevo);
            }

            ArrayList<PartidosPojo> partido_jornada= new ArrayList<PartidosPojo>();
            for(int j=0;j<partidos.size();j++){
                if(partidos.get(j).getPartidos_equipo_local().equals(Equipo) ||
                        partidos.get(j).getPartidos_equipo_visitante().equals(Equipo)){
                    partido_jornada.add(partidos.get(j));
                }
            }

            CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_jornada);
            customAdapter.notifyDataSetChanged();
            simpleList.setAdapter(customAdapter);

        }else if(numero==0 && !partidos.isEmpty()){
            ArrayList<PartidosPojo> partido_jornada= new ArrayList<PartidosPojo>();
            for(int j=0;j<partidos.size();j++){
                if(partidos.get(j).getPartidos_equipo_local().equals(Equipo) ||
                        partidos.get(j).getPartidos_equipo_visitante().equals(Equipo)){
                    partido_jornada.add(partidos.get(j));
                }
            }

            CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_jornada);
            customAdapter.notifyDataSetChanged();
            simpleList.setAdapter(customAdapter);

        }else if(numero==1 && !partidos_copa.isEmpty()){
            ArrayList<CopaPojo> partido_jornada= new ArrayList<CopaPojo>();
            for(int j=0;j<partidos_copa.size();j++){
                if(partidos_copa.get(j).getCopa_local().equals(Equipo) || partidos_copa.get(j).getCopa_visitante().equals(Equipo)){
                    partido_jornada.add(partidos_copa.get(j));
                }
            }

            CustomAdapterPartidoCopa customAdapter = new CustomAdapterPartidoCopa(getApplicationContext(), partido_jornada);
            customAdapter.notifyDataSetChanged();
            simpleList.setAdapter(customAdapter);
        }else if (numero==2 && !partidos_champions.isEmpty()){
            ArrayList<ChampionsPojo> partido_jornada= new ArrayList<ChampionsPojo>();
            for(int j=0;j<partidos_champions.size();j++){
                if(partidos_champions.get(j).getChampions_local().equals(Equipo) || partidos_champions.get(j).getChampions_visitante().equals(Equipo)){
                    partido_jornada.add(partidos_champions.get(j));
                }
            }

            CustomAdapterPartidoChampions customAdapter = new CustomAdapterPartidoChampions(getApplicationContext(), partido_jornada);
            customAdapter.notifyDataSetChanged();
            simpleList.setAdapter(customAdapter);
        }else if(numero==3 && !partidos_uefa.isEmpty()) {
            ArrayList<UefaPojo> partido_jornada = new ArrayList<UefaPojo>();
            for (int j = 0; j < partidos_uefa.size(); j++) {
                if (partidos_uefa.get(j).getUefa_local().equals(Equipo) || partidos_uefa.get(j).getUefa_visitante().equals(Equipo)) {
                    partido_jornada.add(partidos_uefa.get(j));
                }
            }

            CustomAdapterPartidoUefa customAdapter = new CustomAdapterPartidoUefa(getApplicationContext(), partido_jornada);
            customAdapter.notifyDataSetChanged();
            simpleList.setAdapter(customAdapter);
        }else if (numero==4 && !partidos_copa1.isEmpty()) {
            ArrayList<CopaPojo> partido_jornada = new ArrayList<CopaPojo>();
            for (int j = 0; j < partidos_copa1.size(); j++) {
                if (partidos_copa1.get(j).getCopa_local().equals(Equipo) || partidos_copa1.get(j).getCopa_visitante().equals(Equipo)) {
                    partido_jornada.add(partidos_copa1.get(j));
                }
            }

            CustomAdapterPartidoCopa customAdapter = new CustomAdapterPartidoCopa(getApplicationContext(), partido_jornada);
            customAdapter.notifyDataSetChanged();
            simpleList.setAdapter(customAdapter);
        }else {

            BDCall(servicioPartidos, BaseDatos, numero, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }

                @Override
                public void onFinish(int numero) {
                    if(numero==0){
                        ArrayList<PartidosPojo> partido_jornada= new ArrayList<PartidosPojo>();
                        for(int j=0;j<partidos.size();j++){
                            if(partidos.get(j).getPartidos_equipo_local().equals(Equipo) ||
                                    partidos.get(j).getPartidos_equipo_visitante().equals(Equipo)){
                                partido_jornada.add(partidos.get(j));
                            }
                        }

                        CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_jornada);
                        customAdapter.notifyDataSetChanged();
                        simpleList.setAdapter(customAdapter);

                    }else if(numero==1){
                        ArrayList<CopaPojo> partido_jornada= new ArrayList<CopaPojo>();
                        for(int j=0;j<partidos_copa.size();j++){
                            if(partidos_copa.get(j).getCopa_local().equals(Equipo) || partidos_copa.get(j).getCopa_visitante().equals(Equipo)){
                                partido_jornada.add(partidos_copa.get(j));
                            }
                        }

                        CustomAdapterPartidoCopa customAdapter = new CustomAdapterPartidoCopa(getApplicationContext(), partido_jornada);
                        customAdapter.notifyDataSetChanged();
                        simpleList.setAdapter(customAdapter);
                    }else if (numero==2){
                        ArrayList<ChampionsPojo> partido_jornada= new ArrayList<ChampionsPojo>();
                        for(int j=0;j<partidos_champions.size();j++){
                            if(partidos_champions.get(j).getChampions_local().equals(Equipo) || partidos_champions.get(j).getChampions_visitante().equals(Equipo)){
                                partido_jornada.add(partidos_champions.get(j));
                            }
                        }

                        CustomAdapterPartidoChampions customAdapter = new CustomAdapterPartidoChampions(getApplicationContext(), partido_jornada);
                        customAdapter.notifyDataSetChanged();
                        simpleList.setAdapter(customAdapter);
                    }else if(numero==3){
                        ArrayList<UefaPojo> partido_jornada= new ArrayList<UefaPojo>();
                        for(int j=0;j<partidos_uefa.size();j++){
                            if(partidos_uefa.get(j).getUefa_local().equals(Equipo) || partidos_uefa.get(j).getUefa_visitante().equals(Equipo)){
                                partido_jornada.add(partidos_uefa.get(j));
                            }
                        }

                        CustomAdapterPartidoUefa customAdapter = new CustomAdapterPartidoUefa(getApplicationContext(), partido_jornada);
                        customAdapter.notifyDataSetChanged();
                        simpleList.setAdapter(customAdapter);
                    }else {
                        ArrayList<CopaPojo> partido_jornada= new ArrayList<CopaPojo>();
                        for(int j=0;j<partidos_copa1.size();j++){
                            if(partidos_copa1.get(j).getCopa_local().equals(Equipo) || partidos_copa1.get(j).getCopa_visitante().equals(Equipo)){
                                partido_jornada.add(partidos_copa1.get(j));
                            }
                        }

                        CustomAdapterPartidoCopa customAdapter = new CustomAdapterPartidoCopa(getApplicationContext(), partido_jornada);
                        customAdapter.notifyDataSetChanged();
                        simpleList.setAdapter(customAdapter);
                    }
                }
            });
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Método para inicializar las instancias de las bases de datos
     * @return
     */
    public ServicioPartidos iniciaRealm(){
        Realm.init(this);

        final RealmConfiguration realmConfigurationLigaPar= new RealmConfiguration.Builder()
                .name("PartidoLiga")
                .schemaVersion(50)
                .build();

        final RealmConfiguration realmConfigurationPremierPar= new RealmConfiguration.Builder()
                .name("PartidoPremier")
                .schemaVersion(51)
                .build();

        final RealmConfiguration realmConfigurationBundesligaPar= new RealmConfiguration.Builder()
                .name("PartidoBundesliga")
                .schemaVersion(52)
                .build();

        final RealmConfiguration realmConfigurationSerieaPar= new RealmConfiguration.Builder()
                .name("PartidoSeriea")
                .schemaVersion(53)
                .build();

        final RealmConfiguration realmConfigurationLiga19Par= new RealmConfiguration.Builder()
                .name("PartidoLiga19")
                .schemaVersion(54)
                .build();

        final RealmConfiguration realmConfigurationPremier19Par= new RealmConfiguration.Builder()
                .name("PartidoPremier19")
                .schemaVersion(55)
                .build();

        final RealmConfiguration realmConfigurationBundesliga19Par= new RealmConfiguration.Builder()
                .name("PartidoBundesliga19")
                .schemaVersion(56)
                .build();

        final RealmConfiguration realmConfigurationSeriea19Par= new RealmConfiguration.Builder()
                .name("PartidoSeriea19")
                .schemaVersion(57)
                .build();

        final RealmConfiguration realmConfigurationLiga18Par= new RealmConfiguration.Builder()
                .name("PartidoLiga18")
                .schemaVersion(58)
                .build();

        final RealmConfiguration realmConfigurationPremier18Par= new RealmConfiguration.Builder()
                .name("PartidoPremier18")
                .schemaVersion(59)
                .build();

        final RealmConfiguration realmConfigurationBundesliga18Par= new RealmConfiguration.Builder()
                .name("PartidoBundesliga18")
                .schemaVersion(60)
                .build();

        final RealmConfiguration realmConfigurationSeriea18Par= new RealmConfiguration.Builder()
                .name("PartidoSeriea18")
                .schemaVersion(61)
                .build();

        final RealmConfiguration realmConfigurationLiga17Par= new RealmConfiguration.Builder()
                .name("PartidoLiga17")
                .schemaVersion(62)
                .build();

        final RealmConfiguration realmConfigurationPremier17Par= new RealmConfiguration.Builder()
                .name("PartidoPremier17")
                .schemaVersion(63)
                .build();

        final RealmConfiguration realmConfigurationBundesliga17Par= new RealmConfiguration.Builder()
                .name("PartidoBundesliga17")
                .schemaVersion(64)
                .build();

        final RealmConfiguration realmConfigurationSeriea17Par= new RealmConfiguration.Builder()
                .name("PartidoSeriea17")
                .schemaVersion(65)
                .build();

        final Realm myRealmLigaPar = Realm.getInstance(realmConfigurationLigaPar);
        final Realm myRealmPremierPar= Realm.getInstance(realmConfigurationPremierPar);
        final Realm myRealmBundesligaPar = Realm.getInstance(realmConfigurationBundesligaPar);
        final Realm myRealmSerieAPar = Realm.getInstance(realmConfigurationSerieaPar);
        final Realm myRealmLiga19Par = Realm.getInstance(realmConfigurationLiga19Par);
        final Realm myRealmPremier19Par = Realm.getInstance(realmConfigurationPremier19Par);
        final Realm myRealmBundesliga19Par = Realm.getInstance(realmConfigurationBundesliga19Par);
        final Realm myRealmSerieA19Par = Realm.getInstance(realmConfigurationSeriea19Par);
        final Realm myRealmLiga18Par = Realm.getInstance(realmConfigurationLiga18Par);
        final Realm myRealmPremier18Par= Realm.getInstance(realmConfigurationPremier18Par);
        final Realm myRealmBundesliga18Par = Realm.getInstance(realmConfigurationBundesliga18Par);
        final Realm myRealmSerieA18Par = Realm.getInstance(realmConfigurationSeriea18Par);
        final Realm myRealmLiga17Par = Realm.getInstance(realmConfigurationLiga17Par);
        final Realm myRealmPremier17Par= Realm.getInstance(realmConfigurationPremier17Par);
        final Realm myRealmBundesliga17Par = Realm.getInstance(realmConfigurationBundesliga17Par);
        final Realm myRealmSerieA17Par = Realm.getInstance(realmConfigurationSeriea17Par);


        ServicioPartidos servicioClasificacion=null;

        if(Liga.equals("LaLiga Santander")){
            if(Temporada.equals("Temporada2020")){
                servicioClasificacion= new ServicioPartidos(myRealmLigaPar);
            }else if (Temporada.equals("Temporada2019")){
                servicioClasificacion= new ServicioPartidos(myRealmLiga19Par);
            }else if (Temporada.equals("Temporada2018")){
                servicioClasificacion= new ServicioPartidos(myRealmLiga18Par);
            }else {
                servicioClasificacion= new ServicioPartidos(myRealmLiga17Par);
            }
        }else if(Liga.equals("Premier League")){
            if(Temporada.equals("Temporada2020")){
                servicioClasificacion= new ServicioPartidos(myRealmPremierPar);
            }else if (Temporada.equals("Temporada2019")){
                servicioClasificacion= new ServicioPartidos(myRealmPremier19Par);
            }else if (Temporada.equals("Temporada2018")){
                servicioClasificacion= new ServicioPartidos(myRealmPremier18Par);
            }else {
                servicioClasificacion= new ServicioPartidos(myRealmPremier17Par);
            }
        }else if(Liga.equals("Bundesliga")){
            if(Temporada.equals("Temporada2020")){
                servicioClasificacion= new ServicioPartidos(myRealmBundesligaPar);
            }else if (Temporada.equals("Temporada2019")){
                servicioClasificacion= new ServicioPartidos(myRealmBundesliga19Par);
            }else if (Temporada.equals("Temporada2018")){
                servicioClasificacion= new ServicioPartidos(myRealmBundesliga18Par);
            }else {
                servicioClasificacion= new ServicioPartidos(myRealmBundesliga17Par);
            }

        }else if(Liga.equals("Serie A")){
            if(Temporada.equals("Temporada2020")){
                servicioClasificacion= new ServicioPartidos(myRealmSerieAPar);
            }else if (Temporada.equals("Temporada2019")){
                servicioClasificacion= new ServicioPartidos(myRealmSerieA19Par);
            }else if (Temporada.equals("Temporada2018")){
                servicioClasificacion= new ServicioPartidos(myRealmSerieA18Par);
            }else {
                servicioClasificacion= new ServicioPartidos(myRealmSerieA17Par);
            }
        }

        return servicioClasificacion;
    }
}
