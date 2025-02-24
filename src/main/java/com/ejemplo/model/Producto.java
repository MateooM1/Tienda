package com.ejemplo.model;

public class Producto {
    private int id;
    private String nombre;
    private double precio;

    // Constructor completo
    public Producto(int id, String nombre, double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    // Constructor con solo ID
    public Producto(int id) {
        this.id = id;
        this.nombre = "Desconocido"; // Valor por defecto
        this.precio = 0.0; // Valor por defecto
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{id=" + id + ", nombre='" + nombre + "', precio=" + precio + "}";
    }
}
