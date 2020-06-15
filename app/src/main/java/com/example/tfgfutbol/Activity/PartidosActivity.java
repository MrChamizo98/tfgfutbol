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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tfgfutbol.Adapter.CustomAdapterPartido;
import com.example.tfgfutbol.GlobalInfo;
import com.example.tfgfutbol.Pojo.EquipoPojo;
import com.example.tfgfutbol.Firebase.OnGetDataListener;
import com.example.tfgfutbol.Pojo.PartidosPojo;
import com.example.tfgfutbol.R;
import com.example.tfgfutbol.Realm.Partidos;
import com.example.tfgfutbol.Realm.ServicioPartidos;
import com.example.tfgfutbol.Realm.ServicioPlantilla;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class PartidosActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Toolbar toolbar;
    private String Liga;
    private String Temporada;
    private String Jornada;

    ListView simpleList;
    ArrayList<PartidosPojo> partidos;
    ArrayList<EquipoPojo> equipos;
    //ArrayList<AlineacionPojo>alineacion;
    DatabaseReference mRootReference;
    private String NombreBD="";
    private String NombreBD2="";
    TextView nombre_liga;
    Spinner spinner;

    Partidos[] lista_partidos;

    /**
     * Método de creación de vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partidos_layout);
        simpleList = (ListView) findViewById(R.id.listaPartidos);

        Intent i = getIntent();
        Liga=i.getStringExtra("LIGA");
        Temporada=i.getStringExtra("TEMPORADA");
        Jornada=i.getStringExtra("JORNADA");

        mRootReference= FirebaseDatabase.getInstance("https://tfg-futbol-calendario.firebaseio.com/").getReference();

        //alineacion=(ArrayList<AlineacionPojo>) i.getSerializableExtra("ALINEACION");
        spinner= (Spinner)findViewById(R.id.jornada_spinner);
        ArrayAdapter<CharSequence> adapter;
        if (Liga.equals("Bundesliga")){
            adapter=ArrayAdapter.createFromResource(this,R.array.lista_jornada_bundesliga, android.R.layout.simple_spinner_item);
        }else {
            adapter = ArrayAdapter.createFromResource(this, R.array.lista_jornada, android.R.layout.simple_spinner_item);
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        nombre_liga=(TextView)findViewById(R.id.nombre_liga_partidos);
        nombre_liga.setText(Liga);

        if(Liga.equals("LaLiga Santander")) {
            NombreBD="calendario_laliga";
            NombreBD2="EQUIPOS";
        }else if(Liga.equals("Premier League")) {
            NombreBD="calendario_premier";
            NombreBD2="EQUIPOS_PREMIER";
        }else if(Liga.equals("Bundesliga")) {
            NombreBD="calendario_bundesliga";
            NombreBD2="EQUIPOS_BUNDESLIGA";
        }else if(Liga.equals("Serie A")){
            NombreBD="calendario_seriea";
            NombreBD2="EQUIPOS_SERIEA";
        }

        partidos= new ArrayList<PartidosPojo>();
        equipos=new ArrayList<EquipoPojo>();

        /*
        String dato="Jornada 1";
        final ServicioPartidos servicioPartidos=iniciaRealm();
        ArrayList<PartidosPojo> partido_jornada = new ArrayList<PartidosPojo>();

        if(!servicioPartidos.tienedatos() || Temporada.equals("Temporada2020")) {
            BDCall(servicioPartidos,new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                    String dato1 = "Jornada 1";
                    ArrayList<PartidosPojo> partido_jornada = new ArrayList<PartidosPojo>();
                    for (int j = 0; j < partidos.size(); j++) {
                        if (partidos.get(j).getPartidos_jornada().equals(dato1)) {
                            partido_jornada.add(partidos.get(j));
                        }
                    }
                    CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_jornada);
                    customAdapter.notifyDataSetChanged();
                    simpleList.setAdapter(customAdapter);
                }

                @Override
                public void onStart() {

                }

                @Override
                public void onFailure() {

                }

                @Override
                public void onFinish(int numero) {
                }
            });
            for (int j = 0; j < partidos.size(); j++) {
                if (partidos.get(j).getPartidos_jornada().equals(dato)) {
                    partido_jornada.add(partidos.get(j));
                }
            }
        }else {
            lista_partidos=servicioPartidos.ObetenerPartidos();
            for (int j=0;j<lista_partidos.length;j++){
                if(lista_partidos[j].getPartidos_jornada().equals(dato)){
                    PartidosPojo nuevo=new PartidosPojo(lista_partidos[j].getPartidos_fecha(),
                            lista_partidos[j].getPartidos_jornada(),
                            lista_partidos[j].getPartidos_equipo_local(),
                            lista_partidos[j].getPartidos_resultado(),
                            lista_partidos[j].getPartidos_equipo_visitante(),
                            lista_partidos[j].getPartidos_foto_local(),
                            lista_partidos[j].getPartidos_foto_visitante(),
                            lista_partidos[j].getPartidos_temporada());
                    partido_jornada.add(nuevo);
                }
            }
        }

        CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_jornada);
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

       simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String parts[] = Jornada.split(" ");
                int jor=Integer.parseInt(parts[1]);
                jor=jor-1;
                if(Liga.equals("Bundesliga")){
                    jor=jor*9;
                }else {
                    jor = jor * 10;
                }
                int posicion=position+jor;
                Pattern patron= Pattern.compile("[0-9]{2}:[0-9]{2}");
                try {
                    Matcher mat=patron.matcher(partidos.get(posicion).getPartidos_resultado());
                    if (partidos.get(posicion).getPartidos_resultado().equals("Aplazado") ||
                            partidos.get(posicion).getPartidos_resultado().equals("x - x") || mat.matches()) {
                        Intent i = new Intent(PartidosActivity.this, PronosticoActivity.class);
                        i.putExtra("LIGA", Liga);
                        i.putExtra("EQUIPO LOCAL", partidos.get(posicion).getPartidos_equipo_local());
                        i.putExtra("EQUIPO VISITANTE", partidos.get(posicion).getPartidos_equipo_visitante());
                        i.putExtra("FOTO LOCAL", partidos.get(posicion).getPartidos_foto_local());
                        i.putExtra("FOTO VISITANTE", partidos.get(posicion).getPartidos_foto_visitante());
                        i.putExtra("RESULTADO", partidos.get(posicion).getPartidos_resultado());
                        i.putExtra("TEMPORADA", Temporada);
                        i.putExtra("JORNADA", partidos.get(posicion).getPartidos_jornada());
                        //i.putExtra("ALINEACION",alineacion);
                        Toast.makeText(PartidosActivity.this, "Has seleccionado temporada " + partidos.get(posicion).getPartidos_equipo_local() +
                                " contra " + partidos.get(posicion).getPartidos_equipo_visitante(), Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    } else {
                        Intent i = new Intent(PartidosActivity.this, AlineacionActivity.class);
                        i.putExtra("LIGA", Liga);
                        i.putExtra("EQUIPO LOCAL", partidos.get(posicion).getPartidos_equipo_local());
                        i.putExtra("EQUIPO VISITANTE", partidos.get(posicion).getPartidos_equipo_visitante());
                        i.putExtra("FOTO LOCAL", partidos.get(posicion).getPartidos_foto_local());
                        i.putExtra("FOTO VISITANTE", partidos.get(posicion).getPartidos_foto_visitante());
                        i.putExtra("RESULTADO", partidos.get(posicion).getPartidos_resultado());
                        i.putExtra("TEMPORADA", Temporada);
                        i.putExtra("JORNADA", partidos.get(posicion).getPartidos_jornada());
                        //i.putExtra("ALINEACION",alineacion);
                        Toast.makeText(PartidosActivity.this, "Has seleccionado temporada " + partidos.get(posicion).getPartidos_equipo_local() +
                                " contra " + partidos.get(posicion).getPartidos_equipo_visitante(), Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                } catch (Exception E) {
                    Matcher mat=patron.matcher(lista_partidos[posicion].getPartidos_resultado());
                    if (lista_partidos[posicion].getPartidos_resultado().equals("Aplazado") ||
                            lista_partidos[posicion].getPartidos_resultado().equals("x - x") || mat.matches()) {
                        Intent i = new Intent(PartidosActivity.this, PronosticoActivity.class);
                        i.putExtra("LIGA", Liga);
                        i.putExtra("EQUIPO LOCAL", lista_partidos[posicion].getPartidos_equipo_local());
                        i.putExtra("EQUIPO VISITANTE", lista_partidos[posicion].getPartidos_equipo_visitante());
                        i.putExtra("FOTO LOCAL", lista_partidos[posicion].getPartidos_foto_local());
                        i.putExtra("FOTO VISITANTE", lista_partidos[posicion].getPartidos_foto_visitante());
                        i.putExtra("RESULTADO", lista_partidos[posicion].getPartidos_resultado());
                        i.putExtra("TEMPORADA", Temporada);
                        i.putExtra("JORNADA", lista_partidos[posicion].getPartidos_jornada());
                        //i.putExtra("ALINEACION",alineacion);
                        Toast.makeText(PartidosActivity.this, "Has seleccionado temporada " + lista_partidos[posicion].getPartidos_equipo_local() +
                                " contra " + lista_partidos[posicion].getPartidos_equipo_visitante(), Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    } else {
                        Intent i = new Intent(PartidosActivity.this, AlineacionActivity.class);
                        i.putExtra("LIGA", Liga);
                        i.putExtra("EQUIPO LOCAL", lista_partidos[posicion].getPartidos_equipo_local());
                        i.putExtra("EQUIPO VISITANTE", lista_partidos[posicion].getPartidos_equipo_visitante());
                        i.putExtra("FOTO LOCAL", lista_partidos[posicion].getPartidos_foto_local());
                        i.putExtra("FOTO VISITANTE", lista_partidos[posicion].getPartidos_foto_visitante());
                        i.putExtra("RESULTADO", lista_partidos[posicion].getPartidos_resultado());
                        i.putExtra("TEMPORADA", Temporada);
                        i.putExtra("JORNADA", lista_partidos[posicion].getPartidos_jornada());
                        //i.putExtra("ALINEACION",alineacion);
                        Toast.makeText(PartidosActivity.this, "Has seleccionado temporada " + lista_partidos[posicion].getPartidos_equipo_local() +
                                " contra " + lista_partidos[posicion].getPartidos_equipo_visitante(), Toast.LENGTH_SHORT).show();
                        startActivity(i);
                    }
                }
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
     * Método de llamada a base de datos para la obtención de los datos de los partidos
     * @param servicioPartidos
     * @param listener
     */
    public void BDCall(final ServicioPartidos servicioPartidos, final OnGetDataListener listener){
        mRootReference.child(NombreBD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mRootReference.child(NombreBD).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            PartidosPojo partido = snapshot.getValue(PartidosPojo.class);
                            if(partido.getPartidos_temporada().equals(Temporada)) {
                                try {
                                    servicioPartidos.ActualizaPartidos(partido,Liga);
                                }catch (Exception e){

                                }
                                Log.e("FECHA",partido.getPartidos_fecha());
                                Log.e("LOCAL", partido.getPartidos_equipo_local());
                                Log.e("VISITANTE",partido.getPartidos_equipo_visitante());
                                partidos.add(partido);
                                listener.onSuccess(dataSnapshot);
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
     * Método para filtrar por jornada los partidos
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String text= parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(),text,Toast.LENGTH_SHORT);

        Jornada=text;
        final ServicioPartidos servicioPartidos=iniciaRealm();
        ArrayList<PartidosPojo> partido_jornada = new ArrayList<PartidosPojo>();

        if(!servicioPartidos.tienedatos() || Temporada.equals("Temporada2020")) {

            boolean entra=true;
            if (Liga.equals("LaLiga Santander") && GlobalInfo.get_partlaliga()==1) {
                entra=false;
            } else if (Liga.equals("Premier League")&& GlobalInfo.get_partpremier()==1) {
                entra=false;
            } else if (Liga.equals("Bundesliga") && GlobalInfo.get_partbundesliga()==1) {
                entra=false;
            } else if (Liga.equals("Serie A") && GlobalInfo.get_partseriea()==1) {
                entra=false;
            }

            if (Temporada.equals("Temporada2020") && entra){
                if (Liga.equals("LaLiga Santander")) {
                    GlobalInfo.set_partlaliga();
                } else if (Liga.equals("Premier League")) {
                    GlobalInfo.set_partpremier();
                } else if (Liga.equals("Bundesliga")) {
                    GlobalInfo.set_partbundesliga();
                } else if (Liga.equals("Serie A")) {
                    GlobalInfo.set_partseriea();
                }
                BDCall(servicioPartidos, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        String dato1 = "Jornada 1";
                        ArrayList<PartidosPojo> partido_jornada = new ArrayList<PartidosPojo>();
                        for (int j = 0; j < partidos.size(); j++) {
                            if (partidos.get(j).getPartidos_jornada().equals(dato1)) {
                                partido_jornada.add(partidos.get(j));
                            }
                        }
                        CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_jornada);
                        customAdapter.notifyDataSetChanged();
                        simpleList.setAdapter(customAdapter);
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onFinish(int numero) {
                    }
                });
                for (int j = 0; j < partidos.size(); j++) {
                    if (partidos.get(j).getPartidos_jornada().equals(text)) {
                        partido_jornada.add(partidos.get(j));
                    }
                }
            }else if(!entra && Temporada.equals("Temporada2020")){
                lista_partidos=servicioPartidos.ObetenerPartidos();
                for (int j=0;j<lista_partidos.length;j++){
                    if(lista_partidos[j].getPartidos_jornada().equals(text)){
                        Log.e("Realm ",lista_partidos[j].getId());
                        PartidosPojo nuevo=new PartidosPojo(lista_partidos[j].getPartidos_fecha(),
                                lista_partidos[j].getPartidos_jornada(),
                                lista_partidos[j].getPartidos_equipo_local(),
                                lista_partidos[j].getPartidos_resultado(),
                                lista_partidos[j].getPartidos_equipo_visitante(),
                                lista_partidos[j].getPartidos_foto_local(),
                                lista_partidos[j].getPartidos_foto_visitante(),
                                lista_partidos[j].getPartidos_temporada());
                        partido_jornada.add(nuevo);
                    }
                }
            }else if (partidos.isEmpty() && !Temporada.equals("Temporada2020")) {
                BDCall(servicioPartidos, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        String dato1 = "Jornada 1";
                        ArrayList<PartidosPojo> partido_jornada = new ArrayList<PartidosPojo>();
                        for (int j = 0; j < partidos.size(); j++) {
                            if (partidos.get(j).getPartidos_jornada().equals(dato1)) {
                                partido_jornada.add(partidos.get(j));
                            }
                        }
                        CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_jornada);
                        customAdapter.notifyDataSetChanged();
                        simpleList.setAdapter(customAdapter);
                    }

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onFinish(int numero) {
                    }
                });
                for (int j = 0; j < partidos.size(); j++) {
                    if (partidos.get(j).getPartidos_jornada().equals(text)) {
                        partido_jornada.add(partidos.get(j));
                    }
                }
            }else {
                for (int j = 0; j < partidos.size(); j++) {
                    if (partidos.get(j).getPartidos_jornada().equals(text)) {
                        partido_jornada.add(partidos.get(j));
                    }
                }
            }
        }else {
            lista_partidos=servicioPartidos.ObetenerPartidos();
            for (int j=0;j<lista_partidos.length;j++){
                if(lista_partidos[j].getPartidos_jornada().equals(text)){
                    Log.e("Realm ",lista_partidos[j].getId());
                    PartidosPojo nuevo=new PartidosPojo(lista_partidos[j].getPartidos_fecha(),
                            lista_partidos[j].getPartidos_jornada(),
                            lista_partidos[j].getPartidos_equipo_local(),
                            lista_partidos[j].getPartidos_resultado(),
                            lista_partidos[j].getPartidos_equipo_visitante(),
                            lista_partidos[j].getPartidos_foto_local(),
                            lista_partidos[j].getPartidos_foto_visitante(),
                            lista_partidos[j].getPartidos_temporada());
                    partido_jornada.add(nuevo);
                }
            }
        }

        CustomAdapterPartido customAdapter = new CustomAdapterPartido(getApplicationContext(), partido_jornada);
        customAdapter.notifyDataSetChanged();
        simpleList.setAdapter(customAdapter);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    /**
     * Método para la inicialización de las instancias de la base de datos Realm
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
