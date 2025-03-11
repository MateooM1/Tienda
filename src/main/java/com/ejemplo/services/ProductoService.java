package com.ejemplo.services;

import java.util.List;

import com.ejemplo.model.Producto;

/**
 * Interfaz específica para Producto que extiende de CRUD.
 */
public interface ProductoService extends CRUD<Producto> {
    List<Producto> obtenerProductosPorRangoDePrecio(double minPrecio, double maxPrecio) throws Exception;
}
