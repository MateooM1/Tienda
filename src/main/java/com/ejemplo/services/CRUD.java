package com.ejemplo.services;
import java.util.List;


/**
 * Interfaz genérica para operaciones CRUD.
 * @param <T> Tipo de entidad (Cliente, Producto, Pedido,).
 */
public interface CRUD<T> {
    void agregar(T entidad);
    T obtenerPorId(int id);
    List<T> obtenerTodos();
    void actualizar(T entidad);
    void eliminar(int id);
}
