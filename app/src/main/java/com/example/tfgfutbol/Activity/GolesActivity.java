package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tfgfutbol.Firebase.OnGetDataListener;
import com.example.tfgfutbol.ObjetoEquipoGoles;
import com.example.tfgfutbol.Pojo.AlineacionPojo;
import com.example.tfgfutbol.Pojo.EquipoPojo;
import com.example.tfgfutbol.R;
import com.example.tfgfutbol.Realm.Alineacion;
import com.example.tfgfutbol.Realm.ServicioAlineacion;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class GolesActivity extends AppCompatActivity {

    private Toolbar inferior;
   private BarChart barChart;
   private PieChart pieChart;

   private String Liga;
   private String Temporada;

   private Alineacion[]alineaciones;

   private ArrayList<ObjetoEquipoGoles> equipos;
   private ArrayList<String> jugadores;
   private int[] colors;
   private int[] goles_jugador;

    DatabaseReference mRootReference;
    private String NombreBD="";

    TextView nombre_liga;
    ImageView foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goles_layout);

        barChart=(BarChart) findViewById(R.id.barchartgol);
        //pieChart=(PieChart) findViewById(R.id.piechartgol);

        mRootReference= FirebaseDatabase.getInstance().getReference();
        mRootReference.keepSynced(true);

        equipos=new ArrayList<>();
        jugadores=new ArrayList<>();



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

        Intent i = getIntent();
        Liga=i.getStringExtra("LIGA");
        Temporada=i.getStringExtra("TEMPORADA");

        nombre_liga=(TextView)findViewById(R.id.txtdashboard);
        nombre_liga.setText(Liga);

        foto=(ImageView)findViewById(R.id.picturedashboard);
        if(Liga.equals("LaLiga Santander")) {
            foto.setImageResource(R.drawable.dashboard_laliga);
        }else if(Liga.equals("Premier League")) {
            foto.setImageResource(R.drawable.dashboard_premier);
        }else if(Liga.equals("Bundesliga")) {
            foto.setImageResource(R.drawable.dashboard_bundesliga);
        }else{
            foto.setImageResource(R.drawable.dashboard_seriea);
        }

        if(Liga.equals("LaLiga Santander")) {
            //goles_equipos=new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            colors= new int[]{Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,
                    Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,
                    Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN};
            NombreBD="EQUIPOS";
        }else if(Liga.equals("Premier League")) {
            //goles_equipos=new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            colors= new int[]{Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,
                    Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,
                    Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN};
            NombreBD="EQUIPOS_PREMIER";
        }else if(Liga.equals("Bundesliga")) {
            //goles_equipos=new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            colors= new int[]{Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,
                    Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,
                    Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN};
            NombreBD="EQUIPOS_BUNDESLIGA";
        }else if(Liga.equals("Serie A")){
            //goles_equipos=new int[]{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
            colors= new int[]{Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,
                    Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,
                    Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN,Color.BLUE, Color.GREEN};
            NombreBD="EQUIPOS_SERIEA";
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

        alineaciones=servicioAlineacion.ObetenerAlineacion();
        inferior = findViewById(R.id.inferior);
        setSupportActionBar(inferior);

        BDCall(new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                for (int i=0;i<alineaciones.length;i++){
                    Alineacion jugador=alineaciones[i];
                    if (jugador.getAlineacion_gol()!="0"){
                        for (int j=0;j<equipos.size();j++){
                            if(jugador.getAlineacion_equipo().equals(equipos.get(j).getEquipo())){
                                int gol=Integer.parseInt(jugador.getAlineacion_gol());
                                equipos.get(j).sumaGoles(gol);
                            }
                        }
                    }
                }

                createCharts();
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

    }

    private Chart getSameChart(Chart chart, String description, int textColor, int background, int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setBackgroundColor(Color.WHITE);
        //chart.setBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }

    private void legend(Chart chart){
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries=new ArrayList<>();
        for(int i=0;i<equipos.size();i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor=colors[i];
            entry.label=equipos.get(i).getEquipo();
            entries.add(entry);
        }
        legend.setCustom(entries);
    }

    private ArrayList<BarEntry> getBarEntries(){
        ArrayList<BarEntry> entries=new ArrayList<>();
        for(int i=0;i<equipos.size();i++){
            entries.add(new BarEntry(i,equipos.get(i).getGoles()));
        }
        return entries;
    }
    private ArrayList<PieEntry> getPieEntries(){
        ArrayList<PieEntry> entries=new ArrayList<>();
        for(int i=0;i<equipos.size();i++){
            entries.add(new PieEntry(equipos.get(i).getGoles()));
        }
        return entries;
    }

    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(listaequipos()));
        axis.setLabelRotationAngle(90);
    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }

    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }

    public void createCharts(){
        barChart=(BarChart) getSameChart(barChart, "Equipos", Color.RED, Color.CYAN,3000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());
        barChart.invalidate();

        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
        /*
        pieChart=(PieChart) getSameChart(pieChart, "Jugadores", Color.GRAY, Color.WHITE, 3000);
        pieChart.setHoleRadius(10);
        pieChart.setTransparentCircleRadius(12);
        //pieChart.setDrawHoleEnabled(false);
        pieChart.setData(getPieData());
        pieChart.invalidate();
         */
    }

    private DataSet getData(DataSet dataSet){
        dataSet.setColors(colors);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }


    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet) getData(new BarDataSet(getBarEntries(),""));

        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.45f);

        return barData;
    }

    private PieData getPieData(){
        PieDataSet pieDataSet=(PieDataSet) getData(new PieDataSet(getPieEntries(),""));

        pieDataSet.setSliceSpace(2);
        pieDataSet.setValueFormatter(new PercentFormatter());


        return new PieData(pieDataSet);
    }

    public Void BDCall(final OnGetDataListener listener){

        listener.onStart();
        mRootReference.child(NombreBD).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                EquipoPojo equipo = dataSnapshot.getValue(EquipoPojo.class);
                Log.e("NOMBRE EQUIPO",equipo.getEquipos_name());
                if(equipo.getEquipos_temporada().equals(Temporada)) {
                    ObjetoEquipoGoles o=new ObjetoEquipoGoles(equipo.getEquipos_name());
                    equipos.add(o);
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

        mRootReference.child(NombreBD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mRootReference.removeEventListener(this);
                listener.onSuccess(dataSnapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return null;
    }

    public ArrayList<String> listaequipos(){
        ArrayList<String> lista=new ArrayList<>();
        for(int i=0;i<equipos.size();i++){
            lista.add(equipos.get(i).getEquipo());
        }
        return lista;
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
                i = new Intent(this, LigaActivity.class);
                i.putExtra("LIGA", Liga);
                startActivity(i);
                break;
            case R.id.boton_dashboard:
                i = new Intent(this, DashboardActivity.class);
                Log.e("Has seleccionado","dashboard");
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.boton_clasificacion:
                i = new Intent(this, ClasificacionActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.boton_equipos:
                i = new Intent(this, SelectEquiposActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.boton_partidos:
                i = new Intent(this, PartidosActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                i.putExtra("JORNADA", "Jornada 1");
                //i.putExtra("ALINEACION",alineacion);
                startActivity(i);
                break;
            case R.id.boton_arbitros:
                i = new Intent(this, ArbitrosActivity.class);
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
}
