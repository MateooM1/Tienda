package com.ejemplo.services;
import java.util.List;


/**
 * Interfaz genérica para operaciones CRUD.
 * @param <T> Tipo de entidad (Cliente, Producto, Pedido,).
 */
public interface CRUD<T> {
    void agregar(T entidad) throws Exception;
    T obtenerPorId(int id) throws Exception;
    List<T> obtenerTodos() throws Exception;
    void actualizar(T entidad) throws Exception;
    void eliminar(int id) throws Exception;
}
