package com.ejemplo.model;

public class factoriaAlimento implements FactoriaProducto {
    @Override
    public Producto crearProducto(String tipo, int id, String nombre, double precio) {
        return new Alimento(tipo, id, nombre, precio);
    }
}
