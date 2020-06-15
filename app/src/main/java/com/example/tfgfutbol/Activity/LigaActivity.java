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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.tfgfutbol.Adapter.CustomAdapter;
import com.example.tfgfutbol.Pojo.LigaPojo;
import com.example.tfgfutbol.Firebase.OnGetDataListener;
import com.example.tfgfutbol.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LigaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ListView simpleList;
    ArrayList <String> ligaList;
    ArrayList <String> listafotos;
    ArrayList <LigaPojo> Ligas;
    DatabaseReference mRootReference;

    /**
     * Método de creación de la vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        ligaList=new ArrayList<String>();
        listafotos=new ArrayList<String>();
        Ligas=new ArrayList<LigaPojo>();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.liga_layout);

        mRootReference=FirebaseDatabase.getInstance().getReference();
        mRootReference.keepSynced(true);

        simpleList = (ListView) findViewById(R.id.listaLigas);

        BDCall( new OnGetDataListener(){
            @Override
            public void onSuccess(DataSnapshot dataSnapshot) {
                CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), Ligas);
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

        CustomAdapter customAdapter = new CustomAdapter(getApplicationContext(), Ligas);
        simpleList.setAdapter(customAdapter);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayoutligas);
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
                Toast.makeText(LigaActivity.this, "Has seleccionado "+ligaList.get(position),
                        Toast.LENGTH_SHORT).show();
                Intent i=new Intent(LigaActivity.this, TemporadaActivity.class);
                i.putExtra("LIGA",ligaList.get(position));
                i.putExtra("FOTO",listafotos.get(position));
                startActivity(i);

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
            case R.id.menu_usuario:
                i=new Intent(this, UsuarioActivity.class);
                startActivity(i);
                break;
        }
        return true;
    }


    /**
     * Método que descarga los datos de la base de datos
     * @param listener
     */
    public void BDCall(final OnGetDataListener listener){
        mRootReference.child("LIGAS").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(final DataSnapshot snapshot: dataSnapshot.getChildren()){
                    mRootReference.child("LIGAS").child(snapshot.getKey()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            LigaPojo liga = snapshot.getValue(LigaPojo.class);
                            String nombre=liga.getLiga_nombre();
                            String foto= liga.getLiga_foto();

                            Ligas.add(liga);
                            Log.e("NombreLiga: ", ""+nombre);
                            Log.e("LinkFoto: ",""+foto);
                            ligaList.add(nombre);
                            listafotos.add(foto);
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
