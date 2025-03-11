package com.ejemplo.model;

// Clase Electronico que hereda de Producto
public class Electronico extends Producto {
    

    public Electronico(String tipo, int id, String nombre, double precio) {
        super(tipo, id, nombre, precio); // Llama al constructor de Producto
    }

      @Override
    public String toString() {
        return super.toString();
    }
}
