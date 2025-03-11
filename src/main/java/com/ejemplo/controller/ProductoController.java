package com.ejemplo.controller;

import java.util.List;

import com.ejemplo.model.FactoriaProducto;
import com.ejemplo.model.Producto;
import com.ejemplo.model.factoriaAlimento;
import com.ejemplo.model.factoriaElectronico;
import com.ejemplo.services.ProductoDAO;
import com.ejemplo.view.ErrorHandler;


public class ProductoController {

    private ProductoDAO productoDAO;

    public ProductoController() {
        this.productoDAO = new ProductoDAO();
    }

    public void agregarProducto(String nombre, double precio) {
        try {
            Producto producto = new Producto(0, nombre, precio);
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

    public void actualizarProducto(int id, String nombre, double precio) {
        try {
            Producto producto = new Producto(id, nombre, precio);
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

        FactoriaProducto factoriaAlimento = new factoriaAlimento();
        Producto manzana = factoriaAlimento.crearProducto(1, "Manzana", 1.50, "2025-01-01", "0.3");

        FactoriaProducto factoriaElectronico = new factoriaElectronico();
        Producto laptop = factoriaElectronico.crearProducto(2, "Laptop Dell", 1200.00, "Dell", "24");
    }
