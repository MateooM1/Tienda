package com.ejemplo.controller;

import java.util.List;

import com.ejemplo.model.Producto;
import com.ejemplo.services.ProductoDAO;

public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDAO();
    }

    public void agregarProducto(String nombre, double precio) {
        Producto producto = new Producto(0, nombre, precio);
        productoDAO.agregar(producto);
    }

    public List<Producto> obtenerProductos() {
        return productoDAO.obtenerTodos();
    }

    public Producto obtenerProductoPorId(int id) {
        return productoDAO.obtenerPorId(id);
    }

    public void actualizarProducto(int id, String nombre, double precio) {
        Producto producto = new Producto(id, nombre, precio);
        productoDAO.actualizar(producto);
    }

    public void eliminarProducto(int id) {
        productoDAO.eliminar(id);
    }
}