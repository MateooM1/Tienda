package com.ejemplo.model;

import java.util.List;

public class Pedido {
    private int id;
    private Cliente cliente;  // Relación con la clase Cliente
    private List<Producto> productos; // Relación con la clase Producto

    public Pedido(int id, Cliente cliente, List<Producto> productos) {
        this.id = id;
        this.cliente = cliente;
        this.productos = productos;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    @Override
    public String toString() {
        return "Pedido{id=" + id + ", cliente=" + cliente.getNombre() + ", productos=" + productos + "}";
    }
}
