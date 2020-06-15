package com.example.tfgfutbol.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.tfgfutbol.R;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button entrar;

    /**
     * Método de creación de la vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //ServicioAlineacion servicioAlineacion=new ServicioAlineacion(Realm.getDefaultInstance());
        //Alineacion[] jugadores=servicioAlineacion.ObetenerAlineacion();

        /*for(int i=0;i<jugadores.length;i++){
            if(jugadores[i].getLiga().equals("LaLiga Santander"))
                if(jugadores[i].getAlineacion_jugador().equals("Carlos Clerc"))
                    Log.e("JUGADOR: ",jugadores[i].getAlineacion_jugador()+" "+jugadores[i].getLiga()+" "+
                        jugadores[i].getAlineacion_temporada()+" "+jugadores[i].getAlineacion_jornada());
            break;
        }*/

        entrar=findViewById(R.id.button_inicio);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    /**
     * Método de paso de vista tras pulsación del botón
     * @param v
     */
    public void clickBotonEntrar(View v){
        //Creamos el intent
        Intent intent = new Intent(MainActivity.this, LigaActivity.class);
        startActivity(intent);
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
}
