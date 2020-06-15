package com.example.tfgfutbol.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfgfutbol.Realm.Alineacion;
import com.example.tfgfutbol.R;

import java.util.ArrayList;

public class CustomAdapterAlineacionOtro extends BaseAdapter {

    Context context;
    ArrayList<Alineacion> jugadores;
    String tipo;

    private Bitmap bitmap;
    private ImageView imageView;
    private TextView country;

    /**
     * Constructor
     * @param tipo
     * @param applicationContext
     * @param jugadores
     */
    public CustomAdapterAlineacionOtro(String tipo, Context applicationContext, ArrayList<Alineacion> jugadores) {
        this.context = applicationContext;
        this.jugadores=jugadores;
        this.tipo=tipo;
    }

    /**
     * tamaño de array jugadores
     * @return
     */
    @Override
    public int getCount() {
        return jugadores.size();
    }

    /**
     * elemento de array jugadores
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return jugadores.get(i);
    }

    /**
     * id de elemento de array
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * asignar los valores correspondientes del elemento a la vista
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista=view;

        LayoutInflater inflater=LayoutInflater.from(context);
        vista=inflater.inflate(R.layout.listviewotros,null);

        TextView nombre_jugador = (TextView) vista.findViewById(R.id.nombre_jugador);
        nombre_jugador.setText(jugadores.get(i).getAlineacion_jugador());

        ImageView foto_adapter=(ImageView) vista.findViewById(R.id.imagen_adapter);
        foto_adapter.setVisibility(view.VISIBLE);
        TextView text_adapter = (TextView) vista.findViewById(R.id.text_adapter);

        if(tipo.equals("goles")){
            foto_adapter.setImageResource(R.drawable.partidos);
            if(!jugadores.get(i).getAlineacion_gol().equals("1")){
                text_adapter.setText(jugadores.get(i).getAlineacion_gol());
            }
        }else if(tipo.equals("tarjetas")){
            foto_adapter.setImageResource(R.drawable.amarilla);
        }else if(tipo.equals("asistencias")){
            foto_adapter.setImageResource(R.drawable.asistencia);
            if(!jugadores.get(i).getAlineacion_asistencia().equals("1")){
                text_adapter.setText(jugadores.get(i).getAlineacion_asistencia());
            }
        }else if (tipo.equals("lesiones")){
            foto_adapter.setImageResource(R.drawable.lesion);
        }

        return vista;
    }
}
