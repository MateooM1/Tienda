package com.ejemplo.controller;

import java.util.List;

import com.ejemplo.model.Cliente;
import com.ejemplo.model.Pedido;
import com.ejemplo.model.Producto;
import com.ejemplo.services.PedidoDAO;
import com.ejemplo.view.ErrorHandler;

public class PedidoController {
    private PedidoDAO pedidoDAO;

    public PedidoController() {
        this.pedidoDAO = new PedidoDAO();
    }

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
}
