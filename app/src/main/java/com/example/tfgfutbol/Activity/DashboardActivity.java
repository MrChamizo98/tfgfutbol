package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.example.tfgfutbol.Layout.ImageLoadTask;
import com.example.tfgfutbol.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener{

    private String Liga;
    private String foto_liga;
    private Toolbar inferior;
    private String Temporada;

    TextView nombre_liga;
    ImageView foto;
    private CardView card_goles, card_amarillas, card_rojas, card_asistencias;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_layout);

        Intent i = getIntent();
        Liga = i.getStringExtra("LIGA");
        Temporada = i.getStringExtra("TEMPORADA");

        nombre_liga=(TextView)findViewById(R.id.txtdashboard);
        nombre_liga.setText("Dashboard");

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

        card_goles=(CardView) findViewById(R.id.card_goles);
        card_amarillas=(CardView) findViewById(R.id.card_amarillas);
        card_rojas=(CardView) findViewById(R.id.card_rojas);
        card_asistencias=(CardView) findViewById(R.id.card_asistencias);

        inferior = findViewById(R.id.inferior);
        setSupportActionBar(inferior);

        card_goles.setOnClickListener((View.OnClickListener) this);
        card_amarillas.setOnClickListener((View.OnClickListener) this);
        card_rojas.setOnClickListener((View.OnClickListener) this);
        card_asistencias.setOnClickListener((View.OnClickListener) this);

    }

    @Override
    public void onClick(View v){
        Intent i;
        switch (v.getId()){
            case R.id.card_goles:
                i = new Intent(DashboardActivity.this, GolesActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.card_amarillas:
                i = new Intent(DashboardActivity.this, AmarillasActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.card_rojas:
                i = new Intent(DashboardActivity.this, RojasActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.card_asistencias:
                i = new Intent(DashboardActivity.this, AsistenciasActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
        }

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
                i = new Intent(DashboardActivity.this, LigaActivity.class);
                i.putExtra("LIGA", Liga);
                startActivity(i);
                break;
            case R.id.boton_dashboard:
                i = new Intent(DashboardActivity.this, DashboardActivity.class);
                Log.e("Has seleccionado","dashboard");
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.boton_clasificacion:
                i = new Intent(DashboardActivity.this, ClasificacionActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.boton_equipos:
                i = new Intent(DashboardActivity.this, SelectEquiposActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                startActivity(i);
                break;
            case R.id.boton_partidos:
                i = new Intent(DashboardActivity.this, PartidosActivity.class);
                i.putExtra("LIGA", Liga);
                i.putExtra("TEMPORADA", Temporada);
                i.putExtra("JORNADA", "Jornada 1");
                //i.putExtra("ALINEACION",alineacion);
                startActivity(i);
                break;
            case R.id.boton_arbitros:
                i = new Intent(DashboardActivity.this, ArbitrosActivity.class);
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
