package com.example.pm0110tarea42023;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pm0110tarea42023.configuracion.SQLiteConexion;
import com.example.pm0110tarea42023.transacciones.Contactos;
import com.example.pm0110tarea42023.transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityListView extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView listView;
    ArrayList<Contactos> listacontactos;
    ArrayList<String> Arreglocontactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);
        listView = (ListView) findViewById(R.id.listview);

        ObtenerListaPersonas();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_1, Arreglocontactos);
        listView.setAdapter(adp);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contactos contacto = listacontactos.get(i);
                Intent intent = new Intent(ActivityListView.this, ActivityMostrarFoto.class);
                intent.putExtra("id", contacto.getId());
                startActivity(intent);
            }
        });
    }

    private void ObtenerListaPersonas() {
        SQLiteDatabase db = conexion.getWritableDatabase();
        Contactos person = null;
        listacontactos = new ArrayList<Contactos>();

        //Cursor
        Cursor cursor = db.rawQuery("SELECT * FROM contactos", null);

        while (cursor.moveToNext()){
            person = new Contactos();
            person.setId(cursor.getInt(0));
            person.setNombre(cursor.getString(1));
            person.setDescripcion(cursor.getString(2));


            listacontactos.add(person);
        }
        cursor.close();
        FillList();
    }

    private void FillList(){
        Arreglocontactos = new ArrayList<>();
        for (int i = 0; i < listacontactos.size(); i++){

            Arreglocontactos.add(listacontactos.get(i).getId()+ " | " +
                    listacontactos.get(i).getNombre() + " | " +
                    listacontactos.get(i).getDescripcion()
                    + " Da Click en el Item para Mostrar la Foto");

        }
    }
}