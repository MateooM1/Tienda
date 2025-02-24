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
    public void agregar(Producto producto) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "INSERT INTO productos (nombre, precio) VALUES (?, ?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.executeUpdate();
            System.out.println("Producto agregado: " + producto.getNombre());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Producto obtenerPorId(int id) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "SELECT * FROM productos WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Producto> obtenerTodos() {
        List<Producto> productos = new ArrayList<>();
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "SELECT * FROM productos";
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                productos.add(new Producto(rs.getInt("id"), rs.getString("nombre"), rs.getDouble("precio")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }

    @Override
    public void actualizar(Producto producto) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "UPDATE productos SET nombre = ?, precio = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, producto.getNombre());
            stmt.setDouble(2, producto.getPrecio());
            stmt.setInt(3, producto.getId());
            stmt.executeUpdate();
            System.out.println("Producto actualizado: " + producto.getNombre());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "DELETE FROM productos WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Producto eliminado con ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}