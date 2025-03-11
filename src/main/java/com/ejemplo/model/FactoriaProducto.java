package com.ejemplo.model;

public interface FactoriaProducto {

    Producto crearProducto(String tipo, int id, String nombre, double precio);

}
