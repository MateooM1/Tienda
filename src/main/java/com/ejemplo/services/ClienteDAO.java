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

    @Override
    public void agregar(Cliente cliente) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "INSERT INTO clientes (nombre, email) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.executeUpdate();
            System.out.println("Cliente agregado: " + cliente.getNombre());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cliente obtenerPorId(int id) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "SELECT * FROM clientes WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Cliente> obtenerTodos() {
        List<Cliente> clientes = new ArrayList<>();
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "SELECT * FROM clientes";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                clientes.add(new Cliente(rs.getInt("id"), rs.getString("nombre"), rs.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }

    @Override
    public void actualizar(Cliente cliente) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "UPDATE clientes SET nombre = ?, email = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, cliente.getNombre());
            stmt.setString(2, cliente.getEmail());
            stmt.setInt(3, cliente.getId());
            stmt.executeUpdate();
            System.out.println("Cliente actualizado: " + cliente.getNombre());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "DELETE FROM clientes WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Cliente eliminado con ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
