package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tfgfutbol.Pojo.ArbitroPojo;
import com.example.tfgfutbol.Adapter.CustomAdapterArbitro;
import com.example.tfgfutbol.Firebase.OnGetDataListener;
import com.example.tfgfutbol.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArbitrosActivity extends AppCompatActivity {

    private Toolbar toolbar;

    ListView simpleList;
    private String Temporada;
    ArrayList<ArbitroPojo> arbitros;
    DatabaseReference mRootReference;
    private String Liga;
    private String NombreBD="";
    TextView nombre_liga;

    /**
     * Método de creación de la vista
     * Llamada a base de datos y asignación de datos a vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arbitros_layout);
        simpleList = (ListView) findViewById(R.id.listaArbitros);

        mRootReference= FirebaseDatabase.getInstance().getReference();
        mRootReference.keepSynced(true);

        Intent i = getIntent();
        Liga=i.getStringExtra("LIGA");
        Temporada=i.getStringExtra("TEMPORADA");

        nombre_liga=(TextView)findViewById(R.id.nombre_liga_arbitros);
        nombre_liga.setText(Liga);

        if(Liga.equals("LaLiga Santander")) {
            NombreBD="ARBITROS";
        }else if(Liga.equals("Premier League")) {
            NombreBD="ARBITROS_PREMIER";
        }else if(Liga.equals("Bundesliga")) {
            NombreBD="ARBITROS_BUNDESLIGA";
        }else if(Liga.equals("Serie A")){
            NombreBD="ARBITROS_SERIEA";
        }

        arbitros= new ArrayList<ArbitroPojo>();

        BDCall(new OnGetDataListener(){
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                CustomAdapterArbitro customAdapter = new CustomAdapterArbitro(getApplicationContext(), arbitros);
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

        CustomAdapterArbitro customAdapter = new CustomAdapterArbitro(getApplicationContext(), arbitros);
        simpleList.setAdapter(customAdapter);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayoutarbitros);
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
     * Método de llamada a base de datos, para obtención de datos de árbitros
     * @param listener
     */
    public void BDCall(final OnGetDataListener listener){
        mRootReference.child(NombreBD).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mRootReference.child(NombreBD).child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            ArbitroPojo arbitro = snapshot.getValue(ArbitroPojo.class);
                            Log.e("NOMBRE ARBITRO", arbitro.getArbitros_name());
                            arbitros.add(arbitro);
                            listener.onSuccess(dataSnapshot);
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
