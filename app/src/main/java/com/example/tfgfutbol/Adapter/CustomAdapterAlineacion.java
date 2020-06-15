package com.example.tfgfutbol.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfgfutbol.Pojo.AlineacionPojo;
import com.example.tfgfutbol.R;

import java.util.ArrayList;

public class CustomAdapterAlineacion extends BaseAdapter {

    Context context;
    ArrayList<AlineacionPojo> alineacion;

    private Bitmap bitmap;
    private ImageView imageView;
    private TextView country;

    /**
     * Constructor
     * @param applicationContext
     * @param alineacion
     */
    public CustomAdapterAlineacion(Context applicationContext, ArrayList<AlineacionPojo> alineacion) {
        this.context = applicationContext;
        this.alineacion=alineacion;
    }

    /**
     * Tamaño areay alineacion
     * @return
     */
    @Override
    public int getCount() {
        return alineacion.size();
    }

    /**
     * obtener elemento i del array alaineación
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return alineacion.get(i);
    }

    /**
     * obtener el id de un elemento del array
     * @param i
     * @return
     */
    @Override
    public long getItemId(int i) {
        return 0;
    }

    /**
     * asignar los valores correspondientes a la entrada del listview de la vista
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista=view;

        LayoutInflater inflater=LayoutInflater.from(context);
        vista=inflater.inflate(R.layout.listviewalineacion,null);

        TextView nombre_jugador = (TextView) vista.findViewById(R.id.nombre_jugador);
        nombre_jugador.setText(alineacion.get(i).getAlineacion_jugador());

        if(!alineacion.get(i).getAlineacion_cambio().equals("")){
            ImageView foto_cambio=(ImageView) vista.findViewById(R.id.imagen_cambio);
            foto_cambio.setVisibility(view.VISIBLE);

            TextView minuto_cambio=(TextView) vista.findViewById(R.id.minuto_cambio);
            minuto_cambio.setText(alineacion.get(i).getAlineacion_cambio());
        }

        return vista;
    }
}
