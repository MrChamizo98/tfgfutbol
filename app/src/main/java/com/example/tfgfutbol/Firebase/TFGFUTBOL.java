package com.example.tfgfutbol.Firebase;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;

import androidx.multidex.MultiDex;

import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

public class TFGFUTBOL extends Application {

    private LruCache<String, Bitmap> memoryCache;
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    /**
     * Método encargado de asignar que instancias de la base de datos Firebase se van a almacenar en local
     */
    @Override
    public void onCreate(){
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        //FirebaseDatabase.getInstance("https://tfg-futbol-alineacion.firebaseio.com/").setPersistenceEnabled(true);
        //FirebaseDatabase.getInstance("https://tfg-futbol-calendario.firebaseio.com/").setPersistenceEnabled(true);
        //FirebaseDatabase.getInstance("https://tfg-futbol-clasificacion.firebaseio.com/").setPersistenceEnabled(true);
        //FirebaseDatabase.getInstance("https://tfg-futbol-plantilla.firebaseio.com/").setPersistenceEnabled(true);
        FirebaseDatabase.getInstance("https://tfg-futbol-prueba.firebaseio.com/").setPersistenceEnabled(true);

        Picasso.Builder builder=new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        built.setIndicatorsEnabled(false);
        built.setLoggingEnabled(true);
        Picasso.setSingletonInstance(built);

    }
}
