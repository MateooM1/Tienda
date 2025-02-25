package com.ejemplo.controller;

import java.util.List;

import com.ejemplo.model.Cliente;
import com.ejemplo.services.ClienteDAO;
import com.ejemplo.view.ErrorHandler;

public class ClienteController {
    private ClienteDAO clienteDAO;

    public ClienteController() {
        this.clienteDAO = new ClienteDAO();
    }

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
}
