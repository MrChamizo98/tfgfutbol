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

public class TemporadaNotFoundActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private Button entrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notfound_layout);

        entrar=findViewById(R.id.button_volver);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    public void clickBotonVolver(View v){
        //Creamos el intent
        Intent intent = new Intent(TemporadaNotFoundActivity.this, LigaActivity.class);
        //Iniciamos la nueva actividad
        startActivity(intent);

    }

    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menusuperior,menu);
        return true;
    }

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
