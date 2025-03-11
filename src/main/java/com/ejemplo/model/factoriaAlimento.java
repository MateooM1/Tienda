package com.ejemplo.model;

public class factoriaAlimento implements FactoriaProducto {
    @Override
    public Producto crearProducto(int id, String nombre, double precio, String fechaExpiracion, String peso) {
        return new Alimento(id, nombre, precio, fechaExpiracion, Double.parseDouble(peso));
    }
}
