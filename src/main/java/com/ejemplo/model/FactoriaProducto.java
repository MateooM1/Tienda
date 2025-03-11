package com.ejemplo.model;

public interface FactoriaProducto {

    Producto crearProducto(int id, String nombre, double precio, String atributo1, String atributo2);

}
