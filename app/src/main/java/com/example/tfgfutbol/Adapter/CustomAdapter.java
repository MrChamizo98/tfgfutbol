package com.example.tfgfutbol.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tfgfutbol.Pojo.LigaPojo;
import com.example.tfgfutbol.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    Context context;
    ArrayList<LigaPojo> ligas;

    private Bitmap bitmap;
    private ImageView imageView;
    private TextView country;

    public CustomAdapter(Context applicationContext, ArrayList<LigaPojo> Ligas) {
        this.context = applicationContext;
        this.ligas=Ligas;
    }

    @Override
    public int getCount() {
        return ligas.size();
    }

    @Override
    public Object getItem(int i) {
        return ligas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       View vista=view;

       LayoutInflater inflater=LayoutInflater.from(context);
       vista=inflater.inflate(R.layout.listview,null);

       final ImageView imagen = (ImageView) vista.findViewById(R.id.fotos_url);
       TextView titulo = (TextView) vista.findViewById(R.id.textViewLista);

       titulo.setText(ligas.get(i).getLiga_nombre().toString());

       //Picasso.with(context).load(ligas.get(i).getLiga_foto()).into(imagen);

        Picasso.with(context).load(ligas.get(i).getLiga_foto()).networkPolicy(NetworkPolicy.OFFLINE).into(imagen, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError() {
                Picasso.with(context).load(ligas.get(i).getLiga_foto()).into(imagen);
            }
        });
       //new ImageLoadTask(ligas.get(i).getLiga_foto(), imagen).execute();

       return vista;
    }

}
