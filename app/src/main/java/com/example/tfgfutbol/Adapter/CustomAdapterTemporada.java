package com.example.tfgfutbol.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfgfutbol.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterTemporada extends BaseAdapter {

    Context context;
    String [] temporadas;
    String url;

    private Bitmap bitmap;
    private ImageView imageView;
    private TextView country;

    public CustomAdapterTemporada(Context applicationContext, String []temporadas, String url) {
        this.context = applicationContext;
        this.url=url;
        this.temporadas=temporadas;
    }

    @Override
    public int getCount() {
        return temporadas.length;
    }

    @Override
    public Object getItem(int i) {
        return temporadas[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View vista=view;

        LayoutInflater inflater=LayoutInflater.from(context);
        vista=inflater.inflate(R.layout.listview,null);

        final ImageView imagen = (ImageView) vista.findViewById(R.id.fotos_url);
        TextView titulo = (TextView) vista.findViewById(R.id.textViewLista);

        titulo.setText(temporadas[i].toString());

        //Picasso.with(context).load(url).into(imagen);
        //new ImageLoadTask(url, imagen).execute();
        Picasso.with(context).load(url).networkPolicy(NetworkPolicy.OFFLINE).into(imagen, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(url).into(imagen);
            }
        });

        return vista;
    }
}
