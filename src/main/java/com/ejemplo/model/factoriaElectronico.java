package com.ejemplo.model;

public class factoriaElectronico implements FactoriaProducto {
    @Override
    public Producto crearProducto(String tipo, int id, String nombre, double precio ) {
        return new Electronico(tipo, id, nombre, precio);
    }
}
