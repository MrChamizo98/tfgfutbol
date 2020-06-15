package com.example.tfgfutbol.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfgfutbol.Pojo.EquipoPojo;
import com.example.tfgfutbol.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterEquipo  extends BaseAdapter {

    Context context;
    ArrayList<EquipoPojo> equipos;

    private Bitmap bitmap;
    private ImageView imageView;
    private TextView country;

    /**
     * constructor
     * @param applicationContext
     * @param equipos
     */
    public CustomAdapterEquipo(Context applicationContext, ArrayList<EquipoPojo> equipos) {
        this.context = applicationContext;
        this.equipos=equipos;
    }

    /**
     * tamañi de array equipos
     * @return
     */
    @Override
    public int getCount() {
        return equipos.size();
    }

    /**
     * elemento i de array equipos
     * @param i
     * @return
     */
    @Override
    public Object getItem(int i) {
        return equipos.get(i);
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
     * asignar los valores a la vista del elemento seleccionado del array
     * @param i
     * @param view
     * @param viewGroup
     * @return
     */
    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View vista=view;

        LayoutInflater inflater=LayoutInflater.from(context);
        vista=inflater.inflate(R.layout.listviewequipos,null);

        final ImageView imagen = (ImageView) vista.findViewById(R.id.fotos_equipos);
        TextView nombre_equipo = (TextView) vista.findViewById(R.id.nombre_equipo);
        TextView nombre_estadio = (TextView) vista.findViewById(R.id.nombre_estadio);
        TextView nombre_entrenador = (TextView) vista.findViewById(R.id.nombre_entrenador);

        nombre_equipo.setText(equipos.get(i).getEquipos_name().toString());
        nombre_estadio.setText(equipos.get(i).getEquipos_estadio().toString());
        nombre_entrenador.setText(equipos.get(i).getEquipos_entrenador().toString());

       //new ImageLoadTask(equipos.get(i).getEquipos_escudo(), imagen).execute();
        //Picasso.with(context).load(equipos.get(i).getEquipos_escudo()).into(imagen);

        Picasso.with(context).load(equipos.get(i).getEquipos_escudo()).networkPolicy(NetworkPolicy.OFFLINE).into(imagen, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(equipos.get(i).getEquipos_escudo()).into(imagen);
            }
        });

        return vista;
    }
}
