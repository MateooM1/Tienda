package com.ejemplo.controller;

import java.util.List;

import com.ejemplo.model.Cliente;
import com.ejemplo.model.Pedido;
import com.ejemplo.model.Producto;
import com.ejemplo.services.PedidoDAO;

public class PedidoController {

    private PedidoDAO pedidoDAO;

    public PedidoController() {
        this.pedidoDAO = new PedidoDAO();
    }

    public void agregarPedido(Cliente cliente, List<Producto> productos) {
        Pedido pedido = new Pedido(0, cliente, productos);  
        pedidoDAO.agregar(pedido);
    }

    public List<Pedido> obtenerPedidos() {
        return pedidoDAO.obtenerTodos();
    }

    public Pedido obtenerPedidoPorId(int id) {
        return pedidoDAO.obtenerPorId(id);
    }

    public void actualizarPedido(int id, Cliente cliente, List<Producto> productos) {
        Pedido pedido = new Pedido(id, cliente, productos);
        pedidoDAO.actualizar(pedido);
    }

    public void eliminarPedido(int id) {
        pedidoDAO.eliminar(id);
    }
}
