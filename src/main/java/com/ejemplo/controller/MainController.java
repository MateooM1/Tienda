package com.ejemplo.controller;

import java.util.List;

import com.ejemplo.model.Cliente;
import com.ejemplo.model.FactoriaProducto;
import com.ejemplo.model.FactoryProvider;
import com.ejemplo.model.Pedido;
import com.ejemplo.model.Producto;
import com.ejemplo.services.ClienteDAO;
import com.ejemplo.services.PedidoDAO;
import com.ejemplo.services.ProductoDAO;
import com.ejemplo.view.ErrorHandler;

public class MainController {
    private PedidoDAO pedidoDAO;
    private ProductoDAO productoDAO;
    private ClienteDAO clienteDAO;

    public MainController() {
        this.pedidoDAO = new PedidoDAO();
        this.productoDAO = new ProductoDAO();
        this.clienteDAO = new ClienteDAO();
    }

    // Métodos para Clientes
    public void agregarCliente(String nombre, String email) {
        try {
            Cliente cliente = new Cliente(0, nombre, email);
            clienteDAO.agregar(cliente);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }

    public Cliente obtenerClientePorId(int id) {
        try {
            return clienteDAO.obtenerPorId(id);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
            return null;
        }
    }

    public List<Cliente> obtenerClientes() {
        try {
            return clienteDAO.obtenerTodos();
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
            return List.of();
        }
    }

    public void actualizarCliente(int id, String nombre, String email) {
        try {
            Cliente cliente = new Cliente(id, nombre, email);
            clienteDAO.actualizar(cliente);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }

    public void eliminarCliente(int id) {
        try {
            clienteDAO.eliminar(id);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }

    // Métodos para Productos
    public void agregarProducto(String tipo, String nombre, double precio) {
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
            return List.of();
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
            return List.of();
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

    // Métodos para Pedidos
    public void agregarPedido(Cliente cliente, List<Producto> productos) {
        try {
            Pedido pedido = new Pedido(0, cliente, productos);
            pedidoDAO.agregar(pedido);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }

    public List<Pedido> obtenerPedidos() {
        try {
            return pedidoDAO.obtenerTodos();
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
            return List.of();
        }
    }

    public Pedido obtenerPedidoPorId(int id) {
        try {
            return pedidoDAO.obtenerPorId(id);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
            return null;
        }
    }

    public void actualizarPedido(int id, Cliente cliente, List<Producto> productos) {
        try {
            Pedido pedido = new Pedido(id, cliente, productos);
            pedidoDAO.actualizar(pedido);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }

    public void eliminarPedido(int id) {
        try {
            pedidoDAO.eliminar(id);
        } catch (Exception e) {
            ErrorHandler.showError(e.getMessage());
        }
    }

    public void probarClonacion() {
        Producto productoBase = new Producto("Electronico", 0, "Producto Base", 10000);
    
        Producto productoClonado = productoBase.clone();
    
        productoClonado.setNombre(productoClonado.getNombre() + " - Copia");
    
        System.out.println("Producto Original: " + productoBase);
        System.out.println("Producto Clonado: " + productoClonado);
    }
    public static void main(String[] args) {
        MainController controlador = new MainController();
        controlador.probarClonacion();
    }
}
