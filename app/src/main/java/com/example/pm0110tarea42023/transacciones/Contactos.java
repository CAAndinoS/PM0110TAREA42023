package com.example.pm0110tarea42023.transacciones;

public class Contactos {
    private Integer id;
    private String descripcion;
    private String nombre;
    private byte[] foto;

    public Contactos(Integer id, String descripcion, String nombre, byte[] foto) {
        this.id = id;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.foto = foto;
    }

    public Contactos() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }
}
