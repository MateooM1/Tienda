package com.ejemplo.model;

import java.util.Random;

public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private String tipo;

    // Constructor completo
    public Producto(String tipo, int id, String nombre, double precio) {
        this.id = (id == 0) ? generarNuevoId() : id;        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }

    // Constructor con solo ID
    public Producto(int id) {
        this.id = id;
        this.nombre = "Desconocido"; // Valor por defecto
        this.precio = 0.0; // Valor por defecto
        this.tipo = "Desconocido";
    }

    private static int generarNuevoId() {
        return new Random().nextInt(10000); // Simulación de ID único
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

    public String getTipo() {
        return tipo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Producto{tipo='" + tipo + "', id=" + id + ", nombre='" + nombre + "', precio=" + precio + "}";   
     }
}
