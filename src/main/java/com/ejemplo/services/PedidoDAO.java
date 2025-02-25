package com.ejemplo.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.ejemplo.model.Cliente;
import com.ejemplo.model.Pedido;
import com.ejemplo.model.Producto;

public class PedidoDAO implements CRUD<Pedido> {

    @Override
    public void agregar(Pedido pedido) throws Exception{
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            String query = "INSERT INTO pedidos (cliente_id) VALUES (?)";
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, pedido.getCliente().getId());
            stmt.executeUpdate();

            // Obtener el ID del pedido recién insertado
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int pedidoId = -1;
            if (generatedKeys.next()) {
                pedidoId = generatedKeys.getInt(1);
            }

            // Insertar productos asociados al pedido
            String queryProductos = "INSERT INTO pedido_productos (pedido_id, producto_id) VALUES (?, ?)";
            PreparedStatement stmtProd = connection.prepareStatement(queryProductos);
            for (Producto producto : pedido.getProductos()) {
                stmtProd.setInt(1, pedidoId);
                stmtProd.setInt(2, producto.getId());
                stmtProd.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception("Error al agregar el pedido: " + e.getMessage(), e);
        }
    }

    @Override
    public Pedido obtenerPorId(int id) throws Exception {
        Pedido pedido = null;
        List<Producto> productos = new ArrayList<>();
        
        String query = "SELECT p.id, c.id AS cliente_id, c.nombre, c.email FROM pedidos p " +
                       "JOIN clientes c ON p.cliente_id = c.id WHERE p.id = ?";
        String queryProductos = "SELECT producto_id FROM pedido_productos WHERE pedido_id = ?";
        
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente(rs.getInt("cliente_id"), rs.getString("nombre"), rs.getString("email"));
                    pedido = new Pedido(id, cliente, productos);
                }
            }
            
            if (pedido != null) {
                try (PreparedStatement stmtProd = connection.prepareStatement(queryProductos)) {
                    stmtProd.setInt(1, id);
                    try (ResultSet rsProd = stmtProd.executeQuery()) {
                        while (rsProd.next()) {
                            productos.add(new Producto(rsProd.getInt("producto_id")));
                        }
                    }
                }
                pedido.setProductos(productos);
            }
            
        } catch (SQLException e) {
            throw new Exception("Error al obtener el pedido: " + e.getMessage(), e);
        }
        return pedido;
    }
    
    @Override
    public List<Pedido> obtenerTodos() throws Exception {
        List<Pedido> pedidos = new ArrayList<>();
        String query = "SELECT id FROM pedidos";

        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            
            while (rs.next()) {
                Pedido pedido = obtenerPorId(rs.getInt("id"));
                if (pedido != null) {
                    pedidos.add(pedido);
                }
            }
        } catch (SQLException e) {
            throw new Exception("Error al obtener todos los pedidos: " + e.getMessage(), e);
        }
        return pedidos;
    }
    
    @Override
    public void actualizar(Pedido pedido) throws Exception {
        String query = "UPDATE pedidos SET cliente_id = ? WHERE id = ?";
        String deleteProductos = "DELETE FROM pedido_productos WHERE pedido_id = ?";
        String queryProductos = "INSERT INTO pedido_productos (pedido_id, producto_id) VALUES (?, ?)";
        
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query);
             PreparedStatement stmtDelete = connection.prepareStatement(deleteProductos);
             PreparedStatement stmtProd = connection.prepareStatement(queryProductos)) {
            
            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setInt(2, pedido.getId());
            stmt.executeUpdate();
            
            stmtDelete.setInt(1, pedido.getId());
            stmtDelete.executeUpdate();
            
            for (Producto producto : pedido.getProductos()) {
                stmtProd.setInt(1, pedido.getId());
                stmtProd.setInt(2, producto.getId());
                stmtProd.executeUpdate();
            }
        } catch (SQLException e) {
            throw new Exception("Error al actualizar el pedido: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void eliminar(int id) throws Exception {
        String deleteProductos = "DELETE FROM pedido_productos WHERE pedido_id = ?";
        String query = "DELETE FROM pedidos WHERE id = ?";
        
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmtProd = connection.prepareStatement(deleteProductos);
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmtProd.setInt(1, id);
            stmtProd.executeUpdate();
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new Exception("Error al eliminar el pedido: " + e.getMessage(), e);
        }
    }
}
