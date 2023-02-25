package com.example.pm0110tarea42023.transacciones;

public class Transacciones {
    public static final String NameDatabase = "PM01DB";
    // Tablas de la base de datos
    public static final String tablacontactos = "contactos";

    /* Transacciones de la base de datos PM01DB */
    public static final String CreateTBContactos =
            "CREATE TABLE contactos (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "nombre TEXT, descripcion TEXT, foto BLOB )";

    public static final String DropTableContactos = "DROP TABLE IF EXISTS personas";

    // Helpers
    public static final String Empty = "";
}
