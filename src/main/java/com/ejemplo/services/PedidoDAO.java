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
    public void agregar(Pedido pedido) {
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

            System.out.println("Pedido agregado con ID: " + pedidoId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Pedido obtenerPorId(int id) {
        Pedido pedido = null;
        List<Producto> productos = new ArrayList<>();
        
        String query = "SELECT p.id, c.id AS cliente_id, c.nombre, c.email FROM pedidos p " +
                       "JOIN clientes c ON p.cliente_id = c.id WHERE p.id = ?";
    
        try (Connection connection = ConnectionDB.getInstancia().getConexion();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente(rs.getInt("cliente_id"), rs.getString("nombre"), rs.getString("email"));
                    pedido = new Pedido(id, cliente, productos);
                }
            }
    
            // Obtener productos asociados
            String queryProductos = "SELECT producto_id FROM pedido_productos WHERE pedido_id = ?";
            try (PreparedStatement stmtProd = connection.prepareStatement(queryProductos)) {
                stmtProd.setInt(1, id);
                try (ResultSet rsProd = stmtProd.executeQuery()) {
                    while (rsProd.next()) {
                        productos.add(new Producto(rsProd.getInt("producto_id")));
                    }
                }
            }
    
            if (pedido != null) {
                pedido.setProductos(productos);
            }
    
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return pedido;
    }

    @Override
public List<Pedido> obtenerTodos() {
    List<Pedido> pedidos = new ArrayList<>();
    String query = "SELECT id FROM pedidos";

    try (Connection connection = ConnectionDB.getInstancia().getConexion();
         Statement stmt = connection.createStatement();
         ResultSet rs = stmt.executeQuery(query)) {

        while (rs.next()) {
            int id = rs.getInt("id");
            Pedido pedido = obtenerPorId(id); // Cada llamada maneja su propia conexión
            if (pedido != null) {
                pedidos.add(pedido);
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return pedidos;
}


    @Override
    public void actualizar(Pedido pedido) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            // Actualizar cliente en el pedido
            String query = "UPDATE pedidos SET cliente_id = ? WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, pedido.getCliente().getId());
            stmt.setInt(2, pedido.getId());
            stmt.executeUpdate();

            // Eliminar productos previos
            String deleteProductos = "DELETE FROM pedido_productos WHERE pedido_id = ?";
            PreparedStatement stmtDelete = connection.prepareStatement(deleteProductos);
            stmtDelete.setInt(1, pedido.getId());
            stmtDelete.executeUpdate();

            // Insertar nuevos productos
            String queryProductos = "INSERT INTO pedido_productos (pedido_id, producto_id) VALUES (?, ?)";
            PreparedStatement stmtProd = connection.prepareStatement(queryProductos);
            for (Producto producto : pedido.getProductos()) {
                stmtProd.setInt(1, pedido.getId());
                stmtProd.setInt(2, producto.getId());
                stmtProd.executeUpdate();
            }

            System.out.println("Pedido actualizado con ID: " + pedido.getId());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection connection = ConnectionDB.getInstancia().getConexion()) {
            // Eliminar productos asociados
            String deleteProductos = "DELETE FROM pedido_productos WHERE pedido_id = ?";
            PreparedStatement stmtProd = connection.prepareStatement(deleteProductos);
            stmtProd.setInt(1, id);
            stmtProd.executeUpdate();

            // Eliminar pedido
            String query = "DELETE FROM pedidos WHERE id = ?";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, id);
            stmt.executeUpdate();

            System.out.println("Pedido eliminado con ID: " + id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
