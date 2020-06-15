package com.example.tfgfutbol.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tfgfutbol.GlobalInfo;
import com.example.tfgfutbol.Pojo.AlineacionPojo;
import com.example.tfgfutbol.Firebase.OnGetDataListener;
import com.example.tfgfutbol.R;
import com.example.tfgfutbol.Realm.ServicioAlineacion;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class CargarActivity extends AppCompatActivity {

    private Toolbar inferior;
    private String Liga;
    private String Temporada;
    private String NombreBD="";
    private String NombreBD2="";
    TextView nombre_liga;
    TextView nombre_temporada;
    DatabaseReference mRootReference1;
    ArrayList<String> elementos;
    ArrayList<AlineacionPojo>alineacion;
    //ProgressBar barra;


    private ProgressDialog progressDialog;
    CountDownTimer contador;
    int p=0;


    /**
     * Método de creación de la vista
     * Inicialización de instancias de bases de datos Realm
     * Llamada a nueva vista tras carga de datos
     * @param savedInstanceState
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cargar_layout);

        Intent i = getIntent();
        Liga = i.getStringExtra("LIGA");
        Temporada = i.getStringExtra("TEMPORADA");

        progressDialog=new ProgressDialog(CargarActivity.this);
        progressDialog.setMessage("Cargando datos...");
        progressDialog.setCancelable(false);
        progressDialog.setProgress(p);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.GRAY));

        //Log.e("LIGA EN CLASIFICACION", Liga);

        alineacion = new ArrayList<AlineacionPojo>();

        nombre_liga = (TextView) findViewById(R.id.nombre_liga);
        nombre_liga.setText(Liga);
        nombre_temporada=(TextView) findViewById(R.id.nombre_temporada);
        nombre_temporada.setText(Temporada);

        //barra= (ProgressBar) findViewById(R.id.progressBar2);
        mRootReference1= FirebaseDatabase.getInstance("https://tfg-futbol-alineacion.firebaseio.com/").getReference();

        alineacion = new ArrayList<AlineacionPojo>();

        if (Liga.equals("LaLiga Santander")) {
            NombreBD2 = "clasificacion_laliga";
        } else if (Liga.equals("Premier League")) {
            NombreBD2 = "clasificacion_premier";
        } else if (Liga.equals("Bundesliga")) {
            NombreBD2 = "clasificacion_bundesliga";
        } else if (Liga.equals("Serie A")) {
            NombreBD2 = "clasificacion_seriea";
        }


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
        }else if(Liga.equals("Serie A")){
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
        Realm.init(this);

        final RealmConfiguration realmConfigurationLiga= new RealmConfiguration.Builder()
                .name("AlineacionLiga")
                .schemaVersion(2)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationPremier= new RealmConfiguration.Builder()
                .name("AlineacionPremier")
                .schemaVersion(3)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationBundesliga= new RealmConfiguration.Builder()
                .name("AlineacionBundesliga")
                .schemaVersion(4)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationSeriea= new RealmConfiguration.Builder()
                .name("AlineacionSeriea")
                .schemaVersion(5)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationLiga19= new RealmConfiguration.Builder()
                .name("AlineacionLiga19")
                .schemaVersion(6)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationPremier19= new RealmConfiguration.Builder()
                .name("AlineacionPremier19")
                .schemaVersion(7)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationBundesliga19= new RealmConfiguration.Builder()
                .name("AlineacionBundesliga19")
                .schemaVersion(8)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationSeriea19= new RealmConfiguration.Builder()
                .name("AlineacionSeriea19")
                .schemaVersion(9)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationLiga18= new RealmConfiguration.Builder()
                .name("AlineacionLiga18")
                .schemaVersion(10)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationPremier18= new RealmConfiguration.Builder()
                .name("AlineacionPremier18")
                .schemaVersion(11)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationBundesliga18= new RealmConfiguration.Builder()
                .name("AlineacionBundesliga18")
                .schemaVersion(12)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationSeriea18= new RealmConfiguration.Builder()
                .name("AlineacionSeriea18")
                .schemaVersion(13)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationLiga17= new RealmConfiguration.Builder()
                .name("AlineacionLiga17")
                .schemaVersion(14)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationPremier17= new RealmConfiguration.Builder()
                .name("AlineacionPremier17")
                .schemaVersion(15)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationBundesliga17= new RealmConfiguration.Builder()
                .name("AlineacionBundesliga17")
                .schemaVersion(16)
                .deleteRealmIfMigrationNeeded()
                .build();

        final RealmConfiguration realmConfigurationSeriea17= new RealmConfiguration.Builder()
                .name("AlineacionSeriea17")
                .schemaVersion(17)
                .deleteRealmIfMigrationNeeded()
                .build();

        /*
        Realm.deleteRealm(realmConfigurationLiga);
        Realm.deleteRealm(realmConfigurationLiga17);
        Realm.deleteRealm(realmConfigurationLiga18);
        Realm.deleteRealm(realmConfigurationLiga19);
        Realm.deleteRealm(realmConfigurationPremier);
        Realm.deleteRealm(realmConfigurationPremier17);
        Realm.deleteRealm(realmConfigurationPremier18);
        Realm.deleteRealm(realmConfigurationPremier19);
        Realm.deleteRealm(realmConfigurationBundesliga);
        Realm.deleteRealm(realmConfigurationBundesliga17);
        Realm.deleteRealm(realmConfigurationBundesliga18);
        Realm.deleteRealm(realmConfigurationBundesliga19);
        Realm.deleteRealm(realmConfigurationSeriea);
        Realm.deleteRealm(realmConfigurationSeriea17);
        Realm.deleteRealm(realmConfigurationSeriea18);
        Realm.deleteRealm(realmConfigurationSeriea19);
        */

        ServicioAlineacion servicioAlineacion=null;

        if(Liga.equals("LaLiga Santander")){
            if(Temporada.equals("Temporada2020")){
                final Realm myRealmLiga = Realm.getInstance(realmConfigurationLiga);
                servicioAlineacion= new ServicioAlineacion(myRealmLiga);
            }else if (Temporada.equals("Temporada2019")){
                final Realm myRealmLiga19 = Realm.getInstance(realmConfigurationLiga19);
                servicioAlineacion= new ServicioAlineacion(myRealmLiga19);
            }else if (Temporada.equals("Temporada2018")){
                final Realm myRealmLiga18 = Realm.getInstance(realmConfigurationLiga18);
                servicioAlineacion= new ServicioAlineacion(myRealmLiga18);
            }else {
                final Realm myRealmLiga17 = Realm.getInstance(realmConfigurationLiga17);
                servicioAlineacion= new ServicioAlineacion(myRealmLiga17);
            }
        }else if(Liga.equals("Premier League")){
            if(Temporada.equals("Temporada2020")){
                final Realm myRealmPremier = Realm.getInstance(realmConfigurationPremier);
                servicioAlineacion= new ServicioAlineacion(myRealmPremier);
            }else if (Temporada.equals("Temporada2019")){
                final Realm myRealmPremier19 = Realm.getInstance(realmConfigurationPremier19);
                servicioAlineacion= new ServicioAlineacion(myRealmPremier19);
            }else if (Temporada.equals("Temporada2018")){
                final Realm myRealmPremier18 = Realm.getInstance(realmConfigurationPremier18);
                servicioAlineacion= new ServicioAlineacion(myRealmPremier18);
            }else {
                final Realm myRealmPremier17 = Realm.getInstance(realmConfigurationPremier17);
                servicioAlineacion= new ServicioAlineacion(myRealmPremier17);
            }
        }else if(Liga.equals("Bundesliga")){
            if(Temporada.equals("Temporada2020")){
                final Realm myRealmBundesliga = Realm.getInstance(realmConfigurationBundesliga);
                servicioAlineacion= new ServicioAlineacion(myRealmBundesliga);
            }else if (Temporada.equals("Temporada2019")){
                final Realm myRealmBundesliga19 = Realm.getInstance(realmConfigurationBundesliga19);
                servicioAlineacion= new ServicioAlineacion(myRealmBundesliga19);
            }else if (Temporada.equals("Temporada2018")){
                final Realm myRealmBundesliga18 = Realm.getInstance(realmConfigurationBundesliga18);
                servicioAlineacion= new ServicioAlineacion(myRealmBundesliga18);
            }else {
                final Realm myRealmBundesliga17 = Realm.getInstance(realmConfigurationBundesliga17);
                servicioAlineacion= new ServicioAlineacion(myRealmBundesliga17);
            }

        }else if(Liga.equals("Serie A")){
            if(Temporada.equals("Temporada2020")){
                final Realm myRealmSerieA = Realm.getInstance(realmConfigurationSeriea);
                servicioAlineacion= new ServicioAlineacion(myRealmSerieA);
            }else if (Temporada.equals("Temporada2019")){
                final Realm myRealmSerieA19 = Realm.getInstance(realmConfigurationSeriea19);
                servicioAlineacion= new ServicioAlineacion(myRealmSerieA19);
            }else if (Temporada.equals("Temporada2018")){
                final Realm myRealmSerieA18 = Realm.getInstance(realmConfigurationSeriea18);
                servicioAlineacion= new ServicioAlineacion(myRealmSerieA18);
            }else {
                final Realm myRealmSerieA17 = Realm.getInstance(realmConfigurationSeriea17);
                servicioAlineacion= new ServicioAlineacion(myRealmSerieA17);
            }
        }



        final ServicioAlineacion servicio=servicioAlineacion;

        /*if(!servicioAlineacion.tienedatos() || Temporada.equals("Temporada2020")) {
            BDCallAlineacion(servicioAlineacion, new OnGetDataListener() {
                @Override
                public void onSuccess(DataSnapshot dataSnapshot) {
                }
                @Override
                public void onStart() {
                    //barra.setVisibility(View.VISIBLE);
                }
                @Override
                public void onFailure() {

                }
                @Override
                public void onFinish(long datos) {
                    //barra.setVisibility(View.INVISIBLE);
                    Intent k;
                    k = new Intent(CargarActivity.this, ClasificacionActivity.class);
                    k.putExtra("LIGA", Liga);
                    k.putExtra("TEMPORADA", Temporada);
                    k.putExtra("JORNADA", "Jornada 1");
                    //i.putExtra("ALINEACION",alineacion);
                    startActivity(k);
                }
            });
        }*/

        if(!servicioAlineacion.tienedatos() || Temporada.equals("Temporada2020")) {
            boolean entra=true;
            if(Liga.equals("LaLiga Santander") && Temporada.equals("Temporada2020")){
                if(GlobalInfo.get_laliga()==1){
                    entra=false;
                }
                GlobalInfo.set_laliga();
            }else if(Liga.equals("Premier League") && Temporada.equals("Temporada2020")){
                if(GlobalInfo.get_premier()==1){
                    entra=false;
                }
                GlobalInfo.set_premier();
            }else if(Liga.equals("Bundesliga") && Temporada.equals("Temporada2020")){
                if(GlobalInfo.get_bundesliga()==1){
                    entra=false;
                }
                GlobalInfo.set_bundesliga();
            }else if(Liga.equals("Serie A") && Temporada.equals("Temporada2020")){
                if(GlobalInfo.get_seriea()==1){
                    entra=false;
                }
                GlobalInfo.set_seriea();
            }

            if (entra) {
                BDCallAlineacion(servicioAlineacion, new OnGetDataListener() {
                    @Override
                    public void onSuccess(DataSnapshot dataSnapshot) {
                        progressDialog.dismiss();
                    /*
                    myRealmLiga.close();
                    myRealmPremier.close();
                    myRealmBundesliga.close();
                    myRealmSerieA.close();
                    myRealmLiga19.close();
                    myRealmPremier19.close();
                    myRealmBundesliga19.close();
                    myRealmSerieA19.close();
                    myRealmLiga18.close();
                    myRealmPremier18.close();
                    myRealmBundesliga18.close();
                    myRealmSerieA18.close();
                    myRealmLiga17.close();
                    myRealmPremier17.close();
                    myRealmBundesliga17.close();
                    myRealmSerieA17.close();
                    */
                        Intent k;
                        k = new Intent(CargarActivity.this, ClasificacionActivity.class);
                        k.putExtra("LIGA", Liga);
                        k.putExtra("TEMPORADA", Temporada);
                        k.putExtra("JORNADA", "Jornada 1");
                        //i.putExtra("ALINEACION",alineacion);
                        startActivity(k);
                    }

                    @Override
                    public void onStart() {
                        progressDialog.show();
                        progressDialog.setMessage("Espere unos segundos");
                    }

                    @Override
                    public void onFailure() {

                    }

                    @Override
                    public void onFinish(int numero) {
                    }
                });
            }else{
                Intent k;
                k = new Intent(CargarActivity.this, ClasificacionActivity.class);
                k.putExtra("LIGA", Liga);
                k.putExtra("TEMPORADA", Temporada);
                k.putExtra("JORNADA", "Jornada 1");
                //i.putExtra("ALINEACION",alineacion);
                startActivity(k);
            }
        }else {
            /*
            myRealmLiga.close();
            myRealmPremier.close();
            myRealmBundesliga.close();
            myRealmSerieA.close();
            myRealmLiga19.close();
            myRealmPremier19.close();
            myRealmBundesliga19.close();
            myRealmSerieA19.close();
            myRealmLiga18.close();
            myRealmPremier18.close();
            myRealmBundesliga18.close();
            myRealmSerieA18.close();
            myRealmLiga17.close();
            myRealmPremier17.close();
            myRealmBundesliga17.close();
            myRealmSerieA17.close();

             */
            Intent k;
            k = new Intent(CargarActivity.this, ClasificacionActivity.class);
            k.putExtra("LIGA", Liga);
            k.putExtra("TEMPORADA", Temporada);
            k.putExtra("JORNADA", "Jornada 1");
            //i.putExtra("ALINEACION",alineacion);
            startActivity(k);
        }


            /*
            contador= new CountDownTimer(100000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    progressDialog.setMessage("Quedan "+millisUntilFinished/1000+" segundos");
                }

                @Override
                public void onFinish() {
                    progressDialog.dismiss();
                    myRealmLiga.close();
                    myRealmPremier.close();
                    myRealmBundesliga.close();
                    myRealmSerieA.close();
                    myRealmLiga19.close();
                    myRealmPremier19.close();
                    myRealmBundesliga19.close();
                    myRealmSerieA19.close();
                    myRealmLiga18.close();
                    myRealmPremier18.close();
                    myRealmBundesliga18.close();
                    myRealmSerieA18.close();
                    myRealmLiga17.close();
                    myRealmPremier17.close();
                    myRealmBundesliga17.close();
                    myRealmSerieA17.close();
                    Intent k;
                    k = new Intent(CargarActivity.this, ClasificacionActivity.class);
                    k.putExtra("LIGA", Liga);
                    k.putExtra("TEMPORADA", Temporada);
                    k.putExtra("JORNADA", "Jornada 1");
                    //i.putExtra("ALINEACION",alineacion);
                    startActivity(k);
                }
            }.start();
        }else {
            myRealmLiga.close();
            myRealmPremier.close();
            myRealmBundesliga.close();
            myRealmSerieA.close();
            myRealmLiga19.close();
            myRealmPremier19.close();
            myRealmBundesliga19.close();
            myRealmSerieA19.close();
            myRealmLiga18.close();
            myRealmPremier18.close();
            myRealmBundesliga18.close();
            myRealmSerieA18.close();
            myRealmLiga17.close();
            myRealmPremier17.close();
            myRealmBundesliga17.close();
            myRealmSerieA17.close();
            Intent k;
            k = new Intent(CargarActivity.this, ClasificacionActivity.class);
            k.putExtra("LIGA", Liga);
            k.putExtra("TEMPORADA", Temporada);
            k.putExtra("JORNADA", "Jornada 1");
            //i.putExtra("ALINEACION",alineacion);
            startActivity(k);
        }*/

    }


    /*

    public Void BDCallAlineacion(final ServicioAlineacion servicioAlineacion, final OnGetDataListener listener) {
        listener.onStart();
        mRootReference1.child(NombreBD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterator<DataSnapshot> it = dataSnapshot.getChildren().iterator();
                //for (final DataSnapshot snapshot : dataSnapshot.getChildren()) {
                while(it.hasNext()){
                    final DataSnapshot snapshot=it.next();
                    //Log.e("NUMERO DATOS", ""+dataSnapshot.getChildrenCount());
                    mRootReference1.child(NombreBD).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            AlineacionPojo jugador = snapshot.getValue(AlineacionPojo.class);
                            listener.onSuccess(snapshot);
                            //listener.onFinish(dataSnapshot.getChildrenCount());
                            try {
                                Log.e("Guarda",jugador.getAlineacion_jugador());
                                servicioAlineacion.ActualizaAlineacion(jugador, Liga);
                            } catch (Exception e) {
                                Log.e("YA REGISTRADO",jugador.getAlineacion_jugador());
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
        return null;
    }*/

    /**
     * Método encargado de descargar datos de base de datos Firebase
     * Los datos descargados son las Alineaciones de la liga y temporada seleccionada
     * @param servicioAlineacion
     * @param listener
     * @return null
     */
    public Void BDCallAlineacion(final ServicioAlineacion servicioAlineacion, final OnGetDataListener listener) {
        listener.onStart();
        mRootReference1.child(NombreBD).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                AlineacionPojo jugador = dataSnapshot.getValue(AlineacionPojo.class);
                try {
                    Log.e("Guarda",jugador.getAlineacion_jugador());
                    servicioAlineacion.ActualizaAlineacion(jugador, Liga);
                } catch (Exception e) {
                    Log.e("YA REGISTRADO",jugador.getAlineacion_jugador());
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mRootReference1.child(NombreBD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRootReference1.removeEventListener(this);
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }


}
