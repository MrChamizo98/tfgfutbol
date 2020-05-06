package com.example.tfgfutbol.Tabla;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.tfgfutbol.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Tabla {

    private TableLayout tabla; // Layout donde se pintará la tabla
    private ArrayList<TableRow> filas; // Array de las filas de la tabla
    private Activity actividad;
    private Resources rs;
    private int FILAS, COLUMNAS; // Filas y columnas de nuestra tabla

    /**
     * Constructor
     * @param actividad donde se encuentra la tabla
     * @param tabla donde se pintará la tabla
     */
    public Tabla(Activity actividad, TableLayout tabla){
        this.actividad=actividad;
        this.tabla=tabla;
        rs=this.actividad.getResources();
        FILAS=COLUMNAS=0;
        filas=new ArrayList<TableRow>();
    }

    /**
     * Añade cabecera a la tabla
     * @param recursocabecera Array donde se encuentra la cabecera de la tabla
     */
    public void agregarCabecera(int recursocabecera){
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(actividad);
        fila.setLayoutParams(layoutFila);

        String[] arraycabecera=rs.getStringArray(recursocabecera);
        COLUMNAS=arraycabecera.length;

        for(int i=0; i<arraycabecera.length;i++){
            TextView texto=new TextView(actividad);
            layoutCelda= new TableRow.LayoutParams(obtenerAnchoPixelesTexto(arraycabecera[i]),TableRow.LayoutParams.WRAP_CONTENT);
            texto.setText(arraycabecera[i]);
            texto.setGravity(Gravity.CENTER_HORIZONTAL);
            texto.setTextAppearance(actividad, R.style.estilo_celda);
            texto.setBackgroundColor(actividad.getResources().getColor(R.color.Azul));
            texto.setLayoutParams(layoutCelda);
            texto.setTextColor(actividad.getResources().getColor(R.color.Blanco));

            fila.addView(texto);
        }

        tabla.addView(fila);
        filas.add(fila);

        FILAS++;
    }

    /**
     * Agregar una fila a la tabla
     * @param elementos elemento de una fila
     */
    public void agregarFilaTabla(final ArrayList<String>elementos){
        TableRow.LayoutParams layoutCelda;
        TableRow.LayoutParams layoutFila= new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        TableRow fila = new TableRow(actividad);
        fila.setLayoutParams(layoutFila);

        for(int i=0; i<elementos.size();i++){
            if(i==0 || i==3){
                final ImageView imagen= new ImageView(actividad);
                final int entrada=i;
                Picasso.with(actividad).load(elementos.get(i)).networkPolicy(NetworkPolicy.OFFLINE).into(imagen, new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError() {
                        Picasso.with(actividad).load(elementos.get(entrada)).into(imagen);
                    }
                });
                //new ImageLoadTask(elementos.get(i), imagen).execute();
                fila.addView(imagen);
            }else {
                TextView texto = new TextView(actividad);
                texto.setText(String.valueOf(elementos.get(i)));
                texto.setGravity(Gravity.CENTER_HORIZONTAL);
                texto.setTextAppearance(actividad, R.style.estilo_celda);
                //texto.setBackgroundResource(R.drawable.tabla_celda);
                layoutCelda = new TableRow.LayoutParams(obtenerAnchoPixelesTexto(texto.getText().toString()), TableRow.LayoutParams.WRAP_CONTENT);
                texto.setLayoutParams(layoutCelda);
                texto.setTextColor(actividad.getResources().getColor(R.color.Blanco));

                fila.addView(texto);
            }
        }

        tabla.addView(fila);
        filas.add(fila);

        FILAS++;
    }

    /**
     * Obtiene el ancho en pixeles
     * @param texto
     * @return Ancho en pixeles
     */
    private int obtenerAnchoPixelesTexto(String texto){
        Paint p=new Paint();
        Rect bounds=new Rect();
        p.setTextSize(50);


        p.getTextBounds(texto,0,texto.length(),bounds);
        return bounds.width();
    }
}
