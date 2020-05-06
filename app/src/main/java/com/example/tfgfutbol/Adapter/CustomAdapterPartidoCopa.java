package com.example.tfgfutbol.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfgfutbol.Pojo.CopaPojo;
import com.example.tfgfutbol.R;

import java.util.ArrayList;

public class CustomAdapterPartidoCopa extends BaseAdapter {

    Context context;
    ArrayList<CopaPojo> partidos;

    private Bitmap bitmap;
    private ImageView imageView;
    private TextView country;

    public CustomAdapterPartidoCopa(Context applicationContext, ArrayList<CopaPojo> partidos) {
        this.context = applicationContext;
        this.partidos=partidos;
    }

    @Override
    public int getCount() {
        return partidos.size();
    }

    @Override
    public Object getItem(int i) {
        return partidos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista=view;

        LayoutInflater inflater=LayoutInflater.from(context);
        vista=inflater.inflate(R.layout.listviewpartidos_copa_champions_uefa,null);

        TextView fecha = (TextView) vista.findViewById(R.id.partidos_fecha);
        TextView local = (TextView) vista.findViewById(R.id.partidos_local);
        TextView visitante = (TextView) vista.findViewById(R.id.partidos_visitante);
        TextView resultado = (TextView) vista.findViewById(R.id.partidos_resultado);
        //TextView Jornada = (TextView) vista.findViewById(R.id.partidos_jornada);
        //final ImageView imagen_local = (ImageView) vista.findViewById(R.id.foto_local);
        //final ImageView imagen_visitante = (ImageView) vista.findViewById(R.id.foto_visitante);

        //final String url_local=partidos.get(i).getPartidos_foto_local();
        //final String url_visitante=partidos.get(i).getPartidos_foto_visitante();

        fecha.setText(partidos.get(i).getCopa_fecha().toString());
        local.setText(partidos.get(i).getCopa_local().toString());
        visitante.setText(partidos.get(i).getCopa_visitante().toString());
        resultado.setText(partidos.get(i).getCopa_resultado().toString());

        //Picasso.with(context).load(url_local).into(imagen_local);
        //Picasso.with(context).load(url_visitante).into(imagen_visitante);
        //new ImageLoadTask(url_local, imagen_local).execute();
        //new ImageLoadTask(url_visitante, imagen_visitante).execute();
        //Jornada.setText(partidos.get(i).getPartidos_jornada().toString());


        return vista;
    }
}
