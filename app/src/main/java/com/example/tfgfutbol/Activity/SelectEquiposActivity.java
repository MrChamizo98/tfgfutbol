package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tfgfutbol.Adapter.CustomAdapterEquipo;
import com.example.tfgfutbol.Firebase.OnGetDataListener;
import com.example.tfgfutbol.Pojo.EquipoPojo;
import com.example.tfgfutbol.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SelectEquiposActivity extends AppCompatActivity{

    private Toolbar toolbar;
    ListView simpleList;
    DatabaseReference mRootReference;
    ArrayList<EquipoPojo> equipos;
    private String NombreBD="";
    private String Liga;
    private String Temporada;
    TextView nombre_liga;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectequipos_layout);

        mRootReference= FirebaseDatabase.getInstance().getReference();
        mRootReference.keepSynced(true);

        equipos=new ArrayList<EquipoPojo>();
        simpleList = (ListView) findViewById(R.id.listaEquipos);

        Intent i = getIntent();
        Liga=i.getStringExtra("LIGA");
        Temporada=i.getStringExtra("TEMPORADA");

        nombre_liga=(TextView)findViewById(R.id.nombre_liga_equipos);
        nombre_liga.setText(Liga);

        if(Liga.equals("LaLiga Santander")) {
            NombreBD="EQUIPOS";
        }else if(Liga.equals("Premier League")) {
            NombreBD="EQUIPOS_PREMIER";
        }else if(Liga.equals("Bundesliga")) {
            NombreBD="EQUIPOS_BUNDESLIGA";
        }else if(Liga.equals("Serie A")){
            NombreBD="EQUIPOS_SERIEA";
        }

        BDCall(new OnGetDataListener(){
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                CustomAdapterEquipo customAdapter = new CustomAdapterEquipo(getApplicationContext(), equipos);
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

        CustomAdapterEquipo customAdapter = new CustomAdapterEquipo(getApplicationContext(), equipos);
        simpleList.setAdapter(customAdapter);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayoutselectequipos);
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
                Toast.makeText(SelectEquiposActivity.this, "Has seleccionado "+equipos.get(position).getEquipos_name(),
                        Toast.LENGTH_SHORT).show();
                Intent i=new Intent(SelectEquiposActivity.this, PlantillaActivity.class);
                i.putExtra("LIGA",Liga);
                i.putExtra("EQUIPO",equipos.get(position).getEquipos_name());
                i.putExtra("FOTO",equipos.get(position).getEquipos_escudo());
                i.putExtra("TEMPORADA",Temporada);
                startActivity(i);
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

    public void BDCall(final OnGetDataListener listener){
        mRootReference.child(NombreBD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mRootReference.child(NombreBD).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            EquipoPojo equipo = snapshot.getValue(EquipoPojo.class);
                            Log.e("NOMBRE EQUIPO",equipo.getEquipos_name());
                            if(equipo.getEquipos_temporada().equals(Temporada)) {
                                equipos.add(equipo);
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
}
