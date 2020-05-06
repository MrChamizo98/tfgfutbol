package com.example.tfgfutbol.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfgfutbol.Pojo.ArbitroPojo;
import com.example.tfgfutbol.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapterArbitro extends BaseAdapter {


    Context context;
    ArrayList<ArbitroPojo> arbitros;

    private Bitmap bitmap;
    private ImageView imageView;
    private TextView country;

    public CustomAdapterArbitro(Context applicationContext, ArrayList<ArbitroPojo> arbitros) {
        this.context = applicationContext;
        this.arbitros=arbitros;
    }

    @Override
    public int getCount() {
        return arbitros.size();
    }

    @Override
    public Object getItem(int i) {
        return arbitros.get(i);
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
        TextView nombre = (TextView) vista.findViewById(R.id.textViewLista);

        nombre.setText(arbitros.get(i).getArbitros_name().toString());

        //Picasso.with(context).load("http://cdn4.coloringcrew.com/coloring-book/coloring/referee-happy-colorear.jpg").into(imagen);

        Picasso.with(context).load("http://cdn4.coloringcrew.com/coloring-book/coloring/referee-happy-colorear.jpg")
                .networkPolicy(NetworkPolicy.OFFLINE).into(imagen, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load("http://cdn4.coloringcrew.com/coloring-book/coloring/referee-happy-colorear.jpg").into(imagen);
            }
        });
        /*new ImageLoadTask("http://cdn4.coloringcrew.com/coloring-book/coloring/referee-happy-colorear.jpg",
                imagen).execute();
        */
        return vista;
    }
}
