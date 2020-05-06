package com.example.tfgfutbol.Activity;

import android.content.Intent;
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
import com.example.tfgfutbol.Layout.ImageLoadTask;
import com.example.tfgfutbol.Pojo.AlineacionPojo;
import com.example.tfgfutbol.Pojo.GolesEstPojo;
import com.example.tfgfutbol.Pojo.PartidosPojo;
import com.example.tfgfutbol.Pojo.ResultadosEstPojo;
import com.example.tfgfutbol.R;
import com.example.tfgfutbol.Realm.ServicioAlineacion;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import org.apache.commons.math3.distribution.NormalDistribution;
import org.apache.commons.math3.distribution.PoissonDistribution;

import java.util.ArrayList;

public class PronosticoActivity extends AppCompatActivity {

    private Toolbar toolbar;
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

    private String NombreBD="";
    private String NombreBD1="";
    DatabaseReference mRootReference;

    private ArrayList<GolesEstPojo> estadistica_goles_local;
    private ArrayList<GolesEstPojo> estadistica_goles_visitante;

    private ArrayList<ResultadosEstPojo> estadistica_resultado_local;
    private ArrayList<ResultadosEstPojo> estadistica_resultado_visitante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pronostico_layout);

        estadistica_goles_local=new ArrayList<>();
        estadistica_goles_visitante= new ArrayList<>();

        estadistica_resultado_local=new ArrayList<>();
        estadistica_resultado_visitante= new ArrayList<>();

        Intent i = getIntent();
        Liga=i.getStringExtra("LIGA");
        Log.e("Liga",Liga);
        Temporada=i.getStringExtra("TEMPORADA");
        Log.e("Temporada", Temporada);
        Local=i.getStringExtra("EQUIPO LOCAL");
        Log.e("Local",Local);
        Visitante=i.getStringExtra("EQUIPO VISITANTE");
        Log.e("Visitante",Visitante);
        String Escudo_local=i.getStringExtra("FOTO LOCAL");
        String Escudo_visitante=i.getStringExtra("FOTO VISITANTE");
        resultado=i.getStringExtra("RESULTADO");
        jornada=i.getStringExtra("JORNADA");

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

        toolbar=findViewById(R.id.toolbar);
        toolbar.bringToFront();
        setSupportActionBar(toolbar);

        mRootReference= FirebaseDatabase.getInstance("https://tfg-futbol-estadisticas.firebaseio.com/").getReference();
        String[] split=jornada.split(" ");
        int jor_ant=Integer.parseInt(split[1]);
        jor_ant=jor_ant-1;
        final String jornada_pasada="Jornada "+jor_ant;

        NombreBD="goles";
        BDCallEstadisticas(new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                Log.e("acaba ", "acaba base de datos");
                GolesEstPojo datos_local=new GolesEstPojo();
                GolesEstPojo datos_visitante=new GolesEstPojo();

                double sum_local=0;

                for(int i=0;i<estadistica_goles_local.size();i++){
                    if(estadistica_goles_local.get(i).getJornada().equals(jornada_pasada)){
                        datos_local=estadistica_goles_local.get(i);
                    }
                    sum_local=sum_local+Double.parseDouble(estadistica_goles_local.get(i).getY_lambda().replace(",","."));
                }
                double sum_visitante=0;

                for(int i=0;i<estadistica_goles_visitante.size();i++){
                    if(estadistica_goles_visitante.get(i).getJornada().equals(jornada_pasada)) {
                        datos_visitante = estadistica_goles_visitante.get(i);
                    }
                    sum_visitante=sum_visitante+Double.parseDouble(estadistica_goles_visitante.get(i).getY_lambda().replace(",","."));
                }

                ArrayList<Double> prob_gol_local= new ArrayList<>();
                ArrayList<Double> prob_gol_visitante= new ArrayList<>();

                //PRONÓSTICOS GOLES RESPECTO JORNADA PASADA
                for (int j=0;j<9;j++){
                    Log.e("entra","bucle para datos");
                    String prob1=datos_local.getY_lambda();
                    prob1=prob1.replace(",",".");
                    PoissonDistribution poissonDistribution_local= new PoissonDistribution(Double.parseDouble(prob1));
                    prob_gol_local.add(poissonDistribution_local.probability(j));
                    String prob2=datos_visitante.getY_lambda();
                    prob2=prob2.replace(",",".");
                    PoissonDistribution poissonDistribution_visitante= new PoissonDistribution(Double.parseDouble(prob2));
                    prob_gol_visitante.add(poissonDistribution_visitante.probability(j));
                    Log.e("prob local", j+" goles "+poissonDistribution_local.probability(j));
                    Log.e("prob visitante", j+" goles "+poissonDistribution_visitante.probability(j));
                }

                //PRONÓSTICO RESPECTO TODAS LAS JORNADAS
                sum_local=sum_local/estadistica_goles_local.size();
                sum_visitante=sum_visitante/estadistica_goles_visitante.size();

                ArrayList<Double> prob_gol_local_global= new ArrayList<>();
                ArrayList<Double> prob_gol_visitante_global= new ArrayList<>();
                for (int j=0;j<9;j++){
                    PoissonDistribution poissonDistribution_local= new PoissonDistribution(sum_local);
                    prob_gol_local_global.add(poissonDistribution_local.probability(j));
                    PoissonDistribution poissonDistribution_visitante= new PoissonDistribution(sum_visitante);
                    prob_gol_visitante_global.add(poissonDistribution_visitante.probability(j));
                    Log.e("GLOBAL LOCAL", j+" goles "+poissonDistribution_local.probability(j));
                    Log.e("GLOBAL VISITANTE", j+" goles "+poissonDistribution_visitante.probability(j));
                }

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

        NombreBD1="resultados";
        BDCallEstadisticasResultado(new OnGetDataListener() {
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                ResultadosEstPojo resultados_local=new ResultadosEstPojo();
                ResultadosEstPojo resultados_visitante=new ResultadosEstPojo();

                double sum_local_cut1=0;
                double sum_local_cut2=0;

                for(int i=0;i<estadistica_resultado_local.size();i++){
                    if(estadistica_resultado_local.get(i).getJornada().equals(jornada_pasada)){
                        resultados_local=estadistica_resultado_local.get(i);
                    }
                    double val1=Double.parseDouble(estadistica_resultado_local.get(i).getCut1_y().replace(",","."));
                    double val2=Double.parseDouble(estadistica_resultado_local.get(i).getCut2_y().replace(",","."));
                    if(val1<val2) {
                        sum_local_cut1 = sum_local_cut1 + Double.parseDouble(estadistica_resultado_local.get(i).getCut1_y().replace(",", "."));
                        sum_local_cut2 = sum_local_cut2 + Double.parseDouble(estadistica_resultado_local.get(i).getCut2_y().replace(",","."));
                    }else{
                        sum_local_cut2 = sum_local_cut2 + Double.parseDouble(estadistica_resultado_local.get(i).getCut1_y().replace(",", "."));
                        sum_local_cut1 = sum_local_cut1 + Double.parseDouble(estadistica_resultado_local.get(i).getCut2_y().replace(",","."));
                    }
                }

                double sum_visitante_cut1=0;
                double sum_visitante_cut2=0;

                for(int i=0;i<estadistica_resultado_visitante.size();i++){
                    if(estadistica_resultado_visitante.get(i).getJornada().equals(jornada_pasada)) {
                        resultados_visitante = estadistica_resultado_visitante.get(i);
                    }
                    double val1=Double.parseDouble(estadistica_resultado_visitante.get(i).getCut1_y().replace(",","."));
                    double val2=Double.parseDouble(estadistica_resultado_visitante.get(i).getCut2_y().replace(",","."));
                    if(val1<val2) {
                        sum_visitante_cut1 = sum_visitante_cut1 + Double.parseDouble(estadistica_resultado_visitante.get(i).getCut1_y().replace(",", "."));
                        sum_visitante_cut2 = sum_visitante_cut2 + Double.parseDouble(estadistica_resultado_visitante.get(i).getCut2_y().replace(",","."));
                    }else{
                        sum_visitante_cut2 = sum_visitante_cut2 + Double.parseDouble(estadistica_resultado_visitante.get(i).getCut1_y().replace(",", "."));
                        sum_visitante_cut1 = sum_visitante_cut1 + Double.parseDouble(estadistica_resultado_visitante.get(i).getCut2_y().replace(",","."));
                    }
                }

                double cut1_local=Double.parseDouble(resultados_local.getCut1_y().replace(",","."));
                double cut2_local=Double.parseDouble(resultados_local.getCut2_y().replace(",","."));
                double cut1_visitante=Double.parseDouble(resultados_visitante.getCut1_y().replace(",","."));
                double cut2_visitante=Double.parseDouble(resultados_visitante.getCut2_y().replace(",","."));
                NormalDistribution normalDistribution=new NormalDistribution();
                try {
                    double prob_derrota_local = normalDistribution.probability(Double.NEGATIVE_INFINITY, cut1_local);
                    double prob_empate_local = normalDistribution.probability(cut1_local, cut2_local);
                    double prob_ganar_local = normalDistribution.probability(cut2_local, Double.POSITIVE_INFINITY);
                    Log.e("RESULTADOS LOCAL: ", "DERROTA -> "+prob_derrota_local+" EMPATE->"+prob_empate_local+" GANAR->"+prob_ganar_local);
                }catch (Exception e){
                    double prob_derrota_local = normalDistribution.probability(Double.NEGATIVE_INFINITY, cut2_local);
                    double prob_empate_local = normalDistribution.probability(cut2_local, cut1_local);
                    double prob_ganar_local = normalDistribution.probability(cut1_local, Double.POSITIVE_INFINITY);
                    Log.e("RESULTADOS LOCAL: ", "DERROTA -> "+prob_derrota_local+" EMPATE->"+prob_empate_local+" GANAR->"+prob_ganar_local);
                }
                try {
                    double prob_derrota_visitante = normalDistribution.probability(Double.NEGATIVE_INFINITY, cut1_visitante);
                    double prob_empate_visitante = normalDistribution.probability(cut1_visitante, cut2_visitante);
                    double prob_ganar_visitante = normalDistribution.probability(cut2_visitante, Double.POSITIVE_INFINITY);
                    Log.e("RESULTADOS VISITANTE: ", "DERROTA -> "+prob_derrota_visitante+" EMPATE->"+prob_empate_visitante+" GANAR->"+prob_ganar_visitante);
                }catch (Exception e){
                    double prob_derrota_visitante = normalDistribution.probability(Double.NEGATIVE_INFINITY, cut2_visitante);
                    double prob_empate_visitante = normalDistribution.probability(cut2_visitante, cut1_visitante);
                    double prob_ganar_visitante = normalDistribution.probability(cut1_visitante, Double.POSITIVE_INFINITY);
                    Log.e("RESULTADOS VISITANTE: ", "DERROTA -> "+prob_derrota_visitante+" EMPATE->"+prob_empate_visitante+" GANAR->"+prob_ganar_visitante);
                }

                sum_local_cut1=sum_local_cut1/estadistica_resultado_local.size();
                sum_local_cut2=sum_local_cut2/estadistica_resultado_local.size();
                sum_visitante_cut1=sum_visitante_cut1/estadistica_resultado_visitante.size();
                sum_visitante_cut2=sum_visitante_cut2/estadistica_resultado_visitante.size();

                double derrota_local_global=normalDistribution.probability(Double.NEGATIVE_INFINITY,sum_local_cut1);
                double empate_local_global=normalDistribution.probability(sum_local_cut1,sum_local_cut2);
                double ganar_local_global=normalDistribution.probability(sum_local_cut2,Double.POSITIVE_INFINITY);
                double derrota_visitante_global=normalDistribution.probability(Double.NEGATIVE_INFINITY,sum_visitante_cut1);
                double empate_visitante_global=normalDistribution.probability(sum_visitante_cut1,sum_visitante_cut2);
                double ganar_visitante_global=normalDistribution.probability(sum_visitante_cut2,Double.POSITIVE_INFINITY);
                Log.e("RESULTADOSLOCALGLOBAL: ", "DERROTA -> "+derrota_local_global+" EMPATE->"+empate_local_global
                        +" GANAR->"+ganar_local_global);
                Log.e("RESULTADOSVISGLOBAL: ", "DERROTA -> "+derrota_visitante_global+" EMPATE->"+empate_visitante_global
                        +" GANAR->"+ganar_visitante_global);

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


    public Void BDCallEstadisticas(final OnGetDataListener listener) {
        Log.e("entra bd", "base de datos");
        mRootReference.child(NombreBD).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                GolesEstPojo est = dataSnapshot.getValue(GolesEstPojo.class);
                if (est.getTemporada().equals(Temporada) && est.getEquipo().equals(Local)) {
                    estadistica_goles_local.add(est);
                }
                if (est.getTemporada().equals(Temporada) && est.getEquipo().equals(Visitante)) {
                    estadistica_goles_visitante.add(est);
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

    public Void BDCallEstadisticasResultado(final OnGetDataListener listener) {
        Log.e("entra bd", "base de datos");
        mRootReference.child(NombreBD1).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                ResultadosEstPojo est = dataSnapshot.getValue(ResultadosEstPojo.class);
                if (est.getTemporada().equals(Temporada) && est.getEquipo().equals(Local)) {
                    estadistica_resultado_local.add(est);
                }
                if (est.getTemporada().equals(Temporada) && est.getEquipo().equals(Visitante)) {
                    estadistica_resultado_visitante.add(est);
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

        mRootReference.child(NombreBD1).addValueEventListener(new ValueEventListener() {
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
}



/*
                try {
                    GolesEstPojo est = dataSnapshot.getValue(GolesEstPojo.class);
                    if (est.getTemporada().equals(Temporada) && est.getEquipo().equals(Local)) {
                        estadistica_goles_local.add(est);
                    }
                    if (est.getTemporada().equals(Temporada) && est.getEquipo().equals(Visitante)) {
                        estadistica_goles_visitante.add(est);
                    }
                }catch (Exception e){
                    Log.e("error", "no se por que");
                }*/
