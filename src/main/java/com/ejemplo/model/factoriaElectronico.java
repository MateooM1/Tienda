package com.ejemplo.model;

public class factoriaElectronico implements FactoriaProducto {
    @Override
    public Producto crearProducto(int id, String nombre, double precio, String marca, String garantia) {
        return new Electronico(id, nombre, precio, marca, Integer.parseInt(garantia));
    }
}
