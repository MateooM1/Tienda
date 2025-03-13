package com.ejemplo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.model.Cliente;

public class ClienteDAO implements CRUD<Cliente> {
    public void agregar(Cliente cliente) throws Exception {
        String query = "INSERT INTO clientes (nombre, email) VALUES (?, ?)";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al agregar cliente: " + e.getMessage());
        }
    }

    public Cliente obtenerPorId(int id) throws Exception {
        String query = "SELECT * FROM clientes WHERE id = ?";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"));
            }
            throw new Exception("Cliente no encontrado");
        } catch (SQLException e) {
            throw new Exception("Error al obtener cliente: " + e.getMessage());
        }
    }

    public List<Cliente> obtenerTodos() throws Exception {
        List<Cliente> clientes = new ArrayList<>();
        String query = "SELECT * FROM clientes";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email")));
            }
            return clientes;
        } catch (SQLException e) {
            throw new Exception("Error al obtener clientes: " + e.getMessage());
        }
    }

    public void actualizar(Cliente cliente) throws Exception {
        String query = "UPDATE clientes SET nombre = ?, email = ? WHERE id = ?";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setInt(3, cliente.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al actualizar cliente: " + e.getMessage());
        }
    }

    public void eliminar(int id) throws Exception {
        String query = "DELETE FROM clientes WHERE id = ?";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar cliente: " + e.getMessage());
        }
    }
}

