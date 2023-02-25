package com.example.pm0110tarea42023;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

import com.example.pm0110tarea42023.configuracion.SQLiteConexion;
import com.example.pm0110tarea42023.transacciones.Contactos;
import com.example.pm0110tarea42023.transacciones.Transacciones;

import java.util.ArrayList;

public class ActivityMostrarFoto extends AppCompatActivity {
    SQLiteConexion conexion;
    ImageView imageView;

    ArrayList<Contactos> listacontactos;
    ArrayList<String> Arreglocontactos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_foto);

        conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null, 1);

        imageView = (ImageView) findViewById(R.id.imageView);

        int id = getIntent().getIntExtra("id", 0);
        Contactos contacto = ObtenerContactoPorId(id);
        MostrarFoto(contacto.getFoto());

    }

    private void MostrarFoto(byte[] byteArray) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bitmap);

    }

    private Contactos ObtenerContactoPorId(int id) {
        SQLiteDatabase db = conexion.getWritableDatabase();
        Contactos contacto = null;

        Cursor cursor = db.rawQuery("SELECT * FROM contactos WHERE id = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()) {
            contacto = new Contactos();
            contacto.setId(cursor.getInt(0));
            contacto.setNombre(cursor.getString(1));
            contacto.setDescripcion(cursor.getString(2));
            contacto.setFoto(cursor.getBlob(3));
        }
        cursor.close();
        return contacto;
    }
}