package com.example.tfgfutbol.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.tfgfutbol.Adapter.CustomAdapterTemporada;
import com.example.tfgfutbol.R;

public class TemporadaActivity extends AppCompatActivity {

    private Toolbar toolbar;
    ListView simpleList;
    TextView nombre_liga;
    String ligaList[] = {"2019/2020", "2018/2019", "2017/2018", "2016/2017"};
    String ligaList1[]={"Temporada2020","Temporada2019","Temporada2018","Temporada2017"};
    String liga;
    String foto;

    /**
     * Método para la creación de la vista
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temporada_layout);

        simpleList = (ListView) findViewById(R.id.listaLigas);

        Intent i = getIntent();
        foto= DimeFoto(i);
        Log.e("LinkFotoTemporada: ",""+foto);
        final String copiafoto=foto;

        liga=i.getStringExtra("LIGA");
        Log.e("NombreLigaTemporada: ", ""+liga);
        nombre_liga=(TextView)findViewById(R.id.nombre_liga_temporada);
        nombre_liga.setText(liga);


        CustomAdapterTemporada r= new CustomAdapterTemporada(getApplicationContext(), ligaList, foto);
        simpleList.setAdapter(r);

        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(TemporadaActivity.this, "Has seleccionado temporada "+ligaList[position]+
                        " "+liga, Toast.LENGTH_SHORT).show();

                Intent i=new Intent(TemporadaActivity.this, CargarActivity.class);
                i.putExtra("LIGA",liga);
                i.putExtra("TEMPORADA",ligaList1[position]);
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


    private String DimeFoto(Intent i){
        return i.getStringExtra("FOTO");
    }
}