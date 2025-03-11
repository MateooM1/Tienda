package com.ejemplo.model;

// Clase Alimento que hereda de Producto
public class Alimento extends Producto {
    public Alimento(String tipo,int id, String nombre, double precio) {
        super(tipo, id, nombre, precio); // Llama al constructor de Producto
       
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
