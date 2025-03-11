package com.ejemplo.controller;

import java.util.List;

import com.ejemplo.model.FactoriaProducto;
import com.ejemplo.model.FactoryProvider;
import com.ejemplo.model.Producto;
import com.ejemplo.services.ProductoDAO;
import com.ejemplo.view.ErrorHandler;


public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDAO();
    }

    public void agregarProducto(String tipo , String nombre, double precio) {
        try {
            Producto producto = new Producto(tipo, 0, nombre, precio);
            productoDAO.agregar(producto);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }

    public List<Producto> obtenerProductos() {
        try {
            return productoDAO.obtenerTodos();
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
            return null;
        }
    }

    public Producto obtenerProductoPorId(int id) {
        try {
            return productoDAO.obtenerPorId(id);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
            return null;
        }
    }

    public void actualizarProducto(String tipo, int id, String nombre, double precio) {
        try {
            Producto producto = new Producto(tipo, id, nombre, precio);
            productoDAO.actualizar(producto);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }

    public void eliminarProducto(int id) {
        try {
            productoDAO.eliminar(id);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }
    
    public List<Producto> obtenerProductosPorRangoDePrecio(double minPrecio, double maxPrecio) {
        try {
            return productoDAO.obtenerProductosPorRangoDePrecio(minPrecio, maxPrecio);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
            return null;
        }

    }
    public void crearYAgregarProducto(String tipo, int id, String nombre, double precio, String atributo1, String atributo2) {
        try {
            FactoriaProducto factoria = FactoryProvider.getFactory(tipo);
            Producto producto = factoria.crearProducto(tipo, id, nombre, precio);
            productoDAO.agregar(producto);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }
    }
