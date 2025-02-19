package com.ejemplo.controller;

import java.util.List;

import com.ejemplo.model.Cliente;
import com.ejemplo.services.ClienteDAO;

public class ClienteController {

    private ClienteDAO clienteDAO;

    // Constructor
    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

    // Método para agregar un cliente
    public void agregarCliente(String nombre, String email) {
        Cliente cliente = new Cliente(0, nombre, email);  // id=0, ya que lo asigna la BD
        clienteDAO.agregar(cliente);
    }

    // Método para obtener todos los clientes
    public List<Cliente> obtenerClientes() {
        return clienteDAO.obtenerTodos();
    }

    // Método para obtener un cliente por su id
    public Cliente obtenerClientePorId(int id) {
        return clienteDAO.obtenerPorId(id);
    }

    // Método para actualizar un cliente
    public void actualizarCliente(int id, String nombre, String email) {
        Cliente cliente = new Cliente(id, nombre, email);
        clienteDAO.actualizar(cliente);
    }

    // Método para eliminar un cliente
    public void eliminarCliente(int id) {
        clienteDAO.eliminar(id);
    }
}
