package com.ejemplo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.model.Producto;

public class ProductoDAO implements CRUD<Producto> {

    @Override
    public void agregar(Producto producto) throws Exception {
        String query = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al agregar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public Producto obtenerPorId(int id) throws Exception {
        String query = "SELECT * FROM productos WHERE id = ?";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"));
            } else {
                throw new Exception("Producto no encontrado con ID: " + id);
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener producto por ID: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Producto> obtenerTodos() throws Exception {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT * FROM productos";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
             
            while (rs.next()) {
                productos.add(new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio")));
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener la lista de productos: " + e.getMessage(), e);
        }
        return productos;
    }

    @Override
    public void actualizar(Producto producto) throws Exception {
        String query = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getId());
            int filasActualizadas = stmt.executeUpdate();
            if (filasActualizadas == 0) {
                throw new Exception("No se encontró el producto con ID: " + producto.getId());
            }
        } catch (SQLException e) {
            throw new Exception("Error al actualizar producto: " + e.getMessage(), e);
        }
    }

    @Override
    public void eliminar(int id) throws Exception {
        String query = "DELETE FROM productos WHERE id = ?";
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
             
            stmt.setInt(1, id);
            int filasEliminadas = stmt.executeUpdate();
            if (filasEliminadas == 0) {
                throw new Exception("No se encontró el producto con ID: " + id);
            }
        } catch (SQLException e) {
            throw new Exception("Error al eliminar producto: " + e.getMessage(), e);
        }
    }
}