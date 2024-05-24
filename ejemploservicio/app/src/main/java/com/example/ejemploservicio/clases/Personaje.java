package com.example.ejemploservicio.clases;

public class Personaje {
    private String nombre;
    private String estado;
    private String especie;
    private String imagen;

    public Personaje(String nombre, String estado, String especie, String imagen) {
        this.nombre = nombre;
        this.estado = estado;
        this.especie = especie;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
