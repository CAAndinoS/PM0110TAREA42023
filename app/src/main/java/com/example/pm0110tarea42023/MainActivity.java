package com.example.pm0110tarea42023;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.pm0110tarea42023.configuracion.SQLiteConexion;
import com.example.pm0110tarea42023.transacciones.Transacciones;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE = 101;
    static final int PETICION_ACCESS_CAM = 201;
    String currentPhotoPath;
    EditText nombre, descripcion;
    Button btn_capture,btn_lista,btn_guardar;
    ImageView imageView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombre =(EditText) findViewById(R.id.nombre);
        descripcion =(EditText) findViewById(R.id.descripcion);

        imageView = (ImageView) findViewById(R.id.imageView);

        btn_capture = (Button) findViewById(R.id.btn_capture);
        btn_lista = (Button) findViewById(R.id.btn_lista);
        btn_guardar = (Button) findViewById(R.id.btn_guardar);

        btn_capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                permisos();
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AgregarPersona();
            }
        });

        btn_lista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ActivityListView.class);
                startActivity(intent);
            }
        });

    }

    private void AgregarPersona() {
        try
        {
            SQLiteConexion conexion = new SQLiteConexion(this, Transacciones.NameDatabase, null,1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            File imageFile = createImageFile();
            //Bitmap bitmap = BitmapFactory.decodeFile(imageFile.getAbsolutePath());
            Bitmap bitmap = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
            imageView.setImageBitmap(bitmap);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            byte[] byteArray = stream.toByteArray();



            ContentValues valores = new ContentValues();
            valores.put("nombre", nombre.getText().toString());
            valores.put("descripcion", descripcion.getText().toString());
            valores.put("foto", byteArray);

            Long Resultado = db.insert(Transacciones.tablacontactos, "id", valores);
            Toast.makeText(this, Resultado.toString(), Toast.LENGTH_SHORT).show();

            ClearScreen();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"No se pudo insertar el dato", Toast.LENGTH_LONG).show();
        }
    }


    private void ClearScreen()
    {
        nombre.setText(Transacciones.Empty);
        descripcion.setText(Transacciones.Empty);
    }

    private void permisos() {
        //Metodo para obtener los permisos requeridos de la aplicacion
        if(ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, PETICION_ACCESS_CAM);
        }else{
            //TomarFoto();
            dispatchTakePictureIntent();
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.toString();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.pm0110tarea42023.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents

        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if(requestCode == REQUEST_IMAGE && resultCode == RESULT_OK)
        {

            if (currentPhotoPath != null) {
                try {
                    File foto = new File(currentPhotoPath);
                    Bitmap bitmap = BitmapFactory.decodeFile(foto.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                    //imageView.setImageURI(Uri.fromFile(foto));
                }
                catch (Exception ex)
                {
                    ex.toString();
                }
            }
        }
    }
}